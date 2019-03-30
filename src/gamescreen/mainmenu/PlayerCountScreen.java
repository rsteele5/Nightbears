package gamescreen.mainmenu;

import gamescreen.splashscreen.IntroCutScene;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import gamescreen.popup.ConfirmationPopup;
import gamescreen.splashscreen.RandomPlayerScreen;
import input.listeners.Key.ClickableKeyHandler;
import main.utilities.Debug;
import main.utilities.DebugEnabler;


public class PlayerCountScreen extends GameScreen {
    //region <Variables>
    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 920;
    private final int X_BUFFER = 48;
    private final int WIDTH_BUTTON = 256;
    private final int TEDDY_HEIGHT = 200;
    private final int HALF_TEDDY_WIDTH = 50;
    private final int QRTR_BUTTON_WIDTH = 65;

    //endregion

    //region <Construction and Initialization>
    public PlayerCountScreen(ScreenManager screenManager) {
        super(screenManager, "PlayerCountScreen", true);
    }

    @Override
    protected void initializeScreen() {
        ImageContainer background = (new ImageContainer(0,0, "/assets/backgrounds/BG-PlayersMenu.png", DrawLayer.Background));
        background.addToScreen(this, true);

        ImageContainer image;
        image = (new ImageContainer(X_INIT_BUTTON + 2*QRTR_BUTTON_WIDTH - HALF_TEDDY_WIDTH, Y_INIT_BUTTON- TEDDY_HEIGHT, "/assets/player/sidescrolling/Teddy.png", DrawLayer.Entity));
        image.addToScreen(this,true);

        image = new ImageContainer(X_INIT_BUTTON + QRTR_BUTTON_WIDTH - HALF_TEDDY_WIDTH +2*(X_BUFFER+WIDTH_BUTTON), Y_INIT_BUTTON- TEDDY_HEIGHT, "/assets/player/sidescrolling/Teddy.png", DrawLayer.Entity);
        image.addToScreen(this,true);

        image = new ImageContainer(X_INIT_BUTTON + 3*QRTR_BUTTON_WIDTH - HALF_TEDDY_WIDTH +2*(X_BUFFER+WIDTH_BUTTON), Y_INIT_BUTTON- TEDDY_HEIGHT, "/assets/player/sidescrolling/Teddy2.png", DrawLayer.Entity);
        image.addToScreen(this,true);

        Button button;
        //Create Buttons
        button = new Button(X_INIT_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-Solo.png",
                "/assets/buttons/Button-SoloPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Solo");
                    screenManager.addScreen(new ConfirmationPopup(screenManager,
                            "You selected... \nSOLO\nIs this correct?",
                            ()-> screenManager.addScreen(new RandomPlayerScreen(screenManager))));
                });
        button.addToScreen(this, true);

        button = new Button(X_INIT_BUTTON+2*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Coop.png",
                "/assets/buttons/Button-CoopPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Coop");
                    screenManager.addScreen(new ConfirmationPopup(screenManager,
                            "You selected... \nCO-OP\nIs this correct?",
                            ()-> coverWith(new TempCoopScreen(screenManager, this))));
                });
        button.addToScreen(this, true);

        button = new Button(X_INIT_BUTTON+3*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Back.png",
                "/assets/buttons/Button-BackPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Back");
                    this.setScreenState(ScreenState.TransitionOff);
                });
        button.addToScreen(this, true);

        setKeyHandler(new ClickableKeyHandler(this.clickables));
    }
    //endregion
}
