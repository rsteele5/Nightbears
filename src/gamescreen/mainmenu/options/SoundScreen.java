package gamescreen.mainmenu.options;


import gameengine.audio.BackgroundAudio;
import gameengine.audio.SoundEffectAudio;
import gameengine.gamedata.SoundSetting;
import gameobject.renderable.text.TextBox;
import gameobject.renderable.DrawLayer;
import gamescreen.GameScreen;
import gamescreen.ScreenManager;
import gameobject.renderable.ImageContainer;
import gameobject.renderable.button.Button;
import gamescreen.popup.ConfirmationPopup;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;


public class SoundScreen extends GameScreen {

    private SoundSetting[] localSettings;
    private SoundSetting.SoundOption[] options = SoundSetting.SoundOption.values();
    private SoundSetting.SoundVolume[] volumes = SoundSetting.SoundVolume.values();
    private boolean didOptionsChange;

    //region <Variables>
    private final int X_INIT_BUTTON = 996;
    private final int Y_INIT_BUTTON = 400;
    private final int WIDTH_BUTTON = 256;
    private final int X_BUFFER = 48;
    private final int Y_BUFFER = 110;
    //endregion

    //region <Construction and Initialization>
    public SoundScreen(ScreenManager screenManager) {
        super(screenManager, "ControlsScreen", true);
    }

    @Override
    protected void initializeScreen() {

        //Create Background
        didOptionsChange = false;

        localSettings = new SoundSetting[3];
        for (int i = 0; i < localSettings.length; i++) {
            if (i < 2) {
                localSettings[i] = gameData.getSoundSetting(i);
            } else {
                localSettings[i] = gameData.getSoundSetting(i);
            }
        }
        ImageContainer imageContainer;

        imageContainer = new ImageContainer(0, 0, "/assets/backgrounds/BG-SoundMenu.png", DrawLayer.Background);
        imageContainer.addToScreen(this, true);


        //Create button
        Button butt;

        for (int i = 0; i < 3; i++) {
            TextBox controlsText;
            if (i < 2) {
                controlsText = new TextBox(X_INIT_BUTTON+X_BUFFER, Y_INIT_BUTTON + (i * 226) / 2,
                        300,
                        60,
                        localSettings[i].getCurrentOption().name(),
                        new Font("NoScary", Font.PLAIN, 60),
                        Color.WHITE);
            } else {
                controlsText = new TextBox(X_INIT_BUTTON+X_BUFFER, Y_INIT_BUTTON + (i * 226) / 2,
                        300,
                        60,
                        localSettings[i].getCurrentVolume().name(),
                        new Font("NoScary", Font.PLAIN, 60),
                        Color.WHITE);
            }

            controlsText.addToScreen(this, true);
            final int index = i;

            butt = new Button(X_INIT_BUTTON, Y_INIT_BUTTON + (i * 226) / 2,
                    "/assets/buttons/Button-LeftArrow.png",
                    "/assets/buttons/Button-LeftArrowPressed.png",
                    DrawLayer.Entity,
                    () -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Left Arrow");
                        if (index < 2) {
                            int nextOptionOrdinal = (localSettings[index].getCurrentOption().ordinal() - 1) % options.length;
                            if(nextOptionOrdinal < 0) nextOptionOrdinal = 1;
                            localSettings[index].setCurrentOption(options[nextOptionOrdinal]);
                            controlsText.setText(localSettings[index].getCurrentOption().name());
                        } else {
                            int nextVolumeOrdinal = (localSettings[index].getCurrentVolume().ordinal() - 1) % volumes.length;
                            if(nextVolumeOrdinal < 0) nextVolumeOrdinal = 2;
                            localSettings[index].setCurrentSoundVolume(volumes[nextVolumeOrdinal]);
                            controlsText.setText(localSettings[index].getCurrentVolume().name());
                        }
                    });
            butt.addToScreen(this, true);

            butt = new Button(X_INIT_BUTTON + X_BUFFER + WIDTH_BUTTON, Y_INIT_BUTTON + (i * 226) / 2,
                    "/assets/buttons/Button-RightArrow.png",
                    "/assets/buttons/Button-RightArrowPressed.png",
                    DrawLayer.Entity,
                    () -> {
                        Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Right Arrow");
                        if (index < 2) {
                            int nextOptionOrdinal = (localSettings[index].getCurrentOption().ordinal() + 1) % options.length;
                            if(nextOptionOrdinal > 1) nextOptionOrdinal = 0;
                            localSettings[index].setCurrentOption(options[nextOptionOrdinal]);
                            controlsText.setText(localSettings[index].getCurrentOption().name());
                        } else {
                            int nextVolumeOrdinal = (localSettings[index].getCurrentVolume().ordinal() + 1) % volumes.length;
                            if(nextVolumeOrdinal > 2) nextVolumeOrdinal = 0;
                            localSettings[index].setCurrentSoundVolume(volumes[nextVolumeOrdinal]);
                            controlsText.setText(localSettings[index].getCurrentVolume().name());
                        }
                    });
            butt.addToScreen(this, true);
        }

