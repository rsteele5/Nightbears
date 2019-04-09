package gameobject.renderable.house.sidescrolling;

import gameobject.Boundary;
import gameobject.container.TileGridContainer;
import gameobject.renderable.house.overworld.Tile;
import gameobject.CollidableObject;

import java.util.ArrayList;

public class BedroomBackgroundLayout extends BackgroundLayout {

    public static final int TileSize = 100;
    public static final int WallThickness = 35;

    private final int EMPTY = 0;
    private final int START = 1;
    private final int WALL_SW = 2;
    private final int WALL_SE = 3;
    private final int WALL_S = 4;
    private final int WALL_P_E = 5;
    private final int WALL_P_W = 6;
    private final int WALL_TRIM = 7;
    private final int WALL_W_TRIM = 8;
    private final int WALL_E_TRIM = 9;
    private final int WALL_SOCKET = 10;
    private final int WALL_P = 11;
    private final int WALL_NW = 12;
    private final int WALL_NE = 13;
    private final int WALL_W = 14;
    private final int WALL_E = 15;
    private final int WALL_N = 16;
    private final int WALL_SWITCH = 17;
    private final int WALL = 18;
    private final int WINDOW_NW = 19;
    private final int WINDOW_N = 20;
    private final int WINDOW_NE = 21;
    private final int WINDOW_W = 22;
    private final int WINDOW_E = 23;
    private final int WINDOW_SW = 24;
    private final int WINDOW_S = 25;
    private final int WINDOW_SE = 26;
    private final int WINDOW = 27;
    private final int END = 63;

    private final String IMAGEPATH_EMPTY = "";
    private final String IMAGEPATH_START = "";
    private final String IMAGEPATH_WALL_SW = "/assets/sidescroll/bedroom/SideScroll-Bedroom-SouthWest-Wall.png";
    private final String IMAGEPATH_WALL_SE = "/assets/sidescroll/bedroom/SideScroll-Bedroom-SouthEast-Wall.png";
    private final String IMAGEPATH_WALL_S = "/assets/sidescroll/bedroom/SideScroll-Bedroom-South-Wall.png";
    private final String IMAGEPATH_WALL_P_E = "/assets/sidescroll/bedroom/SideScroll-Bedroom-East-Wall-P.png";
    private final String IMAGEPATH_WALL_P_W = "/assets/sidescroll/bedroom/SideScroll-Bedroom-West-Wall-P.png";
    private final String IMAGEPATH_WALL_TRIM = "/assets/sidescroll/bedroom/SideScroll-Bedroom-Wall-T.png";
    private final String IMAGEPATH_WALL_W_TRIM = "/assets/sidescroll/bedroom/SideScroll-Bedroom-West-Wall-T.png";
    private final String IMAGEPATH_WALL_E_TRIM = "/assets/sidescroll/bedroom/SideScroll-Bedroom-East-Wall-T.png";
    private final String IMAGEPATH_WALL_SOCKET = "/assets/sidescroll/bedroom/SideScroll-Bedroom-Wall-P-Socket.png";
    private final String IMAGEPATH_WALL_P = "/assets/sidescroll/bedroom/SideScroll-Bedroom-Wall-P.png";
    private final String IMAGEPATH_WALL_NW = "/assets/sidescroll/bedroom/SideScroll-Bedroom-NorthWest-Wall.png";
    private final String IMAGEPATH_WALL_NE = "/assets/sidescroll/bedroom/SideScroll-Bedroom-NorthEast-Wall.png";
    private final String IMAGEPATH_WALL_W = "/assets/sidescroll/bedroom/SideScroll-Bedroom-West-Wall.png";
    private final String IMAGEPATH_WALL_E = "/assets/sidescroll/bedroom/SideScroll-Bedroom-East-Wall.png";
    private final String IMAGEPATH_WALL_N = "/assets/sidescroll/bedroom/SideScroll-Bedroom-North-Wall.png";
    private final String IMAGEPATH_WALL_SWITCH = "/assets/sidescroll/bedroom/SideScroll-Bedroom-Wall-Switch.png";
    private final String IMAGEPATH_WALL = "/assets/sidescroll/bedroom/SideScroll-Bedroom-Wall.png";
    private final String IMAGEPATH_WINDOW_NW = "/assets/sidescroll/bedroom/SideScroll-Bedroom-NorthWest-Window.png";
    private final String IMAGEPATH_WINDOW_N = "/assets/sidescroll/bedroom/SideScroll-Bedroom-North-Window.png";
    private final String IMAGEPATH_WINDOW_NE = "/assets/sidescroll/bedroom/SideScroll-Bedroom-NorthEast-Window.png";
    private final String IMAGEPATH_WINDOW_W = "/assets/sidescroll/bedroom/SideScroll-Bedroom-West-Window.png";
    private final String IMAGEPATH_WINDOW_E = "/assets/sidescroll/bedroom/SideScroll-Bedroom-East-Window.png";
    private final String IMAGEPATH_WINDOW_SW = "/assets/sidescroll/bedroom/SideScroll-Bedroom-SouthWest-Window.png";
    private final String IMAGEPATH_WINDOW_S = "/assets/sidescroll/bedroom/SideScroll-Bedroom-South-Window.png";
    private final String IMAGEPATH_WINDOW_SE = "/assets/sidescroll/bedroom/SideScroll-Bedroom-SouthEast-Window.png";
    private final String IMAGEPATH_WINDOW = "/assets/sidescroll/bedroom/SideScroll-Bedroom-Window.png";
    private final String IMAGEPATH_END = "";

