package gameobject;

import gameobject.renderable.player.Player;

public class CameraTarget extends GameObject {

    private Player player;

    public CameraTarget(Player player){
        super(player.getX(), player.getY());
        this.player = player;
    }

    @Override
    public void update() {
        if(!player.isAttacking()){
            x = player.getX();
            y = player.getY();
        }
    }
}
