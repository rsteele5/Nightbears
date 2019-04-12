package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;

public class WalkLeftMS extends MinionState {

    public WalkLeftMS() {
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
    public void doAction(Minion minion) {
        minion.setVelocity(new PhysicsVector(-minion.getSpeed(),minion.getVelocity().y));
    }
}
