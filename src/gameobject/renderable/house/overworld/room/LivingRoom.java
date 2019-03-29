package gameobject.renderable.house.overworld.room;

public class LivingRoom extends Room {

    public LivingRoom() {
        super("LivingRoom");
    }

    @Override
    protected Integer[][] constructLayout() {
        return new Integer[][]{
                new Integer[]{14, 2, 2, 2, 2, 2, 2, 2, 2,15},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 1, 1, 5},
                new Integer[]{13, 1, 1, 1, 1, 1, 1, 1, 1, 5},
                new Integer[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 5},
                new Integer[]{12, 1, 1, 1, 1, 1, 1, 1, 1, 5},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 1, 1, 5},
                new Integer[]{11, 1, 1,21, 8, 8,20, 1, 1, 5},
                new Integer[]{11, 1, 1, 5, 0, 0,11, 1, 1, 5},
                new Integer[]{11, 1, 1, 5, 0, 0,11, 1, 1, 5},
                new Integer[]{11, 1, 1,19, 2, 2,18, 1, 1, 5},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 1, 1, 5},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 1, 1, 5},
                new Integer[]{17, 8, 8, 8, 8, 8, 8, 8, 8,16}
        };
    }

    @Override
    public void initializeRoom() {
        createSpawnPoint(3,6, SpawnType.Vendor);
    }

    @Override
    public void update() {}
}
