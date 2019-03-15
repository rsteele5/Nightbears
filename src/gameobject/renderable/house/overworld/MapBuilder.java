package gameobject.renderable.house.overworld;

import gameobject.renderable.house.overworld.room.Room;
import gamescreen.GameScreen;
import gamescreen.container.GridContainer;
import main.utilities.Debug;
import main.utilities.DebugEnabler;
import static gameobject.renderable.house.overworld.OverworldMeta.*;

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
    }

    public Map buildMap(){
        Debug.success(DebugEnabler.OVERWORLD, "MapBuilder - Built Map");
        // Find the farthest cell from the origin
        int maxCellX = 0;
        int maxCellY = 0;
        for(Room room : rooms){
            maxCellX = maxCellX < (room.getCellX()+room.getHeight()) ? (room.getCellX()+room.getHeight()) : maxCellX;
            maxCellY = maxCellY < (room.getCellY()+room.getWidth()) ? (room.getCellY()+room.getWidth()) : maxCellY;
        }
        // Calculate the max chunks needed
        int chunkRows = (maxCellX + (ChunkSize - (maxCellX % ChunkSize))) / ChunkSize;
        int chunkCols = (maxCellY + (ChunkSize - (maxCellY % ChunkSize))) / ChunkSize;

        // Build Chunks and put them into the chunks array list
        for(int row = 0; row < chunkRows+BorderBuffer*2; row++) {
                chunks.add(row,new ArrayList<>());
            for(int col = 0; col < chunkCols+BorderBuffer*2; col++) {
                if(row >= BorderBuffer && row < chunkRows+BorderBuffer
                        && col >= BorderBuffer && col < chunkCols+BorderBuffer){
                    chunkBuilder.createNewChunk(parentScreen);
                    chunkBuilder.fillWithCarpet();
                    chunks.get(row).add(col,chunkBuilder.getChunk());
                } else {
                    chunkBuilder.createNewChunk(parentScreen);
                    chunkBuilder.fillWithGrass();
                    chunks.get(row).add(col,chunkBuilder.getChunk());
                }
            }
        }

        // Organize Chunks
        for(int row = 0; row < chunks.size(); row++){
            for(int col = 0; col < chunks.get(row).size(); col++){
                chunks.get(row).get(col).setLocation(
                        (TileSize * ChunkSize) * col,
                        (TileSize * ChunkSize) * row);
            }
        }

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
        rooms.add(newRoom);
        //TODO: Start Back UP working here~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    }
}
