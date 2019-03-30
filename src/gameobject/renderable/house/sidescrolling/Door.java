package gameobject.renderable.house.sidescrolling;

import gameengine.physics.Interactable;
import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsVector;
import gameobject.GameObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;
import gameobject.renderable.player.Player;
import main.utilities.Action;
import main.utilities.Debug;

import java.awt.*;

public class Door extends RenderableObject implements Kinematic, Interactable {

    private Action exit;

    public Door(int x, int y, String imagePath, Action action) {
        super(x,y,imagePath, DrawLayer.Entity);
        exit = action;
    }


    @Override
    public void update() {

    }

    @Override
    public Rectangle getRequestArea() {
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
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
        if(g instanceof Player){
            exit.doIt();
            return true;
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
    public Rectangle getHitbox() { return new Rectangle(x, y, 0, 0); }
}
