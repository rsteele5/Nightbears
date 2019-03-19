package gameengine.gamedata;

import java.io.Serializable;

public class SoundSetting implements Serializable {

    private SoundOption currentSoundSetting;
    private SoundVolume currentSoundVolume;

    public enum SoundOption {
        On,
        Off
    }

    public enum SoundVolume {
        Low,
        Medium,
        High
    }

    public SoundSetting(SoundOption setting) {
        this.currentSoundSetting = setting;
    }
    public SoundSetting(SoundVolume volume) {
        this.currentSoundVolume = volume;
    }

    public SoundOption getCurrentOption(){
        return currentSoundSetting;
    }
    public SoundVolume getCurrentVolume() {
        return currentSoundVolume;
    }

    public void setCurrentOption(SoundOption setting){
        this.currentSoundSetting = setting;
    }
    public void setCurrentSoundVolume(SoundVolume volume) {
        this.currentSoundVolume = volume;
    }

}
