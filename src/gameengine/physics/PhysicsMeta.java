package gameengine.physics;

public enum PhysicsMeta {;
    public static double Gravity = .15;
    public static int terminalVelocity = 10;
    //TODO: Implement zero gravity physics, use at your own risk tbh
    public static boolean AntiGravity = Gravity < 0;
}
