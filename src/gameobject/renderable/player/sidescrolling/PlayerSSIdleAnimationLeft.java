package gameobject.renderable.player.sidescrolling;

import gameengine.rendering.animation.Animation;
import gameengine.rendering.animation.AnimationImage;

public class PlayerSSIdleAnimationLeft extends Animation {

    public PlayerSSIdleAnimationLeft(String imageDirectory) {
        String path = "/assets/player/color/" + imageDirectory + "/sidescroll/idle/left/";
        AnimationImage idle1 = new AnimationImage(path + "Teddy-Idle1.png", 20);
        AnimationImage idle2 = new AnimationImage(path + "Teddy-Idle2.png", 15);
        AnimationImage idle3 = new AnimationImage(path + "Teddy-Idle3.png", 20);

        addAnimationImage(idle1);
        addAnimationImage(idle2);
        addAnimationImage(idle3);
    }

    @Override
    public String getName() {
        return "SS_Idle_Left";
    }
}
