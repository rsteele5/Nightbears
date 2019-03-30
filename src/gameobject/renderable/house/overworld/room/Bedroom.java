package gameobject.renderable.house.overworld.room;


import gameobject.renderable.house.overworld.Compass;

public class Bedroom extends Room {

    public Bedroom() {
        super("Bedroom");
    }

    @Override
    protected Integer[][] constructLayout() {
        return new Integer[][]{
                new Integer[]{14, 2, 2, 2, 2, 2, 2,15,},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 5,},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 7,},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 1,},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 6,},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 5,},
                new Integer[]{11, 1, 1, 1, 1, 1, 1, 5,},
                new Integer[]{17, 8, 8, 9, 1,10, 8,16,}
        };
    }

    @Override
    public void initializeRoom() {
        createSpawnPoint(1,1, SpawnType.Player);
        createSpawnPoint(2,1, SpawnType.Vendor);

        //createDoor(3, 7, Compass.East);
        //createDoor(7, 4, Compass.South);
    }

    @Override
    public void update() {}
}
