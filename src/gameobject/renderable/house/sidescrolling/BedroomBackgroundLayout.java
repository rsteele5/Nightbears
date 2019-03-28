package gameobject.renderable.house.sidescrolling;

import gameobject.container.TileGridContainer;
import gameobject.renderable.house.overworld.Tile;

public class BedroomBackgroundLayout extends BackgroundLayout {

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

    @Override
    protected Integer[][] constructLayout() {
        return  new Integer[][]{
                new Integer[]{12,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,13,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                new Integer[]{14,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                new Integer[]{14,18,18,19,20,21,18,18,18,18,18,18,18,18,19,20,20,20,21,18,18,18,18,18,18,18,18,19,20,21,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                new Integer[]{14,18,18,22,27,23,18,18,18,18,18,18,18,18,24,25,25,25,26,18,18,18,18,18,18,18,18,22,27,23,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                new Integer[]{14,18,18,24,25,26,18,17,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,17,18,18,24,25,26,18,18,18,18,18,18,18,18,18,18,18,18,18,17,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,17,18,18,18,18,18,18,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                new Integer[]{14,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                new Integer[]{8,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                new Integer[]{6,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                new Integer[]{6,11,11,11,11,10,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,10,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,10,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,10,11,11,11,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                new Integer[]{2,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        };
    }

    @Override
    public TileGridContainer getBackground() {
        TileGridContainer gridContainer = new TileGridContainer(layout.length + 1, layout[0].length, 100,100,0,0);
        for(int row = 0; row < layout.length; row++){
            for(int col = 0; col < layout[row].length; col++){
                switch(layout[row][col]){
                    case EMPTY: break;
                    case START: break;
                    case WALL_SW: gridContainer.addAt(new Tile(IMAGEPATH_WALL_SW), row, col); break;
                    case WALL_SE: gridContainer.addAt(new Tile(IMAGEPATH_WALL_SE), row, col); break;
                    case WALL_S: gridContainer.addAt(new Tile(IMAGEPATH_WALL_S), row, col); break;
                    case WALL_P_E: gridContainer.addAt(new Tile(IMAGEPATH_WALL_P_E), row, col); break;
                    case WALL_P_W: gridContainer.addAt(new Tile(IMAGEPATH_WALL_P_W), row, col); break;
                    case WALL_TRIM: gridContainer.addAt(new Tile(IMAGEPATH_WALL_TRIM), row, col); break;
                    case WALL_W_TRIM: gridContainer.addAt(new Tile(IMAGEPATH_WALL_W_TRIM), row, col); break;
                    case WALL_E_TRIM: gridContainer.addAt(new Tile(IMAGEPATH_WALL_E_TRIM), row, col); break;
                    case WALL_SOCKET: gridContainer.addAt(new Tile(IMAGEPATH_WALL_SOCKET), row, col); break;
                    case WALL_P: gridContainer.addAt(new Tile(IMAGEPATH_WALL_P), row, col); break;
                    case WALL_NW: gridContainer.addAt(new Tile(IMAGEPATH_WALL_NW), row, col); break;
                    case WALL_NE: gridContainer.addAt(new Tile(IMAGEPATH_WALL_NE), row, col); break;
                    case WALL_W: gridContainer.addAt(new Tile(IMAGEPATH_WALL_W), row, col); break;
                    case WALL_E: gridContainer.addAt(new Tile(IMAGEPATH_WALL_E), row, col); break;
                    case WALL_N: gridContainer.addAt(new Tile(IMAGEPATH_WALL_N), row, col); break;
                    case WALL_SWITCH: gridContainer.addAt(new Tile(IMAGEPATH_WALL_SWITCH), row, col); break;
                    case WALL: gridContainer.addAt(new Tile(IMAGEPATH_WALL), row, col); break;
                    case WINDOW_NW: gridContainer.addAt(new Tile(IMAGEPATH_WINDOW_NW), row, col); break;
                    case WINDOW_N: gridContainer.addAt(new Tile(IMAGEPATH_WINDOW_N), row, col); break;
                    case WINDOW_NE: gridContainer.addAt(new Tile(IMAGEPATH_WINDOW_NE), row, col); break;
                    case WINDOW_W: gridContainer.addAt(new Tile(IMAGEPATH_WINDOW_W), row, col); break;
                    case WINDOW_E: gridContainer.addAt(new Tile(IMAGEPATH_WINDOW_E), row, col); break;
                    case WINDOW_SW: gridContainer.addAt(new Tile(IMAGEPATH_WINDOW_SW), row, col); break;
                    case WINDOW_S: gridContainer.addAt(new Tile(IMAGEPATH_WINDOW_S), row, col); break;
                    case WINDOW_SE: gridContainer.addAt(new Tile(IMAGEPATH_WINDOW_SE), row, col); break;
                    case WINDOW: gridContainer.addAt(new Tile(IMAGEPATH_WINDOW), row, col); break;
                    case END: break;
                }
            }
        }

        return gridContainer;
    }
}