        butt = new Button((X_INIT_BUTTON - 600)+ 2 * (X_BUFFER + WIDTH_BUTTON), Y_INIT_BUTTON * 2 + Y_BUFFER,
                "/assets/buttons/Button-Confirm.png",
                "/assets/buttons/Button-ConfrimPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Confirm");
                    this.setScreenState(ScreenState.TransitionOff);
                    for (int i = 0; i < localSettings.length; i++) {
                        gameData.save();
                        switch(i) {
                            case 0:
                                if (localSettings[0].getCurrentOption().equals(SoundSetting.SoundOption.On)) {
                                    BackgroundAudio.changeMuteState(false);
                                } else
                                    BackgroundAudio.changeMuteState(true);
                                break;
                            case 1:
                                if (localSettings[1].getCurrentOption().equals(SoundSetting.SoundOption.On)) {
                                    SoundEffectAudio.changeMuteState(false);
                                } else {
                                    SoundEffectAudio.changeMuteState(true);
                                }
                                break;
                            case 2:
                                switch(localSettings[2].getCurrentVolume()) {
                                    case Low: BackgroundAudio.changeVolume(-10.0f); break;
                                    case Medium: BackgroundAudio.changeVolume(-0.0f); break;
                                    case High: BackgroundAudio.changeVolume(5.0f); break;
                                }
                                break;
                            default: Debug.error(DebugEnabler.GAME_DATA, "Unsupported Sound Option Selected");
                        }
                    }
                });
        butt.addToScreen(this, true);

        butt = new Button((X_INIT_BUTTON - 600) + 3 * (X_BUFFER + WIDTH_BUTTON),
                Y_INIT_BUTTON * 2 + Y_BUFFER,
                "/assets/buttons/Button-Back.png",
                "/assets/buttons/Button-BackPressed.png",
                DrawLayer.Entity,
                () -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Back");
                    for (int i = 0; i < localSettings.length; i++) {
                        if (i < 2) {
                            if (!localSettings[i].getCurrentOption().equals(gameData.getSoundSetting(i).getCurrentOption())) {
                                didOptionsChange = true;
                            }
                        } else {
                            if (!localSettings[i].getCurrentVolume().equals(gameData.getSoundSetting(i).getCurrentVolume())) {
                                didOptionsChange = true;
                            }
                        }

                    }
                    if (didOptionsChange) {
                        screenManager.addScreen(new ConfirmationPopup(screenManager,
                                "Return Without Saving?",
                                ()-> this.setScreenState(ScreenState.TransitionOff)));
                    } else {
                        setScreenState(ScreenState.TransitionOff);
                    }
                });
        butt.addToScreen(this, true);
    }
    //endregion
}
