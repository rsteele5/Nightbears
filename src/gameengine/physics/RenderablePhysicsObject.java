//TODO: add to GameObject Package
package gameengine.physics;

import gameobject.renderable.CollidableRenderable;
import gameobject.renderable.DrawLayer;
import gamescreen.gameplay.GamePlayScreen;

public abstract class RenderablePhysicsObject extends CollidableRenderable implements Kinematic {
    protected PhysicsVector motion;
    protected double speed;

    public RenderablePhysicsObject(int x, int y, String path, DrawLayer drawLayer, double speed){
        super(x,y,path,drawLayer, 1f);
        motion = PhysicsVector.ZERO;
        this.speed = speed;
    }

    @Override
    public PhysicsVector getVelocity() {
        return motion;
    }

    @Override
    public void setVelocity(PhysicsVector pv) {
        motion = pv;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public void move() {
        translate(motion);
    }

    @Override
    public void move(PhysicsVector pV) {
        motion = motion.add(pV);
        translate(pV);
    }

    @Override
    public boolean setActive(GamePlayScreen screen){
        if(super.setActive(screen)){
            screen.kinematics.add(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean setInactive(GamePlayScreen screen){
        if(super.setInactive(screen)){
            screen.kinematics.remove(this);
            return true;
        }
        return false;
    }

    @Override
    public void addToScreen(GamePlayScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        screen.kinematics.remove(this);
        if(isActive) {
           screen.kinematics.add(this);
        }
    }
}
