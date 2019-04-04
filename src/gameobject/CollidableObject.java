package gameobject;

import gameengine.physics.Collidable;
import gamescreen.gameplay.GamePlayScreen;

import java.awt.Rectangle;

public abstract class CollidableObject extends GameObject implements Collidable {
    protected int width;
    protected int height;

    public CollidableObject(int x, int y, int width, int height){
        super(x,y);
        this.width = width;
        this.height = height;
    }

    //Collidable
    /**
     * @return the collision box of the Collidable
     */
    @Override
    public Rectangle getCollisionBox() {
        return new Rectangle(x, y, width, height);
    }

    public boolean setActive(GamePlayScreen screen){
        if(super.setActive(screen)){
            screen.collidables.add(this);
            return true;
        }
        return false;
    }

    public boolean setInactive(GamePlayScreen screen){
        if(super.setInactive(screen)){
            screen.collidables.remove(this);
            return true;
        }
        return false;
    }

    public void addToScreen(GamePlayScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        screen.collidables.remove(this);
        if(isActive) {
            screen.collidables.add(this);
        }
    }
}
