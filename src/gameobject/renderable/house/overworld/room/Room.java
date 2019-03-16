package gameobject.renderable.house.overworld.room;

import gameobject.renderable.player.Player;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.util.ArrayList;

public abstract class Room {

    //region <Variables>
    protected String name;
    protected int cellX;
    protected int cellY;
    protected Integer[][] layout;
    protected int width;
    protected int height;

    protected ArrayList<SpawnPoint> spawnPoints;

    //private ArrayList<Boundary> boundaries;
    //endregion

    public Room(String name){
        this.name = "Room: "+name;
        cellX = -1;
        cellY = -1;
        layout = constructLayout();
        width = layout[0].length;
        height = layout.length;
        spawnPoints = new ArrayList<>();
        initializeSpawnPoints();
    }

    protected abstract Integer[][] constructLayout();
    protected abstract void initializeSpawnPoints();

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

    public ArrayList<SpawnPoint> getObjectSpawnOptions(){
        ArrayList<SpawnPoint> ObjectSpawnOptions = new ArrayList<>();
        for(SpawnPoint spawn : spawnPoints){
            if(spawn.getSpawnType() == SpawnType.Objcect)
                ObjectSpawnOptions.add(spawn);
        }
        if(ObjectSpawnOptions.isEmpty())
            return null;
        return ObjectSpawnOptions;
    }

    public Integer[][] getLayout() {
        return layout;
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
}
