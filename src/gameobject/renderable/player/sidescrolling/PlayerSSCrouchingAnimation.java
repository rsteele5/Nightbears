package gameobject.renderable.player.sidescrolling;

import gameengine.rendering.animation.Animation;
import gameengine.rendering.animation.AnimationImage;

public class PlayerSSCrouchingAnimation extends Animation {

    public PlayerSSCrouchingAnimation() {
        AnimationImage idle = new AnimationImage("/assets/testAssets/square2_crouching.png", 60);

        addAnimationImage(idle);
    }

    @Override
    public String getName() {
        return "SS_Idle";
    }
}
