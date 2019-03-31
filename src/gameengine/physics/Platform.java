package gameengine.physics;

import gameobject.GameObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.player.Player;

import java.awt.*;

public class Platform extends PhysicsObjectStatic implements Trigger{

    private boolean disappearing = false;
    int counter = 0;
    public Platform(int x, int y, String path, DrawLayer drawLayer) {
        super(x, y, path, drawLayer);
    }

    @Override
    public void update() {
        if(!disappearing){
            if (alpha < .9f) setAlpha(alpha + .01f);
            else setAlpha(1);
        }
        super.update();
    }

    @Override
    public Rectangle triggerArea() {
        return getRequestArea();
    }

    @Override
    public boolean effect(GameObject g) {
        if(g instanceof Player) {
            
            disappearing = true;
            if (alpha > .015f) setAlpha(alpha - .01f);
            else setAlpha(0);
        }
        return false;
    }
}
