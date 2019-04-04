package gameobject.renderable.player;

import gameengine.gamedata.PlayerData;
import gameengine.physics.*;
import gameengine.rendering.animation.Animator;
import gameobject.GameObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.item.Item;
import gameobject.renderable.player.overworld.PlayerIdleAnimation;
import gameobject.renderable.player.overworld.PlayerWalkingAnimation;
import gameobject.renderable.player.sidescrolling.*;
import gamescreen.gameplay.GamePlayScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.CopyOnWriteArrayList;

import static input.listeners.Key.KeyCodeMeta.*;

public class Player extends RenderablePhysicsObject implements Interactable {
    //region <Variables>
    private PlayerData playerData;
    private PlayerState playerState;
    private CopyOnWriteArrayList<Item> items;
    private double rotation = 0;
    /*
    * Key codes in relation to x and y:
    * 0 = -x
    * 1 =  x
    * 2 = -y
    * 3 =  y
    */
    private final int[] keys = new int[]{LEFT, RIGHT, UP, DOWN};
    private boolean[] keyFlag = new boolean[]{false,false,false,false};
    private int dimension;          // 2 = x movement; 4 = x,y movement
    private boolean crouch = false;
    private boolean crouchSet = true;
    public boolean grounded;
    private boolean requesting;     //Use for requesting interactions

    public enum PlayerState {
        sideScroll,
        asleep,
        overWorld
    }
    //endregion

    public Player(int x, int y, DrawLayer drawLayer, PlayerData playerData) {
        super(x, y, "/assets/player/TeddySilhouette.png", drawLayer, 4);
        //PlayerData
        this.playerData = playerData;
        items = new CopyOnWriteArrayList<>();
        items = playerData.getInventory();
        //Animator
        animator = new Animator(this);
        animator.addAnimation("Walking", new PlayerWalkingAnimation(playerData.getImageDirectory()));
        animator.addAnimation("Idle", new PlayerIdleAnimation(playerData.getImageDirectory()));
        animator.addAnimation("SS_Idle_Left", new PlayerSSIdleAnimationLeft(playerData.getImageDirectory()));
        animator.addAnimation("SS_Idle_Right", new PlayerSSIdleAnimationRight(playerData.getImageDirectory()));
        animator.addAnimation("SS_Running_Left", new PlayerSSRunningAnimationLeft(playerData.getImageDirectory()));
        animator.addAnimation("SS_Running_Right", new PlayerSSRunningAnimationRight(playerData.getImageDirectory()));
        //animator.addAnimation("SS_Crouch",new PlayerSSCrouchingAnimation(playerData.getImageDirectory()));
        //Interactable
        requesting = false;
    }

    //region <Getters and Setters>
    public PlayerState getState() {
        return playerState;
    }

    public void setState(PlayerState ps) {
        //TODO: Implement error checking
        switch (ps) {
            case overWorld:
                Debug.log(DebugEnabler.PLAYER_STATUS,"Player-State: overWorld");
                speed = 4;
                width = 100;
                height = 100;
                animator.setAnimation("Idle");
                playerState = ps;
                dimension = 4;
                break;
            case asleep:
                Debug.log(DebugEnabler.PLAYER_STATUS,"Player-State: asleep");
                playerState = ps;
                dimension = 0;
                break;
            case sideScroll:
                Debug.log(DebugEnabler.PLAYER_STATUS,"Player-State: sideScroll");
                speed = 4;
                rotation = 0;
                animator.setAnimation("SS_Idle_Right");
                playerState = ps;
                dimension = 2;
                break;
        }
    }
    //endregion

    //region <Update and Draw>
    @Override
    public void update() {
        calculateMove();
        setMovementAnimation();
    }

    private void calculateMove() {
        switch(playerState) {
            case overWorld: // y movement
                if(keyFlag[2] && !keyFlag[3])
                    motion.y = -speed;
                if(!keyFlag[2] && keyFlag[3])
                    motion.y = speed;

            case sideScroll: // x movement
                if(keyFlag[0] && !keyFlag[1])
                     motion.x = -speed;
                if(!keyFlag[0] && keyFlag[1])
                    motion.x = speed;
                break;
        }
    }

