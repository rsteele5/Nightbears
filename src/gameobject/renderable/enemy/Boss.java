package gameobject.renderable.enemy;


import gameengine.physics.PhysicsVector;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public abstract class Boss extends Enemy /*implements Collidable, Kinematic, Interactable*/ {

    protected PhysicsVector accel = new PhysicsVector(0,1);
    protected PhysicsVector movement = new PhysicsVector(0, 0);

    public Boss(int x, int y, String path, DrawLayer drawLayer, float speed, int hp) {
        super(x, y, path, drawLayer, speed, hp);
    }

    @Override
    public void addToScreen(GameScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        // add stuff to Screen here
    }
}
