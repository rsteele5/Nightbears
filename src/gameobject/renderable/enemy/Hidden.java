package gameobject.renderable.enemy;

import gameengine.physics.PhysicsVector;
import main.utilities.Debug;

public class Hidden extends EnemyState {

    public Hidden() {
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
    public void doAction(Enemy e) {
       //minion.setVelocity(new PhysicsVector(-minion.getSpeed(),minion.getVelocity().y));i
        if(e.getX() != 6500) {e.setX(6500);
            e.setVelocity(new PhysicsVector(0,0));}
        if(e.getY() != 300) {e.setY(300);
            e.setVelocity(new PhysicsVector(0,0));}
    }

    @Override
    public boolean complete() {
        return true;
    }
}