    public void draw(Graphics2D graphics2D) {
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        graphics2D.setComposite(alphaComposite);
        if(animator != null){
            animator.animate();
        }
        Debug.drawRect(DebugEnabler.RENDERABLE_LOG,graphics2D,
                new Rectangle2D.Double(x, y, width, height));

        Graphics2D g2 = (Graphics2D) graphics2D.create();
        g2.rotate(rotation, x + (width / 2.0), y + (height / 2.0));
        g2.drawImage(image, x, y, null);
        g2.dispose();
    }
    //endregion

    //region <Support functions>

    private void setMovementAnimation() {
        switch(playerState){
            case sideScroll:
                switch(animator.getCurrentAnimation().getName()){
                    case "SS_Running_Right":
                        if(grounded) {
                            if (motion.x < 0.05) {
                                if (motion.x > -0.05) animator.setAnimation("SS_Idle_Right");
                                else animator.setAnimation("SS_Running_Left");
                            }
                        } else { /* Jumping or falling animation */}
                        break;
                    case "SS_Running_Left":
                        if(grounded) {
                            if (motion.x > -0.05) {
                                if (motion.x < 0.05) animator.setAnimation("SS_Idle_Left");
                                else animator.setAnimation("SS_Running_Right");
                            }
                        } else { /* Jumping or falling animation */}
                        break;
                    case "SS_Idle_Right":
                    case "SS_Idle_Left":
                        if(grounded) {
                            if (motion.x < -0.05)
                                animator.setAnimation("SS_Running_Left");
                            else if (motion.x > 0.05)
                                animator.setAnimation("SS_Running_Right");
                        } else { /* Jumping or falling animation */}
                        break;
                }
                break;
            case overWorld:
                if (motion.x != 0.0 || motion.y != 0.0) {
                    rotation = getVelocity().direction();
                    if(!animator.getCurrentAnimationName().equals("Walking"))
                        animator.setAnimation("Walking");
                } else {
                    if(!animator.getCurrentAnimationName().equals("Idle"))
                        animator.setAnimation("Idle");
                }
                break;
        }

    }

    public void handleKeyPress(KeyEvent e) {
        switch (playerState) {
            case sideScroll:
                if (e.getKeyCode() == JUMP && grounded) { // JUMP
                    motion.add(0, -7);
                    grounded = false;
                } else if(e.getKeyCode() == DOWN && !crouch){ // CROUCH
                    crouch = true;
                    crouchSet = false;
                }
            case overWorld:
                if(e.getKeyCode() == SPRINT) { // SPRINT
                    speed = 8;
                } else if(modifyKeyFlag(e, true)){
                    //DO stuff on unique movement key press if you want
                }
                break;
        }
    }

    public void handleKeyReleased(KeyEvent e) {
        switch (getState()) {
            case sideScroll:
                if(e.getKeyCode() == DOWN && crouch){
                    Debug.log(DebugEnabler.PLAYER_STATUS,"CROUCHING RELEASE");
                    crouch = false;
                    crouchSet = false;
                }
            case overWorld:
                if(e.getKeyCode() == SPRINT) {
                    speed = 4;
                } else if(modifyKeyFlag(e, false)){
                    //DO stuff on unique movement key release if you want
                }
                break;
        }

    }

    private boolean modifyKeyFlag(KeyEvent e, boolean state){
        for (int i = 0; i < dimension; i++){
            if(e.getKeyCode() == keys[i] && keyFlag[i] != state){
                keyFlag[i] = state;
                setMovementAnimation();
                return true;
            }
        } return false;
    }

    public void addItem(Item i){
        playerData.addItem(i);
    }
    //endregion

    //region <Interactable>
    @Override
    public Rectangle getRequestArea() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void setRequesting(boolean isRequesting) {
        requesting = isRequesting;
    }

    @Override
    public boolean isRequesting() {
        return requesting;
    }

    @Override
    public boolean action(GameObject g) {
        return false;
    }
    //endregion

    //region <GameObject Overrides>
    @Override
    public boolean setActive(GamePlayScreen screen){
        if(super.setActive(screen)){
            screen.interactables.add(this);
            return true;
        }return false;
    }

    @Override
    public boolean setInactive(GamePlayScreen screen){
        if(super.setInactive(screen)){
            screen.interactables.remove(this);
            return true;
        }return false;
    }

    @Override
    public void addToScreen(GamePlayScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        screen.interactables.remove(this);
        if(isActive) screen.interactables.add(this);
    }
    //endregion
}
