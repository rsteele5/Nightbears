package gameengine.gamedata;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CopyOnWriteArrayList;

public class EndGamePlayerData implements Serializable {
//    private CopyOnWriteArrayList<PlayerData> previousPlayers = new CopyOnWriteArrayList<>();
    private String imagePath;
    private String pressedImagePath;
    private String name;
    private LocalDate creationDate;
    private LocalDate deathDate;
    private LocalDate victoryDate;
    private int gold;

    public EndGamePlayerData(){
        gold = 10;
        imagePath = "";
        pressedImagePath = "";
        name = "";
        creationDate = null;
        deathDate = null;
        victoryDate = null;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreationDate(LocalDate birthday) {
        this.creationDate = birthday;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setDeathDate(LocalDate deathDay) {
        this.deathDate = deathDay;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public void setVictoryDate(LocalDate victoryDay) {
        this.victoryDate = victoryDay;
    }

    public LocalDate getVictoryDate() {
        return victoryDate;
    }
}
