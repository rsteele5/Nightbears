package gameobject.renderable;

import gameengine.gamedata.PlayerData;
import gameobject.renderable.player.Player;
import main.utilities.AssetLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD extends RenderableObject {

    private Player player;
    private PlayerData playerData;
    //Image
    private BufferedImage portrait;
    private BufferedImage heartFull;
    private BufferedImage heartHalf;
    private BufferedImage heartEmpty;
    private BufferedImage armorHeartFull;
    private BufferedImage armorHeartHalf;
    //Path
    private String portraitPath;
    private String heartFullPath;
    private String heartHalfPath;
    private String heartEmptyPath;
    private String armorHeartFullPath;
    private String armorHeartHalfPath;
    //Size
    private int heartStartX = 202;
    private int heartStartY = 54;
    private int heartWidth = 64;
    private int heartHeight = 52;



    public HUD(PlayerData pd, Player player){
        super();
        this.player = player;
        playerData = pd;
        portraitPath = "/assets/player/color/" + pd.getImageDirectory() + "/hud/Portrait.png";
        heartFullPath = "/assets/player/color/" + pd.getImageDirectory() + "/hud/Heart-FULL.png";
        heartHalfPath = "/assets/player/color/" + pd.getImageDirectory() + "/hud/Heart-HALF.png";
        heartEmptyPath = "/assets/player/hud/Heart-EMPTY.png";
        armorHeartFullPath = "/assets/player/hud/ArmorHeart-FULL.png";
        armorHeartHalfPath = "/assets/player/hud/ArmorHeart-HALF.png";
    }

    @Override
    public void load() {
        if(portrait == null) portrait = AssetLoader.load(portraitPath);
        if(heartFull == null) heartFull = AssetLoader.load(heartFullPath);
        if(heartHalf == null) heartHalf = AssetLoader.load(heartHalfPath);
        if(heartEmpty == null) heartEmpty = AssetLoader.load(heartEmptyPath);
        if(armorHeartFull == null) armorHeartFull = AssetLoader.load(armorHeartFullPath);
        if(armorHeartHalf == null) armorHeartHalf = AssetLoader.load(armorHeartHalfPath);
    }

    @Override
    public void draw(Graphics2D graphics) {
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        graphics.setComposite(alphaComposite);
        graphics.drawImage(portrait, 30, 30, portrait.getWidth(), portrait.getHeight(), null);
        for(int i = 1; i <= player.getMaxHealth(); i += 2){
            if(i+1 < player.getCurrentHealth())
                graphics.drawImage(heartFull, heartStartX + heartWidth * (i)/2 , heartStartY,
                        heartWidth, heartHeight, null);
            else if(i <= player.getCurrentHealth())
                graphics.drawImage(heartHalf, heartStartX + heartWidth * (i)/2 , heartStartY,
                        heartWidth, heartHeight, null);
            else
                graphics.drawImage(heartEmpty, heartStartX + heartWidth * (i)/2 , heartStartY,
                        heartWidth, heartHeight, null);
        }
        for(int i = 1; i <= player.getCurrentArmor(); i+=2){
            if(i+1 < player.getCurrentArmor())
                graphics.drawImage(armorHeartFull, heartStartX + heartWidth * (i)/2 , heartStartY + heartHeight+10,
                        heartWidth, heartHeight, null);
            else
                graphics.drawImage(armorHeartHalf, heartStartX + heartWidth * (i)/2 , heartStartY + heartHeight+10,
                        heartWidth, heartHeight, null);
        }
    }

    @Override
    public void update() {

    }
}
