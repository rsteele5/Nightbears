package gameengine.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class BackgroundAudio {
    private static AudioInputStream backgroundAudioIn;
    private static Clip backGroundClip;
    private static URL url;
    private static boolean isMute = false;
    private static FloatControl gainControl;

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

    public static void changeSoundState() {
        if (backGroundClip != null) {
            if (backGroundClip.isRunning()) {
                backGroundClip.stop();
                backGroundClip.close();
            }
            else {
                backGroundClip.start();
                backGroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

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

    public static void changeVolume(float gValue) {
        if (backGroundClip != null) {
            gainControl = (FloatControl) backGroundClip.getControl(FloatControl.Type.MASTER_GAIN);
            if (gValue > 0) {
                gValue = gainControl.getMaximum();
            }
            gainControl.setValue(gValue);
        }
    }

}
