package gameengine.gamedata;

import java.io.Serializable;

public class GraphicsSetting implements Serializable {

    private GraphicsOption currentGraphicsOption;

    private static final float SCALE_FACTOR_HIGH = 1f;
    private static final float SCALE_FACTOR_MEDIUM = 0.6666f;
    private static final float SCALE_FACTOR_LOW = 0.5f;
    private float scaleFactor = 1;

    private int renderWidth = 1920;
    private int renderHeight = 1080;

    public enum GraphicsOption implements Serializable { High, Medium, Low }

    public GraphicsSetting(GraphicsOption setting) { setCurrentOption(setting); }

    public GraphicsOption getCurrentOption(){
        return currentGraphicsOption;
    }

    public void setCurrentOption(GraphicsOption setting){
        this.currentGraphicsOption = setting;
        switch(setting){
            case High: renderWidth = 1920; renderHeight = 1080; scaleFactor = SCALE_FACTOR_HIGH; break;
            case Medium: renderWidth = 1280; renderHeight = 720; scaleFactor = SCALE_FACTOR_MEDIUM; break;
            case Low: renderWidth = 960; renderHeight = 540; scaleFactor = SCALE_FACTOR_LOW; break;
        }
    }

    public int getRenderWidth() { return renderWidth; }
    public int getRenderHeight() { return renderHeight; }
    public float getScaleFactor() { return scaleFactor; }
}
