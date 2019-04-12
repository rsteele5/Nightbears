package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;

public class FlyShoot extends MinionState {

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
        //minion.setVelocity(new PhysicsVector(0, 0));
        Flyer flyboi = ((Flyer) minion);
        int slopex = flyboi.getX() - flyboi.px();
        int slopey = flyboi.getY() - flyboi.py();
        minion.setVelocity(new PhysicsVector(slopex, slopey));

    }
}
