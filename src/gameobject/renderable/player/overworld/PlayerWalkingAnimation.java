package gameobject.renderable.player.overworld;

import gameengine.rendering.animation.Animation;
import gameengine.rendering.animation.AnimationImage;

public class PlayerWalkingAnimation extends Animation {

    public PlayerWalkingAnimation(String imageDirectory) {
        String path = "/assets/player/color/" + imageDirectory + "/overworld/walking/";
        AnimationImage walking1 = new AnimationImage(path + "Overworld-Teddy-Walking1.png", 10);
        AnimationImage walking2 = new AnimationImage(path + "Overworld-Teddy-Walking2.png", 10);
        AnimationImage walking3 = new AnimationImage(path + "Overworld-Teddy-Walking3.png", 10);
        AnimationImage walking4 = new AnimationImage(path + "Overworld-Teddy-Walking4.png", 10);

        addAnimationImage(walking2);
        addAnimationImage(walking3);
        addAnimationImage(walking4);
        addAnimationImage(walking1);
    }

    @Override
    public String getName() {
        return "Walking";
    }
}
