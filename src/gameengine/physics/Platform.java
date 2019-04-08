package gameengine.physics;

import gameobject.renderable.CollidableRenderable;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.player.Player;

public class Platform extends CollidableRenderable{

    private int counter = 0;
    public Platform(int x, int y, String path, DrawLayer drawLayer) {
        super(x, y, path, drawLayer, 1f, false);
    }

    @Override
    public void update() {
        counter--;
        counter = counter < 0 ? 0 : counter;
        if(counter <= 0){
            if (alpha < .9f) setAlpha(alpha + .01f);
            else setAlpha(1);
        }
    }

    @Override
    public boolean isTrigger(){
        return alpha == 0;
    }

    @Override
    public boolean collide(Collidable c) {
        if(c instanceof Player) {
            counter+=2;
            counter = counter > 10 ? 10 : counter;
            if (alpha > .01f) setAlpha(alpha - .01f);
            else setAlpha(0);
            return true;
        }
        return false;
    }
}
