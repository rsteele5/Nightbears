package gameobject.renderable.house.overworld.room;

import gameobject.GameObject;
import gameobject.renderable.house.overworld.Compass;
import gameobject.renderable.house.overworld.Tile;
import gamescreen.GameScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static gameobject.renderable.house.overworld.OverworldMeta.TileSize;
import static gameobject.renderable.house.overworld.OverworldMeta.WallThickness;

public abstract class Room extends GameObject {

    //region <Variables>
    protected String name;
    protected int cellCol;
    protected int cellRow;
    protected Integer[][] layout;
    protected int width;
    protected int height;
    protected Tile[][] roomTiles;

    protected ArrayList<SpawnPoint> spawnPoints;
    protected ArrayList<Boundary> boundaries;
    protected ArrayList<Door> doors;
    //endregion

    //region <Construction and Initialization>
    public Room(String name){
        super();
        this.name = "Room: "+name;
        cellCol = -1;
        cellRow = -1;
        layout = constructLayout();
        roomTiles = new Tile[layout.length][layout[0].length];
        width = layout[0].length;
        height = layout.length;
        boundaries = new ArrayList<>();
        doors = new ArrayList<>();
        spawnPoints = new ArrayList<>();
    }

    protected abstract Integer[][] constructLayout();

    /**
     * Creates all of the spawn points in this room. This is called when you add this room to a Map using the
     * BackgroundBuilder.
     * @see gameobject.renderable.house.overworld.Map
     * @see gameobject.renderable.house.overworld.MapBuilder
     */
    public abstract void initializeRoom();
    //endregion

    //region <Getters and Setters>
    public String getName() {
        return name;
    }

    public int getCellRow() {
        return cellRow;
    }

    public int getCellCol() {
        return cellCol;
    }

    public ArrayList<SpawnPoint> getPlayerSpawnOptions(){
        ArrayList<SpawnPoint> playerSpawnOptions = new ArrayList<>();
        for(SpawnPoint spawn : spawnPoints){
            if(spawn.getSpawnType() == SpawnType.Player)
                playerSpawnOptions.add(spawn);
        }
        if(playerSpawnOptions.isEmpty())
            return null;
        return playerSpawnOptions;
    }

    public ArrayList<SpawnPoint> getVendorSpawnOptions(){
        ArrayList<SpawnPoint> vendorSpawnOptions = new ArrayList<>();
        for(SpawnPoint spawn : spawnPoints){
            if(spawn.getSpawnType() == SpawnType.Vendor)
                vendorSpawnOptions.add(spawn);
        }
        if(vendorSpawnOptions.isEmpty())
            return null;
        return vendorSpawnOptions;
    }

    public Integer[][] getLayout() {
        return layout;
    }

    public Tile getRoomTileAt(int row, int col) {
        return roomTiles[row][col];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }

    public boolean containsTile(Tile tile){
        for(int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++)
                if(tile == roomTiles[r][c]) return true;
        } return false;
    }

    public Rectangle2D getBoundingBox() {
        return new Rectangle2D.Double(cellCol *TileSize, cellRow *TileSize, width, height);
    }

    public void setCell(int x, int y) {
        cellCol = x;
        cellRow = y;
    }

    public void setTile(int row, int col, Tile content) {
        roomTiles[row][col] = content;
    }
    //endregion

    //region <Support Functions>
    public boolean isConflicting(Room newRoom) {
        if(cellCol >= 0 && cellRow >= 0){
            if(newRoom.cellCol >= 0 && newRoom.cellRow >= 0){
                if(this.getBoundingBox().intersects(newRoom.getBoundingBox())){
                    //TODO: Check if the conflicting sections have irrelevant differences
                    return true;
                }
            }else{
                Debug.warning(DebugEnabler.OVERWORLD, newRoom.name
                        + " - is being compared, but does not have a set cell yet. Returning false. ");
            }
        }else{
            Debug.warning(DebugEnabler.OVERWORLD, name
                    + " - is trying to compare layouts, but does not have a set cell yet. Returning false. ");
        }
        return false;
    }

    public void addBoundary(Boundary boundary){
        boundaries.add(boundary);
    }

    protected void createSpawnPoint(int row, int col, SpawnType type){
        Tile spawnTile = roomTiles[row][col];
        spawnPoints.add(new SpawnPoint(spawnTile.getX(), spawnTile.getY(), type));
    }

    protected void createDoor(int row, int col, Compass attachedDirection) {
        Tile referenceTile = roomTiles[row][col];
        int doorX = referenceTile.getX();
        int doorY = referenceTile.getY();
        int interactX = doorX;
        int interactY = doorY;
        int interactW = TileSize;
        int interactH = TileSize;

        switch(attachedDirection){
            case South:
                doorY += TileSize - WallThickness;
                interactH += TileSize;
                break;
            case North:
                interactY -= TileSize;
                interactH += TileSize;
                break;
            case East:
                doorX += TileSize - WallThickness;
                interactW += TileSize;
                break;
            case West:
                interactX -= TileSize;
                interactW += TileSize;
                break;
        }

        doors.add(new Door(referenceTile, attachedDirection));
    }
    //endregion



    //region <GameObject Overrides>
    @Override
    public boolean setActive(GameScreen screen){
        if(super.setActive(screen)){
            boundaries.forEach(boundary -> boundary.setActive(screen));
            doors.forEach(door -> door.setActive(screen));
            for(Tile[] row : roomTiles){
                for(Tile tile : row)
                    if(tile != null) tile.setActive(screen);
            }return true;
        }return false;
    }

    @Override
    public boolean setInactive(GameScreen screen){
        if(super.setInactive(screen)){
            boundaries.forEach(boundary -> boundary.setInactive(screen));
            doors.forEach(door -> door.setInactive(screen));
            for(Tile[] row : roomTiles){
                for(Tile tile : row)
                    if(tile != null) tile.setInactive(screen);
            }return true;
        }return false;
    }

    @Override
    public void addToScreen(GameScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        boundaries.forEach(boundary -> boundary.addToScreen(screen, isActive));
        doors.forEach(door -> door.addToScreen(screen, isActive));
    }
    //endregion
}
