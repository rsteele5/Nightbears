package gameengine.gamedata;

import java.io.Serializable;

public class InputSetting implements Serializable {

    private InputMethod currentInputSetting;

    public enum InputMethod implements Serializable{
        KeyBoard,
        GamePad
    }

    public InputSetting(InputMethod setting) {
        this.currentInputSetting = setting;
    }

    public InputMethod getCurrentOption(){
        return currentInputSetting;
    }

    public void setCurrentOption(InputMethod setting){
        this.currentInputSetting = setting;
    }
}
