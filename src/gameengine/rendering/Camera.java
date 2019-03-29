package gameengine.rendering;

import gameobject.GameObject;
import gameobject.renderable.RenderableObject;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;

import java.awt.*;

public class Camera {

    private GameScreen screen;
    private GameObject target;
    private ScreenManager screenManager;
    private int renderDistance;

    public Camera(ScreenManager screenManager, GameScreen gameScreen, GameObject target) {
        this.screenManager = screenManager;
        this.screen = gameScreen;
        this.target = target;
        renderDistance = 1200;
    }

    public void track(Graphics2D graphics) {
        graphics.translate(-target.getX( ) + screenManager.getGameData().getGraphicsSettings().getRenderWidth()/2,
                -target.getY() + screenManager.getGameData().getGraphicsSettings().getRenderHeight()/2);
        for(RenderableObject renderableObject: screen.getRenderables()){
            if(Math.hypot(target.getX()-renderableObject.getX(), target.getY()-renderableObject.getY()) < renderDistance)
                renderableObject.draw(graphics);
        }
        graphics.translate(target.getX()-screenManager.getGameData().getGraphicsSettings().getRenderWidth()/2,
                target.getY()-screenManager.getGameData().getGraphicsSettings().getRenderHeight()/2);
    }

    public void setTarget(GameObject target){
        this.target = target;
    }

    public void setRenderDistance(int renderDistance) {
        this.renderDistance = renderDistance;
    }
}
