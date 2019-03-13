package gameobject.renderable;

import gameengine.gamedata.GraphicsSetting;
import gameengine.rendering.animation.Animator;
import gameobject.GameObject;
import gamescreen.GameScreen;
import main.utilities.AssetLoader;
import main.utilities.Debug;
import main.utilities.Loadable;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class RenderableObject extends GameObject implements Loadable {

    //region <Variables>
    protected DrawLayer drawLayer;
    protected float alpha = 1f;
    protected int width;
    protected int height;
    protected String imagePath;
    protected BufferedImage image;
    protected Animator animator;
    //endregion

    //region <Construction and Initialization>
    public RenderableObject() {
        super();
        image = null;
        imagePath = "";
        width = 0;
        height = 0;
        drawLayer = DrawLayer.Background;
    }

    public RenderableObject(int x, int y) {
        super(x,y);
        image = null;
        imagePath = "";
        width = 0;
        height = 0;
        drawLayer = DrawLayer.Background;
    }

    public RenderableObject(int x, int y, DrawLayer layer) {
        super(x,y);
        this.imagePath = imagePath;
        drawLayer = layer;
    }

    public RenderableObject(int x, int y, BufferedImage image, DrawLayer layer) {
        super(x,y);
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        drawLayer = layer;
    }

    public RenderableObject(int x, int y, String imagePath, DrawLayer layer) {
        super(x,y);
        this.imagePath = imagePath;
        drawLayer = layer;
    }

    public RenderableObject(int x, int y, String imagePath, DrawLayer layer, float alpha) {
        super(x,y);
        this.imagePath = imagePath;
        this.drawLayer = layer;
        this.alpha = alpha;
    }
    //endregion

    //region <Getters and Setters>
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
    public float getAlpha() {
        return alpha;
    }

    public void setCurrentImage(BufferedImage currentImage) {
        this.image = currentImage;
//        width = currentImage.getWidth();
//        height = currentImage.getHeight();
    }

    public DrawLayer getDrawLayer() {
        return drawLayer;
    }

    public void setDrawLayer(DrawLayer drawLayer) {
        this.drawLayer = drawLayer;
    }

    public String getImagePath() {
        return imagePath;
    }

    public BufferedImage getCurrentImage() {
        return image;
    }

    public Rectangle2D getBoundingBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public void setWidth(int w){
        width = w;
    }
    public int getWidth() {
        return width;
    }

    public void setHeight(int h){
        height = h;
    }
    public int getHeight() {
        return height;
    }


    public void setSize(int w, int h){
        width = w;
        height = h;
    }
    //endregion

    public void draw(Graphics2D graphics) {
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        graphics.setComposite(alphaComposite);
        if(animator != null){
            animator.animate();
        }
        Debug.drawRect(true,graphics, new Rectangle2D.Double(x,y,width, height));
        graphics.drawImage(image, x , y, width, height, null);
    }

    public void load() {
        if(image == null){
            image = AssetLoader.load(imagePath);
            if(width != 0 && height != 0) {
                setSize(width, height);
            } else {
                setSize(image.getWidth(), image.getHeight());
            }
        }
        if(animator != null){
            animator.load();
        }
    }

    @Override
    public boolean setActive(GameScreen screen){
        if(super.setActive(screen)){
            addToRenderables(screen);
            return true;
        }
        return false;
    }

    @Override
    public boolean setInactive(GameScreen screen){
        if(super.setInactive(screen)){
            screen.renderables.remove(this);
            return true;
        }
        return false;
    }

    @Override
    public void addToScreen(GameScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        if(isActive) addToRenderables(screen);
        screen.loadables.add(this);
        if(animator != null){
            screen.loadables.add(animator);
        }
    }

    public void addAnimator(Animator animator){
        this.animator = animator;
    }

    private void addToRenderables(GameScreen screen){
        int i;
        final int SIZE = screen.renderables.size();
        for(i = 0; i < SIZE; i++){
            if(screen.renderables.get(i).getDrawLayer().ordinal() > drawLayer.ordinal()) break;
        }
        screen.renderables.add(i, this);
    }

    public abstract void update();

    public void scale(GraphicsSetting.GraphicsOption graphicsOption) {
        super.scale(graphicsOption);
        switch(graphicsOption){
            case High: { /*Do nothing this is our native res*/ break;}
            case Medium: { width *= 0.5;  height *= 0.5; break; }
//            case Low: {
//                width *= 3.556;
//                height *= 3.556;
//            }
        }
    }
}

