package gamescreen.mainmenu;

import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import main.utilities.Debug;
import main.utilities.DebugEnabler;
import java.util.ArrayList;


public class HeroHallScreen extends GameScreen {

    //region <Variables>
    private ArrayList<Button> heroes = new ArrayList<>();
    private final int MAX_NUMBER_OF_HEROES = 3;
    private int[] heroesOnScreenIndex = new int[MAX_NUMBER_OF_HEROES];
    //endregion

    //region <Construction and Initialization>
    public HeroHallScreen(ScreenManager screenManager) {
        super(screenManager, "HeroHallScreen", true);
    }

    @Override
    protected void initializeScreen() {

        //Initial position of the first button
        int X_INIT_BUTTON = 64;
        int Y_INIT_BUTTON = 920;
        int WIDTH_BUTTON = 256;
        int X_BUFFER = 48;

        //TEST: Add four heroes (will later be loaded from GameData
        heroes.add(new Button(0, 0,"/assets/player/images/Teddy.png", DrawLayer.Entity));
        heroes.add(new Button(0, 0, "/assets/player/images/TeddyRed.png", DrawLayer.Entity));
        heroes.add(new Button(0, 0,"/assets/player/images/TeddyOrange.png", DrawLayer.Entity));
        heroes.add(new Button(0,0,"/assets/player/images/TeddyYellow.png", DrawLayer.Entity));
        heroes.add(new Button(0,0,"/assets/player/images/TeddyBlue.png", DrawLayer.Entity));

        for (Button b: heroes) {
            b.addToScreen(this, true); //NOTE: isActive/isInactive cannot be called inside lambda expressions
                                                      //Therefore, any teddy bear not displayed has to be rendered off screen
        }

        for (int i = 0; i < heroes.size(); i++) {
            if (i < MAX_NUMBER_OF_HEROES) {
                heroesOnScreenIndex[i] = i;
                heroes.get(i).setX(X_INIT_BUTTON + (i * 2 + 1) * (X_BUFFER + WIDTH_BUTTON - 30));
                heroes.get(i).setY(Y_INIT_BUTTON - 230);
            } else {
                heroes.get(i).setX(9999);
                heroes.get(i).setY(9999);
            }
        }


        //Create Background
        ImageContainer imageContainer;

        imageContainer = new ImageContainer(0,0, "/assets/backgrounds/BG-HeroHall.png", DrawLayer.Background);
        imageContainer.addToScreen(this, true);

        //Create button
        Button butt;

        //Left Arrow
        butt = new Button(X_INIT_BUTTON, Y_INIT_BUTTON - 200,
                "/assets/buttons/Button-LeftArrow.png",
                "/assets/buttons/Button-LeftArrowPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Left Arrow");
                    for (int i = 0; i < heroes.size(); i++) {
                        if (i < MAX_NUMBER_OF_HEROES) {
                            int newIndex = ((heroesOnScreenIndex[i]) - 1 < 0) ? heroes.size() - 1 : heroesOnScreenIndex[i] - 1;
                            heroesOnScreenIndex[i] = newIndex;
                        }
                        heroes.get(i).setX(9999);
                        heroes.get(i).setY(9999);
                    }

                    for (int i = 0; i < heroesOnScreenIndex.length; i++) {
                        heroes.get(heroesOnScreenIndex[i]).setX(X_INIT_BUTTON + (i * 2 + 1) * (X_BUFFER + WIDTH_BUTTON - 30));
                        heroes.get(heroesOnScreenIndex[i]).setY(Y_INIT_BUTTON - 230);
                    }
                });
        butt.addToScreen(this, true);

        //Right Arrow
        butt = new Button(X_INIT_BUTTON + 6 * (X_BUFFER + WIDTH_BUTTON) - 60, Y_INIT_BUTTON - 200,
                "/assets/buttons/Button-RightArrow.png",
                "/assets/buttons/Button-RightArrowPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Right Arrow");
                    for (int i = 0; i < heroes.size(); i++) {
                        if (i < MAX_NUMBER_OF_HEROES) {
                            int newIndex = (heroesOnScreenIndex[i] + 1 > heroes.size() - 1) ? 0 : heroesOnScreenIndex[i] + 1;
                            heroesOnScreenIndex[i] = newIndex;
                        }
                        heroes.get(i).setX(9999);
                        heroes.get(i).setY(9999);
                    }

                    for (int i = 0; i < heroesOnScreenIndex.length; i++) {
                        heroes.get(heroesOnScreenIndex[i]).setX(X_INIT_BUTTON + (i * 2 + 1) * (X_BUFFER + WIDTH_BUTTON - 30));
                        heroes.get(heroesOnScreenIndex[i]).setY(Y_INIT_BUTTON - 230);
                    }
                });
        butt.addToScreen(this, true);

        //Back
        butt = new Button(X_INIT_BUTTON + 5 * (X_BUFFER + WIDTH_BUTTON),
                Y_INIT_BUTTON,
                "/assets/buttons/Button-Back.png",
                "/assets/buttons/Button-BackPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Back");
                    setScreenState(ScreenState.TransitionOff);
                });
        butt.addToScreen(this, true);
    }

    //endregion
}
