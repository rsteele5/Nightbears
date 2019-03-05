package gameobject.renderable.house.sidescrolling;

import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsVector;
import gameobject.renderable.RenderableObject;
import gameobject.renderable.DrawLayer;

import java.awt.*;

public class FloorTile extends RenderableObject implements Kinematic {

    public FloorTile(int x, int y, String path) {
        super(x, y, path, DrawLayer.Entity);
    }

    @Override
    public void update() {
    }

    @Override
    public PhysicsVector getVelocity() {
        return new PhysicsVector(0,0);
    }

    @Override
    public void setVelocity(PhysicsVector pv) {
    }

    @Override
    public PhysicsVector getAcceleration() {
        return new PhysicsVector(0,0);
    }

    @Override
    public void setAcceleration(PhysicsVector pv) {
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }
}
