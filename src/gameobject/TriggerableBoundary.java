package gameobject;

import gameengine.physics.Collidable;

public class TriggerableBoundary extends CollidableObject {

    private boolean isTriggered;

    public TriggerableBoundary(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isTrigger() {
        return isTriggered;
    }

    @Override
    public boolean triggered(GameObject gameObject) {
        return false;
    }

    public void setTrigger(boolean triggered) {
        this.isTriggered = triggered;
    }
}
