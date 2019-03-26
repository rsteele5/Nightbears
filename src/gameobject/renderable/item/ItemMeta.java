package gameobject.renderable.item;

public enum ItemMeta {;

    public static int minWeapon = 5;
    public static int maxWeapon = 10;
    public static int minArmor = 5;
    public static int maxArmor = 10;
    public static int minConsumable = 8;
    public static int maxConsumable = 15;
    public static double amplifier = 1.0;
    public static double depreciator = 0.9;

    public static void attributeAmplifier(){
        amplifier += .25;
    }

}




