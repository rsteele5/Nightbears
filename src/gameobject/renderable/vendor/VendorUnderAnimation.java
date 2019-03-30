package gameobject.renderable.vendor;

import gameengine.rendering.animation.Animation;
import gameengine.rendering.animation.AnimationImage;

public class VendorUnderAnimation extends Animation {

    public VendorUnderAnimation() {
        String path = "/assets/vendor/vendorcrawlinganimation/";
        AnimationImage wait = new AnimationImage(path + "VendorCrawling1.png", 120);
        AnimationImage done = new AnimationImage(path + "VendorCrawling1.png", 120);

        addAnimationImage(wait);
        addAnimationImage(done);

    }

    @Override
    public String getName() {
        return "Crawling";
    }
}
