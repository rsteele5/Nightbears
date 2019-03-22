package gamescreen.container;

import gameobject.GameObject;
import gamescreen.GameScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public abstract class GridContainer<T> extends GameObject {

    //region <Variables>
    protected GameScreen parentScreen;
    protected int rows;
    protected int cols;
    protected int padding;
    protected int itemWidth;
    protected int itemHeight;
    protected int width;    //(size of item + padding) * cols + padding = width
    protected int height;   //(size of item + padding) * rows + padding = height

    protected ArrayList<ArrayList<T>> contents;
    //endregion

    //region <Construction and Initialization>
    public GridContainer(GameScreen parentScreen, int rows, int cols, int itemWidth, int itemHeight, int xPos, int yPos, int padding) {
        super();
        this.parentScreen = parentScreen;
        this.rows = rows;
        this.cols = cols;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        width = (itemWidth + padding) * cols + padding;
        height =(itemHeight + padding) * rows + padding;
        x = xPos;
        y = yPos;
        this.padding = padding;

        contents = new ArrayList<>();
        for(int row = 0; row < rows; row++) {
            contents.add(new ArrayList<>(cols));
            for(int col = 0; col < cols; col++){
                contents.get(row).add(null);
            }
        }

        Debug.log(DebugEnabler.GRID_CONTAINER, "Create Grid - Width: "+ width +", Height: " + height);
    }
    //endregion

    //region <Getters and Setters>
    /**
     * @return  a Retangle2D representing the grid container's bounding box.
     * @see     Rectangle2D
     */
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    /**
     * @return the total rows of the grid
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return the total columns of the grid
     */
    public int getCols() {
        return cols;
    }

    public T getContentAt(int row, int col) {
        return contents.get(row).get(col);
    }

    /**
     * Sets the position of the grid container and also sets the positions of all objects contained in the container
     * relative to their position in the grid.
     *
     * @param xPos  The x position relative the parent screen.
     * @param yPos  The y position relative the parent screen.
     */
    @Override
    public void setPosition(int xPos, int yPos){
        x = xPos;
        y = yPos;
        for(int row = 0; row < contents.size(); row++){
            for(int col = 0; col < contents.get(row).size(); col++){
                if(contents.get(row).get(col) != null){
                    this.setContentPosition(contents.get(row).get(col),
                            x + (itemWidth + padding) * col,
                            y + (itemHeight + padding) * row);
                }
            }
        }
    }

    @Override
    public boolean setActive(GameScreen screen){
        if(super.setActive(screen)){
            for (ArrayList<T> row : contents) {
                for (T content : row) {
                    if (content != null) {
                        this.setContentActive(content, screen);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean setInactive(GameScreen screen){
        if(super.setInactive(screen)){
            for (ArrayList<T> row : contents) {
                for (T content : row) {
                    if (content != null) {
                        this.setContentInactive(content, screen);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void addToScreen(GameScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        for (ArrayList<T> row : contents) {
            for (T content : row) {
                if (content != null) {
                    this.addContentToScreen(content, screen, isActive);
                }
            }
        }
    }

    protected abstract void setContentPosition(T content, int newX, int newY);
    protected abstract void setContentActive(T content, GameScreen screen);
    protected abstract void setContentInactive(T content, GameScreen screen);
    protected abstract void addContentToScreen(T content, GameScreen screen, boolean isActive);
    //endregion

    //region <Public Utilities>
    /**
     * Adds an Object to the GridContainer at a specified row and column abiding by the rules of the
     * container. If the adding was successful, the renderable is dynamically added to the parent screen of the grid.
     * All items being added should be the same size to avoid organization complications.
     *
     * @param content   the object that is being added to the grid.
     * @param row       row of the grid at which to add the renderable.
     * @param col       column of the grid at which to add the renderable.
     */
    public void dynamicAddAt(T content, int row, int col){
        if(addAt(content,row,col))
            this.addContentToScreen(content, parentScreen,true);
        else Debug.warning(DebugEnabler.GRID_CONTAINER, "Add error was dynamic.");
    }

    /**
     * Adds an Object to the GridContainer at a specified row and column abiding by the rules of the
     * container, and returns True if the adding was successful. All items being added should be the same size to
     * avoid organization complications.
     *
     * @param content   the object that is being added to the grid.
     * @param row       row of the grid at which to add the renderable.
     * @param col       column of the grid at which to add the renderable.
     * @return          true indicates a successful addAt, and false indicates a failure to addAt.
     */
    public boolean addAt(T content, int row, int col){
        if((row >= 0 && col >= 0) && (row < rows && col < cols)) {
            if(content != null) {
                Debug.warning(DebugEnabler.GRID_CONTAINER, "AddAt("+row+", "+col+") - "
                        + "x: " + (x + ((itemWidth + padding) * col))
                        + ", y: " + (y + (itemHeight + padding) * row));
                //Set the position of the renderable
                this.setContentPosition(content,
                        x + (itemWidth + padding) * col,
                        y + (itemHeight + padding) * row);
                contents.get(row).set(col, content);
                return true;
            }else {
                Debug.error(DebugEnabler.GRID_CONTAINER, "- addAt() was passed null");
            }
        } else{
            Debug.error(DebugEnabler.GRID_CONTAINER,
                    "Grid dynamicAddAt failed- Current Range: row: 0-" + (rows-1) + ", col: 0-" + (cols-1));
            Debug.error(DebugEnabler.GRID_CONTAINER,
                    "Grid dynamicAddAt failed- dynamicAddAt( row: " + row + ", col: " + col + ") is out of bounds");
        }
        return false;
    }

    //endregion

}
