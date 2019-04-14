package gameobject.renderable.physics;

import gameengine.physics.RenderablePhysicsObject;
import gameobject.renderable.DrawLayer;

public class Square extends RenderablePhysicsObject {
    public Square(int x, int y){
        super(x,y,"/assets/testAssets/square.png",DrawLayer.Entity,-3);
    }
    @Override
    public void update() {}
}
