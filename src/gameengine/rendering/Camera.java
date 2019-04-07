package gameengine.rendering;

import gameobject.GameObject;
import gameobject.renderable.RenderableObject;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;

import java.awt.*;

public class Camera {

    private GameScreen screen;
    private int targetX;
    private int targetY;
    private GameObject target;
    private int renderDistance;
    private int renderWidth;
    private int renderHeight;

    public Camera(ScreenManager screenManager, GameScreen gameScreen, int targetX, int targetY) {
        this.screen = gameScreen;
        this.targetX = targetX;
        this.targetY = targetY;
        this.renderDistance = 1200;
        this.renderWidth = screenManager.getGameData().getGraphicsSettings().getRenderWidth() / 2;
        this.renderHeight = screenManager.getGameData().getGraphicsSettings().getRenderHeight() / 2;
    }

    public Camera(ScreenManager screenManager, GameScreen gameScreen, GameObject target) {
        this.screen = gameScreen;
        this.target = target;
        this.targetX = target.getX();
        this.targetY = target.getY();
        this.renderDistance = 1200;
        this.renderWidth = screenManager.getGameData().getGraphicsSettings().getRenderWidth() / 2;
        this.renderHeight = screenManager.getGameData().getGraphicsSettings().getRenderHeight() / 2;
    }

    public void track(Graphics2D graphics) {
        if(target != null) {
            targetX = target.getX();
            targetY = target.getY();
        }
        graphics.translate(-targetX + renderWidth, -targetY + renderHeight);
        for(RenderableObject renderableObject: screen.getRenderables()){
            if(Math.hypot(targetX-renderableObject.getX(), targetY-renderableObject.getY()) < renderDistance)
                renderableObject.draw(graphics);
        }
        graphics.translate(targetX - renderWidth,targetY - renderHeight);
    }

    public void setTarget(GameObject target){
        this.target = target;
    }

    public void setRenderDistance(int renderDistance) {
        this.renderDistance = renderDistance;
    }
}
