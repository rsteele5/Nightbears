package gameobject.renderable.enemy;

import gameengine.physics.PhysicsMeta;
import gameengine.physics.PhysicsVector;
import gameengine.rendering.animation.Animator;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.player.Player;
import gameobject.renderable.player.overworld.PlayerIdleAnimation;
import gameobject.renderable.player.overworld.PlayerWalkingAnimation;
import gameobject.renderable.player.sidescrolling.PlayerSSIdleAnimation;
import main.utilities.AssetLoader;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Minion extends Enemy {

    protected MinionState state;
    private int speed = 1;
    private PhysicsVector accel = new PhysicsVector(1, 1);
    private PhysicsVector movement = new PhysicsVector(0, 0);

    public Minion() {
    }

    public Minion(int x, int y) {
        super(x, y);
    }

    public Minion(int x, int y, DrawLayer layer) {
        super(x, y, layer);
    }

    public Minion(int x, int y, BufferedImage image, DrawLayer layer) {
        super(x, y, image, layer);
    }

    public Minion(int x, int y, String imagePath, DrawLayer layer) {
        super(x, y, imagePath, layer);
    }

    public Minion(int x, int y, String imagePath, DrawLayer layer, float alpha) {
        super(x, y, imagePath, layer, alpha);
    }

    /**
     * Returns true or false depending on the acceptance of the state transition.
     */
    public boolean setState(MinionState state) {

        this.state = state;
        //TODO: Implement error checking
        /*switch (ps) {
            case overWorld:
                Debug.log(DebugEnabler.PLAYER_STATUS,"Player-State: overWorld");
                //speed = 3;
                image = AssetLoader.load("/assets/player/overworld/teddyidleanimation/Overworld-Teddy-Center.png");
                width = image.getWidth();
                height = image.getHeight();
                animator = new Animator(this);
                animator.addAnimation("Walking", new PlayerWalkingAnimation());
                animator.addAnimation("Idle", new PlayerIdleAnimation());
                animator.addAnimation("SS_Idle", new PlayerSSIdleAnimation());
                animator.setAnimation("SS_Idle");
                //playerState = ps;
                return true;
            case asleep:
                Debug.log(DebugEnabler.PLAYER_STATUS,"Player-State: asleep");
                //playerState = ps;
                return true;
            case sideScroll:
                Debug.log(DebugEnabler.PLAYER_STATUS,"Player-State: sideScroll");
                //speed = 1;
                animator = null;
                image = AssetLoader.load("/assets/testAssets/square2.png");
                //imagePath = "/assets/testAssets/square2.png";
                //rotation = 0;
                //playerState = ps;
                return true;
        }*/
        return false;
    }

    public PhysicsVector getVelocity() {
        int gravSign = PhysicsMeta.Gravity != 0 ? 1 : 0;
        PhysicsVector pV = movement.add(new PhysicsVector(0, gravSign)).mult(accel);
        double y = pV.y;
        y = y < 1 && y > .5 ? 1 : y;
        y = y < -.5 && y > -1 ? -1 : y;
        return new PhysicsVector(pV.x, y);
    }

    @Override
    public void setVelocity(PhysicsVector pv) {
        if(pv.x != 0 && pv.y != 0){
            //TODO: this is broken. Needs to make speed constant in all directions
//            pv.x = (pv.x / Math.sqrt(2));
//            pv.y = (pv.y / Math.sqrt(2));
        }
        movement = pv.mult(speed);
    }

    @Override
    public PhysicsVector getAcceleration() {
        return accel;
    }

    @Override
    public void setAcceleration(PhysicsVector pv) {
        accel = pv;
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }
}
