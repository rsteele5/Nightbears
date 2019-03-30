package gameobject.renderable.house.overworld.room;

import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsVector;
import gameobject.GameObject;
import gamescreen.GameScreen;

import java.awt.Rectangle;

public class Boundary extends GameObject implements Kinematic {
    protected int width;
    protected int height;

    public Boundary(int x, int y, int width, int height){
        super(x,y);
        this.width = width;
        this.height = height;
    }

    //Kinematic
    @Override
    public boolean isStatic() { return true; }
    @Override
    public PhysicsVector getVelocity() { return PhysicsVector.ZERO; }
    @Override
    public void setVelocity(PhysicsVector pv) {}
    @Override
    public PhysicsVector getAcceleration() { return PhysicsVector.ZERO; }
    @Override
    public void setAcceleration(PhysicsVector pv) {}
    @Override
    public Rectangle getHitbox() { return new Rectangle(x, y, width, height); }
    //Game Object
    @Override
    public boolean setActive(GameScreen screen){
        if(super.setActive(screen)){
            screen.kinematics.add(this);
            return true;
        }return false;
    }

    @Override
    public boolean setInactive(GameScreen screen){
        if(super.setInactive(screen)){
            screen.kinematics.remove(this);
            return true;
        }return false;
    }

    @Override
    public void addToScreen(GameScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        screen.kinematics.remove(this);
        if(isActive) screen.kinematics.add(this);
    }
    @Override
    public void update() {}
}
