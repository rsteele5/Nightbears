package gameengine.physics;

import gameobject.GameObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;
import gamescreen.GameScreen;

import java.awt.*;

public class PhysicsObject extends RenderableObject implements Kinematic, Interactable {
    private PhysicsVector accel = new PhysicsVector(0,1);
    public PhysicsObject(int x, int y, String path, DrawLayer drawLayer){
        super(x,y,path,drawLayer);
    }
    PhysicsVector movement = new PhysicsVector(0,0);
    public String name = "square";
    @Override
    public void update() {

    }

    @Override
    public boolean isStatic() {
        return false;
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

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }

    public String description(){
        return "\nX:\t\t" + x + "\nY:\t\t" + y + "\nWidth:\t" + width + "\nHeight:\t" + height;
    }

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

        if(isActive) {
           screen.kinematics.add(this);
        }
    }

    //Interactable
    @Override
    public Rectangle getRequestArea() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void setRequesting(boolean isRequesting) {

    }

    @Override
    public boolean isRequesting() {
        return false;
    }

    @Override
    public boolean action(GameObject g) {
        return false;
    }
}
