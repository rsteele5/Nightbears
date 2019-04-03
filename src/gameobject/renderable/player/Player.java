package gameobject.renderable.player;

import gameengine.gamedata.PlayerData;
import gameengine.physics.*;
import gameengine.rendering.animation.Animator;
import gameobject.GameObject;
import gameobject.renderable.CollidableRenderable;
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

public class Player extends CollidableRenderable implements Kinematic, Interactable {

    //region <Variables>
    private int speed = 1;
    private PlayerData playerData;
    private PlayerState playerState;
    private CopyOnWriteArrayList<Item> items;
    private PhysicsVector motion = new PhysicsVector(1, 1);
    //private PhysicsVector magnitude = new PhysicsVector(0, 0);
    private double moveFactor = 1;
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
    private int dimension; // 2 = x movement; 4 = x,y movement
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
        super(x, y, "/assets/player/TeddySilhouette.png", drawLayer, 1f);
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
                speed = 3;
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
        if(playerState == PlayerState.sideScroll && !crouchSet  ){
            crouchSet = true;
            if(crouch) {
                position.y += image.getHeight()/2;
                //animator.setAnimation("SS_Crouch");
            }
            else {
                animator.setAnimation("SS_Idle");
                position.y -= image.getHeight()/2;
            }
        }
        if (playerState == PlayerState.overWorld) {
            if (motion.x != 0.0 || motion.y != 0.0) {
                rotation = getVelocity().direction();
                if(!animator.getCurrentAnimationName().equals("Walking"))
                    animator.setAnimation("Walking");
            } else {
                if(!animator.getCurrentAnimationName().equals("Idle"))
                    animator.setAnimation("Idle");
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        graphics2D.setComposite(alphaComposite);
        if(animator != null){
            animator.animate();
        }
        Debug.drawRect(DebugEnabler.RENDERABLE_LOG,graphics2D,
                new Rectangle2D.Double(position.x, position.y, width, height));

        Graphics2D g2 = (Graphics2D) graphics2D.create();
        g2.rotate(rotation, position.x + (width / 2.0), position.y + (height / 2.0));
        g2.drawImage(image, position.x, position.y, null);
        g2.dispose();
    }
    //endregion

    //region <Support functions>

    /*
    0b1     =   right
    0b10    =   left
    0b100   =   down
    0b1000  =   up
     */
    private void setMovementState(int flags) {
        int x1 = 0b1 & flags;
        x1 += (((0b10 & flags) / 0b10) * -1);
        int y1 = ((0b100 & flags) / 0b100);
        y1 += (((0b1000 & flags) / 0b1000) * -1);

        if(playerState == PlayerState.sideScroll) {
            if(x1 == 1 && grounded && animator.getCurrentAnimation().getName() != "SS_Running_Right") {
                animator.setAnimation("SS_Running_Right");
            } else if (x1 == -1 && grounded && animator.getCurrentAnimation().getName() != "SS_Running_Left" ) {
                animator.setAnimation("SS_Running_Left");
            } else if(x1 == 0 && grounded && animator.getCurrentAnimation().getName() == "SS_Running_Left" && animator.getCurrentAnimation().getName() != "SS_Idle_Left") {
                animator.setAnimation("SS_Idle_Left");
            } else if (x1 == 0 && grounded && animator.getCurrentAnimation().getName() == "SS_Running_Right" && animator.getCurrentAnimation().getName() != "SS_Idle_Right") {
                animator.setAnimation("SS_Idle_Right");
            }
        }


        setVelocity(new PhysicsVector(x1, y1));
    }

    private void calculateMove(KeyEvent e) {
        if(modifyKeyFlag(e, true)){
            if(playerState == PlayerState.sideScroll)
                //TODO: Start Here ***************************************************************
            setMovementState(movFlag);
        }

//        for (int i = 0; i < keys.length; i++)
//            movFlag += e.getKeyCode() == keys[i] && ((movFlag & (int) Math.pow(2, i)) == 0) ? (int) Math.pow(2, i) : 0;
//        setMovementState(movFlag);
    }

    private void calculateRelease(KeyEvent e) {
        if(modifyKeyFlag(e, false)){

        }
//        for (int i = 0; i < keys.length; i++)
//            movFlag -= e.getKeyCode() == keys[i] && ((movFlag & (int) Math.pow(2, i)) == Math.pow(2, i)) ? (int) Math.pow(2, i) : 0;
//        setMovementState(movFlag);
    }

    private boolean modifyKeyFlag(KeyEvent e, boolean state){
        for (int i = 0; i < dimension; i++){
            if(e.getKeyCode() == keys[i]){
                keyFlag[i] = state;
                return true;
            }
        } return false;
    }

    public void move(KeyEvent e) {
        switch (getState()) {
            case sideScroll:
                if (e.getKeyCode() == 32 && grounded) { // JUMP
                    setAcceleration(getAcceleration().add(new PhysicsVector(0, -7)));
                    grounded = false;
                }
                if(e.getKeyCode() == 83 && !crouch){ // CROUCH
                    crouch = true;
                    crouchSet = false;
                }
                if(e.getKeyCode() == 16) { // SPRINT
                    moveFactor = 2.5;
                }
                else calculateMove(e);
                break;
            case overWorld:
                calculateMove(e);
                break;
        }

    }

    public void moveRelease(KeyEvent e) {
        switch (getState()) {
            case sideScroll:
                if(e.getKeyCode() == DOWN && crouch){
                    Debug.log(DebugEnabler.PLAYER_STATUS,"CROUCHING RELEASE");
                    crouch = false;
                    crouchSet = false;

                }
                if(e.getKeyCode() == SPRINT) {
                    moveFactor = 1;
                }
                else calculateRelease(e);

                break;

            case overWorld:
                calculateRelease(e);
                break;
        }

    }

    public void addItem(Item i){
        playerData.addItem(i);
    }
    //endregion

    //region <Collidable>
    @Override
    public boolean collide(Collidable c2) {
        return false;
    }
    //endregion

    //region <Kinematics>
    @Override
    public PhysicsVector getVelocity() {
        int gravSign = PhysicsMeta.Gravity != 0 && playerState == PlayerState.sideScroll ? 1 : 0;
        PhysicsVector pV = magnitude.add(new PhysicsVector(0, gravSign)).mult(moveState);
        double y = pV.y;
        y = y < 1 && y > .5 ? 1 : y;
        y = y < -.5 && y > -1 ? -1 : y;
        return new PhysicsVector(pV.x * moveFactor, y);
    }

    @Override
    public void setVelocity(PhysicsVector pv) { magnitude = pv.mult(speed); }

    @Override
    public PhysicsVector getAcceleration() {
        return moveState;
    }

    @Override
    public void setAcceleration(PhysicsVector pv) {
        moveState = pv;
    }
    //endregion

    //region <Interactable>
    @Override
    public Rectangle getRequestArea() {
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
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
            screen.kinematics.add(this);
            screen.interactables.add(this);
            return true;
        }return false;
    }

    @Override
    public boolean setInactive(GamePlayScreen screen){
        if(super.setInactive(screen)){
            screen.kinematics.remove(this);
            screen.interactables.remove(this);
            return true;
        }return false;
    }

    @Override
    public void addToScreen(GamePlayScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        screen.kinematics.remove(this);
        screen.interactables.remove(this);
        if(isActive){
            screen.kinematics.add(this);
            screen.interactables.add(this);
        }
    }
    //endregion
}
