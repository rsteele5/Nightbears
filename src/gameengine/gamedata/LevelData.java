package gameengine.gamedata;


import gameobject.renderable.house.overworld.Map;

import java.io.Serializable;

public class LevelData implements Serializable {

    private Map map;

    public Map getCurrentMap(){
        return map;
    }

    public void setCurrentMap(Map map){
        this.map = map;
    }
}
