package gamescreen.container;

import gameobject.GameObject;
import gameobject.renderable.RenderableObject;
import gamescreen.GameScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class GridContainer extends GameObject {

    //region <Variables>
    protected GameScreen parentScreen;
    private int rows;
    private int cols;
    private int padding;
    private int itemWidth;
    private int itemHeight;
    protected int width;    //(size of item + padding) * cols + padding = width
    protected int height;   //(size of item + padding) * rows + padding = height

    protected ArrayList<RenderableObject[]> renderableGrid;
    //endregion

    //region <Construction and Initialization>
    public GridContainer(GameScreen parentScreen, int rows, int cols, int itemWidth, int itemHeight) {
        super();
        this.parentScreen = parentScreen;
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

        Debug.warning(DebugEnabler.GRID_CONTAINER, "Create Grid - Width: "+ width +", Height: " + height);
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
        for(int row = 0; row < renderableGrid.size(); row++){
            for(int col = 0; col < renderableGrid.get(row).length; col++){
                if(renderableGrid.get(row)[col] != null){
                    renderableGrid.get(row)[col]
                            .setPosition(x + ((itemWidth + padding) * col),
                                    y + (itemHeight + padding) * row);
                }
            }
        }
    }
    //endregion

    //region <Public Utilities>
    /**
     * Adds a RenderableObject to the GridContainer at a specified row and column abiding by the rules of the
     * container. If the adding was successful, the renderable is dynamically added to the parent screen of the grid.
     * All items being added should be the same size to avoid organization complications.
     *
     * @param renderable    the object that is being added to the grid.
     * @param row           row of the grid at which to add the renderable.
     * @param col           column of the grid at which to add the renderable.
     */
    public void dynamicAddAt(RenderableObject renderable, int row, int col){
        if(addAt(renderable,row,col))
            renderable.addToScreen(parentScreen,true);
        else Debug.warning(DebugEnabler.GRID_CONTAINER, "Add error was dynamic.");
    }

    /**
     * Adds a RenderableObject to the GridContainer at a specified row and column abiding by the rules of the
     * container, and returns True if the adding was successful. All items being added should be the same size to
     * avoid organization complications.
     *
     * @param renderable    the object that is being added to the grid.
     * @param row           row of the grid at which to add the renderable.
     * @param col           column of the grid at which to add the renderable.
     * @return              true indicates a successful addAt, and false indicates a failure to addAt.
     */
    public boolean addAt(RenderableObject renderable, int row, int col){
        if((row >= 0 && col >= 0) && (row < rows && col < cols)) {
            if(renderable != null) {
                Debug.warning(DebugEnabler.GRID_CONTAINER, "AddAt("+row+", "+col+") - "
                        + "x: " + (x + ((itemWidth + padding) * col))
                        + ", y: " + (y + (itemHeight + padding) * row));
                //Set the position of the renderable
                renderable.setPosition(x + ((itemWidth + padding) * col)
                        ,y + (itemHeight + padding) * row);
                renderableGrid.get(row)[col] = renderable;
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

    @Override
    public boolean setActive(GameScreen screen){
        if(super.setActive(screen)){
            for (RenderableObject[] row : renderableGrid) {
                for (RenderableObject renderable : row) {
                    if (renderable != null) {
                        renderable.setActive(screen);
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
            for (RenderableObject[] row : renderableGrid) {
                for (RenderableObject renderable : row) {
                    if (renderable != null) {
                        renderable.setInactive(screen);
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
        for (RenderableObject[] row : renderableGrid) {
            for (RenderableObject renderable : row) {
                if (renderable != null) {
                    renderable.addToScreen(screen, isActive);
                }
            }
        }
    }

    @Override
    public void update() {

    }
    //endregion
}
