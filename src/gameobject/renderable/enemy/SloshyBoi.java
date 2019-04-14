package gameobject.renderable.enemy;

import gameengine.physics.Collidable;
import gameengine.physics.Platform;
import gameobject.Boundary;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.player.Player;
import main.utilities.Debug;

public class SloshyBoi extends Boss {

    EnemyState pstate;
    int wait, turn = 0;

    public SloshyBoi(int x, int y, DrawLayer drawLayer, float speed, int hp) {
        super(x, y, "/assets/enemies/bosses/sloshyboi/sloshyboi.png", drawLayer, speed, hp);
        setState(new WalkLeft());
    }
    @Override
    public void changeState()
    {

        if(wait> 20){
            wait = 0;
            pstate = state;
            state = new Jump();
        }
        switch (state.getState()){
            case "Walk Left" :
                pstate = state;wait+=5;
                if(turn>3){state = new WalkRight();turn=0;}
                turn++;
            break;
            case "Walk Right" :
                pstate = state;wait+=5;
                if(turn>3){state = new WalkLeft();turn=0;}
                turn++;
            break;
            case "Jump" :
                wait+=2;
                if(wait>9) {
                    //turn =0;
                    switch (pstate.getState()) {
                        case "Walk Left" :
                            state = new RainL();turn++;
                            Debug.log(true,"turn" + turn);
                            break;
                        case "Walk Right" :
                            state = new RainR();turn++;
                            Debug.log(true,"turn" + turn);
                            break;
                            default: Debug.error(true,"bad news bears");
                    }
                }
            break;
            case "Rain Right" :
                if(turn > 400) {state = pstate;turn=0;}
                turn++;

                break;
            case "Rain Left" :
                if(turn > 400) {state = pstate;turn=0;}
                turn++;
                break;

        }
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public boolean collide(Collidable c2) {
        if((c2 instanceof Boundary)){
            if(c2.getCollisionBox().getWidth() < 200) {
                changeState();//Wall
            } else {
                if(state instanceof Jump|| state instanceof RainL || state instanceof  RainR) changeState();
            }
        } else if (c2 instanceof Player && !((Player) c2).isAttacking())
        {
            changeState();
        }


        if(c2 instanceof Player) {
            if(((Player) c2).isAttacking()){
                //Damage function here hp - c2.getWeaponDamage()
                addhp(-(((Player) c2).getWeaponDamage()));
                Debug.log(true, "MY HEALTH: " + hp);
            }
        }
        return true;
    }
}
