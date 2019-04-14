package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;

public class FlyRU extends EnemyState {

    public FlyRU() {
        str_State = "FlyRU";
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
        e.setVelocity(new PhysicsVector(e.getSpeed(),-e.getSpeed()));
    }

    @Override
    public boolean complete() {
        return true;
    }
}
