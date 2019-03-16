package gameobject.renderable.enemy;

import _test.Square;
import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsMeta;
import gameengine.physics.PhysicsVector;
import gameengine.rendering.animation.Animator;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;
import gameobject.renderable.player.Player;
import gameobject.renderable.player.overworld.PlayerIdleAnimation;
import gameobject.renderable.player.overworld.PlayerWalkingAnimation;
import gameobject.renderable.player.sidescrolling.PlayerSSIdleAnimation;
import gamescreen.GameScreen;
import main.utilities.AssetLoader;
import main.utilities.Debug;
import main.utilities.DebugEnabler;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Minion extends Enemy implements Kinematic {

    protected MinionState state;
    private int speed = 1;
    protected PhysicsVector accel = new PhysicsVector(0,1);
    protected PhysicsVector movement = new PhysicsVector(0, 0);


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
        return true;
    }

    @Override
    public void update() {

    }

    @Override
    public PhysicsVector getVelocity() {
        int gravSign = PhysicsMeta.Gravity != 0 ? 1 : 0;
        PhysicsVector pV = movement.add(new PhysicsVector(0,gravSign)).mult(accel);
        double y = pV.y;
        y = y < 1 && y > .5 ? 1 : y;
        y = y < -.5 && y > -1 ? -1 : y;
        return new PhysicsVector(pV.x,y);
    }

    @Override
    public void setVelocity(PhysicsVector pv) {
        movement = pv;
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

    @Override
    public boolean setActive(GameScreen screen){
        if(super.setActive(screen)){
            screen.kinematics.add(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean setInactive(GameScreen screen){
        if(super.setInactive(screen)){
            screen.kinematics.remove(this);
            return true;
        }
        return false;
    }

    @Override
    public void addToScreen(GameScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);

        if(isActive) {
            screen.kinematics.add(this);
        }
    }

    @Override
    public void changeState(){}

    protected BufferedImage flipVertical(BufferedImage src){
        AffineTransform tx=AffineTransform.getScaleInstance(-1.0,1.0);  //scaling
        tx.translate(-src.getWidth(),0);  //translating
        AffineTransformOp tr=new AffineTransformOp(tx,null);  //transforming

        return tr.filter(src, null);  //filtering
    }
}
