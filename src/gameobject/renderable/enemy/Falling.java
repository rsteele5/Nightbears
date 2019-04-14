package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;
import main.utilities.Debug;

public class Falling extends EnemyState {

    public Falling() {
        str_State = "Falling";
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
    public void doAction(Enemy e) {
        e.setVelocity(new PhysicsVector(0,1));

    }

    @Override
    public boolean complete() {
        return true;
    }
}
