package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;

public class WalkerHidden extends EnemyState {

    public WalkerHidden() {
        str_State = "Walk Idle";
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
       //minion.setVelocity(new PhysicsVector(-minion.getSpeed(),minion.getVelocity().y));
    }

    @Override
    public boolean complete() {
        return true;
    }
}
