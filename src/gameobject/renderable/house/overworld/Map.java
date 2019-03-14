package gameobject.renderable.house.overworld;

import gameobject.renderable.house.overworld.room.Room;
import gamescreen.GameScreen;
import gamescreen.container.GridContainer;

import java.util.ArrayList;

public class Map {

    private final GameScreen parentScreen;
    private GridContainer[][] chunkMap;
    private ArrayList<Room> rooms;

    public Map(GameScreen parentScreen, ArrayList<Room> rooms, ArrayList<ArrayList<GridContainer>> chunks){
        this.parentScreen = parentScreen;
        this.rooms = rooms;
        chunkMap = new GridContainer[chunks.size()][chunks.get(0).size()];
        //Concrete chunks into 2D array
        for(int row = 0; row < chunkMap.length; row++){
            for(int col = 0; col < chunkMap[0].length; col++){
                chunkMap[row][col] = chunks.get(row).get(col);
            }
        }
    }
}
