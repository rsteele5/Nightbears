package gameobject.renderable.vendor;

import gameengine.rendering.animation.Animation;
import gameengine.rendering.animation.AnimationImage;

public class VendorIdleAnimation extends Animation {

    public VendorIdleAnimation(){
        String path = "/assets/vendor/vendoridleanimation/";

        AnimationImage lookCenter = new AnimationImage(path + "VendorOverworldForward.png", 60);
        AnimationImage lookLeft1 = new AnimationImage(path + "VendorOverworldForwardLeft.png", 5);
        AnimationImage lookLeft2 = new AnimationImage(path + "VendorOverworldForwardLeft2.png", 5);
        AnimationImage lookLeft3 = new AnimationImage(path + "VendorOverworldForwardLeft3.png", 60);
        AnimationImage lookRight1 = new AnimationImage(path + "VendorOverworldForwardRight.png", 5);
        AnimationImage lookRight2 = new AnimationImage(path + "VendorOverworldForwardRight2.png", 5);
        AnimationImage lookRight3 = new AnimationImage(path + "VendorOverworldForwardRight3.png", 60);

        addAnimationImage(lookCenter);
        addAnimationImage(lookLeft1);
        addAnimationImage(lookLeft2);
        addAnimationImage(lookLeft3);
        addAnimationImage(lookLeft2);
        addAnimationImage(lookLeft1);
        addAnimationImage(lookCenter);
        addAnimationImage(lookRight1);
        addAnimationImage(lookRight2);
        addAnimationImage(lookRight3);
        addAnimationImage(lookRight2);
        addAnimationImage(lookRight1);
    }

    @Override
    public String getName() {
        return "Idle";
    }
}
