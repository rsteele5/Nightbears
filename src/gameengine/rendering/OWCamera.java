package gameengine.rendering;

import gameobject.GameObject;
import gameobject.renderable.RenderableObject;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;

import java.awt.*;

public class OWCamera extends Camera {

    public OWCamera(ScreenManager screenManager, GameScreen gameScreen, GameObject target) {
        super(screenManager, gameScreen, target);
    }
    public OWCamera(ScreenManager screenManager, GameScreen gameScreen, GameObject target, int renderDistance) {
        this(screenManager, gameScreen, target);
        this.renderDistance = renderDistance;
    }

    public void track(Graphics2D graphics) {
        graphics.translate(-target.getX() + renderWidth, -target.getY() + renderHeight);
        for(RenderableObject renderableObject: screen.getRenderables()){
            if(Math.hypot(target.getX() -renderableObject.getX(), target.getY()-renderableObject.getY()) < renderDistance)
                renderableObject.draw(graphics);
        }
        graphics.translate(target.getX()  - renderWidth,target.getY() - renderHeight);
    }

    public void setTarget(GameObject target){
        this.target = target;
    }

    public void setRenderDistance(int renderDistance) {
        this.renderDistance = renderDistance;
    }
}
