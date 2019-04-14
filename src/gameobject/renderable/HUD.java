package gameobject.renderable;

import gameengine.gamedata.PlayerData;
import main.utilities.AssetLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD extends RenderableObject {

    private PlayerData playerData;
    //Image
    private BufferedImage portrait;
    private BufferedImage portraitDamaged;
    private BufferedImage heartFull;
    private BufferedImage heartHalf;
    private BufferedImage heartEmpty;
    private BufferedImage armorHeartFull;
    private BufferedImage armorHeartHalf;
    //Path
    private String portraitPath;
    private String portraitDamagedPath;
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
    //Name
    protected Font font = new Font("NoScary", Font.PLAIN, 48);
    protected Color color = Color.WHITE;



    public HUD(PlayerData pd){
        super();
        playerData = pd;
        portraitPath = "/assets/player/color/" + pd.getImageDirectory() + "/hud/Portrait.png";
        portraitDamagedPath = "/assets/player/color/" + pd.getImageDirectory() + "/hud/Portrait-Damaged.png";
        heartFullPath = "/assets/player/color/" + pd.getImageDirectory() + "/hud/Heart-FULL.png";
        heartHalfPath = "/assets/player/color/" + pd.getImageDirectory() + "/hud/Heart-HALF.png";
        heartEmptyPath = "/assets/player/hud/Heart-EMPTY.png";
        armorHeartFullPath = "/assets/player/hud/ArmorHeart-FULL.png";
        armorHeartHalfPath = "/assets/player/hud/ArmorHeart-HALF.png";
    }

    @Override
    public void load() {
        if(portrait == null) portrait = AssetLoader.load(portraitPath);
        if(portraitDamaged == null) portraitDamaged = AssetLoader.load(portraitDamagedPath);
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
        if (playerData.getCurrentHealth() > 2)
            graphics.drawImage(portrait, 30, 30, portrait.getWidth(), portrait.getHeight(), null);
        else graphics.drawImage(portraitDamaged, 30, 30, portrait.getWidth(), portrait.getHeight(), null);
        for(int i = 1; i <= playerData.getMaxHealth(); i += 2){
            if(i+1 <= playerData.getCurrentHealth())
                graphics.drawImage(heartFull, heartStartX + heartWidth * (i)/2 , heartStartY,
                        heartWidth, heartHeight, null);
            else if(i <= playerData.getCurrentHealth())
                graphics.drawImage(heartHalf, heartStartX + heartWidth * (i)/2 , heartStartY,
                        heartWidth, heartHeight, null);
            else
                graphics.drawImage(heartEmpty, heartStartX + heartWidth * (i)/2 , heartStartY,
                        heartWidth, heartHeight, null);
        }
        for(int i = 1; i <= playerData.getCurrentArmor(); i+=2){
            if(i+1 <= playerData.getCurrentArmor())
                graphics.drawImage(armorHeartFull, heartStartX + heartWidth * (i)/2 , heartStartY + heartHeight+10,
                        heartWidth, heartHeight, null);
            else
                graphics.drawImage(armorHeartHalf, heartStartX + heartWidth * (i)/2 , heartStartY + heartHeight+10,
                        heartWidth, heartHeight, null);
        }
        graphics.setFont(font);
        graphics.setColor(color);
        graphics.drawString(playerData.getName(), 30, 230);
    }

    @Override
    public void update() {

    }
}
