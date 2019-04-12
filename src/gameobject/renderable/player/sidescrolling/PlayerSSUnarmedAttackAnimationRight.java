package gameobject.renderable.player.sidescrolling;

import gameengine.rendering.animation.Animation;
import gameengine.rendering.animation.AnimationImage;

public class PlayerSSUnarmedAttackAnimationRight extends Animation {

    public PlayerSSUnarmedAttackAnimationRight(String imageDirectory) {
        String path = "/assets/player/color/" + imageDirectory + "/sidescroll/attack/unarmed/right/";
        AnimationImage attack1 = new AnimationImage(path + "Teddy-Attack1.png", 2);
        AnimationImage attack2 = new AnimationImage(path + "Teddy-Attack2.png", 10);
        AnimationImage attack3 = new AnimationImage(path + "Teddy-Attack3.png", 10);
        AnimationImage attack4 = new AnimationImage(path + "Teddy-Attack4.png", 10);
        AnimationImage attack5 = new AnimationImage(path + "Teddy-Attack5.png", 2);
        AnimationImage attack6 = new AnimationImage(path + "Teddy-Attack5.png", 2);
        AnimationImage attack7 = new AnimationImage(path + "Teddy-Attack5.png", 2);

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
        return "SS_Unarmed_Attack_Right";
    }
}
