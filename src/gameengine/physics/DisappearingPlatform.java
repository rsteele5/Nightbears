package gameengine.physics;

import gameobject.GameObject;
import gameobject.renderable.CollidableRenderable;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.player.Player;

public class DisappearingPlatform extends CollidableRenderable{

    private int counter = 0;
    private boolean isTrigger = false;
    public DisappearingPlatform(int x, int y, String path, DrawLayer drawLayer) {
        super(x, y, path, drawLayer, 1f, false);
    }

    @Override
    public void update() {
        counter--;
        counter = counter < 0 ? 0 : counter;
        if(counter <= 0){
            if (alpha < .9f) setAlpha(alpha + .03f);
            else{
                isTrigger = false;
                setAlpha(1);
            }
        }
    }

    @Override
    public boolean isTrigger(){
        return isTrigger;
    }

    @Override
    public boolean triggered(GameObject g) {
        counter+=2;
        counter = counter > 10 ? 10 : counter;
        if (alpha > .035f) setAlpha(alpha - .03f);
        else setAlpha(0);
       return true;
    }

    @Override
    public boolean collide(Collidable c) {
        if(c instanceof Player) {
            counter+=2;
            counter = counter > 10 ? 10 : counter;
            if (alpha > .035f) setAlpha(alpha - .03f);
            else{
                isTrigger = true;
                setAlpha(0);
            }return true;
        }return false;
    }
}
