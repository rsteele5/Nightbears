package gameengine.physics;

import gameobject.renderable.CollidableRenderable;
import gameobject.renderable.DrawLayer;
import gamescreen.gameplay.GamePlayScreen;

import java.awt.*;

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

    /**
     * @return the collision box of the Collidable
     */
    @Override
    public Rectangle getCollisionBox() {
        return new Rectangle(x, y, width, height);
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
