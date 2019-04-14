package gameobject.renderable.enemy;

import gameengine.physics.Kinematic;
import gameengine.physics.RenderablePhysicsObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;
import gamescreen.GameScreen;
import gamescreen.gameplay.GamePlayScreen;

import java.awt.image.BufferedImage;

public abstract class Enemy extends RenderablePhysicsObject {

    protected int hp;

    public Enemy(int x, int y, String imagePath, DrawLayer layer, float speed, int hp) {
        super(x, y, imagePath, layer, speed);
        this.hp = hp;
    }

    public abstract void changeState();

    public int getHp() { return hp; }

    public void setHp(int hp) { this.hp = hp; }

    public void addhp(int hp) { this.hp += hp; }

    @Override
    public void addToScreen(GameScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        // add stuff to Screen here
    }

    public int getDamage()
    {
        return 2;
    }

    protected void killSelf(){
        setAlpha(0f);
        isTrigger = true;
    }

    protected void attack(){
        //donothing
    }
}
