package gameobject.renderable.house.overworld;

import gameobject.GameObject;
import gameobject.renderable.house.overworld.room.Room;
import gameobject.renderable.house.overworld.room.SpawnPoint;
import gameobject.renderable.house.overworld.room.SpawnType;
import gamescreen.GameScreen;
import gameobject.container.TileGridContainer;

import java.util.ArrayList;

import static gameobject.renderable.house.overworld.OverworldMeta.*;

public class Map extends GameObject {

    private TileGridContainer[][] chunkMap;
    private ArrayList<Room> rooms;

    Map(ArrayList<Room> rooms, ArrayList<ArrayList<TileGridContainer>> chunks){
        super();
        this.rooms = rooms;
        chunkMap = new TileGridContainer[chunks.size()][chunks.get(0).size()];
        //Concrete chunks into 2D array
        for(int row = 0; row < chunkMap.length; row++){
            for(int col = 0; col < chunkMap[0].length; col++){
                chunkMap[row][col] = chunks.get(row).get(col);
            }
        }
    }

    //region <GameObject Overrides>
    @Override
    public boolean setActive(GameScreen screen){
        if(super.setActive(screen)){
            for (Room room : rooms) room.setActive(screen);
            for(TileGridContainer[] row : chunkMap){
                for(TileGridContainer chunk : row) chunk.setActive(screen);
            }return true;
        }return false;
    }

    @Override
    public boolean setInactive(GameScreen screen){
        if(super.setInactive(screen)){
            for (Room room : rooms) room.setInactive(screen);
            for(TileGridContainer[] row : chunkMap){
                for(TileGridContainer chunk : row) chunk.setInactive(screen);
            }return true;
        }return false;
    }

    @Override
    public void addToScreen(GameScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        for (Room room : rooms) room.addToScreen(screen, isActive);
        for(TileGridContainer[] row : chunkMap){
            for(TileGridContainer chunk : row) chunk.addToScreen(screen, isActive);
        }
    }

    @Override
    public void update() {}
    //endregion

    //**************************************************************************************************************
    //TODO: CHANGE THIS
    public SpawnPoint getPlayerSpawn(){
        for(Room room : rooms){
            if(room.getPlayerSpawnOptions() != null){
                SpawnPoint tempSpawn = room.getPlayerSpawnOptions().get(0);
                SpawnPoint playerSpawn = new SpawnPoint(
                        tempSpawn.getTileX()*TileSize + room.getCellX()*TileSize + (ChunkSize*TileSize*BorderBuffer),
                        tempSpawn.getTileY()*TileSize + room.getCellY()*TileSize + (ChunkSize*TileSize*BorderBuffer),
                        SpawnType.Player);
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
                        SpawnType.Vendor);
                return vendorSpawn;
            }
        }
        return null;
    }
}
