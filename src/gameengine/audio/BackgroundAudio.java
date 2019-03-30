package gameengine.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class BackgroundAudio {
    /**
     * The backgroundAudioIn variable is an object that represents the sound file
     * that will be used for the background music
     * */
    private static AudioInputStream backgroundAudioIn;

    /**
     * The backgroundClip variable is an object that can load and stream audio data from an
     * AudioInputStream object
     */
    private static Clip backGroundClip;

    private static URL url;
    private static boolean isMute = false;
    private static FloatControl gainControl;

    /**
     * <p>Plays an audio file as the background music</p>
     * @param myURL The filepath of the sound file to use as the background music
     */
    public static void play(URL myURL) {
        url = myURL;
        try {
            //Check if background is playing already and close if it is
            if (backGroundClip != null && backGroundClip.isOpen()) {
                backGroundClip.close();
            }

            //Start clip and loop infinitely
            backgroundAudioIn = AudioSystem.getAudioInputStream(myURL);
            backGroundClip = AudioSystem.getClip();
            if(backGroundClip != null && !isMute) {
                backGroundClip.open(backgroundAudioIn);
                backGroundClip.start();
                backGroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

//    public static void changeSoundState() {
//        if (backGroundClip != null) {
//            if (backGroundClip.isRunning()) {
//                backGroundClip.stop();
//                backGroundClip.close();
//            }
//            else {
//                backGroundClip.start();
//                backGroundClip.loop(Clip.LOOP_CONTINUOUSLY);
//            }
//        }
//    }

    /**
     * Changes the state of the mute option and either plays or stops the background audio depending on its state
     * @param isMuted The state to the mute option is to be changed to (true = mute, false = unmute)
     */
    public static void changeMuteState(boolean isMuted) {
        isMute = isMuted;
        if (isMute) {
            if (backGroundClip != null) {
                backGroundClip.stop();
                backGroundClip.close();
            }
        } else {
            if (url != null && !backGroundClip.isRunning()) {
                play(url);
            }
        }
    }

    /**
     * Changes the volume of the background sound
     * @param gValue The volume decibel that the background sound effect will be set to
     */
    public static void changeVolume(float gValue) {
        if (backGroundClip != null) {
            gainControl = (FloatControl) backGroundClip.getControl(FloatControl.Type.MASTER_GAIN);
            if (gValue > gainControl.getMaximum()) {
                gValue = gainControl.getMaximum();
            }
            gainControl.setValue(gValue);
        }
    }

}
