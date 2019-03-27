package gameobject.renderable.house.overworld.room;


public class Bedroom extends Room {

    public Bedroom() {
        super("Bedroom");
    }

    @Override
    protected Integer[][] constructLayout() {
        return new Integer[][]{
                new Integer[]{9,2,2,2,2,2,2,2,2,2,2,3},
                new Integer[]{8,1,1,1,1,1,1,1,1,1,1,4},
                new Integer[]{8,1,1,1,1,1,1,1,1,1,1,4},
                new Integer[]{8,1,1,1,1,1,1,1,1,1,1,4},
                new Integer[]{8,1,1,1,1,1,1,1,1,1,1,4},
                new Integer[]{7,6,6,6,6,6,1,6,6,6,6,5},
                new Integer[]{9,2,2,1,1,1,1,1,1,1,1,4},
                new Integer[]{8,1,1,1,1,1,1,1,1,1,1,4},
                new Integer[]{8,1,1,1,1,1,1,1,9,2,2,2},
                new Integer[]{8,1,1,1,1,1,1,1,8,0,0,0},
                new Integer[]{7,6,6,6,6,6,6,6,8,0,0,0}
        };
    }

    @Override
    public void initializeSpawnPoints() {
        createSpawnPoint(1,1, SpawnType.Player);
        createSpawnPoint(2,1, SpawnType.Vendor);
    }

    @Override
    public void update() {}
}
