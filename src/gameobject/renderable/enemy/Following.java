package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;
import main.utilities.Debug;

public class Following extends EnemyState {

    public Following() {
        str_State = "Following";
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
            Debug.log(true, "Help me, Im at x : " + e.getX() + " and y : " + e.getY());
    }

    @Override
    public boolean complete() {
        return true;
    }
}
