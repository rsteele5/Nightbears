package gameobject.renderable.house.overworld;

public enum OverworldMeta {;
    public static int TileSize = 100;   // Width and Height of each tile
    public static int ChunkSize = 5;    // # of tiles that makeup the width and height of a chunk
    public static int BorderBuffer = 2; // Grass border that surrounds the map

    //region<Tiles>
    public enum Tiles {;
        public static int EMPTY = 0;
        //House tiles
        public enum House {;
            public static int CARPET =  1;
            public static int WALLN =   2;
            public static int WALLNE =  3;
            public static int WALLE =   4;
            public static int WALLSE =  5;
            public static int WALLS =   6;
            public static int WALLSW =  7;
            public static int WALLW =   8;
            public static int WALLNW =  9;
        }
        public enum Outside{;
            public static int GRASS =   20;
        }
    }
    //endregion
}
