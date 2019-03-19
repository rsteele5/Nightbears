package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;

public class WalkRightMS extends MinionState {

    public WalkRightMS() {
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
    public void doAction(Minion minion) {
        minion.setVelocity(new PhysicsVector(1,0));
    }
}
