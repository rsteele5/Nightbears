package gameengine.physics;

import gameobject.GameObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.player.Player;

import java.awt.*;

public class Platform extends PhysicsObjectStatic implements Trigger{

    private int counter = 0;
    public Platform(int x, int y, String path, DrawLayer drawLayer) {
        super(x, y, path, drawLayer);
    }

    @Override
    public void update() {
        counter--;
        counter = counter < 0 ? 0 : counter;
        if(counter <= 0){
            if (alpha < .9f) setAlpha(alpha + .03f);
            else setAlpha(1);
        }
        super.update();
    }

    @Override
    public Rectangle triggerArea() {
        return getRequestArea();
    }

    @Override
    public boolean isCollidable(){
        return alpha != 0;
    }

    @Override
    public boolean effect(GameObject g) {
        if(g instanceof Player) {
            counter+=2;
            counter = counter > 10 ? 10 : counter;
            if (alpha > .035f) setAlpha(alpha - .03f);
            else setAlpha(0);
        }
        return false;
    }
}
