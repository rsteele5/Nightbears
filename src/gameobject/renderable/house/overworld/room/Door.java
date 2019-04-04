package gameobject.renderable.house.overworld.room;

import gameengine.physics.Interactable;
import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsVector;
import gameobject.GameObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;
import gameobject.renderable.house.overworld.Compass;
import gameobject.renderable.house.overworld.Tile;
import gamescreen.GameScreen;
import main.utilities.Action;

import java.awt.*;
import java.io.Serializable;

import static gameobject.renderable.house.overworld.OverworldMeta.TileSize;
import static gameobject.renderable.house.overworld.OverworldMeta.WallThickness;

public class Door extends RenderableObject implements Kinematic, Interactable, Serializable {
    private Rectangle interactionBox;
    private Tile referenceTile;
    private Compass attachedDirection;
    private boolean openable;
    private Action open;

    public Door(Tile referenceTile, Compass attachedDirection){
        super(0,0,DrawLayer.Entity);
        imagePath = "/assets/overworld/room/Overworld-RedCarpet-Door-";
        openable = false;
        this.referenceTile = referenceTile;
        this.attachedDirection = attachedDirection;
        x = referenceTile.getX();
        y = referenceTile.getY();
        int interactX = x;
        int interactY = y;
        int interactW = TileSize;
        int interactH = TileSize;
        //Adjust Position and Interaction box
        switch (attachedDirection){
            case North:
                interactY -= TileSize;
                interactH += TileSize;
                imagePath += "Hori.png";
                break;
            case South:
                y += TileSize - WallThickness;
                interactH += TileSize;
                imagePath += "Hori.png";
                break;
            case East:
                x += TileSize - WallThickness;
                interactW += TileSize;
                imagePath += "Vert.png";
                break;
            case West:
                interactX -= TileSize;
                interactW += TileSize;
                imagePath += "Vert.png";
                break;
        }
        interactionBox = new Rectangle(interactX, interactY, interactW, interactH);
    }

    //Getters and Setters
    public Tile getReferenceTile(){ return referenceTile; }
    public Compass getAttachedDirection(){ return attachedDirection; }
    public void setOpenable(boolean isOpenable){ openable = isOpenable; }
    public void setOpenOperation(Action operation){ open = operation; }

    //Interactable
    @Override
    public Rectangle getRequestArea() {
        return interactionBox;
    }

    @Override
    public void setRequesting(boolean isRequesting) { }

    @Override
    public boolean isRequesting() {
        return false;
    }

    @Override
    public boolean action(GameObject g) {
        if(openable){
            open.doIt();
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
