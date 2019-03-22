package gameobject.renderable.player;

import gameengine.GameEngine;
import gameengine.gamedata.GameData;
import gameengine.gamedata.PlayerData;
import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsMeta;
import gameengine.physics.PhysicsVector;
import gameengine.rendering.animation.Animator;
import gameobject.renderable.RenderableObject;
import gameobject.renderable.item.ItemComparator;
import gameobject.renderable.item.*;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.item.armor.ArmorBuilder;
import gameobject.renderable.item.armor.ArmorType;
import gameobject.renderable.item.weapon.WeaponBuilder;
import gameobject.renderable.item.weapon.WeaponType;
import gameobject.renderable.player.overworld.PlayerIdleAnimation;
import gameobject.renderable.player.overworld.PlayerWalkingAnimation;
import gameobject.renderable.player.sidescrolling.PlayerSSCrouchingAnimation;
import gameobject.renderable.player.sidescrolling.PlayerSSIdleAnimation;
import gamescreen.GameScreen;
import main.Game;
import main.utilities.AssetLoader;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player extends RenderableObject implements Kinematic {

    private int speed = 1;
    private PlayerData playerData;
    private CopyOnWriteArrayList<Item> items;
    private PhysicsVector moveState = new PhysicsVector(1, 1);
    private PhysicsVector magnitude = new PhysicsVector(0, 0);
    private final int[] ssKeys = new int[]{68, 65};
    private final int[] owKeys = new int[]{68, 65, 83, 87};
    private boolean crouch = false;
    private boolean crouchSet = true;
    public boolean interaction = false;
    private int movFlag = 0;
        private double moveFactor = 1;
    private double rotation = 0;
    /*
    0b1     =   right
    0b10    =   left
    0b100   =   down
    0b1000  =   up
     */
    public boolean grounded;
    private PlayerState playerState;

    public enum PlayerState {
        sideScroll,
        asleep,
        overWorld
    }

    public void draw(Graphics2D graphics2D) {
        if(animator != null){
            animator.animate();
        }

        Graphics2D g2 = (Graphics2D) graphics2D.create();
        g2.rotate(rotation, x + (width / 2.0), y + (height / 2.0));
        g2.drawImage(image, x, y, null);
        g2.dispose();
    }

    public Player(int x, int y, DrawLayer drawLayer, PlayerData playerData) {
        //TODO: Set to the random bear selection.
        super(x, y, "/assets/player/TeddySilhouette.png", drawLayer);
        playerState = PlayerState.asleep;
        //TODO:Review
        this.playerData = playerData;
        items = new CopyOnWriteArrayList<>();
        items = playerData.getInventory();
        //initializeItems()

        animator = new Animator(this);
        animator.addAnimation("Walking", new PlayerWalkingAnimation());
        animator.addAnimation("Idle", new PlayerIdleAnimation());
        animator.addAnimation("SS_Idle", new PlayerSSIdleAnimation());
        animator.addAnimation("SS_Crouch",new PlayerSSCrouchingAnimation());
    }


    @Override
    public void update() {
        if(interaction) Debug.log(DebugEnabler.PLAYER_STATUS,"Interaction Available! Act now!");
        if(playerState == PlayerState.sideScroll && !crouchSet  ){
            crouchSet = true;
            if(crouch) {
                y = y + image.getHeight()/2;
                animator.setAnimation("SS_Crouch");
            }
            else {
              //  image = AssetLoader.load("/assets/testAssets/square2.png");
             //   imagePath = "/assets/testAssets/square2.png";
                animator.setAnimation("SS_Idle");
                y = y - image.getHeight()/2;

             //   animator.setAnimation("SS_Idle");
            }
        }
        if (playerState == PlayerState.overWorld) {
            if (magnitude.x != 0.0 || magnitude.y != 0.0) {
                rotation = getVelocity().direction();
                if(!animator.getCurrentAnimationName().equals("Walking"))
                    animator.setAnimation("Walking");
            } else {
                if(!animator.getCurrentAnimationName().equals("Idle"))
                    animator.setAnimation("Idle");
            }
        }
    }

    //region <Physics functions>
    private void setVelocity(int flags) {
        int x1 = 0b1 & flags;
        x1 += (((0b10 & flags) / 0b10) * -1);
        int y1 = ((0b100 & flags) / 0b100);
        y1 += (((0b1000 & flags) / 0b1000) * -1);
        setVelocity(new PhysicsVector(x1, y1));
    }

    private void calculateMove(KeyEvent e, int[] keys) {
        for (int i = 0; i < keys.length; i++)
            movFlag += e.getKeyCode() == keys[i] && ((movFlag & (int) Math.pow(2, i)) == 0) ? (int) Math.pow(2, i) : 0;
        setVelocity(movFlag);
    }

    private void calculateRelease(KeyEvent e, int[] keys) {
        for (int i = 0; i < keys.length; i++)
            movFlag -= e.getKeyCode() == keys[i] && ((movFlag & (int) Math.pow(2, i)) == Math.pow(2, i)) ? (int) Math.pow(2, i) : 0;
        setVelocity(movFlag);
    }

    public void move(KeyEvent e) {
        switch (getState()) {

            case sideScroll:
                if (e.getKeyCode() == 32 && grounded) {
                    int sign = PhysicsMeta.AntiGravity ? -1 : 1;
                    setAcceleration(getAcceleration().add(new PhysicsVector(0, -7 * sign)));
                    grounded = false;
                }
                if(e.getKeyCode() == 83 && !crouch){
                    Debug.log(DebugEnabler.PLAYER_STATUS,"CROUCHING");
                    crouch = true;
                    crouchSet = false;
                }
                if(e.getKeyCode() == 16) {
                    moveFactor = 2.5;
                }
                if (PhysicsMeta.Gravity == 0) calculateMove(e, owKeys);
                else calculateMove(e, ssKeys);

                break;

            case overWorld:
                calculateMove(e, owKeys);
                break;
        }

    }

    public void moveRelease(KeyEvent e) {
        switch (getState()) {
            case sideScroll:
                if(e.getKeyCode() == 83 && crouch){
                    Debug.log(DebugEnabler.PLAYER_STATUS,"CROUCHING RELEASE");
                    crouch = false;
                    crouchSet = false;

                }
                if(e.getKeyCode() == 16) {
                    moveFactor = 1;
                }
                if (PhysicsMeta.Gravity == 0) calculateRelease(e, owKeys);
                else calculateRelease(e, ssKeys);

                break;

            case overWorld:
                calculateRelease(e, owKeys);
                break;
        }

    }

    @Override
    public boolean isStatic() {
        return false;
    }

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
    public void setVelocity(PhysicsVector pv) {
        if(pv.x != 0 && pv.y != 0){
            //TODO: this is broken. Needs to make speed constant in all directions
//            pv.x = (pv.x / Math.sqrt(2));
//            pv.y = (pv.y / Math.sqrt(2));
        }
        magnitude = pv.mult(speed);
    }

    @Override
    public PhysicsVector getAcceleration() {
        return moveState;
    }

    @Override
    public void setAcceleration(PhysicsVector pv) {
        moveState = pv;
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    @Override
    public void addToScreen(GameScreen screen, boolean isActive) {
        super.addToScreen(screen, isActive);
        if (isActive) screen.kinematics.add(this);
    }

    /**
     * Reset player coordinates and acceleration.
     */
    public void reset() {
        x = 50;
        y = 50;
        moveState = new PhysicsVector(1, 1);
    }

    public PlayerState getState() {
        return playerState;
    }

    /**
     * Returns true or false depending on the acceptance of the state transition.
     */
    public boolean setState(PlayerState ps) {
        //TODO: Implement error checking
        switch (ps) {
            case overWorld:
                Debug.log(DebugEnabler.PLAYER_STATUS,"Player-State: overWorld");
                speed = 3;
                width = 100;
                height = 100;
                animator.setAnimation("Idle");
                playerState = ps;
                return true;
            case asleep:
                Debug.log(DebugEnabler.PLAYER_STATUS,"Player-State: asleep");
                playerState = ps;
                return true;
            case sideScroll:
                Debug.log(DebugEnabler.PLAYER_STATUS,"Player-State: sideScroll");
                speed = 1;
                rotation = 0;
                animator.setAnimation("SS_Idle");
                playerState = ps;
                return true;
        }
        return false;
    }
    //endregion

    public void setImage(String imagePath) {
        this.imagePath = imagePath;
    }
}
