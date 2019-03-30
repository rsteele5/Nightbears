package gameobject.renderable.house.overworld.room;

import gameengine.physics.Interactable;
import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsVector;
import gameobject.GameObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;
import gamescreen.GameScreen;

import java.awt.*;

public class Door extends RenderableObject implements Kinematic, Interactable {
    private final String BASEPATH = "/assets/overworld/room/Overworld-RedCarpet-Door-";
    Rectangle interactionBox;
    boolean openable;

    public Door(int x, int y, boolean orientation, Rectangle interactionBox){
        super(x,y,DrawLayer.Entity);
        imagePath = BASEPATH;
        imagePath += orientation ? "Vert.png" : "Hori.png";
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

    //Game Object
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
        screen.kinematics.remove(this);
        if(isActive) {
            screen.kinematics.add(this);
        }
    }
    @Override
    public void update() {

    }
}
