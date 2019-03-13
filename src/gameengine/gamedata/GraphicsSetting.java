package gameengine.gamedata;

import java.io.Serializable;

public class GraphicsSetting implements Serializable {

    private GraphicsOption currentGraphicsSetting;

    private int renderWidth = 1920;
    private int renderHeight = 1080;

    public enum GraphicsOption implements Serializable { High, Medium, Low }

    public GraphicsSetting(GraphicsOption setting) { setCurrentOption(setting); }

    public GraphicsOption getCurrentOption(){
        return currentGraphicsSetting;
    }

    public void setCurrentOption(GraphicsOption setting){
        this.currentGraphicsSetting = setting;
        switch(setting){
            case High: renderWidth = 1920; renderHeight = 1080; break;
            case Medium: renderWidth = 1280; renderHeight = 720; break;
            case Low: renderWidth = 640; renderHeight = 480; break;
        }
    }

    public int getRenderWidth() { return renderWidth; }
    public int getRenderHeight() { return renderHeight; }
}
