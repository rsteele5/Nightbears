package gameobject.renderable.enemy;

import gameengine.physics.Collidable;
import gameobject.Boundary;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.player.Player;
import gamescreen.GameScreen;
import main.utilities.Debug;

public class Flyer extends Minion {

    int distance = 0, myx, myy;
    Player boi;

    public Flyer(int x, int y, DrawLayer drawLayer, float speed, int hp, Player boi) {
        super(x, y, "/assets/enemies/minions/flyer/flyer.png", drawLayer, speed, hp);
        setState(new FlyLD());
        myx = x;
        myy = y;
        this.boi = boi;
    }

    public Flyer(int x, int y, DrawLayer drawLayer, float speed, MinionState state, int hp, Player boi) {
        super(x, y, "/assets/enemies/minions/flyer/flyer.png", drawLayer, speed, hp);
        this.state = state;
        this.boi = boi;
    }

    @Override
    public void changeState()
    {
        distance += Math.sqrt(Math.pow(x - myx, 2) + Math.pow(y -myy, 2));
        myx = x; myy = y;
        Debug.log(true,"Distance : " + distance);
        switch (state.getState()){
            case "FlyLU" :
                image = flipHorizontal(image);
                state = new FlyLD();
                break;
            case "FlyLD" :
                image = flipVertical(image);
                state = new FlyRD();
                break;
            case "FlyRD" :
                image = flipHorizontal(image);
                state = new FlyRU();
                break;
            case "FlyRU" :
                image = flipVertical(image);
                state = new FlyLU();
                break;
        }
    }

    @Override
    public void update() {
        state.doAction(this);
    }

    @Override
    public boolean collide(Collidable c2) {
        Debug.log(true, "Colliding with: " + c2.getClass().toString());
        //if(!(c2 instanceof Boundary))
            changeState();
        if(c2 instanceof Player) {
            if(((Player) c2).isAttacking()){
                //Damage function here hp - c2.getWeaponDamage()
                setHp(-(((Player) c2).getWeaponDamage()));
                Debug.log(true, "MY HEALTH: " + hp);
                Debug.success(true, "Flyer took some damage!!!!");
            }
        }
        return true;
    }

    @Override
    public void attack() {
        getScreen();
    }

    public int px() {
        return boi.getX();
    }

    public int py() {
        return boi.getY();
    }

}
