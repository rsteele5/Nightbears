package gamescreen.container;

import gameobject.renderable.RenderableObject;
import gamescreen.GameScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class GridContainer {

    protected GameScreen parentScreen;
    private int x;
    private int y;
    private int rows;
    private int cols;
    private int padding;
    private int itemWidth;
    private int itemHeight;
    protected int width;    //(size of item + padding*2) * rows = height
    protected int height;   //(size of item + padding*2) * cols = width

    protected ArrayList<RenderableObject[]> renderableGrid;



    public GridContainer(GameScreen parentScreen, int rows, int cols, int itemWidth, int itemHeight) {
        this.parentScreen = parentScreen;
        x = 0;
        y = 0;
        this.rows = rows;
        this.cols = cols;
        padding = 5;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        width = (itemWidth + padding) * cols + padding;
        height =(itemHeight + padding) * rows + padding;

        renderableGrid = new ArrayList<>();
        for(int row = 0; row < rows; row++)
            renderableGrid.add(new RenderableObject[cols]);
    }

    public GridContainer(GameScreen parentScreen, int rows, int cols, int itemWidth , int itemHeight, int xPos, int yPos) {
        this(parentScreen, rows, cols, itemWidth, itemHeight);
        x = xPos;
        y = yPos;
    }

    public GridContainer(GameScreen parentScreen, int rows, int cols, int itemWidth , int itemHeight, int xPos, int yPos, int padding) {
        this(parentScreen, rows, cols, itemWidth, itemHeight,xPos, yPos);
        this.padding = padding;
    }

    public void addAt(RenderableObject renderable, int row, int col){

        if((row >= 0 && col >= 0) && (row <= rows && col <= cols)) {
            if(renderable != null) {
                //Set the position of the renderable
                renderable.setPosition(x + ((itemWidth + padding) * col)
                        ,y + (itemWidth + padding) * row);
                renderable.addToScreen(parentScreen,true);
                renderableGrid.get(row)[col] = renderable;
            }else {
                Debug.error(DebugEnabler.GAME_SCREEN_LOG, "- addAt() was passed null");
            }
        } else{
            Debug.warning(DebugEnabler.GAME_SCREEN_LOG,
                    "Grid addAt failed- Current Range: row: 0-" + (rows-1) + ", col: 0-" + (cols-1));
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,
                    "Grid addAt failed- addAt( row: " + row + ", col: " + col + ") is out of bounds");
        }
    }

    public Rectangle2D getBoundingBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void setLocation(int xPos, int yPos){
        x = xPos;
        y = yPos;
        for(int row = 0; row < renderableGrid.size(); row++){
            for(int col = 0; col < renderableGrid.get(row).length; col++){
                if(renderableGrid.get(row)[col] != null){
                    renderableGrid.get(row)[col]
                            .setPosition(x + ((itemWidth + padding) * col),
                                         y + (itemWidth + padding) * row);
                }
            }
        }
    }
}
