package gameobject;

import gameengine.physics.Collidable;
import gameengine.rendering.Camera;
import gameengine.rendering.SSCamera;
import gameobject.renderable.player.Player;
import main.utilities.Debug;

public class LockCameraTrigger extends CollidableObject {

    private SSCamera camera;
    private boolean isTriggered;
    private int cameraXPos;
    private final int translateSpeed = 10;
    private TriggerableBoundary bounds;

    public LockCameraTrigger(int x, int y, int width, int height, SSCamera camera, TriggerableBoundary bounds) {
        super(x, y, width, height);
        this.camera = camera;
        this.isTriggered = false;
        this.cameraXPos = x;
        this.bounds = bounds;
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
        if(getCollisionBox().contains(gameObject.x, gameObject.y)){
            bounds.setTrigger(false);
            if(!isTriggered && gameObject instanceof Player) {
                cameraXPos += translateSpeed;
                if(cameraXPos >= x + width / 2){
                    isTriggered = true;
                } else {
                    camera.setTarget(cameraXPos);
                }
            }
        }
        return false;
    }
}
