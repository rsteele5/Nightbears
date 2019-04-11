package gameobject.renderable.player.sidescrolling;

import gameengine.rendering.animation.Animation;
import gameengine.rendering.animation.AnimationImage;

public class PlayerSSCrawlingAnimationLeft extends Animation {

    public PlayerSSCrawlingAnimationLeft(String imageDirectory) {
        String path = "/assets/player/color/" + imageDirectory + "/sidescroll/crawling/left/";
        AnimationImage crawl1 = new AnimationImage(path + "TeddyCrawling1.png", 10);
        AnimationImage crawl2 = new AnimationImage(path + "TeddyCrawling2.png", 10);

        addAnimationImage(crawl1);
        addAnimationImage(crawl2);
    }

    @Override
    public String getName() {
        return "SS_Crawl_Left";
    }
}
