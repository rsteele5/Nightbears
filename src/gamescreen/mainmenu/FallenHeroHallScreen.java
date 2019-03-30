package gamescreen.mainmenu;

import gameengine.gamedata.EndGamePlayerData;
import gameobject.renderable.DrawLayer;
import gameobject.renderable.text.TextBox;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CopyOnWriteArrayList;


public class FallenHeroHallScreen extends GameScreen {

    //region <Variables>
    private CopyOnWriteArrayList<Button> fallenHeroButtons = new CopyOnWriteArrayList<>();
    private final int MAX_NUMBER_OF_FALLEN_HEROES = 3;
    private int[] fallenHeroesOnScreenIndex = new int[MAX_NUMBER_OF_FALLEN_HEROES];
    private TextBox displayFallenHeroAttributes;
    private CopyOnWriteArrayList<EndGamePlayerData> victims = new CopyOnWriteArrayList<>();
    //endregion

    //region <Construction and Initialization>
    public FallenHeroHallScreen(ScreenManager screenManager) {
        super(screenManager, "HeroHallScreen", true);
    }

    @Override
    protected void initializeScreen() {

        //Initial position of the first button
        int X_INIT_BUTTON = 64;
        int Y_INIT_BUTTON = 920;
        int WIDTH_BUTTON = 256;
        int X_BUFFER = 48;

        //load PlayerData
        loadPlayers();

        displayFallenHeroAttributes = new TextBox(X_INIT_BUTTON, Y_INIT_BUTTON - 700, 800, 800, "",
                new Font("NoScary", Font.PLAIN, 76), Color.WHITE);
        displayFallenHeroAttributes.addToScreen(this, true);

        //TEST: Add four heroes (will later be loaded from GameData)
        for (int i = 0; i < 5; i++) {
            final EndGamePlayerData selectedHero = victims.get(i);
            fallenHeroButtons.add(new Button(0, 0,
                    victims.get(i).getImagePath(),
                    victims.get(i).getPressedImagePath(),
                    DrawLayer.Entity,
                    () -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Hero");
                        displayFallenHeroAttributes.setText(
                                "Name: " + selectedHero.getName() +
                                        "\nGold: " + selectedHero.getGold() +
                                        "\nCreation Date: " + selectedHero.getCreationDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")) +
                                        "\nDeath Date: " + selectedHero.getDeathDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
                    }));
        }

        for (Button b: fallenHeroButtons) {
            b.addToScreen(this, true); //NOTE: isActive/isInactive cannot be called inside lambda expressions
            //Therefore, any teddy bear not displayed has to be rendered off screen
        }

        for (int i = 0; i < fallenHeroButtons.size(); i++) {
            if (i < MAX_NUMBER_OF_FALLEN_HEROES) {
                fallenHeroesOnScreenIndex[i] = i;
                fallenHeroButtons.get(i).setX(X_INIT_BUTTON + (i * 2 + 1) * (X_BUFFER + WIDTH_BUTTON - 30));
                fallenHeroButtons.get(i).setY(Y_INIT_BUTTON - 230);
            } else {
                fallenHeroButtons.get(i).setX(9999);
                fallenHeroButtons.get(i).setY(9999);
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
                    for (int i = 0; i < fallenHeroButtons.size(); i++) {
                        if (i < MAX_NUMBER_OF_FALLEN_HEROES) {
                            int newIndex = ((fallenHeroesOnScreenIndex[i]) - 1 < 0) ? fallenHeroButtons.size() - 1 : fallenHeroesOnScreenIndex[i] - 1;
                            fallenHeroesOnScreenIndex[i] = newIndex;
                        }
                        fallenHeroButtons.get(i).setX(9999);
                        fallenHeroButtons.get(i).setY(9999);
                    }

                    for (int i = 0; i < fallenHeroesOnScreenIndex.length; i++) {
                        fallenHeroButtons.get(fallenHeroesOnScreenIndex[i]).setX(X_INIT_BUTTON + (i * 2 + 1) * (X_BUFFER + WIDTH_BUTTON - 30));
                        fallenHeroButtons.get(fallenHeroesOnScreenIndex[i]).setY(Y_INIT_BUTTON - 230);
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
                    for (int i = 0; i < fallenHeroButtons.size(); i++) {
                        if (i < MAX_NUMBER_OF_FALLEN_HEROES) {
                            int newIndex = (fallenHeroesOnScreenIndex[i] + 1 > fallenHeroButtons.size() - 1) ? 0 : fallenHeroesOnScreenIndex[i] + 1;
                            fallenHeroesOnScreenIndex[i] = newIndex;
                        }
                        fallenHeroButtons.get(i).setX(9999);
                        fallenHeroButtons.get(i).setY(9999);
                    }

                    for (int i = 0; i < fallenHeroesOnScreenIndex.length; i++) {
                        fallenHeroButtons.get(fallenHeroesOnScreenIndex[i]).setX(X_INIT_BUTTON + (i * 2 + 1) * (X_BUFFER + WIDTH_BUTTON - 30));
                        fallenHeroButtons.get(fallenHeroesOnScreenIndex[i]).setY(Y_INIT_BUTTON - 230);
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

    private void loadPlayers() {
        //NOTE: THIS IS ONLY A TEMPORARY FUNCTION TO REPLICATE LOADING PLAYERS FROM GAMEDATA
        if (victims.isEmpty()) {
            for (int i = 0; i < 5; i++) {
                victims.add(new EndGamePlayerData());
                victims.get(i).changeGold(i * 5);
                switch(i) {
                    case 0:
                        victims.get(i).setImagePath("/assets/player/images/TeddyGreen.png");
                        victims.get(i).setPressedImagePath("/assets/player/images/TeddyGreen.png");
                        victims.get(i).setName("Loyal Doyal");
                        victims.get(i).setCreationDate(LocalDate.of(2019, Month.MARCH, 25));
                        victims.get(i).setDeathDate(LocalDate.of(2019, Month.APRIL, 1));
                        break;
                    case 1:
                        victims.get(i).setImagePath("/assets/player/images/TeddyPurple.png");
                        victims.get(i).setPressedImagePath("/assets/player/images/TeddyPurple.png");
                        victims.get(i).setName("Royal Coil");
                        victims.get(i).setCreationDate(LocalDate.of(2019, Month.MARCH, 26));
                        victims.get(i).setDeathDate(LocalDate.of(2019, Month.APRIL, 2));
                        break;
                    case 2:
                        victims.get(i).setImagePath("/assets/player/images/TeddyBlue.png");
                        victims.get(i).setPressedImagePath("/assets/player/images/TeddyBlue.png");
                        victims.get(i).setName("Ants Pants");
                        victims.get(i).setCreationDate(LocalDate.of(2019, Month.MARCH, 27));
                        victims.get(i).setDeathDate(LocalDate.of(2019, Month.APRIL, 3));
                        break;
                    case 3:
                        victims.get(i).setImagePath("/assets/player/images/TeddyRed.png");
                        victims.get(i).setPressedImagePath("/assets/player/images/TeddyRed.png");
                        victims.get(i).setName("Ben Ten");
                        victims.get(i).setCreationDate(LocalDate.of(2019, Month.MARCH, 28));
                        victims.get(i).setDeathDate(LocalDate.of(2019, Month.APRIL, 4));
                        break;
                    case 4:
                        victims.get(i).setImagePath("/assets/player/images/Teddy.png");
                        victims.get(i).setPressedImagePath("/assets/player/images/Teddy.png");
                        victims.get(i).setName("Jeremy");
                        victims.get(i).setCreationDate(LocalDate.of(2019, Month.MARCH, 29));
                        victims.get(i).setDeathDate(LocalDate.of(2019, Month.APRIL, 5));
                        break;
                }
            }

        } else {
            victims = gameData.getPreviousPlayerData();
            return;
        }
        gameData.save();
    }


    //endregion
}
