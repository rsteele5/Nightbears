package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;
import main.utilities.Debug;

public class WalkRight extends EnemyState {

    public WalkRight() {
        str_State = "Walk Right";
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
        e.setVelocity(new PhysicsVector(e.getSpeed(),e.getVelocity().y));
    }

    @Override
    public boolean complete() {
        return true;
    }
}
