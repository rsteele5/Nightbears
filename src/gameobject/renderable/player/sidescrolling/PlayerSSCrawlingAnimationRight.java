package gameobject.renderable.player.sidescrolling;

import gameengine.rendering.animation.Animation;
import gameengine.rendering.animation.AnimationImage;

public class PlayerSSCrawlingAnimationRight extends Animation {

    public PlayerSSCrawlingAnimationRight(String imageDirectory) {
        String path = "/assets/player/color/" + imageDirectory + "/sidescroll/crawling/right/";
        AnimationImage crawl1 = new AnimationImage(path + "TeddyCrawling1.png", 10);
        AnimationImage crawl2 = new AnimationImage(path + "TeddyCrawling2.png", 10);

        addAnimationImage(crawl1);
        addAnimationImage(crawl2);
    }

    @Override
    public String getName() {
        return "SS_Crawl_Right";
    }
}
