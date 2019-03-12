package gameengine;

import java.awt.*;
import static gameengine.GameSettings.InputMethod.*;
import static gameengine.GameSettings.GraphicsOption.*;
import static gameengine.GameSettings.PlayerOption.*;
import static gameengine.GameSettings.SoundOption.*;

public class GameSettings {

    private static GameEngine gameEngine;

    private static InputMethod inputMethod;
    private static GraphicsOption graphicsOption;
    private static PlayerOption playerOptions;
    private static SoundOption[] soundOption = new SoundOption[3];

    public GameSettings(GameEngine gameEngine) {
        GameSettings.gameEngine = gameEngine;
        inputMethod = KeyBoard;
        graphicsOption = High;
        playerOptions = Solo;
        for (int i =0 ; i < 3; i++) {
            soundOption[i] = On;
        }
    }

    public enum InputMethod {
        KeyBoard,
        GamePad
    }

    public enum GraphicsOption {
        High,
        Medium,
        Low
    }

    public enum PlayerOption {
        Solo,
        Coop
    }

    public enum SoundOption {
        On,
        Off
    }

    public InputMethod getInputMethod() {
        return inputMethod;
    }

    public void setIputMethod(InputMethod inputMethod) {
        this.inputMethod = inputMethod;
        gameEngine.changeInputMethod(inputMethod);
    }

    public GraphicsOption getGraphicsOption() {
        return graphicsOption;
    }

    public void setGraphicsOption(GraphicsOption graphicsOption) {
        this.graphicsOption = graphicsOption;
        gameEngine.changeGraphicsOption(graphicsOption);
    }

    public SoundOption getSoundOption(int index) {
        return soundOption[index];
    }

    public void setSoundOption(SoundOption[] soundArray) {
        soundOption = soundArray.clone();
        gameEngine.changeSoundOption(soundOption[0]); //TODO: CHANGE THIS
    }

    public Graphics getGraphics() {
        return gameEngine.getGraphics();
    }
}