    private ArrayList<CollidableObject> boundaries;

    @Override
    protected void constructLayout() {
        layout = new Integer[][]{
                new Integer[]{12, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new Integer[]{14, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new Integer[]{14, 18, 18, 19, 20, 21, 18, 18, 18, 18, 18, 18, 18, 18, 19, 20, 20, 20, 21, 18, 18, 18, 18, 18, 18, 18, 18, 19, 20, 21, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new Integer[]{14, 18, 18, 22, 27, 23, 18, 18, 18, 18, 18, 18, 18, 18, 24, 25, 25, 25, 26, 18, 18, 18, 18, 18, 18, 18, 18, 22, 27, 23, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new Integer[]{14, 18, 18, 24, 25, 26, 18, 17, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 17, 18, 18, 24, 25, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 17, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 17, 18, 18, 18, 18, 18, 18, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new Integer[]{14, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new Integer[]{8, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new Integer[]{6, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new Integer[]{6, 11, 11, 11, 11, 10, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 10, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 10, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 10, 11, 11, 11, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new Integer[]{2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
    }

    @Override
    public TileGridContainer getBackground() {
        return background;
    }

    @Override
    public void constructBackground() {
        background = new TileGridContainer(layout.length + 1, layout[0].length, 100,100,0,0);
        for(int row = 0; row < layout.length; row++){
            for(int col = 0; col < layout[row].length; col++){
                switch(layout[row][col]){
                    case EMPTY: break;
                    case START: break;
                    case WALL_SW: background.addAt(new Tile(IMAGEPATH_WALL_SW), row, col); break;
                    case WALL_SE: background.addAt(new Tile(IMAGEPATH_WALL_SE), row, col); break;
                    case WALL_S: background.addAt(new Tile(IMAGEPATH_WALL_S), row, col); break;
                    case WALL_P_E: background.addAt(new Tile(IMAGEPATH_WALL_P_E), row, col); break;
                    case WALL_P_W: background.addAt(new Tile(IMAGEPATH_WALL_P_W), row, col); break;
                    case WALL_TRIM: background.addAt(new Tile(IMAGEPATH_WALL_TRIM), row, col); break;
                    case WALL_W_TRIM: background.addAt(new Tile(IMAGEPATH_WALL_W_TRIM), row, col); break;
                    case WALL_E_TRIM: background.addAt(new Tile(IMAGEPATH_WALL_E_TRIM), row, col); break;
                    case WALL_SOCKET: background.addAt(new Tile(IMAGEPATH_WALL_SOCKET), row, col); break;
                    case WALL_P: background.addAt(new Tile(IMAGEPATH_WALL_P), row, col); break;
                    case WALL_NW: background.addAt(new Tile(IMAGEPATH_WALL_NW), row, col); break;
                    case WALL_NE: background.addAt(new Tile(IMAGEPATH_WALL_NE), row, col); break;
                    case WALL_W: background.addAt(new Tile(IMAGEPATH_WALL_W), row, col); break;
                    case WALL_E: background.addAt(new Tile(IMAGEPATH_WALL_E), row, col); break;
                    case WALL_N: background.addAt(new Tile(IMAGEPATH_WALL_N), row, col); break;
                    case WALL_SWITCH: background.addAt(new Tile(IMAGEPATH_WALL_SWITCH), row, col); break;
                    case WALL: background.addAt(new Tile(IMAGEPATH_WALL), row, col); break;
                    case WINDOW_NW: background.addAt(new Tile(IMAGEPATH_WINDOW_NW), row, col); break;
                    case WINDOW_N: background.addAt(new Tile(IMAGEPATH_WINDOW_N), row, col); break;
                    case WINDOW_NE: background.addAt(new Tile(IMAGEPATH_WINDOW_NE), row, col); break;
                    case WINDOW_W: background.addAt(new Tile(IMAGEPATH_WINDOW_W), row, col); break;
                    case WINDOW_E: background.addAt(new Tile(IMAGEPATH_WINDOW_E), row, col); break;
                    case WINDOW_SW: background.addAt(new Tile(IMAGEPATH_WINDOW_SW), row, col); break;
                    case WINDOW_S: background.addAt(new Tile(IMAGEPATH_WINDOW_S), row, col); break;
                    case WINDOW_SE: background.addAt(new Tile(IMAGEPATH_WINDOW_SE), row, col); break;
                    case WINDOW: background.addAt(new Tile(IMAGEPATH_WINDOW), row, col); break;
                    case END: break;
                }
            }
        }
    }

    @Override
    protected void constructBoundaries() {
        boundaries = new ArrayList<>();
        createNorthWall(0,0);
        createSouthWall(layout.length - 1, 0);
    }

    public void addBoundary(CollidableObject boundary){
        boundaries.add(boundary);
    }

    public ArrayList<CollidableObject> getBoundaries(){
        return boundaries;
    }

    private void createNorthWall(int iRow, int iCol) {
        final Integer[] row = layout[iRow];
        final Tile startTile = getBackground().getContentAt(iRow, iCol);
        int x = startTile.getX();
        int width = 0;
        boolean done = false;

        for (int col = iCol; col < row.length; col++){
            if(!done) {
                switch (row[col]) {
                    case WALL_NW:
                        width += TileSize;
                        row[col] = WALL_W;
                        createWestWall(iRow, col);
                        break;
                    case WALL_NE:
                        width += TileSize;
                        row[col] = WALL_E;
                        createEastWall(iRow, col);
                        done = true;
                        break;
                    case WALL_N:
                        width += TileSize;
                        row[col] = -WALL_N;
                        break;
                    default:
                        done = true;
                        break;
                }
            }else break;
        }
        addBoundary(new Boundary(x, startTile.getY(), width, WallThickness));
    }

    private void createSouthWall(int iRow, int iCol) {
        final Integer[] row = layout[iRow];
        final Tile startTile = getBackground().getContentAt(iRow, iCol);
        int x = startTile.getX();
        int width = 0;
        boolean done = false;

        for (int col = iCol; col < layout[iRow].length - 1; col++){
            switch(row[col]){
                case WALL_SW:
                    width += TileSize;
                    row[col] = WALL_W;
                    createWestWall(iRow, col);
                    break;
                case WALL_SE:
                    width += TileSize;
                    row[col] = WALL_E;
                    createEastWall(iRow, col);
                    break;
                case WALL_S:
                    width += TileSize;
                    row[col] = -WALL_S;
                    break;
                default: done = true;
                    break;
            }
            if(done) break;
        }
        addBoundary(new Boundary(x, startTile.getY() + TileSize - WallThickness,
                width, WallThickness));
    }

    private void createEastWall(int iRow, int iCol) {
        final Tile startTile = getBackground().getContentAt(iRow, iCol);
        int y = startTile.getY();
        int height = 0;
        boolean done = false;

        for (int row = iRow; row < layout.length; row++){
            switch(layout[row][iCol]){
                case WALL_NE:
                    height += TileSize;
                    layout[row][iCol] = WALL_N;
                    createNorthWall(row, iCol);
                    break;
                case WALL_SE:
                    height += TileSize;
                    layout[row][iCol] = WALL_S;
                    createSouthWall(row, iCol);
                    break;
                case WALL_E:
                case WALL_E_TRIM:
                case WALL_P_E:
                    height += TileSize;
                    layout[row][iCol] = -WALL_E;
                    break;
                default: done = true;
                    break;
            }
            if(done) break;
        }
        addBoundary(new Boundary(startTile.getX() + TileSize - WallThickness, y, WallThickness, height));
    }

    private void createWestWall(int iRow, int iCol) {
        final Tile startTile = getBackground().getContentAt(iRow, iCol);
        int y = startTile.getY();
        int height = 0;
        boolean done = false;

        for (int row = iRow; row < layout.length; row++){
            switch(layout[row][iCol]){
                case WALL_NW:
                    height += TileSize;
                    layout[row][iCol] = WALL_N;
                    createNorthWall(row, iCol);
                    break;
                case WALL_SW:
                    height += TileSize;
                    layout[row][iCol] = WALL_S;
                    createSouthWall(row, iCol);
                    break;
                case WALL_W:
                case WALL_W_TRIM:
                case WALL_P_W:
                    height += TileSize;
                    layout[row][iCol] = -WALL_W;
                    break;
                default: done = true;
                    break;
            }
            if(done) break;
        }
        addBoundary(new Boundary(startTile.getX() - 100, y, 100 + WallThickness, height));
    }
}
