package gameengine.gamedata;

import java.io.Serializable;

public class SoundSetting implements Serializable {

    private SoundOption currentSoundSetting;

    public enum SoundOption {
        On,
        Off
    }

    public SoundSetting(SoundOption setting) {
        this.currentSoundSetting = setting;
    }

    public SoundOption getCurrentOption(){
        return currentSoundSetting;
    }

    public void setCurrentOption(SoundOption setting){
        this.currentSoundSetting = setting;
    }

}
