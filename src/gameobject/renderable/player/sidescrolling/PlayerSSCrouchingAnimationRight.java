package gameobject.renderable.player.sidescrolling;

import gameengine.rendering.animation.Animation;
import gameengine.rendering.animation.AnimationImage;

public class PlayerSSCrouchingAnimationRight extends Animation {

    public PlayerSSCrouchingAnimationRight(String imageDirectory) {
        String path = "/assets/player/color/" + imageDirectory + "/sidescroll/crawling/right/";
        AnimationImage crawl1 = new AnimationImage(path + "TeddyCrawling2.png", 10);

        addAnimationImage(crawl1);
    }

    @Override
    public String getName() {
        return "SS_Crouch_Right";
    }
}