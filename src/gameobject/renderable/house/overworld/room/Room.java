package gameobject.renderable.house.overworld.room;

import gameobject.GameObject;
import gameobject.renderable.house.overworld.Tile;
import gamescreen.GameScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.util.ArrayList;

public abstract class Room extends GameObject {

    //region <Variables>
    protected String name;
    protected int cellX;
    protected int cellY;
    protected Integer[][] layout;
    protected int width;
    protected int height;
    protected Tile[][] roomTiles;

    protected ArrayList<SpawnPoint> spawnPoints;
    protected ArrayList<Boundary> boundaries;
    //endregion

    //region <Construction and Initialization>
    public Room(String name){
        this.name = "Room: "+name;
        cellX = -1;
        cellY = -1;
        layout = constructLayout();
        roomTiles = new Tile[layout.length][layout[0].length];
        width = layout[0].length;
        height = layout.length;
        boundaries = new ArrayList<>();
        spawnPoints = new ArrayList<>();
    }

    protected abstract Integer[][] constructLayout();

    /**
     * Creates all of the spawn points in this room. This is called when you add this room to a Map using the
     * MapBuilder.
     * @see gameobject.renderable.house.overworld.Map
     * @see gameobject.renderable.house.overworld.MapBuilder
     */
    public abstract void initializeSpawnPoints();
    //endregion

    //region <Getters and Setters>
    public String getName() {
        return name;
    }

    public int getCellX() {
        return cellX;
    }

    public int getCellY() {
        return cellY;
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

    public void setCell(int x, int y) {
        cellX = x;
        cellY = y;
    }

    public void setTile(int row, int col, Tile content) {
        roomTiles[row][col] = content;
    }
    //endregion

    //region <Support Functions>
    public boolean isConflicting(Room newRoom) {
        if(cellX >= 0 && cellY >= 0){
            if(newRoom.cellX >= 0 && newRoom.cellY >= 0){
                if((newRoom.cellX >= cellX && newRoom.cellY >= cellY)
                        && (newRoom.cellY < cellY + layout.length && newRoom.cellX < cellX + layout[0].length)) {
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
    //endregion



    //region <GameObject Overrides>
    @Override
    public boolean setActive(GameScreen screen){
        if(super.setActive(screen)){
            for (Boundary boundary : boundaries) {
                boundary.setActive(screen);
            }return true;
        }return false;
    }

    @Override
    public boolean setInactive(GameScreen screen){
        if(super.setInactive(screen)){
            for (Boundary boundary : boundaries) {
                boundary.setInactive(screen);
            }return true;
        }return false;
    }

    @Override
    public void addToScreen(GameScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        for (Boundary boundary : boundaries) {
            boundary.addToScreen(screen, isActive);
        }
    }
    //endregion
}
