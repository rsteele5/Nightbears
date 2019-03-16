package gameobject.renderable.house.overworld;

import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.container.GridContainer;

public class ChunkBuilder {

    GridContainer chunk;
    private DrawLayer layer = DrawLayer.Background;
    private String roomPath = "/assets/overworld/room/Overworld-";
    private String outsidePath = "/assets/overworld/outside/Overworld-";

    public void createNewChunk(GameScreen parentScreen) {
        chunk = new GridContainer(parentScreen, OverworldMeta.ChunkSize, OverworldMeta.ChunkSize,
                                                OverworldMeta.TileSize, OverworldMeta.TileSize,
                                                0, 0,0);
    }

    public GridContainer getChunk(){
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

    public void fillWithCarpet(){
        for(int row = 0; row < OverworldMeta.ChunkSize; row++){
            for(int col = 0; col < OverworldMeta.ChunkSize; col++){
                addHouseTileAt(row, col);
            }
        }
    }

    public void addHouseTileAt(int row, int col, Compass wall){
        String path = roomPath + "RedCarpet-" + wall.name() + "-Wall.png";
        addTitleAt(path, row, col);
    }

    private void addTitleAt(String imagePath, int row, int col){
        chunk.addAt(new Tile(imagePath), row, col);
    }

    public void setDrawLayer(DrawLayer drawLayer){
        layer = drawLayer;
    }

}
