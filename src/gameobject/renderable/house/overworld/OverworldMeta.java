package gameobject.renderable.house.overworld;

public enum OverworldMeta {;
    public static final int TileSize = 100;     // Width and Height of each tile
    public static final int ChunkSize = 5;      // # of tiles that makeup the width and height of a chunk
    public static final int BorderBuffer = 2;   // Grass border that surrounds the map
    public static final int WallThickness = 20; // Thickness of walls in the Overworld

    //region<Tiles>
    public enum Tiles {;
        public static final int EMPTY = 0;
        //House tiles
        public enum House {;
            public static final int CARPET  =   1;
            public static final int WALLN   =   2;
            public static final int WALLNE  =   3;
            public static final int WALLE   =   4;
            public static final int WALLSE  =   5;
            public static final int WALLS   =   6;
            public static final int WALLSW  =   7;
            public static final int WALLW   =   8;
            public static final int WALLNW  =   9;
            public static final int WALLNWC =  10;
            public static final int WALLNEC =  11;
            public static final int WALLSWC =  12;
            public static final int WALLSEC =  13;
            public static final int WALLNC  =  14;
            public static final int WALLEC  =  15;
            public static final int WALLSC  =  16;
            public static final int WALLWC  =  17;

        }
        public enum Outside{;
            public static final int GRASS  =  20;
        }
    }
    //endregion
}
