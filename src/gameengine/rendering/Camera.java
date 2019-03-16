package gameengine.rendering;

import gameobject.GameObject;
import gameobject.renderable.RenderableObject;
import gamescreen.GameScreen;

import java.awt.*;

public class Camera {

    public GameScreen screen;
    public GameObject target;

    public Camera(GameScreen gameScreen, GameObject target) {
        this.screen = gameScreen;
        this.target = target;
    }

    public void track(Graphics2D graphics) {
        graphics.translate(-target.getX()+640, -target.getY()+360);
        for(RenderableObject renderableObject: screen.getRenderables()){
            if(Math.hypot(target.getX()-renderableObject.getX(), target.getY()-renderableObject.getY()) < 1105)
                renderableObject.draw(graphics);
        }
        graphics.translate(target.getX()-640, target.getY()-360);
    }

    public void setTarget(GameObject target){
        this.target = target;
    }
}
