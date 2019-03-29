package gamescreen.gameplay.level;

import _test.Square;
import gameengine.gamedata.PlayerData;
import gameengine.rendering.Camera;
import gameobject.renderable.house.sidescrolling.BedroomBackgroundLayout;
import gameobject.renderable.house.sidescrolling.Floor;
import gameobject.renderable.enemy.Minion;
import gameobject.renderable.enemy.WalkLeftMS;
import gameobject.renderable.enemy.Walker;
import gameobject.renderable.player.Player;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.item.weapon.Weapon;
import gameobject.renderable.item.weapon.WeaponBuilder;
import gameobject.renderable.item.weapon.WeaponType;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import input.listeners.Key.SideScrollKeyHandler;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

public class BedroomLevel extends GameScreen {

    public BedroomLevel(ScreenManager screenManager) {
        super(screenManager, "BedroomLevel");
    }

    @Override
    protected void initializeScreen() {
        Player player = new Player(10, 276, DrawLayer.Entity, gameData.getPlayerData());
        player.addToScreen(this, true);
        player.setState(Player.PlayerState.overWorld);
        setKeyHandler(new SideScrollKeyHandler(player));
        setCamera(new Camera(screenManager, this, player));
        BedroomBackgroundLayout background = new BedroomBackgroundLayout();
        background.getBackground().addToScreen(this, true);
        //This is where the instruction for how to procedurally generate a level would go
        Floor floorTile = new Floor(10, 576, "/assets/levelObjects/WoodTile1.png",DrawLayer.Entity);
        Floor floorTile2 = new Floor(10, 576, "/assets/levelObjects/WoodTile1.png",DrawLayer.Entity);
        floorTile.setWidth(1260);
        floorTile.setHeight(50);
        floorTile2.setWidth(50);
        floorTile2.setHeight(96);
        floorTile.addToScreen(this, true);

        Square square;
        for(int x1 = 0; x1 < 5; x1++){
            for(int y1 = 0; y1 < x1; y1++){
                square = new Square(x1 * 75 + 100,y1 * 75,"/assets/testAssets/square.png",DrawLayer.Entity);
                square.addToScreen(this, true);
            }
        }

        square = new Square(800,75,"/assets/testAssets/square.png",DrawLayer.Entity);
        square.addToScreen(this, true);

        Weapon myWeap = new WeaponBuilder()
                .position(800, 476)
                .imagePath("/assets/Items/sword1.png")
                .name("My Fwirst Sword!")
                .type(WeaponType.Sword)
                .value(15)
                .buildWeapon();
        myWeap.setWidth(50);
        myWeap.setHeight(50);
        myWeap.addToScreen(this, true);
    }

    private void createNorthWall(Room room, int iRow, int iCol) {
        final Integer[] row = room.getLayout()[iRow];
        final Tile startTile = room.getRoomTileAt(iRow, iCol);
        int x = startTile.getX();
        int width = 0;
        boolean done = false;

        for (int col = iCol; col < room.getWidth(); col++){
            if(!done) {
                switch (row[col]) {
                    case WALLNW:
                        width += TileSize;
                        row[col] = WALLW;
                        createWestWall(room, iRow, col);
                        break;
                    case WALLNE:
                        width += TileSize;
                        row[col] = WALLE;
                        createEastWall(room, iRow, col);
                        break;
                    case WALLNWC:
                        width += WallThickness;
                        row[col] = -WALLNWC;
                        done = true;
                        break;
                    case WALLNEC:
                        if(col != iCol) done = true;
                        else {
                            x += TileSize-WallThickness;
                            width += WallThickness;
                            row[col] = -WALLNEC;
                        }
                        break;
                    case WALLNCW:
                    case WALLNCE: done = true;
                    case WALLN:
                        width += TileSize;
                        row[col] = -WALLN;
                        break;
                    default:
                        done = true;
                        break;
                }
            }else break;
        }
        room.addBoundary(new Boundary(x, startTile.getY(), width, WallThickness));
    }

