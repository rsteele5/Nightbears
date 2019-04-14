package gameobject.renderable;

import gameengine.physics.Collidable;
import gamescreen.gameplay.GamePlayScreen;

import java.awt.Rectangle;

public abstract class CollidableRenderable extends RenderableObject implements Collidable {
    protected boolean isTrigger;
    public CollidableRenderable(int x, int y, String imagePath, DrawLayer layer, float alpha, boolean isTrigger) {
        super(x,y,imagePath,layer,alpha);
        this.isTrigger = isTrigger;
    }

    /**
     * @return the collision box of the Collidable
     */
    @Override
    public Rectangle getCollisionBox() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public boolean isTrigger() {
        return isTrigger;
    }

    public void setTrigger(boolean isTrigger) {
        this.isTrigger = isTrigger;
    }

    //Game Object
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
