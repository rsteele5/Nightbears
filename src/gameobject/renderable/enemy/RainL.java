package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;

public class RainL extends EnemyState {

    public RainL() {
        str_State = "Rain Left";
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
        e.setVelocity(new PhysicsVector(-e.getSpeed()*2,-6));
        e.attack();
    }

    @Override
    public boolean complete() {
        return true;
    }
}