    private void createSouthWall(Room room, int iRow, int iCol) {
        final Integer[] row = room.getLayout()[iRow];
        final Tile startTile = room.getRoomTileAt(iRow, iCol);
        int x = startTile.getX();
        int width = 0;
        boolean done = false;

        for (int col = iCol; col < room.getWidth(); col++){
            switch(row[col]){
                case WALLSW:
                    width += TileSize;
                    row[col] = WALLW;
                    createWestWall(room, iRow, col);
                    break;
                case WALLSE:
                    width += TileSize;
                    row[col] = WALLE;
                    createEastWall(room, iRow, col);
                    break;
                case WALLSWC:
                    width += WallThickness;
                    row[col] = -WALLSWC;
                    done = true;
                    break;
                case WALLSEC:
                    if(col != iCol) done = true;
                    else {
                        x += TileSize-WallThickness;
                        width += WallThickness;
                        row[col] = -WALLSEC;
                    }
                    break;
                case WALLSCW:
                case WALLSCE: done = true;
                case WALLS:
                    width += TileSize;
                    row[col] = -WALLS;
                    break;
                default: done = true;
                    break;
            }
            if(done) break;
        }
        room.addBoundary(new Boundary(x, startTile.getY() + TileSize - WallThickness,
                width, WallThickness));
    }

    private void createEastWall(Room room, int iRow, int iCol) {
        final Tile startTile = room.getRoomTileAt(iRow, iCol);
        int y = startTile.getY();
        int height = 0;
        boolean done = false;

        for (int row = iRow; row < room.getHeight(); row++){
            switch(room.getLayout()[row][iCol]){
                case WALLNE:
                    height += TileSize;
                    room.getLayout()[row][iCol] = WALLN;
                    createNorthWall(room, row, iCol);
                    break;
                case WALLSE:
                    height += TileSize;
                    room.getLayout()[row][iCol] = WALLS;
                    createSouthWall(room, row, iCol);
                    break;
                case WALLNEC:
                    height += WallThickness;
                    room.getLayout()[row][iCol] = -WALLSWC;
                    done = true;
                    break;
                case WALLSEC:
                    if(row != iRow) done = true;
                    else {
                        y += TileSize-WallThickness;
                        height += WallThickness;
                        room.getLayout()[row][iCol] = -WALLSEC;
                    }
                    break;
                case WALLECN:
                case WALLECS: done = true;
                case WALLE:
                    height += TileSize;
                    room.getLayout()[row][iCol] = -WALLE;
                    break;
                default: done = true;
                    break;
            }
            if(done) break;
        }
        room.addBoundary(new Boundary(startTile.getX() + TileSize - WallThickness, y, WallThickness, height));
    }

    private void createWestWall(Room room, int iRow, int iCol) {
        final Tile startTile = room.getRoomTileAt(iRow, iCol);
        int y = startTile.getY();
        int height = 0;
        boolean done = false;

        for (int row = iRow; row < room.getHeight(); row++){
            switch(room.getLayout()[row][iCol]){
                case WALLNW:
                    height += TileSize;
                    room.getLayout()[row][iCol] = WALLN;
                    createNorthWall(room, row, iCol);
                    break;
                case WALLSW:
                    height += TileSize;
                    room.getLayout()[row][iCol] = WALLS;
                    createSouthWall(room, row, iCol);
                    break;
                case WALLNWC:
                    height += WallThickness;
                    room.getLayout()[row][iCol] = -WALLSWC;
                    done = true;
                    break;
                case WALLSWC:
                    if(row != iRow) done = true;
                    else {
                        y += TileSize-WallThickness;
                        height += WallThickness;
                        room.getLayout()[row][iCol] = -WALLSEC;
                    }
                    break;
                case WALLWCN:
                case WALLWCS: done = true;
                case WALLW:
                    height += TileSize;
                    room.getLayout()[row][iCol] = -WALLW;
                    break;
                default: done = true;
                    break;
            }
            if(done) break;
        }
        room.addBoundary(new Boundary(startTile.getX(), y, WallThickness, height));
    }
}


