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
    public void doAction(Enemy minion) {
        minion.setVelocity(new PhysicsVector(-minion.getSpeed(),minion.getVelocity().y));
    }

    @Override
    public boolean complete() {
        return true;
    }
}
