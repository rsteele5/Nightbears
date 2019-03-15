package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;
import gameobject.renderable.DrawLayer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Walker extends Minion {
    public Walker() {
    }

    public Walker(int x, int y) {
        super(x, y);
    }

    public Walker(int x, int y, DrawLayer layer) {
        super(x, y, layer);
    }

    public Walker(int x, int y, BufferedImage image, DrawLayer layer) {
        super(x, y, image, layer);
    }

    public Walker(int x, int y, String imagePath, DrawLayer layer) {
        super(x, y, imagePath, layer);
    }

    public Walker(int x, int y, String imagePath, DrawLayer layer, float alpha) {
        super(x, y, imagePath, layer, alpha);
    }

    @Override
    public void update() {
        state.doAction(this);
    }


    @Override
    public PhysicsVector getVelocity() {
        return super.getVelocity();
    }

    @Override
    public void setVelocity(PhysicsVector pv) {
        super.setVelocity(pv);
    }

    @Override
    public PhysicsVector getAcceleration() {
        return super.getAcceleration();
    }

    @Override
    public void setAcceleration(PhysicsVector pv) {
        super.setAcceleration(pv);
    }

    @Override
    public Rectangle getHitbox() {
        return super.getHitbox();
    }
}
