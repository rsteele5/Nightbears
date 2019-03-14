package gameobject.renderable.house.overworld.room;

import gameobject.renderable.RenderableObject;
import gameobject.renderable.DrawLayer;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.util.ArrayList;

public abstract class Room {

    //private ArrayList<Boundary> boundaries;
    //private ArrayList<interactables> boundaries;
    //private ArrayList<Boundary> boundaries;
    protected String name;
    private int cellX;
    private int cellY;
    private Integer[][] layout;
    //TODO: Setup Anchor points here

    public Room(String name){
        layout = constructLayout();
        cellX = -1;
        cellY = -1;
        this.name = "Room: "+name;
    }

    protected abstract Integer[][] constructLayout();

    public String getName() {
        return name;
    }

    public int getCellX() {
        return cellX;
    }

    public int getCellY() {
        return cellY;
    }

    public Integer[][] getLayout() {
        return layout;
    }

    public void setCell(int x, int y) {
        cellX = x;
        cellY = y;
    }

    public boolean isConflicting(Room newRoom) {
        if(cellX >= 0 && cellY >= 0){
            if(newRoom.cellX >= 0 && newRoom.cellY >= 0){
                if((newRoom.cellX >= cellX && newRoom.cellY >= cellY)
                        && (newRoom.cellY < cellY + layout.length && newRoom.cellX < cellX + layout[0].length)) {
                    //TODO: Check if the conflicting sections have irrelevant differences
                    return true;
                }
            }else{
                Debug.warning(DebugEnabler.OVERWORLD, newRoom.name
                        + " - is being compared, but does not have a set cell yet. Returning false. ");
            }
        }else{
            Debug.warning(DebugEnabler.OVERWORLD, name
                    + " - is trying to compare layouts, but does not have a set cell yet. Returning false. ");
        }

        return false;
    }
}
