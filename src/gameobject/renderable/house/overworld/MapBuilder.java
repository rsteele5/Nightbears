package gameobject.renderable.house.overworld;

import gameobject.renderable.house.overworld.room.Room;
import gamescreen.GameScreen;
import gamescreen.container.GridContainer;
import main.utilities.Debug;
import main.utilities.DebugEnabler;
import static gameobject.renderable.house.overworld.OverworldMeta.*;
import static gameobject.renderable.house.overworld.OverworldMeta.Tiles.House.*;

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

        // Build the Map Structure and returns a chunk grid that needs room data
        ArrayList<ArrayList<GridContainer>> noBorderMap = buildMapStructure();

        organizeChunks();
        roomToChunkConverter(noBorderMap);

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
    }

    private ArrayList<ArrayList<GridContainer>> buildMapStructure(){
        // Find the farthest cell from the origin
        int maxCellX = ChunkSize;
        int maxCellY = ChunkSize;
        for(Room room : rooms){
            maxCellX = maxCellX < (room.getCellX()+room.getHeight()) ? (room.getCellX()+room.getHeight()) : maxCellX;
            maxCellY = maxCellY < (room.getCellY()+room.getWidth()) ? (room.getCellY()+room.getWidth()) : maxCellY;
        }
        // Calculate the max chunks needed
        int chunkRows = roundUpToChunk(maxCellX) / ChunkSize;
        int chunkCols = roundUpToChunk(maxCellY) / ChunkSize;

        // Build Chunks and put them into the chunks array list
        ArrayList<ArrayList<GridContainer>> noBorderMap = new ArrayList<>();
        for(int row = 0; row < chunkRows+BorderBuffer*2; row++) {
            chunks.add(new ArrayList<>());
            if(row >= BorderBuffer && row < chunkRows+BorderBuffer)
                noBorderMap.add(new ArrayList<>());
            for(int col = 0; col < chunkCols+BorderBuffer*2; col++) {
                if(row >= BorderBuffer && row < chunkRows+BorderBuffer
                        && col >= BorderBuffer && col < chunkCols+BorderBuffer){
                    // Create an empty chunk
                    chunkBuilder.createChunk(parentScreen);
                    chunks.get(row).add(chunkBuilder.getChunk());
                    noBorderMap.get(row-BorderBuffer).add(chunks.get(row).get(col));
                } else {
                    chunkBuilder.createChunk(parentScreen);
                    chunkBuilder.fillWithGrass();
                    chunks.get(row).add(chunkBuilder.getChunk());
                }
            }
        }
        return noBorderMap;
    }

    private int roundUpToChunk(int num){
        if(num % ChunkSize == 0) return num;
        return num + (ChunkSize - (num % ChunkSize));
    }

    private void organizeChunks(){
        for(int row = 0; row < chunks.size(); row++){
            for(int col = 0; col < chunks.get(row).size(); col++){
                chunks.get(row).get(col).setPosition(
                        (TileSize * ChunkSize) * col,
                        (TileSize * ChunkSize) * row);
            }
        }
    }

    private void roomToChunkConverter(ArrayList<ArrayList<GridContainer>> map) {
        int cellX, cellY,
            chunkRow, chunkCol,
            chunkX, chunkY;

        for(Room room : rooms){
            // get cell location
            for(int row = 0; row < room.getHeight(); row++) {
                cellX = room.getCellX() + row;
                for(int col = 0; col < room.getWidth(); col++) {
                    cellY = room.getCellY() + col;
                    // get chunk location from cell
                    chunkRow = Math.floorDiv(cellX, ChunkSize);
                    chunkCol = Math.floorDiv(cellY, ChunkSize);
                    // get relative cell location in chunk
                    chunkX = cellX % ChunkSize;
                    chunkY = cellY % ChunkSize;
                    // edit chunk
                    chunkBuilder.editChunk(map.get(chunkRow).get(chunkCol));
                    Compass wallNS = null;
                    Compass wallEW = null;
                    switch(room.getLayout()[row][col]){
                        case CARPET: break;
                        case WALLN : wallNS = Compass.North; break;
                        case WALLE : wallEW = Compass.East; break;
                        case WALLS : wallNS = Compass.South; break;
                        case WALLW : wallEW = Compass.West; break;
                        case WALLNE:
                            wallNS = Compass.North;
                            wallEW = Compass.East;
                            break;
                        case WALLSE:
                            wallNS = Compass.South;
                            wallEW = Compass.East;
                            break;
                        case WALLSW:
                            wallNS = Compass.South;
                            wallEW = Compass.West;
                            break;
                        case WALLNW:
                            wallNS = Compass.North;
                            wallEW = Compass.West;
                            break;
                        default: break;
                    }
                    if(wallNS != null){
                        if(wallEW != null){
                            chunkBuilder.addHouseTileAt(chunkX, chunkY, wallNS, wallEW);
                        } else chunkBuilder.addHouseTileAt(chunkX, chunkY, wallNS);
                    } else if(wallEW != null){
                        chunkBuilder.addHouseTileAt(chunkX, chunkY, wallEW);
                    } else chunkBuilder.addHouseTileAt(chunkX, chunkY);
                }
            }
        }
    }
}
