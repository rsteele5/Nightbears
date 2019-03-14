package gameobject.renderable.house.overworld;

import gameobject.renderable.RenderableObject;
import gameobject.renderable.DrawLayer;

import java.awt.*;
import java.util.ArrayList;

public abstract class Room {

    //private ArrayList<Boundary> boundaries;
    //private ArrayList<interactables> boundaries;
    //private ArrayList<Boundary> boundaries;
    private int cellX;
    private int cellY;
    private Integer[][] layout;
    //TODO: Setup Anchor points here

    public Room(){
        layout = constructLayout();
        cellX = -1;
        cellY = -1;
    }

    protected abstract Integer[][] constructLayout();

    public void setCell(int x, int y) {
        cellX = x;
        cellY = y;
    }

}
