package gamescreen.mainmenu.options;

import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import input.listeners.Key.ClickableKeyHandler;
import main.utilities.Debug;
import main.utilities.DebugEnabler;


public class OptionScreen extends GameScreen {
    //region <Variables>
    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 920;
    private final int WIDTH_BUTTON = 256;
    private float alphaTransition = 0.0f;
    private final int X_BUFFER = 48;
    //endregion

    //region <Construction and Initialization>
    public OptionScreen(ScreenManager screenManager) {
        super(screenManager, "Options Menu", true);
    }
    @Override
    protected void initializeScreen() {

        //Create Background
        ImageContainer image;
        image = (new ImageContainer(0,0, "/assets/backgrounds/BG-OptionMenu.png", DrawLayer.Background));
        image.addToScreen(this,true);

        //Create button
        Button button;
        button = new Button(X_INIT_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-Graphics.png",
                "/assets/buttons/Button-GraphicsPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Graphics");
                    screenManager.addScreen(new GraphicsScreen(screenManager));
                });
        button.addToScreen(this,true);

        button = new Button(X_INIT_BUTTON+X_BUFFER+WIDTH_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-Sound.png",
                "/assets/buttons/Button-SoundPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Sound");
                    screenManager.addScreen(new SoundScreen(screenManager));
                });
        button.addToScreen(this,true);

        button = new Button(X_INIT_BUTTON+2*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Controls.png",
                "/assets/buttons/Button-ControlsPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Controls");
                    screenManager.addScreen(new ControlsScreen(screenManager));
                });
        button.addToScreen(this,true);

        button = new Button(X_INIT_BUTTON+3*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Back.png",
                "/assets/buttons/Button-BackPressed.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Back");
                    this.setScreenState(ScreenState.TransitionOff);
                });
        button.addToScreen(this,true);

        setKeyHandler(new ClickableKeyHandler(this.clickables));


    }

    //endregion

}
