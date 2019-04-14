package gameobject;

import gameengine.physics.Collidable;
import gameengine.rendering.Camera;
import gameengine.rendering.SSCamera;
import gameobject.renderable.enemy.*;
import gameobject.renderable.player.Player;
import main.utilities.Debug;

public class LockCameraTrigger extends CollidableObject {

    private SSCamera camera;
    private boolean isTriggered;
    private int cameraXPos;
    private final int translateSpeed = 10;
    private TriggerableBoundary bounds;
    private SloshyBoi boss;

    public LockCameraTrigger(int x, int y, int width, int height, SSCamera camera, TriggerableBoundary bounds, SloshyBoi boss) {
        super(x, y, width, height);
        this.camera = camera;
        this.isTriggered = false;
        this.cameraXPos = x;
        this.bounds = bounds;
        this.boss = boss;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isTrigger() {
        return true;
    }

    @Override
    public boolean triggered(GameObject gameObject) {
        if(getCollisionBox().contains(gameObject.x, gameObject.y) && gameObject instanceof Player){
            bounds.setTrigger(false);
            if(!isTriggered && gameObject instanceof Player) {
                cameraXPos += translateSpeed;
                if(cameraXPos >= x + width / 2){
                    isTriggered = true;
                    boss.setState(new WalkRight());
                    Debug.log(true, "setting trigger and stuff");
                } else {
                    camera.setTarget(cameraXPos);
                }
            }
        }
        return false;
    }
}
