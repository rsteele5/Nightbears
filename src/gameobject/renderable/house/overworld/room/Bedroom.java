package gameobject.renderable.house.overworld.room;


public class Bedroom extends Room {

    public Bedroom() {
        super("Bedroom");
    }

    @Override
    protected Integer[][] constructLayout() {
        return new Integer[][]{
                new Integer[]{9,2,2,2,2,2,3},
                new Integer[]{8,1,1,1,1,1,4},
                new Integer[]{8,1,1,1,1,1,4},
                new Integer[]{8,1,1,1,1,1,4},
                new Integer[]{7,6,6,6,6,6,5}
        };
    }

    @Override
    protected void initializeSpawnPoints() {
        spawnPoints.add(new SpawnPoint(1,1, SpawnType.Player, this));
        spawnPoints.add(new SpawnPoint(2,1, SpawnType.Vendor,this));
        spawnPoints.add(new SpawnPoint(0,0, SpawnType.Objcect,this));
        spawnPoints.add(new SpawnPoint(4,0, SpawnType.Objcect,this));
        spawnPoints.add(new SpawnPoint(4,4, SpawnType.Objcect,this));
    }
}
