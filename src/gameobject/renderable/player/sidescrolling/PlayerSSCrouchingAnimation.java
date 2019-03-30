package gameobject.renderable.player.sidescrolling;

import gameengine.rendering.animation.Animation;
import gameengine.rendering.animation.AnimationImage;

public class PlayerSSCrouchingAnimation extends Animation {

    public PlayerSSCrouchingAnimation(String imageDirectory) {
        String path = "/assets/player/overworld/teddyidleanimation/";
        AnimationImage idle = new AnimationImage("/assets/testAssets/square2_crouching.png", 60);
        AnimationImage lookCenter = new AnimationImage(path + "Overworld-Teddy-Center.png", 60);
        AnimationImage lookLeft1 = new AnimationImage(path + "Overworld-Teddy-Left1.png", 5);
        AnimationImage lookLeft2 = new AnimationImage(path + "Overworld-Teddy-Left2.png", 5);
        AnimationImage lookLeft3 = new AnimationImage(path + "Overworld-Teddy-Left3.png", 60);
        AnimationImage lookRight1 = new AnimationImage(path + "Overworld-Teddy-Right1.png", 5);
        AnimationImage lookRight2 = new AnimationImage(path + "Overworld-Teddy-Right2.png", 5);
        AnimationImage lookRight3 = new AnimationImage(path + "Overworld-Teddy-Right3.png", 60);
        addAnimationImage(idle);
    }

    @Override
    public String getName() {
        return "SS_Idle";
    }
}
