package gameobject.renderable.house.sidescrolling;

import _test.Square;
import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsVector;
import gameobject.renderable.RenderableObject;
import gameobject.renderable.DrawLayer;
import main.utilities.Debug;
import main.utilities.Loadable;

import java.awt.*;

public class Floor extends Square {
    public Floor(int x, int y, String path, DrawLayer drawLayer){
        super(x,y,path,drawLayer);
    }
    @Override
    public void update() {
    }

    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }
}
