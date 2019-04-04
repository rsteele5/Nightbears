package gameobject.renderable.house.overworld;

import gameobject.container.GridIndex;
import gameobject.renderable.house.overworld.room.Boundary;
import gameobject.renderable.house.overworld.room.Door;
import gameobject.renderable.house.overworld.room.Room;
import gameobject.container.TileGridContainer;
import gamescreen.GameScreen;
import main.utilities.Action;
import main.utilities.Debug;
import main.utilities.DebugEnabler;


import static gameobject.renderable.house.overworld.OverworldMeta.*;
import static gameobject.renderable.house.overworld.OverworldMeta.Tiles.EMPTY;
import static gameobject.renderable.house.overworld.OverworldMeta.Tiles.House.*;

import java.io.Serializable;
import java.util.ArrayList;

public class MapBuilder implements Serializable {
    private GameScreen parentScreen;
    private ChunkBuilder chunkBuilder;
    private ArrayList<ArrayList<TileGridContainer>> chunks;
    private ArrayList<Room> rooms;



    public void createMap(GameScreen parentScreen){
        Debug.log(DebugEnabler.OVERWORLD, "MapBuilder - Start creating Map");
        this.parentScreen = parentScreen;
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
        fillEmptySpace(noBorderMap);
        return new Map(rooms, chunks);
    }

    //region<Support Functions>

