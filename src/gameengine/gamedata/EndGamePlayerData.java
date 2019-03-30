package gameengine.gamedata;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class EndGamePlayerData implements Serializable {
    private CopyOnWriteArrayList<PlayerData> previousPlayers = new CopyOnWriteArrayList<>();
    private int gold;
    private String imagePath;
    private String pressedImagePath;

    public EndGamePlayerData(){
        gold = 10;
        imagePath = "";
        pressedImagePath = "";
    }

    public void addPlayer(PlayerData pData) {
        previousPlayers.add(pData);
    }

    public CopyOnWriteArrayList<PlayerData> getPreviousPlayers() {
        return previousPlayers;
    }

    public int getGold() {
        return gold;
    }

    public void changeGold(int amt) {
        gold += amt;
    }

    public void setImagePath(String path) {
        this.imagePath = path;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setPressedImagePath(String path) {
        this.pressedImagePath = path;
    }

    public String getPressedImagePath() {
        return pressedImagePath;
    }
}
