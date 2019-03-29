package gameobject.renderable.house.overworld.room;

import gameengine.physics.Interactable;
import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsVector;
import gameobject.GameObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;

import java.awt.*;

public class Door extends RenderableObject implements Kinematic, Interactable {
    Rectangle interactionBox;
    boolean openable;

    public Door(int x, int y, String path, Rectangle interactionBox){
        super(x,y, path,DrawLayer.Entity);
        this.interactionBox = interactionBox;
        openable = false;
    }


    @Override
    public Rectangle hitbox() {
        return interactionBox;
    }

    @Override
    public boolean action(GameObject g) {
        if(openable){
            Open
        }
        return false;
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

    @Override
    public void update() {

    }
}
