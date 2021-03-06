package gameobject;

import gamescreen.GameScreen;

import java.io.Serializable;

public abstract class GameObject implements Serializable {

    protected int x;
    protected int y;

    boolean isActive = true;

    public GameObject() {
        x = 0;
        y = 0;
    }

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void translate(int x, int y){
        this.x += x;
        this.y += y;
    }

    public boolean setActive(GameScreen screen){
        if(!isActive){
            screen.inactiveObjects.remove(this);
            screen.activeObjects.add(this);
            this.isActive = true;
            return true;
        }
        return false;
    }

    public boolean setInactive(GameScreen screen){
        if(isActive){
            screen.activeObjects.remove(this);
            screen.inactiveObjects.add(this);
            this.isActive = false;
            return true;
        }
        return false;
    }

    public void addToScreen(GameScreen screen, boolean isActive){

        this.isActive = isActive;
        x += screen.getX();
        y += screen.getY();

        //Remove if the object is already in the list.
        screen.activeObjects.remove(this);
        screen.inactiveObjects.remove(this);

        if(isActive){
            screen.activeObjects.add(this);
        } else {
            screen.inactiveObjects.add(this);
        }
    }

    public void scale(float scaleFactor) {
        x *= scaleFactor;
        y *= scaleFactor;
    }

    public abstract void update();
}
