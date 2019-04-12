package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;
import main.utilities.Debug;

public class FlyLU extends MinionState {

    public FlyLU() {
        str_State = "FlyLU";
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
        minion.setVelocity(new PhysicsVector(-minion.getSpeed(),minion.getSpeed()));
    }
}
