package gameengine.gamedata;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CopyOnWriteArrayList;

public class EndGamePlayerData implements Serializable {

    private String imagePath;
    private String pressedImagePath;
    private String name;
    private LocalDate creationDate;
    private LocalDate deathDate;
    private LocalDate victoryDate;
    private int gold;

    public EndGamePlayerData(
            int gold,
            String imagePath,
            String name,
            LocalDate creationDate,
            LocalDate deathDate,
            LocalDate victoryDate
    ){
        this.gold = gold;
        this.imagePath = imagePath;
        this.pressedImagePath = imagePath;
        this.name = name;
        this.creationDate = creationDate;
        this.deathDate = deathDate;
        this.victoryDate = victoryDate;
    }

    public int getGold() {
        return gold;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getPressedImagePath() {
        return pressedImagePath;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public LocalDate getVictoryDate() {
        return victoryDate;
    }
}
