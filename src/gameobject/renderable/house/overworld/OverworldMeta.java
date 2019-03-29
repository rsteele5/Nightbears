package gameobject.renderable.house.overworld;

public enum OverworldMeta {;
    public static final int TileSize = 100;     // Width and Height of each tile
    public static final int ChunkSize = 5;      // # of tiles that makeup the width and height of a chunk
    public static final int BorderBuffer = 2;   // Grass border that surrounds the map
    public static final int WallThickness = 34; // Thickness of walls in the Overworld

    //region<Tiles>
    public enum Tiles {;
        public static final int EMPTY = 0;
        //House tiles
        public enum House {;
            public static final int CARPET  =   1;
            //North
            public static final int WALLN   =   2;
            public static final int WALLNCE =   3;
            public static final int WALLNCW =   4;
            //East
            public static final int WALLE   =   5;
            public static final int WALLECN =   6;
            public static final int WALLECS =   7;
            //South
            public static final int WALLS   =   8;
            public static final int WALLSCE =   9;
            public static final int WALLSCW =  10;
            //West
            public static final int WALLW   =  11;
            public static final int WALLWCN =  12;
            public static final int WALLWCS =  13;
            //Corners
            public static final int WALLNW  =  14;
            public static final int WALLNE  =  15;
            public static final int WALLSE  =  16;
            public static final int WALLSW  =  17;
            //EndCaps
            public static final int WALLNWC =  18;
            public static final int WALLNEC =  19;
            public static final int WALLSWC =  20;
            public static final int WALLSEC =  21;


        }
        public enum Outside{;
            public static final int GRASS  =  30;
        }
    }
    //endregion
}
