package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;
import main.utilities.Debug;

public class FlyLD extends EnemyState {

    public FlyLD() {
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
    public void doAction(Enemy minion) {
        minion.setVelocity(new PhysicsVector(-minion.getSpeed(), minion.getSpeed()));

    }

    @Override
    public boolean complete() {
        return true;
    }
}
