package gameengine.rendering;

import gameobject.Boundary;
import gameobject.GameObject;
import gameobject.renderable.RenderableObject;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;

import java.awt.*;

public abstract class Camera {

    protected GameScreen screen;
    protected GameObject target;
    protected int renderDistance;
    protected int renderWidth;
    protected int renderHeight;

    public Camera(ScreenManager screenManager, GameScreen gameScreen, GameObject target) {
        this.screen = gameScreen;
        this.target = target;
        this.renderDistance = 1600;
        this.renderWidth = screenManager.getGameData().getGraphicsSettings().getRenderWidth() / 2;
        this.renderHeight = screenManager.getGameData().getGraphicsSettings().getRenderHeight() / 2;
    }

    public Camera(ScreenManager screenManager, GameScreen gameScreen, GameObject target, int renderDistance) {
        this(screenManager, gameScreen, target);
        this.renderDistance = renderDistance;
    }

    public abstract void track(Graphics2D graphics);

    public void setTarget(GameObject target){
        this.target = target;
    }

    public void setRenderDistance(int renderDistance) {
        this.renderDistance = renderDistance;
    }
}
