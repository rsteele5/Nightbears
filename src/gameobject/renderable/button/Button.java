package gameobject.renderable.button;

import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gameobject.renderable.RenderableObject;
import main.utilities.Clickable;
import main.utilities.Action;
import main.utilities.Debug;

public class Button extends RenderableObject implements Clickable{

    protected boolean isClicked = false;

    //public static ButtonType type
    public Button(int x, int y, String imagePath, DrawLayer drawLayer) {
        super(x, y, imagePath, drawLayer);
    }

    public Button(int x, int y, String imagePath, DrawLayer drawLayer, Action handleOnClick) {
        this(x, y, imagePath, drawLayer);
        onClick = handleOnClick;
    }

    public Button(String imagePath, Action handleOnClick) {
        this(0, 0, imagePath, DrawLayer.Entity, handleOnClick);
    }

    @Override
    public void update() {
        if(isClicked){
            Debug.log(true, "doing the action");
            onClick.doIt();
            isClicked = false;
        }
    }

    public Action onClick;

    @Override
    public void onClick() {
        if(onClick != null){
            isClicked = true;
            Debug.log(true, "setting on click to true");
        }
    }

    @Override
    public void setOnClick(Action onClick) {
        this.onClick = onClick;
    }

    @Override
    public boolean contains(int x, int y) {
        return getBoundingBox().contains(x,y);
    }


    @Override
    public boolean setActive(GameScreen screen){
        if(super.setActive(screen)){
            screen.clickables.add(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean setInactive(GameScreen screen){
        if(super.setInactive(screen)){
            screen.clickables.remove(this);
            return true;
        }
        return false;
    }

    @Override
    public void addToScreen(GameScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);

        if(isActive) {
            screen.clickables.add(this);
        }
    }
}
