package gameobject.renderable.house.overworld.room;


public class Bedroom extends Room {

    public Bedroom() {
        super("Bedroom");
    }

    @Override
    protected Integer[][] constructLayout() {
        return new Integer[][]{
                new Integer[]{14, 2, 2, 2, 2, 2, 2,15,14, 2, 2, 2, 2, 2, 2, 2, 2,15},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 5,11, 1, 1, 1, 1, 1, 1, 1, 1, 5},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 7,13, 1, 1, 1, 1, 1, 1, 1, 1, 5},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 6,12, 1, 1, 1, 1, 1, 1, 1, 1, 5},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 5,11, 1, 1, 1, 1, 1, 1, 1, 1, 5},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 5,11, 1, 1,21, 8, 8,20, 1, 1, 5},
                new Integer[]{17, 8, 8, 9, 1,10, 8,16,11, 1, 1, 5, 0, 0,11, 1, 1, 5},
                new Integer[]{14, 2, 2, 3, 1, 4, 2, 2,18, 1, 1, 5, 0, 0,11, 1, 1, 5},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,19, 2, 2,18, 1, 1, 5},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5},
                new Integer[]{17, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,16}
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
