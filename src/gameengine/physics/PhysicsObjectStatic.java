package gameengine.physics;

import gameobject.renderable.DrawLayer;

import java.awt.*;

public class PhysicsObjectStatic extends PhysicsObject {
    public PhysicsObjectStatic(int x, int y, String path, DrawLayer drawLayer){
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
