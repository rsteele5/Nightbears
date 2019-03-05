package gameobject.renderable.house.sidescrolling;

import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsVector;
import gameobject.renderable.RenderableObject;
import gameobject.renderable.DrawLayer;
import main.utilities.Loadable;

import java.awt.*;

public class Floor extends RenderableObject implements Kinematic, Loadable {

    private PhysicsVector accel = new PhysicsVector(0,0);

    public Floor(int x, int y, String imagePath, DrawLayer drawLayer){
        super(x,y, imagePath ,drawLayer);
    }

    @Override
    public void update() {

    }

    @Override
    public PhysicsVector getVelocity() {
        return new PhysicsVector(0,0);
    }

    @Override
    public void setVelocity(PhysicsVector pv) {}

    @Override
    public PhysicsVector getAcceleration() {
        return accel;
    }

    @Override
    public void setAcceleration(PhysicsVector pv) { }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

}
