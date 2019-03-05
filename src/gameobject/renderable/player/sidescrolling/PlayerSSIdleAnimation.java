package gameobject.renderable.player.sidescrolling;

import gameengine.rendering.animation.Animation;
import gameengine.rendering.animation.AnimationImage;

public class PlayerSSIdleAnimation extends Animation {

    public PlayerSSIdleAnimation() {
        AnimationImage idle = new AnimationImage("/assets/testAssets/square2.png", 60);

        addAnimationImage(idle);
    }

    @Override
    public String getName() {
        return "SS_Idle";
    }
}
