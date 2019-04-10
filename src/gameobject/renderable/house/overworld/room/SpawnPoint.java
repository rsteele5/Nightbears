package gameobject.renderable.house.overworld.room;

public class SpawnPoint {
    private int tileX;
    private int tileY;
    private SpawnType spawnType;

    public SpawnPoint(int tileX, int tileY, SpawnType type){
        this.tileX = tileX;
        this.tileY = tileY;
        this.spawnType = type;
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
