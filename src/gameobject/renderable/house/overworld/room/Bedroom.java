package gameobject.renderable.house.overworld.room;

public class Bedroom extends Room {

    public Bedroom() {
        super("Bedroom");
    }

    @Override
    protected Integer[][] constructLayout() {
        return new Integer[][]{
                new Integer[]{9,2,2,2,3},
                new Integer[]{8,1,1,1,4},
                new Integer[]{8,1,1,1,4},
                new Integer[]{8,1,1,1,4},
                new Integer[]{7,6,6,6,5}
        };
    }
}
