package gameobject.renderable.house.overworld.room;

import gameengine.physics.Interactable;
import gameobject.GameObject;
import gameobject.renderable.CollidableRenderable;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.house.overworld.Compass;
import gameobject.renderable.house.overworld.Tile;
import gamescreen.gameplay.GamePlayScreen;
import main.utilities.Action;

import java.awt.*;
import java.io.Serializable;

import static gameobject.renderable.house.overworld.OverworldMeta.TileSize;
import static gameobject.renderable.house.overworld.OverworldMeta.WallThickness;

public class Door extends CollidableRenderable implements Interactable, Serializable {
    private Rectangle interactionBox;
    private Tile referenceTile;
    private Compass attachedDirection;
    private boolean openable;
    private boolean open;
    private Room connectedRoom;

    public Door(Tile referenceTile, Compass attachedDirection){
        super(0,0, "/assets/overworld/room/Overworld-RedCarpet-Door-",
                DrawLayer.Entity, 1f, false);
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
    public boolean isOpen(){ return open; }
    public void setOpenable(boolean isOpenable){ openable = isOpenable; }
    public void setRoomToOpen(Room connectedRoom){ this.connectedRoom = connectedRoom; }

    //Interactable
    @Override
    public Rectangle getRequestArea() {
        return interactionBox;
    }

    @Override
    public boolean action(GameObject g) {
        if(openable){
            if(connectedRoom != null)
                connectedRoom.discovered();
            open = true;
            openable = false;
            isTrigger = true;
            alpha = 0f;
        }
        return false;
    }


    //Game Object
    @Override
    public boolean setActive(GamePlayScreen screen){
        if(super.setActive(screen)){
            screen.interactables.add(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean setInactive(GamePlayScreen screen){
        if(super.setInactive(screen)){
            screen.interactables.remove(this);
            return true;
        }
        return false;
    }

    @Override
    public void addToScreen(GamePlayScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        screen.interactables.remove(this);
        if(isActive) {
            screen.interactables.add(this);
        }
    }

    @Override
    public void update() { }
}
