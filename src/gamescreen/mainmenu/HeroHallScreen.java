package gamescreen.mainmenu;

import gameengine.gamedata.EndGamePlayerData;
import gameengine.gamedata.PlayerData;
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


public class HeroHallScreen extends GameScreen {

    //region <Variables>
    private CopyOnWriteArrayList<Button> heroButtons = new CopyOnWriteArrayList<>();
    private final int MAX_NUMBER_OF_HEROES = 3;
    private int[] heroesOnScreenIndex = new int[MAX_NUMBER_OF_HEROES];
    private TextBox displayHeroAttributes;
    private CopyOnWriteArrayList<EndGamePlayerData> heroes = new CopyOnWriteArrayList<>();
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

        //load PlayerData
        loadPlayers();

        displayHeroAttributes = new TextBox(X_INIT_BUTTON, Y_INIT_BUTTON - 700, 800, 800, "",
                new Font("NoScary", Font.PLAIN, 76), Color.WHITE);
        displayHeroAttributes.addToScreen(this, true);

        //TEST: Add four heroes (will later be loaded from GameData)
        for (int i = 0; i < 5; i++) {
            final EndGamePlayerData selectedHero = heroes.get(i);
            heroButtons.add(new Button(0, 0,
                    heroes.get(i).getImagePath(),
                    heroes.get(i).getPressedImagePath(),
                    DrawLayer.Entity,
                    () -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Hero");
                        displayHeroAttributes.setText(
                                "Name: " + selectedHero.getName() +
                                "\nGold: " + selectedHero.getGold() +
                                "\nCreation Date: " + selectedHero.getCreationDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")) +
                                "\nVictory Date: " + selectedHero.getVictoryDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
                    }));
        }

        for (Button b: heroButtons) {
            b.addToScreen(this, true); //NOTE: isActive/isInactive cannot be called inside lambda expressions
                                                      //Therefore, any teddy bear not displayed has to be rendered off screen
        }

        for (int i = 0; i < heroButtons.size(); i++) {
            if (i < MAX_NUMBER_OF_HEROES) {
                heroesOnScreenIndex[i] = i;
                heroButtons.get(i).setX(X_INIT_BUTTON + (i * 2 + 1) * (X_BUFFER + WIDTH_BUTTON - 30));
                heroButtons.get(i).setY(Y_INIT_BUTTON - 230);
            } else {
                heroButtons.get(i).setX(9999);
                heroButtons.get(i).setY(9999);
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
                    for (int i = 0; i < heroButtons.size(); i++) {
                        if (i < MAX_NUMBER_OF_HEROES) {
                            int newIndex = ((heroesOnScreenIndex[i]) - 1 < 0) ? heroButtons.size() - 1 : heroesOnScreenIndex[i] - 1;
                            heroesOnScreenIndex[i] = newIndex;
                        }
                        heroButtons.get(i).setX(9999);
                        heroButtons.get(i).setY(9999);
                    }

                    for (int i = 0; i < heroesOnScreenIndex.length; i++) {
                        heroButtons.get(heroesOnScreenIndex[i]).setX(X_INIT_BUTTON + (i * 2 + 1) * (X_BUFFER + WIDTH_BUTTON - 30));
                        heroButtons.get(heroesOnScreenIndex[i]).setY(Y_INIT_BUTTON - 230);
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
                    for (int i = 0; i < heroButtons.size(); i++) {
                        if (i < MAX_NUMBER_OF_HEROES) {
                            int newIndex = (heroesOnScreenIndex[i] + 1 > heroButtons.size() - 1) ? 0 : heroesOnScreenIndex[i] + 1;
                            heroesOnScreenIndex[i] = newIndex;
                        }
                        heroButtons.get(i).setX(9999);
                        heroButtons.get(i).setY(9999);
                    }

                    for (int i = 0; i < heroesOnScreenIndex.length; i++) {
                        heroButtons.get(heroesOnScreenIndex[i]).setX(X_INIT_BUTTON + (i * 2 + 1) * (X_BUFFER + WIDTH_BUTTON - 30));
                        heroButtons.get(heroesOnScreenIndex[i]).setY(Y_INIT_BUTTON - 230);
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
        if (heroes.isEmpty()) {
            for (int i = 0; i < 5; i++) {
                heroes.add(new EndGamePlayerData());
                heroes.get(i).changeGold(i * 5);
                switch(i) {
                    case 0:
                        heroes.get(i).setImagePath("/assets/player/color/none/Teddy.png");
                        heroes.get(i).setPressedImagePath("/assets/player/color/none/Teddy.png");
                        heroes.get(i).setName("Captain Snuggles");
                        heroes.get(i).setCreationDate(LocalDate.of(2019, Month.MARCH, 25));
                        heroes.get(i).setVictoryDate(LocalDate.of(2019, Month.APRIL, 1));
                        break;
                    case 1:
                        heroes.get(i).setImagePath("/assets/player/color/red/Teddy.png");
                        heroes.get(i).setPressedImagePath("/assets/player/color/red/Teddy.png");
                        heroes.get(i).setName("Mr. Fuzzy");
                        heroes.get(i).setCreationDate(LocalDate.of(2019, Month.MARCH, 26));
                        heroes.get(i).setVictoryDate(LocalDate.of(2019, Month.APRIL, 2));
                        break;
                    case 2:
                        heroes.get(i).setImagePath("/assets/player/color/orange/Teddy.png");
                        heroes.get(i).setPressedImagePath("/assets/player/color/orange/Teddy.png");
                        heroes.get(i).setName("Ms. Fudge");
                        heroes.get(i).setCreationDate(LocalDate.of(2019, Month.MARCH, 27));
                        heroes.get(i).setVictoryDate(LocalDate.of(2019, Month.APRIL, 3));
                        break;
                    case 3:
                        heroes.get(i).setImagePath("/assets/player/color/yellow/Teddy.png");
                        heroes.get(i).setPressedImagePath("/assets/player/color/yellow/Teddy.png");
                        heroes.get(i).setName("Sir Wally");
                        heroes.get(i).setCreationDate(LocalDate.of(2019, Month.MARCH, 28));
                        heroes.get(i).setVictoryDate(LocalDate.of(2019, Month.APRIL, 4));
                        break;
                    case 4:
                        heroes.get(i).setImagePath("/assets/player/color/blue/Teddy.png");
                        heroes.get(i).setPressedImagePath("/assets/player/color/blue/Teddy.png");
                        heroes.get(i).setName("Dr. Honey");
                        heroes.get(i).setCreationDate(LocalDate.of(2019, Month.MARCH, 29));
                        heroes.get(i).setVictoryDate(LocalDate.of(2019, Month.APRIL, 5));
                        break;
                }
            }

        } else {
            heroes = gameData.getPreviousPlayerData();
            return;
        }
        gameData.save();
    }
}
    //endregion
