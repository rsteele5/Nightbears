package gameobject.renderable.enemy;

import gameengine.physics.Kinematic;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;

import java.awt.image.BufferedImage;

public abstract class Enemy extends RenderableObject implements Kinematic {

    protected int hp;

    public Enemy() {
    }

    public Enemy(int x, int y) {
        super(x, y);
    }

    public Enemy(int x, int y, DrawLayer layer) {
        super(x, y, layer);
    }

    public Enemy(int x, int y, BufferedImage image, DrawLayer layer) {
        super(x, y, image, layer);
    }

    public Enemy(int x, int y, String imagePath, DrawLayer layer) {
        super(x, y, imagePath, layer);
    }

    public Enemy(int x, int y, String imagePath, DrawLayer layer, float alpha) {
        super(x, y, imagePath, layer, alpha);
    }

    public abstract void changeState();

    public int getHp() { return hp; }

    public void setHp(int hp) { this.hp = hp; }

    public void addhp(int hp) { this.hp += hp; }
}
