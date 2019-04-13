package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;
import main.utilities.Debug;

public class FlyShoot extends MinionState {

    FlyerBall ball;
    boolean completed = false;

    public FlyShoot() {
        str_State = "FlyLD";
    }

    @Override
    public String toString() {
        return str_State;
    }

    @Override
    public String getState() {
        return str_State;
    }

    @Override
    public void doAction(Minion minion) {
        Debug.log(true,"STOP");
        minion.setVelocity(new PhysicsVector(0, 0));
        Flyer flyboi = ((Flyer) minion);
        int slopex = flyboi.getX() - flyboi.px();
        /*int slopey = flyboi.getY() - flyboi.py();
        ball = new FlyerBall(flyboi.getX(),flyboi.getY(),flyboi.getDrawLayer(),3);
        if(slopex == 0 ){
            if(slopex < 0) Debug.log(true,"Shoot left");
            else if (slopex > 0) Debug.log(true,"Shoot Right");
        }
        if(slopey == 0 ){
            if(slopey < 0) Debug.log(true,"Shoot down");
            else if (slopey > 0) Debug.log(true,"Shoot Up");
        }*/

        //minion.setVelocity(new PhysicsVector(slopex, slopey));

    }

    @Override
    public boolean complete() {
        return completed;
    }
}
