package gameobject.renderable.player.sidescrolling;

import gameengine.rendering.animation.Animation;
import gameengine.rendering.animation.AnimationImage;

public class PlayerSSRunningAnimationRight extends Animation {

    public PlayerSSRunningAnimationRight(String imageDirectory) {
        String path = "/assets/player/color/" + imageDirectory + "/sidescroll/running/right/";
        AnimationImage running1 = new AnimationImage(path + "Teddy-Running1.png", 5);
        AnimationImage running2 = new AnimationImage(path + "Teddy-Running2.png", 5);
        AnimationImage running3 = new AnimationImage(path + "Teddy-Running3.png", 5);
        AnimationImage running4 = new AnimationImage(path + "Teddy-Running4.png", 5);
        AnimationImage running5 = new AnimationImage(path + "Teddy-Running5.png", 5);
        AnimationImage running6 = new AnimationImage(path + "Teddy-Running6.png", 5);
        AnimationImage running7 = new AnimationImage(path + "Teddy-Running7.png", 5);
        AnimationImage running8 = new AnimationImage(path + "Teddy-Running8.png", 5);

        addAnimationImage(running1);
        addAnimationImage(running2);
        addAnimationImage(running3);
        addAnimationImage(running4);
        addAnimationImage(running5);
        addAnimationImage(running6);
        addAnimationImage(running7);
        addAnimationImage(running8);
    }

    @Override
    public String getName() {
        return "SS_Running_Right";
    }
}
