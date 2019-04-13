package gameobject.renderable.enemy;

import gameengine.physics.Collidable;
import gameengine.physics.DisappearingPlatform;
import gameobject.Boundary;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.player.Player;
import main.utilities.Debug;

public class Flyer extends Minion {

    int distance = 0, myx, myy;
    Player boi;
    MinionState prevState;

    public Flyer(int x, int y, DrawLayer drawLayer, float speed, int hp, Player boi) {
        super(x, y, "/assets/enemies/minions/flyer/flyer.png", drawLayer, speed, hp);
        setState(new FlyLU());
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
        /*if(distance > 1500) {
            distance -= 1500;
            prevState = state;
            Debug.log(true,"State CHANGE FLY SHOOT");
            state = new FlyShoot();
        }
        if(!state.complete())
        {
            Debug.log(true,"WAITING");
            //waiting
        } else {
            Debug.log(true,"NO WAITING");*/
            switch (state.getState()) {
                case "FlyLU":
                    if(x<100) {
                        image = flipVertical(image);
                        state = new FlyRU();
                    }
                    if(y<100) {
                        image = flipHorizontal(image);
                        state = new FlyLD();
                    }
                    break;
                case "FlyLD":
                    if(x<100) {
                        image = flipVertical(image);
                        state = new FlyRD();
                    }
                    if(y>700) {
                        image = flipHorizontal(image);
                        state = new FlyLU();
                    }
                    break;
                case "FlyRD":
                    if(y>700) {
                        image = flipHorizontal(image);
                        state = new FlyRU();
                    } else {
                        image = flipVertical(image);
                        state = new FlyLD();
                    }
                    break;
                case "FlyRU":
                    if(y<100) {
                        image = flipHorizontal(image);
                        state = new FlyRD();
                    } else {
                        image = flipVertical(image);
                        state = new FlyLU();
                    }
                    break;
            }
        }
    //}

    @Override
    public void update() {
        state.doAction(this);
        //distance += Math.sqrt(Math.pow(x - myx, 2) + Math.pow(y -myy, 2));
        //myx = x; myy = y;
        //Debug.log(true,"Distance : " + distance);
        //if(distance > 1500) changeState();
    }

    @Override
    public boolean collide(Collidable c2) {
        Debug.log(true, "Colliding with: " + c2.getClass().toString());
        if((c2 instanceof Boundary) || (c2 instanceof DisappearingPlatform)) changeState();
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
