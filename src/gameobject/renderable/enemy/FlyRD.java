package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;
import main.utilities.Debug;

public class FlyRD extends MinionState {

    public FlyRD() {
        str_State = "FlyRD";
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
        minion.setVelocity(new PhysicsVector(minion.getSpeed(),-minion.getSpeed()));
    }
}
