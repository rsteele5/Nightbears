package gameobject.renderable.enemy;

import gameengine.physics.Collidable;
import gameengine.physics.PhysicsVector;
import gameobject.Boundary;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.player.Player;
import main.utilities.Debug;

public class Walker extends Minion {
    public Walker(int x, int y, DrawLayer drawLayer, float speed, int hp) {
        super(x, y, "/assets/enemies/minions/walker/walker.png", drawLayer, speed, hp);
        setState(new WalkLeftMS());
    }
    @Override
    public void changeState()
    {
        switch (state.getState()){
            case "Walk Left" :
                image = flipVertical(image);
                state = new WalkRightMS();
                break;
            case "Walk Right" :
                image = flipVertical(image);
                state = new WalkLeftMS();
                break;
        }
    }

    @Override
    public void update() {
        state.doAction(this);
    }

    @Override
    public boolean collide(Collidable c2) {
        //Debug.log(true, "Colliding with: " + c2.getClass().toString());
        if(!(c2 instanceof Boundary))
            changeState();
        if(c2 instanceof Player) {
            if(((Player) c2).isAttacking()){
                //Damage function here hp - c2.getWeaponDamage()
                addhp(-(((Player) c2).getWeaponDamage()));
                Debug.log(true, "MY HEALTH: " + hp);
                Debug.success(true, "Walker took some damage!!!!");
            }
        }
        return true;
    }
}