    //region<Tile Structure>
    private ArrayList<ArrayList<TileGridContainer>> buildMapStructure(){
        // Find the farthest cell from the origin
        int maxCellX = ChunkSize;
        int maxCellY = ChunkSize;
        for(Room room : rooms){
            maxCellX = maxCellX < (room.getCellRow()+room.getHeight()) ? (room.getCellRow()+room.getHeight()) : maxCellX;
            maxCellY = maxCellY < (room.getCellCol()+room.getWidth()) ? (room.getCellCol()+room.getWidth()) : maxCellY;
        }
        // Calculate the max chunks needed
        int chunkRows = roundUpToChunk(maxCellX) / ChunkSize;
        int chunkCols = roundUpToChunk(maxCellY) / ChunkSize;

        // Build Chunks and put them into the chunks array list
        ArrayList<ArrayList<TileGridContainer>> noBorderChunks = new ArrayList<>();
        for(int row = 0; row < chunkRows+BorderBuffer*2; row++) {
            chunks.add(new ArrayList<>());
            if(row >= BorderBuffer && row < chunkRows+BorderBuffer)
                noBorderChunks.add(new ArrayList<>());
            for(int col = 0; col < chunkCols+BorderBuffer*2; col++) {
                if(row >= BorderBuffer && row < chunkRows+BorderBuffer
                        && col >= BorderBuffer && col < chunkCols+BorderBuffer){
                    // Create an empty chunk
                    chunkBuilder.createChunk();
                    chunks.get(row).add(chunkBuilder.getChunk());
                    noBorderChunks.get(row-BorderBuffer).add(chunks.get(row).get(col));
                } else {
                    chunkBuilder.createChunk();
                    chunkBuilder.fillWithGrass();
                    chunks.get(row).add(chunkBuilder.getChunk());
                }
            }
        }
        return noBorderChunks;
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
                cellX = room.getCellRow() + row;
                for(int col = 0; col < room.getWidth(); col++) {
                    cellY = room.getCellCol() + col;
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

    private void fillEmptySpace(ArrayList<ArrayList<TileGridContainer>> noBorderMap) {
        for(ArrayList<TileGridContainer> row : noBorderMap){
            for(TileGridContainer chunk : row){
                chunkBuilder.editChunk(chunk);
                chunkBuilder.fillWithGrass();
            }
        }
    }
    //endregion

    //region<Room Object Generation>
    private void generateRoomObjects() {
        for(Room room : rooms){
            room.initializeRoom();
            connectRoomsByDoors(room, room.getDoors());
            for(int row = 0; row < room.getHeight(); row++) {
                for (int col = 0; col < room.getWidth(); col++) {
                    switch (room.getLayout()[row][col]) {
                        case WALLNW:
                        case WALLNWC:
                        case WALLNE:
                        case WALLNEC:
                        case WALLNCW:
                        case WALLNCE:
                        case WALLN: createNorthWall(room, row, col);
                            break;
                        case WALLSE:
                        case WALLSEC:
                        case WALLSW:
                        case WALLSWC:
                        case WALLSCW:
                        case WALLSCE:
                        case WALLS: createSouthWall(room, row, col);
                            break;
                        case WALLECN:
                        case WALLECS:
                        case WALLE: createEastWall(room, row, col);
                            break;
                        case WALLWCN:
                        case WALLWCS:
                        case WALLW: createWestWall(room, row, col);
                            break;
                        default: break;
                    }
                }
            }
        }
    }

    private void connectRoomsByDoors(Room room, ArrayList<Door> doors) {
        doors.forEach(door -> {
            Tile otherRoomTile = null;
            GridIndex chunkIndex = getChunkIndexOf(door.getReferenceTile());
            if(chunkIndex != null) {
                GridIndex relativeIndex = chunks.get(chunkIndex.row).get(chunkIndex.col)
                                                .getGridIndexOf(door.getReferenceTile());
                switch (door.getAttachedDirection()) {
                    case North:
                        if (relativeIndex.row - 1 < 0 && chunkIndex.row - 1 >= BorderBuffer) {
                            otherRoomTile = chunks.get(chunkIndex.row - 1).get(chunkIndex.col)
                                    .getContentAt(ChunkSize - 1, relativeIndex.col);
                        } else {
                            otherRoomTile = chunks.get(chunkIndex.row).get(chunkIndex.col)
                                    .getContentAt(relativeIndex.row - 1, relativeIndex.col);
                        } break;
                    case South:
                        if (relativeIndex.row + 1 >= ChunkSize && chunkIndex.row + 1 < chunks.size()-BorderBuffer) {
                            otherRoomTile = chunks.get(chunkIndex.row + 1).get(chunkIndex.col)
                                    .getContentAt(0, relativeIndex.col);
                        } else {
                            otherRoomTile = chunks.get(chunkIndex.row).get(chunkIndex.col)
                                    .getContentAt(relativeIndex.row + 1, relativeIndex.col);
                        } break;
                    case East:
                        if (relativeIndex.col + 1 >= ChunkSize
                                && chunkIndex.col + 1 < chunks.get(chunkIndex.row).size()-BorderBuffer) {
                            otherRoomTile = chunks.get(chunkIndex.row).get(chunkIndex.col + 1)
                                    .getContentAt(relativeIndex.row, 0);
                        } else {
                            otherRoomTile = chunks.get(chunkIndex.row).get(chunkIndex.col)
                                    .getContentAt(relativeIndex.row, relativeIndex.col + 1);
                        } break;
                    case West:
                        if (relativeIndex.col - 1 < 0 && chunkIndex.col - 1 >= BorderBuffer) {
                            otherRoomTile = chunks.get(chunkIndex.row).get(chunkIndex.col - 1)
                                    .getContentAt(relativeIndex.row, ChunkSize - 1);
                        } else {
                            otherRoomTile = chunks.get(chunkIndex.row).get(chunkIndex.col)
                                    .getContentAt(relativeIndex.row, relativeIndex.col - 1);
                        } break;
                }
            }

            if(otherRoomTile != null)
                for(Room otherRoom : rooms)
                    if (otherRoom != room)
                        if (otherRoom.containsTile(otherRoomTile)){}
//                            Action action =
//                            door.setOpenOperation(() -> {
//                                otherRoom.setActive(parentScreen);
//                                door.setInactive(parentScreen);
//                            });
        });
    }

    private GridIndex getChunkIndexOf(Tile referenceTile) {
        for(int row = 0; row < chunks.size(); row++)
            for(int col = 0; col < chunks.get(row).size(); col++)
                if(chunks.get(row).get(col).getGridIndexOf(referenceTile) != null) return new GridIndex(row,col);
        return null;

    }

    private void createNorthWall(Room room, int iRow, int iCol) {
        final Integer[] row = room.getLayout()[iRow];
        final Tile startTile = room.getRoomTileAt(iRow, iCol);
        int x = startTile.getX();
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
                    case WALLNE:
                        width += TileSize;
                        row[col] = WALLE;
                        createEastWall(room, iRow, col);
                        break;
                    case WALLNWC:
                        width += WallThickness;
                        row[col] = -WALLNWC;
                        done = true;
                        break;
                    case WALLNEC:
                        if(col != iCol) done = true;
                        else {
                            x += TileSize-WallThickness;
                            width += WallThickness;
                            row[col] = -WALLNEC;
                        }
                        break;
                    case WALLNCW:
                    case WALLNCE: done = true;
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
        room.addBoundary(new Boundary(x, startTile.getY(), width, WallThickness));
    }

    private void createSouthWall(Room room, int iRow, int iCol) {
        final Integer[] row = room.getLayout()[iRow];
        final Tile startTile = room.getRoomTileAt(iRow, iCol);
        int x = startTile.getX();
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
                case WALLSWC:
                    width += WallThickness;
                    row[col] = -WALLSWC;
                    done = true;
                    break;
                case WALLSEC:
                    if(col != iCol) done = true;
                    else {
                        x += TileSize-WallThickness;
                        width += WallThickness;
                        row[col] = -WALLSEC;
                    }
                    break;
                case WALLSCW:
                case WALLSCE: done = true;
                case WALLS:
                    width += TileSize;
                    row[col] = -WALLS;
                    break;
                default: done = true;
                    break;
            }
            if(done) break;
        }
        room.addBoundary(new Boundary(x, startTile.getY() + TileSize - WallThickness,
                                      width, WallThickness));
    }

