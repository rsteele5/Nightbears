package gameobject.renderable.house.sidescrolling;

import gameengine.physics.Interactable;
import gameobject.GameObject;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.RenderableObject;
import gameobject.renderable.player.Player;
import gamescreen.gameplay.GamePlayScreen;
import main.utilities.Action;

import java.awt.*;

public class Door extends RenderableObject implements Interactable {

    private Action exit;

    public Door(int x, int y, String imagePath, Action action) {
        super(x,y,imagePath, DrawLayer.Entity);
        exit = action;
    }


    @Override
    public void update() {

    }

    @Override
    public Rectangle getRequestArea() {
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    @Override
    public void setRequesting(boolean isRequesting) {
    }

    @Override
    public boolean isRequesting() {
        return false;
    }

    @Override
    public boolean action(GameObject g) {
        if(g instanceof Player){
            exit.doIt();
            return true;
        }
        return false;
    }

    public boolean setActive(GamePlayScreen screen){
        if(super.setActive(screen)){
            screen.interactables.add(this);
            return true;
        }return false;
    }

    public boolean setInactive(GamePlayScreen screen){
        if(super.setInactive(screen)){
            screen.interactables.remove(this);
            return true;
        }return false;
    }

    public void addToScreen(GamePlayScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        screen.interactables.remove(this);
        if(isActive){
            screen.interactables.add(this);
        }
    }
}
