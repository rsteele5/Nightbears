package gameobject.renderable.button;

import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gameobject.renderable.RenderableObject;
import main.utilities.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Button extends RenderableObject implements Clickable{

    protected boolean isClicked = false;
    protected boolean isPressed = false;
    protected String pressedImagePath;
    protected BufferedImage pressedImage;

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

    public Button(int x, int y, String imagePath, String pressedImagePath, DrawLayer drawLayer, Action handleOnClick) {
        this(x, y, imagePath, drawLayer);
        onClick = handleOnClick;
        this.pressedImagePath = pressedImagePath;
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

    public void draw(Graphics2D graphics) {
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        graphics.setComposite(alphaComposite);
        if(animator != null){
            animator.animate();
        }
        Debug.drawRect(DebugEnabler.RENDERABLE_LOG,graphics,
                new Rectangle2D.Double(position.x, position.y, width, height));
        if(isPressed) {
            graphics.drawImage(pressedImage, position.x - 10  , position.y - 10, width + 20, height + 20, null);
        } else {
            graphics.drawImage(image, position.x , position.y, width, height, null);
        }
    }

    @Override
    public void load() {
        super.load();
        pressedImage = AssetLoader.load(pressedImagePath);
    }

    public void setPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }
}
