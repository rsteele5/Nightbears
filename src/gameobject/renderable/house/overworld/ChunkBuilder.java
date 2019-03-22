package gameobject.renderable.house.overworld;

import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.container.TileGridContainer;

public class ChunkBuilder {

    TileGridContainer chunk;
    private DrawLayer layer = DrawLayer.Background;
    private String roomPath = "/assets/overworld/room/Overworld-";
    private String outsidePath = "/assets/overworld/outside/Overworld-";

    public void createChunk(GameScreen parentScreen) {
        chunk = new TileGridContainer(parentScreen, OverworldMeta.ChunkSize, OverworldMeta.ChunkSize,
                                                OverworldMeta.TileSize, OverworldMeta.TileSize, 0, 0);
    }
    public void editChunk(TileGridContainer chunk){
        this.chunk = chunk;
    }

    public TileGridContainer getChunk(){
        return chunk;
    }

    public void addGrassTileAt(int row, int col){
        addTitleAt(outsidePath + "Grass.png", row, col );
    }

    public void fillWithGrass(){
        for(int row = 0; row < OverworldMeta.ChunkSize; row++){
            for(int col = 0; col < OverworldMeta.ChunkSize; col++){
                addGrassTileAt(row, col);
            }
        }
    }

    public void addHouseTileAt(int row, int col){
        addTitleAt(roomPath + "RedCarpet.png", row, col );
    }

    public void addHouseTileAt(int row, int col, Compass wall){
        String path = roomPath + "RedCarpet-" + wall.name() + "-Wall.png";
        addTitleAt(path, row, col);
    }

    public void addHouseTileAt(int row, int col, Compass wallNS, Compass wallEW){
        String path = roomPath + "RedCarpet-" + wallNS.name() + wallEW.name() + "-Wall.png";
        addTitleAt(path, row, col);
    }

    private void addTitleAt(String imagePath, int row, int col){
        chunk.dynamicAddAt(new Tile(imagePath), row, col);
    }

    public void setDrawLayer(DrawLayer drawLayer){
        layer = drawLayer;
    }

}
