package gameobject.renderable.house.overworld;

import gameobject.renderable.house.overworld.room.Room;
import gamescreen.GameScreen;
import gamescreen.container.GridContainer;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.util.ArrayList;

public class MapBuilder {

    private ChunkBuilder chunkBuilder;
    private ArrayList<ArrayList<GridContainer>> chunks;
    private ArrayList<Room> rooms;
    private GameScreen parentScreen;



    public void createMap(GameScreen parentScreen){
        Debug.log(DebugEnabler.OVERWORLD, "MapBuilder - Start creating Map");
        this.parentScreen = parentScreen;
        rooms = new ArrayList<>();
        chunks = new ArrayList<>();
        chunkBuilder = new ChunkBuilder();
        //Create initial Border
        for(int row = 0; row < OverworldMeta.BorderBuffer*2; row++){
            chunks.add(new ArrayList<>());
            for(int col = 0; col < OverworldMeta.BorderBuffer*2; col++){
                chunkBuilder.createNewChunk(parentScreen);
                chunkBuilder.fillWithGrass();
                chunks.get(row).add(chunkBuilder.getChunk());
                chunks.get(row).get(col).setLocation(
                        (OverworldMeta.TileSize*OverworldMeta.ChunkSize) * col,
                        (OverworldMeta.TileSize*OverworldMeta.ChunkSize) * row);
            }
        }
    }

    public Map buildMap(){
        Debug.success(DebugEnabler.OVERWORLD, "MapBuilder - Built Map");
        return new Map(parentScreen, rooms, chunks);
    }

    public void addRoomAtCell(int x, int y, Room newRoom){
        newRoom.setCell(x,y);
        for(Room room : rooms){
            if(room.isConflicting(newRoom)){
                Debug.error(DebugEnabler.OVERWORLD, newRoom.getName() + " - is conflicting with: " + room.getName());
                return;
            }
        }
        //TODO: Start Back UP working here~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    }
}
