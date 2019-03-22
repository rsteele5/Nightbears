package gameengine.gamedata;

import gameengine.audio.BackgroundAudio;
import gameengine.audio.SoundEffectAudio;
import gameobject.renderable.RenderableObject;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import static gameengine.gamedata.GraphicsSetting.GraphicsOption.*;
import static gameengine.gamedata.InputSetting.InputMethod.*;
import static gameengine.gamedata.SoundSetting.SoundOption.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class GameData implements Serializable {

    private final String FILE_NAME = "GameData.dat";
    private GraphicsSetting currentGraphicsSetting;
    private InputSetting currentInputSetting;
    private SoundSetting[] currentSoundSetting = new SoundSetting[3];
    private PlayerData currentPlayerData;
    private VendorData currentVendorData;

    public GameData(){
        try {
            Debug.success(DebugEnabler.GAME_DATA,"Loading GameData from file");

            File dataFile = new File(FILE_NAME);

            if(!dataFile.exists()) {
                currentGraphicsSetting = new GraphicsSetting(High);
                currentInputSetting = new InputSetting(KeyBoard);
                for (int i = 0; i < currentSoundSetting.length; i++) {
                    if (i < 2) {
                        currentSoundSetting[i] = new SoundSetting(On);
                    } else {
                        currentSoundSetting[i] = new SoundSetting(SoundSetting.SoundVolume.Medium);
                    }
                }
                currentPlayerData = new PlayerData();
                currentVendorData = new VendorData();
                save();
            } else {
                FileInputStream file = new FileInputStream(dataFile);
                ObjectInputStream in = new ObjectInputStream(file);

                GameData gameDataInput = (GameData)in.readObject();
                this.currentGraphicsSetting = gameDataInput.getGraphicsSettings();
                this.currentInputSetting = gameDataInput.getInputSetting();
                for (int i = 0; i < currentSoundSetting.length; i++) {
                    if (i < 2) {
                        currentSoundSetting[i] = gameDataInput.getSoundSetting(i);
                    } else {
                        currentSoundSetting[i] = new SoundSetting(SoundSetting.SoundVolume.Medium);
                    }
                }
                this.currentPlayerData = gameDataInput.getPlayerData();
                this.currentVendorData = gameDataInput.getVendorData();
                Debug.log(true, "Do I have shit?: " + currentPlayerData.getInventory().get(0).getImagePath());

                in.close();
                file.close();
            }
            initializeSound();

            Debug.success(DebugEnabler.GAME_DATA,"Loaded GameData successfully");

            Debug.log(DebugEnabler.GAME_DATA, currentGraphicsSetting.getCurrentOption().name());
            Debug.log(DebugEnabler.GAME_DATA, currentInputSetting.getCurrentOption().name());
            Debug.log(DebugEnabler.GAME_DATA, currentSoundSetting[0].getCurrentOption().name());

        } catch (IOException ex) { Debug.error(DebugEnabler.GAME_DATA, "Loading Failed - IOException is caught " + ex.getMessage());
        } catch (ClassNotFoundException ex) { Debug.error(DebugEnabler.GAME_DATA,"Loading Failed - ClassNotFoundException is caught" + ex.getMessage()); }
    }

    public VendorData getVendorData() {
        return currentVendorData;
    }

    public PlayerData getPlayerData() { return currentPlayerData; }

    public GraphicsSetting getGraphicsSettings() {
        return currentGraphicsSetting;
    }

    public InputSetting getInputSetting() {
        return currentInputSetting;
    }

    public SoundSetting getSoundSetting(int index) {
        return currentSoundSetting[index];
    }

    public void save() {
        try {
            File dataFile = new File(FILE_NAME);
            FileOutputStream file = new FileOutputStream(dataFile);
            ObjectOutputStream output = new ObjectOutputStream(file);

            output.writeObject(this);

            output.close();
            file.close();

            Debug.success(DebugEnabler.GAME_DATA,"Saved GameData to file");

        } catch(IOException ex) { System.out.println("IOException is caught"); }
    }

    private void initializeSound() {
        for (int i = 0; i < currentSoundSetting.length; i++) {
                switch(i) {
                    case 0:
                        if (currentSoundSetting[i].getCurrentOption().equals(On)) {
                            BackgroundAudio.changeMuteState(false);
                        } else {
                            BackgroundAudio.changeMuteState(true);
                            break;
                        }
                    case 1:
                        if (currentSoundSetting[i].getCurrentOption().equals(On)) {
                            SoundEffectAudio.changeMuteState(false);
                        } else {
                            SoundEffectAudio.changeMuteState(true);
                            break;
                        }
                        break;
                    case 2:
                        switch(currentSoundSetting[i].getCurrentVolume()) {
                            case Low: BackgroundAudio.changeVolume(-10.0f); break;
                            case Medium: BackgroundAudio.changeVolume(0.0f); break;
                            case High: BackgroundAudio.changeVolume(1.0f); break;
                        }
                        break;
                    default:
                }
            }
        }
    }














