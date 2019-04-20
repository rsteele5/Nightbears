package gameobject.renderable.enemy;

import gameengine.physics.Collidable;
import gameengine.physics.PhysicsVector;
import gameengine.physics.RenderablePhysicsObject;
import gameobject.Boundary;
import gameobject.GameObject;
import gameobject.TriggerableBoundary;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.player.Player;
import main.utilities.Debug;

public class Debris extends Enemy {

    boolean ishit =false;

    public Debris(int x, int y, DrawLayer drawLayer, float speed, int type) {
        super(x, y, "", drawLayer, speed, 2);
        state = new Following();
        switch (type)
        {
            case 0:
                this.imagePath = "/assets/enemies/bosses/sloshyboi/debris1.png";
                setSize(40,40);
                break;
            case 1:
                this.imagePath = "/assets/enemies/bosses/sloshyboi/debris2.png";
                setSize(110,60);
                break;
            case 2:
                this.imagePath = "/assets/enemies/bosses/sloshyboi/debris3.png";
                setSize(80,80);
                break;
        }

    }

    @Override
    public void update() {
        super.update();
        state.doAction(this);
    }

    @Override
    public PhysicsVector getVelocity() {
        return super.getVelocity();
    }

    @Override
    public void setVelocity(PhysicsVector pv) {
        super.setVelocity(pv);
    }

    @Override
    public boolean collide(Collidable c2) {
        if(c2 instanceof Boundary){
            state = new Following();
        }
        return true;
    }

    @Override
    public boolean triggered(GameObject gameObject) {
        return false;
    }

    @Override
    public void changeState() {

    }
}
