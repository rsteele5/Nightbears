package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;

public class Jump extends EnemyState {

    public Jump() {
        str_State = "Jump";
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
        e.setVelocity(new PhysicsVector(0,-6));
    }

    @Override
    public boolean complete() {
        return true;
    }
}
