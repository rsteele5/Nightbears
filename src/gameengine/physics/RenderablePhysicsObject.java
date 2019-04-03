package gameengine.physics;

import gameobject.renderable.CollidableRenderable;
import gameobject.renderable.DrawLayer;
import gamescreen.gameplay.GamePlayScreen;

public class RenderablePhysicsObject extends CollidableRenderable implements Kinematic {
    private PhysicsVector accel = new PhysicsVector(0,1);
    public RenderablePhysicsObject(int x, int y, String path, DrawLayer drawLayer){
        super(x,y,path,drawLayer, 1f);
    }
    PhysicsVector movement = new PhysicsVector(0,0);
    public String name = "square";
    @Override
    public void update() {

    }
    @Override
    public PhysicsVector getVelocity() {
        //TODO: Get this working
        return PhysicsVector.ZERO;
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
