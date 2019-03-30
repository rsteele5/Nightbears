package gameobject.renderable.house.overworld;

import gameobject.renderable.DrawLayer;
import gameobject.container.TileGridContainer;

public class ChunkBuilder {

    TileGridContainer chunk;
    private DrawLayer layer = DrawLayer.Background;
    private String roomPath = "/assets/overworld/room/Overworld-";
    private String outsidePath = "/assets/overworld/outside/Overworld-";

    public void createChunk() {
        chunk = new TileGridContainer(OverworldMeta.ChunkSize, OverworldMeta.ChunkSize,
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
                if(chunk.getContentAt(row,col) == null)
                    addGrassTileAt(row, col);
            }
        }
    }

    public void addHouseTileAt(int row, int col){
        addTitleAt(roomPath + "RedCarpet.png", row, col );
    }

    public void addHouseTileAt(int row, int col, Compass wall1, Compass wall2, boolean endcap, Compass capDirection){
        String path = roomPath + "RedCarpet-";
        path += wall1 != null ? wall1.name(): "";
        path += wall2 != null ? wall2.name(): "";
        path += endcap ? "-WallC" : "-Wall";
        path += capDirection != null ? capDirection.name(): "";
        path += ".png";
        addTitleAt(path, row, col);
    }

    private void addTitleAt(String imagePath, int row, int col){
        chunk.addAt(new Tile(imagePath), row, col);
    }

    public void setDrawLayer(DrawLayer drawLayer){
        layer = drawLayer;
    }

    public void removeAt(int chunkX, int chunkY) {
        chunk.removeAt(chunkX,chunkY);
    }
}
