package gameobject.renderable.house.overworld.room;

public class Bathroom extends Room {

    public Bathroom() {
        super("Bathroom");
    }

    @Override
    protected Integer[][] constructLayout() {
        return new Integer[][]{
                new Integer[]{14, 2, 2, 3, 1, 4, 2,15},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 5},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 5},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 5},
                new Integer[]{17, 8, 8, 8, 8, 8, 8,16}
        };
    }

    @Override
    public void initializeRoom() {
        createSpawnPoint(1,1, SpawnType.Player);
        createSpawnPoint(2,1, SpawnType.Vendor);
    }

    @Override
    public void update() {}
}
