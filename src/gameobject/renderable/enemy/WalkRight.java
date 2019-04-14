package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;

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
    public void doAction(Enemy minion) {
        minion.setVelocity(new PhysicsVector(minion.getSpeed(),minion.getVelocity().y));
    }

    @Override
    public boolean complete() {
        return true;
    }
}
