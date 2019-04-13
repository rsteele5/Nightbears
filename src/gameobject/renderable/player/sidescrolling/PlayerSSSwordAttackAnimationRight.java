package gameobject.renderable.player.sidescrolling;

import gameengine.rendering.animation.Animation;
import gameengine.rendering.animation.AnimationImage;

public class PlayerSSSwordAttackAnimationRight extends Animation {

    public PlayerSSSwordAttackAnimationRight(String imageDirectory) {
        String path = "/assets/player/color/" + imageDirectory + "/sidescroll/attack/sword/right/";
        AnimationImage attack1 = new AnimationImage(path + "Teddy-Attack1.png", 2);
        AnimationImage attack2 = new AnimationImage(path + "Teddy-Attack2.png", 2);
        AnimationImage attack3 = new AnimationImage(path + "Teddy-Attack3.png", 2);
        AnimationImage attack4 = new AnimationImage(path + "Teddy-Attack4.png", 2);
        AnimationImage attack5 = new AnimationImage(path + "Teddy-Attack5.png", 2);
        AnimationImage attack6 = new AnimationImage(path + "Teddy-Attack6.png", 2);
        AnimationImage attack7 = new AnimationImage(path + "Teddy-Attack7.png", 10);

        addAnimationImage(attack1);
        addAnimationImage(attack2);
        addAnimationImage(attack3);
        addAnimationImage(attack4);
        addAnimationImage(attack5);
        addAnimationImage(attack6);
        addAnimationImage(attack7);
    }

    @Override
    public String getName() {
        return "SS_Sword_Attack_Right";
    }

}