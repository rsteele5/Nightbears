package gameobject.renderable.player.sidescrolling;

import gameengine.rendering.animation.Animation;
import gameengine.rendering.animation.AnimationImage;

public class PlayerSSHitStunAnimationRight extends Animation {

    public PlayerSSHitStunAnimationRight(String imageDirectory) {
        String path = "/assets/player/color/" + imageDirectory + "/sidescroll/hitstun/right/Teddy-HitStun.png";
        AnimationImage hitStun1 = new AnimationImage(path, 45);

        addAnimationImage(hitStun1);
    }

    @Override
    public String getName() {
        return "SS_HitStun_Right";
    }
}
