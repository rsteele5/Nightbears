package gameobject.renderable.vendor;

import gameengine.rendering.animation.Animation;
import gameengine.rendering.animation.AnimationImage;

public class VendorCrawlingAnimation extends Animation {

    public VendorCrawlingAnimation() {
        String path = "/assets/vendor/vendorcrawlinganimation/";
        AnimationImage crawl1 = new AnimationImage(path + "VendorCrawling1.png", 10);
        AnimationImage crawl2 = new AnimationImage(path + "VendorCrawling2.png", 10);
        AnimationImage crawl3 = new AnimationImage(path + "VendorCrawling3.png", 10);
        AnimationImage crawl4 = new AnimationImage(path + "VendorCrawling4.png", 10);
        AnimationImage crawl5 = new AnimationImage(path + "VendorCrawling5.png", 10);
        AnimationImage crawl6 = new AnimationImage(path + "VendorCrawling6.png", 10);
        AnimationImage crawl7 = new AnimationImage(path + "VendorCrawling7.png", 10);
        AnimationImage crawl8 = new AnimationImage(path + "VendorCrawling8.png", 10);

        addAnimationImage(crawl1);
        addAnimationImage(crawl2);
        addAnimationImage(crawl3);
        addAnimationImage(crawl4);
        addAnimationImage(crawl5);
        addAnimationImage(crawl6);
        addAnimationImage(crawl7);
        addAnimationImage(crawl8);

    }

    @Override
    public String getName() {
        return "Crawling";
    }
}
