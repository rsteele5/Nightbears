package gameobject.renderable.player;

import gameengine.gamedata.PlayerData;
import gameengine.physics.*;
import gameengine.rendering.animation.Animator;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.enemy.Enemy;
import gameobject.renderable.enemy.Minion;
import gameobject.renderable.enemy.Walker;
import gameobject.renderable.item.Item;
import gameobject.renderable.item.weapon.WeaponType;
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

public class Player extends RenderablePhysicsObject {
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
    private boolean[] keyFlag = new boolean[]{false, false, false, false};
    private int dimension;          // 2 = x movement; 4 = x,y movement
    private PhysicsVector movement;
    private boolean crouch = false;
    private boolean grounded = false;
    private boolean hitStun = false;
    private int hitStunFrames = 45;
    private int hitStunFrameCounter = 0;
    private int hitStunJump = 13;
    private boolean considerArc = false;
    private boolean requesting;     //Use for requesting interactions
    private boolean facing;//false is right, true is left


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
        animator.addAnimation("SS_Crawl_Left", new PlayerSSCrawlingAnimationLeft(playerData.getImageDirectory()));
        animator.addAnimation("SS_Crawl_Right", new PlayerSSCrawlingAnimationRight(playerData.getImageDirectory()));
        animator.addAnimation("SS_Crouch_Left", new PlayerSSCrouchingAnimationLeft(playerData.getImageDirectory()));
        animator.addAnimation("SS_Crouch_Right", new PlayerSSCrouchingAnimationRight(playerData.getImageDirectory()));
        animator.addAnimation("SS_Sword_Attack_Right", new PlayerSSSwordAttackAnimationRight(playerData.getImageDirectory()));
        animator.addAnimation("SS_Sword_Attack_Left", new PlayerSSSwordAttackAnimationLeft(playerData.getImageDirectory()));
        animator.addAnimation("SS_Spear_Attack_Right", new PlayerSSSpearAttackAnimationRight(playerData.getImageDirectory()));
        animator.addAnimation("SS_Spear_Attack_Left", new PlayerSSSpearAttackAnimationLeft(playerData.getImageDirectory()));
        animator.addAnimation("SS_Unarmed_Attack_Right", new PlayerSSUnarmedAttackAnimationRight(playerData.getImageDirectory()));
        animator.addAnimation("SS_Unarmed_Attack_Left", new PlayerSSUnarmedAttackAnimationLeft(playerData.getImageDirectory()));
        animator.addAnimation("SS_Club_Attack_Right", new PlayerSSClubAttackAnimationRight(playerData.getImageDirectory()));
        animator.addAnimation("SS_Club_Attack_Left", new PlayerSSClubAttackAnimationLeft(playerData.getImageDirectory()));
        animator.addAnimation("SS_HitStun_Right", new PlayerSSHitStunAnimationRight(playerData.getImageDirectory()));
        animator.addAnimation("SS_HitStun_Left", new PlayerSSHitStunAnimationLeft(playerData.getImageDirectory()));
        //Interactable
        requesting = false;
        movement = new PhysicsVector(0, 0);
    }

    //region <Getters and Setters>
    public PlayerState getState() {
        return playerState;
    }

    public void setState(PlayerState ps) {
        //TODO: Implement error checking
        switch (ps) {
            case overWorld:
                Debug.log(DebugEnabler.PLAYER_STATUS, "Player-State: overWorld");
                speed = 4;
                width = 100;
                height = 100;
                animator.setAnimation("Idle");
                playerState = ps;
                dimension = 4;
                break;
            case asleep:
                Debug.log(DebugEnabler.PLAYER_STATUS, "Player-State: asleep");
                playerState = ps;
                dimension = 0;
                break;
            case sideScroll:
                Debug.log(DebugEnabler.PLAYER_STATUS, "Player-State: sideScroll");
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
        if (!grounded && motion.y < 1 && motion.y >= 0) {
            if (considerArc) considerArc = false;
            else grounded = true;
        }
        calculateMove();
        setMovementAnimation();
    }

    private void calculateMove() {
        switch (playerState) {
            case overWorld: // y movement
                if (keyFlag[2] && !keyFlag[3])
                    motion.y = -speed;
                else if (!keyFlag[2] && keyFlag[3])
                    motion.y = speed;
                else motion.y = 0;

            case sideScroll: // x movement
                if (hitStun) {
                    hitStunFrameCounter--;
                    if (alpha == 1) alpha = 0;
                    else if (alpha == 0) alpha = 1;
                    if (hitStunFrameCounter > 0) {
                        if (facing) {
                            if (animator.getCurrentAnimationName() != "SS_HitStun_Left")
                                animator.setAnimation("SS_HitStun_Left");
                            motion.x = 5;
                        } else {
                            if (animator.getCurrentAnimationName() != "SS_HitStun_Right")
                                animator.setAnimation("SS_HitStun_Right");
                            motion.x = -5;
                        }
                    } else {
                        hitStun = false;
                        motion.x = 0;
                        alpha = 1;
                        if (facing) {
                            animator.setAnimation("SS_Idle_Left");
                        } else {
                            animator.setAnimation("SS_Idle_Right");
                        }
                    }
                } else if (!isAttacking() && keyFlag[0] && !keyFlag[1]) {
                    motion.x = -speed;
                    facing = true;
                } else if (!isAttacking() && !keyFlag[0] && keyFlag[1]) {
                    motion.x = speed;
                    facing = false;
                } else motion.x = 0;
                break;
        }
    }

    public void draw(Graphics2D graphics2D) {
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        graphics2D.setComposite(alphaComposite);
        if (animator != null) {
            animator.animate();
        }
        Debug.drawRect(DebugEnabler.RENDERABLE_LOG, graphics2D,
                new Rectangle2D.Double(x, y, width, height));

        Graphics2D g2 = (Graphics2D) graphics2D.create();
        g2.rotate(rotation, x + (width / 2.0), y + (height / 2.0));
        g2.drawImage(image, x, y, null);
        g2.dispose();
    }
    //endregion

    //region <Support functions>

    public boolean isAttacking() {
        return animator.getCurrentAnimationName() == "SS_Sword_Attack_Right" ||
                animator.getCurrentAnimationName() == "SS_Sword_Attack_Left" ||
                animator.getCurrentAnimationName() == "SS_Spear_Attack_Right" ||
                animator.getCurrentAnimationName() == "SS_Spear_Attack_Left" ||
                animator.getCurrentAnimationName() == "SS_Unarmed_Attack_Right" ||
                animator.getCurrentAnimationName() == "SS_Unarmed_Attack_Left" ||
                animator.getCurrentAnimationName() == "SS_Club_Attack_Right" ||
                animator.getCurrentAnimationName() == "SS_Club_Attack_Left";
    }

    public int getWeaponDamage() {
        return playerData.getWeaponDamage();
    }

    public int getHealth() {
        return playerData.getCurrentHealth();
    }


    private void setMovementAnimation() {
        switch (playerState) {
            case sideScroll:
                switch (animator.getCurrentAnimationName()) {
                    case "SS_Idle_Right":
                    case "SS_Idle_Left":
                        if (grounded) {
                            if (motion.x < -0.05)
                                animator.setAnimation("SS_Running_Left");
                            else if (motion.x > 0.05)
                                animator.setAnimation("SS_Running_Right");
                        } else { /* Jumping or falling animation */}
                        break;
                    case "SS_Crouch_Left":
                    case "SS_Crouch_Right":
                        if (grounded) {
                            if (motion.x < -0.05)
                                animator.setAnimation(crouch ? "SS_Crawl_Left" : "SS_Running_Left");
                            else if (motion.x > 0.05)
                                animator.setAnimation(crouch ? "SS_Crawl_Right" : "SS_Running_Right");
                            else if (!crouch) { //Not Crouching or Moving
                                if (facing) animator.setAnimation("SS_Idle_Left");
                                else animator.setAnimation("SS_Idle_Right");
                            }
                        }
                        break;
                    case "SS_Running_Left":
                    case "SS_Crawl_Left":
                        if (grounded && !hitStun) {
                            if (motion.x > -0.05) {
                                if (motion.x < 0.05) animator.setAnimation(crouch ? "SS_Crouch_Left" : "SS_Idle_Left");
                                else animator.setAnimation(crouch ? "SS_Crawl_Right" : "SS_Running_Right");
                            } else {
                                if (crouch && animator.isCurrentAnimationName("SS_Running_Left")) {
                                    animator.setAnimation("SS_Crawl_Left");
                                } else if (!crouch && animator.isCurrentAnimationName("SS_Crawl_Left")) {
                                    animator.setAnimation("SS_Running_Left");
                                }
                            }
                        } else { /* Jumping or falling animation */}
                        break;
                    case "SS_Running_Right":
                    case "SS_Crawl_Right":
                        if (grounded && !hitStun) {
                            if (motion.x < 0.05) {
                                if (motion.x > -0.05)
                                    animator.setAnimation(crouch ? "SS_Crouch_Right" : "SS_Idle_Right");
                                else animator.setAnimation(crouch ? "SS_Crawl_Left" : "SS_Running_Left");
                            } else {
                                if (crouch && animator.isCurrentAnimationName("SS_Running_Right")) {
                                    animator.setAnimation("SS_Crawl_Right");
                                } else if (!crouch && animator.isCurrentAnimationName("SS_Crawl_Right")) {
                                    animator.setAnimation("SS_Running_Right");
                                }
                            }
                        } else { /* Jumping or falling animation */}
                        break;
                    case "SS_Club_Attack_Right":
                        if (animator.getCurrentAnimation().getFrameToDisplay() == 6) {
                            animator.setAnimation("SS_Idle_Right");
                            translate(73, 0);
                        }
                        break;
                    case "SS_Sword_Attack_Right":
                    case "SS_Spear_Attack_Right":
                    case "SS_Unarmed_Attack_Right":
                        if (animator.getCurrentAnimation().getFrameToDisplay() == 6) {
                            animator.setAnimation("SS_Idle_Right");
                        }
                        break;
                    case "SS_Sword_Attack_Left":
                        if (animator.getCurrentAnimation().getFrameToDisplay() == 6) {
                            if (facing) {
                                animator.setAnimation("SS_Idle_Left");
                                translate(176, 0);
                            } else animator.setAnimation("SS_Idle_Right");
                        }
                        break;
                    case "SS_Spear_Attack_Left":
                        if (animator.getCurrentAnimation().getFrameToDisplay() == 6) {
                            animator.setAnimation("SS_Idle_Left");
                            translate(176, 0);
                        }
                        break;
                    case "SS_Club_Attack_Left":
                        if (animator.getCurrentAnimation().getFrameToDisplay() == 6) {
                            animator.setAnimation("SS_Idle_Left");
                            translate(73, 0);
                        }
                        break;
                    case "SS_Unarmed_Attack_Left":
                        if (animator.getCurrentAnimation().getFrameToDisplay() == 6) {
                            animator.setAnimation("SS_Idle_Left");
                            translate(45, 0);
                        }
                        break;
                }
                break;
            case overWorld:
                if (motion.x != 0.0 || motion.y != 0.0) {
                    rotation = getVelocity().direction();
                    if (!animator.getCurrentAnimationName().equals("Walking"))
                        animator.setAnimation("Walking");
                } else {
                    if (!animator.getCurrentAnimationName().equals("Idle"))
                        animator.setAnimation("Idle");
                }
                break;
        }
    }

    public void handleKeyPress(KeyEvent e) {
        switch (playerState) {
            case sideScroll:
                if (grounded) {
                    if (e.getKeyCode() == JUMP && !hitStun) { // JUMP
                        motion = motion.add(0, -20);
                        considerArc = true;
                    } else if (e.getKeyCode() == DOWN && !crouch && !hitStun) { // CROUCH
                        crouch = true;
                        translate(0, 37);
                        if (facing) animator.setAnimation("SS_Crouch_Left");
                        else animator.setAnimation("SS_Crouch_Right");

                    } else if (!isAttacking() && e.getKeyCode() == ATTACK && !hitStun) {
                        if (facing) {// facing left
                            if (playerData.getPlayerEquipment().get(3) == null) {
                                animator.setAnimation("SS_Unarmed_Attack_Left");
                                translate(-45, 0);
                            } else if (playerData.getPlayerEquipment().get(3).getType() == WeaponType.Sword.ordinal()) {
                                animator.setAnimation("SS_Sword_Attack_Left");
                                translate(-176, 0);
                            } else if (playerData.getPlayerEquipment().get(3).getType() == WeaponType.Spear.ordinal()) {
                                animator.setAnimation("SS_Spear_Attack_Left");
                                translate(-176, 0);
                            } else if (playerData.getPlayerEquipment().get(3).getType() == WeaponType.Club.ordinal()) {
                                animator.setAnimation("SS_Club_Attack_Left");
                                translate(-73, -30);
                            }
                        } else {// facing right
                            if (playerData.getPlayerEquipment().get(3) == null) {
                                animator.setAnimation("SS_Unarmed_Attack_Right");
                            } else if (playerData.getPlayerEquipment().get(3).getType() == WeaponType.Sword.ordinal()) {
                                animator.setAnimation("SS_Sword_Attack_Right");
                            } else if (playerData.getPlayerEquipment().get(3).getType() == WeaponType.Spear.ordinal()) {
                                animator.setAnimation("SS_Spear_Attack_Right");
                            } else if (playerData.getPlayerEquipment().get(3).getType() == WeaponType.Club.ordinal()) {
                                animator.setAnimation("SS_Club_Attack_Right");
                                translate(-73, -30);
                            }
                        }
                    }
                }
            case overWorld:
                if (e.getKeyCode() == SPRINT) { // SPRINT
                    speed = 8;
                } else if (modifyKeyFlag(e, true)) {
                    //DO stuff on unique movement key press if you want
                }
                break;
        }
    }

    public void handleKeyReleased(KeyEvent e) {
        switch (getState()) {
            case sideScroll:
                if (e.getKeyCode() == DOWN && crouch) {
                    crouch = false;
                    translate(0, -37);
                }
            case overWorld:
                if (e.getKeyCode() == SPRINT) {
                    speed = 4;
                } else if (modifyKeyFlag(e, false)) {
                    //DO stuff on unique movement key release if you want
                }
                break;
        }

    }

    private boolean modifyKeyFlag(KeyEvent e, boolean state) {
        for (int i = 0; i < dimension; i++) {
            if (e.getKeyCode() == keys[i] && keyFlag[i] != state) {
                keyFlag[i] = state;
                setMovementAnimation();
                return true;
            }
        }
        return false;
    }

    public void addItem(Item i) {
        playerData.addItem(i);
    }
    //endregion

    //region <Interactable>

    public Rectangle getRequestArea() {
        return new Rectangle(x, y, width, height);
    }

    public void setRequesting(boolean isRequesting) {
        requesting = isRequesting;
    }

    public boolean isRequesting() {
        return requesting;
    }
    //endregion

    //region <GameObject Overrides>
    @Override
    public boolean setActive(GamePlayScreen screen) {
        if (super.setActive(screen)) {
            // set active stuff here
            return true;
        }
        return false;
    }

    @Override
    public boolean setInactive(GamePlayScreen screen) {
        if (super.setInactive(screen)) {
            // set inactive stuff here
            return true;
        }
        return false;
    }

    @Override
    public void addToScreen(GamePlayScreen screen, boolean isActive) {
        super.addToScreen(screen, isActive);
        // add stuff to Screen here
    }

    @Override
    public boolean collide(Collidable c2) {
        if (c2 instanceof Minion) {
            if (!isAttacking() && !hitStun) {
                Debug.error(true, "We took some damage!");
                hitStun = true;
                hitStunFrameCounter = hitStunFrames;
                motion.y = -hitStunJump;
                if (playerData.getCurrentArmor() > 0) {
                    playerData.modifyCurrentArmor(-((Enemy) c2).getDamage());
                } else {
                    playerData.modifyCurrentHealth(-((Enemy) c2).getDamage());
                    if (playerData.getCurrentHealth() < 1) {
                        //kill;
                    }
                }

                //TODO: Take damage like this -> playerData.modifyCurrentHealth(-c2.getDamage());
            }
        }
        return true;
    }

    public void hit(int i){
        if(!hitStun){
            hitStun = true;
            hitStunFrameCounter = hitStunFrames;
            motion.y = -hitStunJump;
            if (playerData.getCurrentArmor() > 0)  playerData.modifyCurrentArmor(-i);
            else playerData.modifyCurrentHealth(-i);
        }
    }
}
