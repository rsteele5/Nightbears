package gameengine.rendering;

import gameobject.GameObject;
import gameobject.renderable.RenderableObject;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;

import java.awt.*;

public class SSCamera extends Camera{

    private int targetX;

    public SSCamera(ScreenManager screenManager, GameScreen gameScreen, GameObject target) {
        super(screenManager, gameScreen, target);
    }
    public SSCamera(ScreenManager screenManager, GameScreen gameScreen, GameObject target, int renderDistance) {
        this(screenManager, gameScreen, target);
        this.renderDistance = renderDistance;
    }

    public void track(Graphics2D graphics) {
        if(target != null){
            targetX = target.getX();
        }
        graphics.translate(-targetX + renderWidth, 0);
        for(RenderableObject renderableObject: screen.getRenderables()){
            if(Math.hypot(targetX-renderableObject.getX(), renderHeight-renderableObject.getY()) < renderDistance)
                renderableObject.draw(graphics);
        }
        graphics.translate(targetX - renderWidth, 0);
    }


    public void setTarget(GameObject target){
        this.target = target;
    }

    public void setTarget(int x){
        targetX = x;
        target = null;
    }

    public void setRenderDistance(int renderDistance) {
        this.renderDistance = renderDistance;
    }
}