    private void createEastWall(Room room, int iRow, int iCol) {
        final Tile startTile = room.getRoomTileAt(iRow, iCol);
        int y = startTile.getY();
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
                case WALLNEC:
                    height += WallThickness;
                    room.getLayout()[row][iCol] = -WALLSWC;
                    done = true;
                    break;
                case WALLSEC:
                    if(row != iRow) done = true;
                    else {
                        y += TileSize-WallThickness;
                        height += WallThickness;
                        room.getLayout()[row][iCol] = -WALLSEC;
                    }
                    break;
                case WALLECN:
                case WALLECS: done = true;
                case WALLE:
                    height += TileSize;
                    room.getLayout()[row][iCol] = -WALLE;
                    break;
                default: done = true;
                    break;
            }
            if(done) break;
        }
        room.addBoundary(new Boundary(startTile.getX() + TileSize - WallThickness, y, WallThickness, height));
    }

    private void createWestWall(Room room, int iRow, int iCol) {
        final Tile startTile = room.getRoomTileAt(iRow, iCol);
        int y = startTile.getY();
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
                case WALLNWC:
                    height += WallThickness;
                    room.getLayout()[row][iCol] = -WALLSWC;
                    done = true;
                    break;
                case WALLSWC:
                    if(row != iRow) done = true;
                    else {
                        y += TileSize-WallThickness;
                        height += WallThickness;
                        room.getLayout()[row][iCol] = -WALLSEC;
                    }
                    break;
                case WALLWCN:
                case WALLWCS: done = true;
                case WALLW:
                    height += TileSize;
                    room.getLayout()[row][iCol] = -WALLW;
                    break;
                default: done = true;
                    break;
            }
            if(done) break;
        }
        room.addBoundary(new Boundary(startTile.getX(), y, WallThickness, height));
    }
    //endregion

    //endregion
}