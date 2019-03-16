package gameengine.gamedata;

import java.io.Serializable;

public class GraphicsSetting implements Serializable {

    private GraphicsOption currentGraphicsSetting;

    public enum GraphicsOption implements Serializable {
        High,
        Medium,
        Low
    }

    public GraphicsSetting(GraphicsOption setting) {
        this.currentGraphicsSetting = setting;
    }

    public GraphicsOption getCurrentOption(){
        return currentGraphicsSetting;
    }

    public void setCurrentOption(GraphicsOption setting){
        this.currentGraphicsSetting = setting;
    }
}
