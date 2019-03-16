package gameobject.renderable.house.overworld.room;

public class SpawnPoint {
    private int tileX;
    private int tileY;
    private SpawnType spawnType;
    private Room room;

    public SpawnPoint(int tileX, int tileY, SpawnType type, Room room){
        this.tileX = tileX;
        this.tileY = tileY;
        this.spawnType = type;
        this.room = room;
    }

    public SpawnType getSpawnType() {
        return spawnType;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }
}
