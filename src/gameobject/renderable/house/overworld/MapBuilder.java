package gameobject.renderable.house.overworld;

import gameobject.renderable.house.overworld.room.Boundary;
import gameobject.renderable.house.overworld.room.Room;
import gameobject.container.TileGridContainer;
import main.utilities.Debug;
import main.utilities.DebugEnabler;


import static gameobject.renderable.house.overworld.OverworldMeta.*;
import static gameobject.renderable.house.overworld.OverworldMeta.Tiles.EMPTY;
import static gameobject.renderable.house.overworld.OverworldMeta.Tiles.House.*;

import java.util.ArrayList;

public class MapBuilder {

    private ChunkBuilder chunkBuilder;
    private ArrayList<ArrayList<TileGridContainer>> chunks;
    private ArrayList<Room> rooms;



    public void createMap(){
        Debug.log(DebugEnabler.OVERWORLD, "MapBuilder - Start creating Map");
        rooms = new ArrayList<>();
        chunks = new ArrayList<>();
        chunkBuilder = new ChunkBuilder();
    }

    public void addRoomAtCell(int x, int y, Room newRoom){
        newRoom.setCell(x,y);
        for(Room room : rooms){
            if(room.isConflicting(newRoom)){
                Debug.error(DebugEnabler.OVERWORLD, newRoom.getName() + " - is conflicting with: " + room.getName());
                newRoom.setCell(-1,-1);
                return;
            }
        }
        rooms.add(newRoom);
    }

    public Map buildMap() {
        Debug.success(DebugEnabler.OVERWORLD, "MapBuilder - Built Map");

        // Build the Map Structure and returns a chunk grid that needs room data
        ArrayList<ArrayList<TileGridContainer>> noBorderMap = buildMapStructure();
        organizeChunks();
        roomToChunkConverter(noBorderMap);
        generateRoomObjects();
        return new Map(rooms, chunks);
    }

