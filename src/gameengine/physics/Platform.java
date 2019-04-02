package gameengine.physics;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;
import gameobject.renderable.player.Player;

import java.awt.*;

public class Platform extends RenderableObject implements Collidable {

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
    }

    /**
     * @return the collision box of the Collidable
     */
    @Override
    public Rectangle getCollisionBox() {
        return new Rectangle(x,y,width,height);
    }

    @Override
    public boolean isTrigger(){
        return alpha != 0;
    }

    @Override
    public boolean collide(Collidable c) {
        if(c instanceof Player) {
            counter+=2;
            counter = counter > 10 ? 10 : counter;
            if (alpha > .035f) setAlpha(alpha - .03f);
            else setAlpha(0);
        }
        return false;
    }
}
