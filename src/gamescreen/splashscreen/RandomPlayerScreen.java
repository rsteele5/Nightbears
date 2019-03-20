package gamescreen.splashscreen;

import gameobject.renderable.DrawLayer;
import gameobject.renderable.ImageContainer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import input.listeners.MouseController;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.io.File;
import java.util.ArrayList;


public class RandomPlayerScreen extends GameScreen {

    private ImageContainer silhouette;
    private ImageContainer teddyImage;
    private boolean clicked;

    public ArrayList<ImageContainer> characterImages;

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
    }

    private ImageContainer getRandomImage(){
        ArrayList<ImageContainer> images = new ArrayList<>();

        File file = new File("src/assets/player/images");
        String[] directories = file.list();
        int xPos = 20;
        for(String filePath: directories) {
            Debug.error(true, "Directory: " + filePath);
            ImageContainer image = new ImageContainer(912,723,
                    "/src/assets/player/images/" + filePath,
                    DrawLayer.Entity);
            images.add(image);

            xPos += 220;
        }

        int totalImages = images.size() - 1;
        int randomIndex = (int)(Math.random() * (totalImages));
        return images.get(randomIndex);
    }

    @Override
    public boolean handleMousePress(MouseController mouseController, int x, int y){
        if(!clicked) {
            Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "- handle click " + x + " " + y);
            silhouette.setAlpha(0f);
            clicked = true;
        } else {
            setScreenState(ScreenState.TransitionOff);
        }

        return true;
    }

//    @Override
//    public boolean handleClickEvent(int x, int y) {
//        if(!clicked) {
//            Debug.log(DebugEnabler.GAME_SCREEN_LOG, name + "- handle click " + x + " " + y);
//            silhouette.setAlpha(0f);
//            clicked = true;
//        } else {
//            setScreenState(ScreenState.TransitionOff);
//        }
//
//        return true;
//    }

    @Override
    protected void transitionOff() {
        exiting = true;
        screenManager.addScreen(new IntroCutScene(screenManager));
    }
}
