package gameobject.renderable.enemy;

import gameengine.physics.Collidable;
import gameengine.physics.PhysicsVector;
import gameengine.physics.RenderablePhysicsObject;
import gameobject.GameObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.player.Player;
import main.utilities.Debug;

public class Debris extends Enemy {

    boolean ishit =false;

    public Debris(int x, int y, DrawLayer drawLayer, float speed, int type) {
        super(x, y, "", drawLayer, speed, 2);
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
        Debug.log(true, "Colliding with: " + c2.getClass().toString());
        if(!ishit){
            if(c2 instanceof Player) {
                if (((Player) c2).isAttacking()) {
                    setVelocity(new PhysicsVector(-getVelocity().x,-getVelocity().y));
                    Debug.log(true, "REVERSE REVERSE");
                }
            }
        }else{
            if(c2 instanceof Minion) {
                ((Minion) c2).addhp(-300);//todo update 300
                Debug.log(true, "Fireball smacked a bitch: " + 300);
            }
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
