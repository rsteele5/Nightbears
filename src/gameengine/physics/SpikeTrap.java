package gameengine.physics;

import gameobject.GameObject;
import gameobject.renderable.CollidableRenderable;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.player.Player;

import java.awt.*;

public class SpikeTrap extends CollidableRenderable {
    @Override
    public void update() {

    }
    public SpikeTrap(int x, int y){
        super(x, y,"/assets/sidescroll/Spike_Floor.png", DrawLayer.Entity, 1, true);
    }
    @Override
    public boolean triggered(GameObject gameObject) {
        if(gameObject instanceof Player){
            ((Player)gameObject).hit(3);
        }
        return true;
    }
    public Rectangle getCollisionBox() {
        return new Rectangle(x,y,width,height);
    }
}
