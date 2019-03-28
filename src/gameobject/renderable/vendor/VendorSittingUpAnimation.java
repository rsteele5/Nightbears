package gameobject.renderable.vendor;

import gameengine.rendering.animation.Animation;
import gameengine.rendering.animation.AnimationImage;

public class VendorSittingUpAnimation extends Animation {

    public VendorSittingUpAnimation(){
        String path = "/assets/vendor/vendorsittingupanimation/";
        AnimationImage sit1 = new AnimationImage(path + "VendorSittingUp1.png", 10);
        AnimationImage sit2 = new AnimationImage(path + "VendorSittingUp2.png", 10);
        AnimationImage sit3 = new AnimationImage(path + "VendorSittingUp3.png", 10);
        AnimationImage sit4 = new AnimationImage(path + "VendorSittingUp4.png", 10);
        AnimationImage sit5 = new AnimationImage(path + "VendorSittingUp5.png", 10);
        AnimationImage sit6 = new AnimationImage(path + "VendorSittingUp6.png", 10);
        AnimationImage sit7 = new AnimationImage(path + "VendorSittingUp7.png", 10);
        AnimationImage sit8 = new AnimationImage(path + "VendorSittingUp8.png", 10);

        addAnimationImage(sit1);
        addAnimationImage(sit2);
        addAnimationImage(sit3);
        addAnimationImage(sit4);
        addAnimationImage(sit5);
        addAnimationImage(sit6);
        addAnimationImage(sit7);
        addAnimationImage(sit8);

    }

    @Override
    public String getName() {
        return "SittingUp";
    }
}
