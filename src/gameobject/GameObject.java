package gameobject;

import gameengine.physics.PhysicsVector;
import gamescreen.GameScreen;

public abstract class GameObject {

    protected PositionVector position;

    boolean isActive = true;

    public GameObject() {
        position = PositionVector.ORIGIN;
    }

    public GameObject(int x, int y) {
        position = new PositionVector(x,y);
    }

    public PositionVector getPosition() {
        return position;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public void setX(int x) {
        position.x = x;
    }

    public void setY(int y) {
        position.y = y;
    }

    public void setPosition(PositionVector position) {
        this.position = position;
    }

    public void setPosition(int x, int y) {
        position.x = x;
        position.y = y;
    }

    public void translate(PositionVector vector){
        position = position.add(vector);
    }

    public void translate(PhysicsVector vector){
        position = position.add(vector);
    }

    public void translate(int x, int y){
        position = position.add(x,y);
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
        position.x += screen.getX();
        position.y += screen.getY();

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
        position.x *= scaleFactor;
        position.y *= scaleFactor;
    }

    public abstract void update();
}