    private ArrayList<ArrayList<TileGridContainer>> buildMapStructure(){
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
        ArrayList<ArrayList<TileGridContainer>> noBorderMap = new ArrayList<>();
        for(int row = 0; row < chunkRows+BorderBuffer*2; row++) {
            chunks.add(new ArrayList<>());
            if(row >= BorderBuffer && row < chunkRows+BorderBuffer)
                noBorderMap.add(new ArrayList<>());
            for(int col = 0; col < chunkCols+BorderBuffer*2; col++) {
                if(row >= BorderBuffer && row < chunkRows+BorderBuffer
                        && col >= BorderBuffer && col < chunkCols+BorderBuffer){
                    // Create an empty chunk
                    chunkBuilder.createChunk();
                    chunks.get(row).add(chunkBuilder.getChunk());
                    noBorderMap.get(row-BorderBuffer).add(chunks.get(row).get(col));
                } else {
                    chunkBuilder.createChunk();
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

    private void roomToChunkConverter(ArrayList<ArrayList<TileGridContainer>> map) {
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
                    boolean empty = false;
                    Compass wallNS = null;
                    Compass wallEW = null;
                    boolean endcap = false;
                    Compass capDirection = null;

                    switch(room.getLayout()[row][col]){
                        //The Void...
                        case EMPTY: empty = true; break;
                        //Just Carpet...
                        case CARPET: break;

                        //Setup North Wall Variants
                        case WALLNCW: capDirection = Compass.West;
                        case WALLNCE:
                            if(capDirection == null) capDirection = Compass.East;
                            endcap = true;
                        case WALLN : wallNS = Compass.North; break;

                        //Setup East Wall Variants
                        case WALLECN: capDirection = Compass.North;
                        case WALLECS:
                            if(capDirection == null) capDirection = Compass.South;
                            endcap = true;
                        case WALLE : wallEW = Compass.East; break;

                        //Setup South Wall Variants
                        case WALLSCE: capDirection = Compass.East;
                        case WALLSCW:
                            if(capDirection == null) capDirection = Compass.West;
                            endcap = true;
                        case WALLS : wallNS = Compass.South; break;

                        //Setup West Wall Variants
                        case WALLWCS: capDirection = Compass.South;
                        case WALLWCN:
                            if(capDirection == null) capDirection = Compass.North;
                            endcap = true;
                        case WALLW : wallEW = Compass.West; break;

                        //Setup NorthEast Wall Variants
                        case WALLNEC: endcap = true;
                        case WALLNE:
                            wallNS = Compass.North;
                            wallEW = Compass.East;
                            break;

                        //Setup SouthEast Wall Variants
                        case WALLSEC: endcap = true;
                        case WALLSE:
                            wallNS = Compass.South;
                            wallEW = Compass.East;
                            break;

                        //Setup SouthWest Wall Variants
                        case WALLSWC: endcap = true;
                        case WALLSW:
                            wallNS = Compass.South;
                            wallEW = Compass.West;
                            break;

                        //Setup NorthWest Wall Variants
                        case WALLNWC: endcap = true;
                        case WALLNW:
                            wallNS = Compass.North;
                            wallEW = Compass.West;
                            break;
                        default: break;
                    }
                    if(empty){
                      chunkBuilder.removeAt(chunkX, chunkY);
                    } else if(wallNS != null || wallEW != null){
                        chunkBuilder.addHouseTileAt(chunkX, chunkY, wallNS, wallEW, endcap, capDirection);
                    } else chunkBuilder.addHouseTileAt(chunkX, chunkY);

                    room.setTile(row,col, map.get(chunkRow).get(chunkCol).getContentAt(chunkX,chunkY));
                }
            }
        }
    }

    private void generateRoomObjects() {
        for(Room room : rooms){
            room.initializeSpawnPoints();
            for(int row = 0; row < room.getHeight(); row++) {
                for (int col = 0; col < room.getWidth(); col++) {
                    switch (room.getLayout()[row][col]) {
                        case WALLNW:
                        case WALLNWC:
                        case WALLNE:
                        case WALLNEC:
                        //case WALLNC:
                        case WALLN: createNorthWall(room, row, col); break;
                        case WALLSE:
                        case WALLSEC:
                        case WALLSW:
                        case WALLSWC:
                        //case WALLSC:
                        case WALLS: createSouthWall(room, row, col); break;
                        //case WALLEC:
                        case WALLE: createEastWall(room, row, col);  break;
                        //case WALLWC:
                        case WALLW: createWestWall(room, row, col);  break;
                        default: break;
                    }
                }
            }
        }
    }

    private void createNorthWall(Room room, int iRow, int iCol) {
        final Integer[] row = room.getLayout()[iRow];
        final Tile startTile = room.getRoomTileAt(iRow, iCol);
        int x = startTile.getX();
        int y = startTile.getY();
        int width = 0;
        boolean done = false;

        for (int col = iCol; col < room.getWidth(); col++){
            if(!done) {
                switch (row[col]) {
                    case WALLNW:
                        width += TileSize;
                        row[col] = WALLW;
                        createWestWall(room, iRow, col);
                        break;
                    case WALLNWC:
                        width += WallThickness;
                        row[col] = -WALLNWC;
                        done = true;
                        break;
                    case WALLNE:
                        width += TileSize;
                        row[col] = WALLE;
                        createEastWall(room, iRow, col);
                        break;
                    case WALLNEC:
                        //TODO: Adjust position
                        width += WallThickness;
                        row[col] = -WALLNEC;
                        break;
                    case WALLN:
                        width += TileSize;
                        row[col] = -WALLN;
                        break;
                    default:
                        done = true;
                        break;
                }
            }else break;
        }
        room.addBoundary(new Boundary(startTile.getX(), startTile.getY(), width, WallThickness));
    }

    private void createSouthWall(Room room, int iRow, int iCol) {
        final Integer[] row = room.getLayout()[iRow];
        final Tile startTile = room.getRoomTileAt(iRow, iCol);
        int width = 0;
        boolean done = false;

        for (int col = iCol; col < room.getWidth(); col++){
            switch(row[col]){
                case WALLSW:
                    width += TileSize;
                    row[col] = WALLW;
                    createWestWall(room, iRow, col);
                    break;
                case WALLSE:
                    width += TileSize;
                    row[col] = WALLE;
                    createEastWall(room, iRow, col);
                    break;
                case WALLS:
                    width += TileSize;
                    row[col] = -WALLS;
                    break;
                default: done = true;
                    break;
            }
            if(done) break;
        }
        room.addBoundary(new Boundary(startTile.getX(), startTile.getY() + TileSize - WallThickness,
                                      width, WallThickness));
    }

    private void createEastWall(Room room, int iRow, int iCol) {
        final Tile startTile = room.getRoomTileAt(iRow, iCol);
        int height = 0;
        boolean done = false;

        for (int row = iRow; row < room.getHeight(); row++){
            switch(room.getLayout()[row][iCol]){
                case WALLNE:
                    height += TileSize;
                    room.getLayout()[row][iCol] = WALLN;
                    createNorthWall(room, row, iCol);
                    break;
                case WALLSE:
                    height += TileSize;
                    room.getLayout()[row][iCol] = WALLS;
                    createSouthWall(room, row, iCol);
                    break;
                case WALLE:
                    height += TileSize;
                    room.getLayout()[row][iCol] = -WALLE;
                    break;
                default: done = true;
                    break;
            }
            if(done) break;
        }
        room.addBoundary(new Boundary(startTile.getX() + TileSize - WallThickness, startTile.getY(),
                                      WallThickness, height));
    }

    private void createWestWall(Room room, int iRow, int iCol) {
        final Tile startTile = room.getRoomTileAt(iRow, iCol);
        int height = 0;
        boolean done = false;

        for (int row = iRow; row < room.getHeight(); row++){
            switch(room.getLayout()[row][iCol]){
                case WALLNW:
                    height += TileSize;
                    room.getLayout()[row][iCol] = WALLN;
                    createNorthWall(room, row, iCol);
                    break;
                case WALLSW:
                    height += TileSize;
                    room.getLayout()[row][iCol] = WALLS;
                    createSouthWall(room, row, iCol);
                    break;
                case WALLW:
                    height += TileSize;
                    room.getLayout()[row][iCol] = -WALLW;
                    break;
                default: done = true;
                    break;
            }
            if(done) break;
        }
        room.addBoundary(new Boundary(startTile.getX(), startTile.getY(), WallThickness, height));
    }
}