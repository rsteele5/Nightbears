package gameobject.renderable.player.sidescrolling;

import gameengine.rendering.animation.Animation;
import gameengine.rendering.animation.AnimationImage;

public class PlayerSSCrouchingAnimationLeft extends Animation {

    public PlayerSSCrouchingAnimationLeft(String imageDirectory) {
        String path = "/assets/player/color/" + imageDirectory + "/sidescroll/crawling/left/";
        AnimationImage crawl1 = new AnimationImage(path + "TeddyCrawling2.png", 10);

        addAnimationImage(crawl1);
    }

    @Override
    public String getName() {
        return "SS_Crouch_Left";
    }
}