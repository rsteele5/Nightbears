package gamescreen.splashscreen;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.text.TextBox;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import input.listeners.MouseController;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class RandomPlayerScreen extends GameScreen {

    private ImageContainer silhouette;
    private ImageContainer teddyImage;
    private TextBox nameTextBox;
    private String name;
    private boolean clicked;

    public RandomPlayerScreen(ScreenManager screenManager) {
        super(screenManager, "RandomPlayerScreen", 1f);
    }

    @Override
    protected void initializeScreen() {
        ImageContainer background = new ImageContainer(0,0,
                "/assets/backgrounds/BG-RandomPlayer.png",
                DrawLayer.Background);
        background.addToScreen(this, true);

        teddyImage = getRandomImage();
        teddyImage.addToScreen(this,true);

        silhouette = new ImageContainer(912,723,
                "/assets/player/TeddySilhouette.png",
                DrawLayer.Entity);
        silhouette.addToScreen(this, true);

        name = getRandomName();
        nameTextBox = new TextBox(776, 963,
                400, 100, "", new Font("NoScary", Font.PLAIN, 60),
                Color.WHITE, true);
        nameTextBox.addToScreen(this, true);
    }

    private ImageContainer getRandomImage(){
        ArrayList<ImageContainer> images = new ArrayList<>();

        File file =  new File("src/assets/player/color");
        String[] directories = file.list();
        for(String filePath: directories) {
            ImageContainer image = new ImageContainer(912,723,
                    "/assets/player/color/" + filePath + "/Teddy.png",
                    DrawLayer.Entity);
            images.add(image);
            Debug.log(true, filePath);
        }

        int totalImages = images.size();
        int randomIndex = (int)(Math.random() * (totalImages));
        gameData.getPlayerData().setImageDirectory(directories[randomIndex]);
        return images.get(randomIndex);
    }

    private String getRandomName() {
        try {
            File firstNameFile = new File("src/gameengine/namegenerator/NameTitle.txt");
            File lastNameFile = new File("src/gameengine/namegenerator/Surname.txt");
            RandomAccessFile rFirstNameFile = new RandomAccessFile(firstNameFile, "r");
            RandomAccessFile rLastNameFile = new RandomAccessFile(lastNameFile, "r");
            int rLine1 = (int) (Math.random() * rFirstNameFile.length());
            int rLine2 = (int) (Math.random() * rLastNameFile.length());
            rFirstNameFile.seek(rLine1);
            rLastNameFile.seek(rLine2);

            //Prevent partial words from getting into the name by moving to the next available full line
            rFirstNameFile.readLine();
            rLastNameFile.readLine();

            String rFirstName = rFirstNameFile.readLine();
            String rLastName = rLastNameFile.readLine();
            gameData.getPlayerData().setName(rFirstName + " " + rLastName);
            return rFirstName + " " + rLastName;
        } catch (IOException e) {
            e.getMessage();
            return "";
        }
    }

    @Override
    public boolean handleMousePress(MouseController mouseController, int x, int y){
        if(!clicked) {
            Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "- handle click " + x + " " + y);
            silhouette.setAlpha(0f);
            nameTextBox.setText(name);
            clicked = true;
        } else {
            setScreenState(ScreenState.TransitionOff);
        }
        return true;
    }

    @Override
    protected void transitionOff() {
        exiting = true;
        screenManager.addScreen(new IntroCutScene(screenManager));
    }
}
