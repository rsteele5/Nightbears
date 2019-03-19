package gameobject.renderable.house.overworld;

import gameobject.renderable.house.overworld.room.Room;
import gameobject.renderable.house.overworld.room.SpawnPoint;
import gameobject.renderable.house.overworld.room.SpawnType;
import gamescreen.GameScreen;
import gamescreen.container.GridContainer;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.util.ArrayList;

import static gameobject.renderable.house.overworld.OverworldMeta.*;

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

    //TODO: CHANGE THIS
    public SpawnPoint getPlayerSpawn(){
        for(Room room : rooms){
            if(room.getPlayerSpawnOptions() != null){
                SpawnPoint tempSpawn = room.getPlayerSpawnOptions().get(0);
                SpawnPoint playerSpawn = new SpawnPoint(
                        tempSpawn.getTileX()*TileSize + room.getCellX()*TileSize + (ChunkSize*TileSize*BorderBuffer),
                        tempSpawn.getTileY()*TileSize + room.getCellY()*TileSize + (ChunkSize*TileSize*BorderBuffer),
                        SpawnType.Player,room);
                return playerSpawn;
            }
        }
        return null;
    }
    //TODO: CHANGE THIS
    public SpawnPoint getVendorSpawn(){
        for(Room room : rooms){
            if(room.getVendorSpawnOptions() != null){
                SpawnPoint tempSpawn = room.getVendorSpawnOptions().get(0);
                SpawnPoint vendorSpawn = new SpawnPoint(
                        tempSpawn.getTileX()*TileSize+TileSize/2 + room.getCellX()*TileSize + (ChunkSize*TileSize*BorderBuffer),
                        tempSpawn.getTileY()*TileSize+TileSize/2 + room.getCellY()*TileSize + (ChunkSize*TileSize*BorderBuffer),
                        SpawnType.Vendor,room);
                return vendorSpawn;
            }
        }
        return null;
    }

    //TODO: CHANGE THIS
    public ArrayList<SpawnPoint> getObjectSpawns(){
        for(Room room : rooms){
            if(room.getObjectSpawnOptions() != null){
                ArrayList<SpawnPoint> objectSpawns = new ArrayList<>();
                for(SpawnPoint spawn : room.getObjectSpawnOptions()) {
                    SpawnPoint objectSpawn = new SpawnPoint(
                            spawn.getTileX() * TileSize + TileSize / 2 + room.getCellX() * TileSize + (ChunkSize * TileSize * BorderBuffer),
                            spawn.getTileY() * TileSize + TileSize / 2 + room.getCellY() * TileSize + (ChunkSize * TileSize * BorderBuffer),
                            SpawnType.Objcect, room);
                    objectSpawns.add(objectSpawn);
                }
                return objectSpawns;
            }
        }
        return null;
    }
}
