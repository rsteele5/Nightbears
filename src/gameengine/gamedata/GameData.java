package gameengine.gamedata;

import main.utilities.Debug;
import main.utilities.DebugEnabler;

import static gameengine.gamedata.GraphicsSetting.GraphicsOption.*;
import static gameengine.gamedata.InputSetting.InputMethod.*;
import static gameengine.gamedata.SoundSetting.SoundOption.*;

import java.io.*;


public class GameData implements Serializable {

    private final String FILE_NAME = "GameData.dat";
    private GraphicsSetting currentGraphicsSetting;
    private InputSetting currentInputSetting;
    private SoundSetting currentSoundSetting;

    public GameData(){
        try {
            Debug.success(DebugEnabler.GAME_DATA,"Loading GameData from file");

            File dataFile = new File(FILE_NAME);
            if(!dataFile.exists()) {
                currentGraphicsSetting = new GraphicsSetting(High);
                currentInputSetting = new InputSetting(KeyBoard);
                currentSoundSetting = new SoundSetting(On);
                save();
            } else {
                FileInputStream file = new FileInputStream(dataFile);
                ObjectInputStream in = new ObjectInputStream(file);

                GameData gameDataInput = (GameData)in.readObject();
                this.currentGraphicsSetting = gameDataInput.getGraphicsSettings();
                this.currentInputSetting = gameDataInput.getInputSetting();
                this.currentSoundSetting = gameDataInput.getSoundSetting();

                in.close();
                file.close();
            }
            Debug.success(DebugEnabler.GAME_DATA,"Loaded GameData successfully");

            Debug.log(DebugEnabler.GAME_DATA, currentGraphicsSetting.getCurrentOption().name());
            Debug.log(DebugEnabler.GAME_DATA, currentInputSetting.getCurrentOption().name());
            Debug.log(DebugEnabler.GAME_DATA, currentSoundSetting.getCurrentOption().name());

        } catch (IOException ex) { Debug.error(DebugEnabler.GAME_DATA, "Loading Failed - IOException is caught");
        } catch (ClassNotFoundException ex) { Debug.error(DebugEnabler.GAME_DATA,"Loading Failed - ClassNotFoundException is caught"); }
    }

    public GraphicsSetting getGraphicsSettings() {
        return currentGraphicsSetting;
    }

    public void setGraphicsSetting(GraphicsSetting setting) {
        this.currentGraphicsSetting = setting;
        save();
    }

    public InputSetting getInputSetting() {
        return currentInputSetting;
    }

    public void setInputSetting(InputSetting setting) {
        this.currentInputSetting = setting;
        save();
    }

    public SoundSetting getSoundSetting() {
        return currentSoundSetting;
    }

    public void setSoundSetting(SoundSetting setting) {
        this.currentSoundSetting = setting;
        save();
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













}
