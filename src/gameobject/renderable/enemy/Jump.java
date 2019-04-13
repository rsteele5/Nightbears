package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;

public class WalkLeft extends EnemyState {

    public WalkLeft() {
        str_State = "Walk Left";
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
        e.setVelocity(new PhysicsVector(-e.getSpeed(),e.getVelocity().y));
    }

    @Override
    public boolean complete() {
        return true;
    }
}
