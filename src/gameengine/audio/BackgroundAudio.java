package gameengine.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class BackgroundAudio {
    private static AudioInputStream backgroundAudioIn;
    private static Clip backGroundClip;

    public static void play(URL url) {
        try {
            //Check if background is playing already and close if it is
            if (backGroundClip != null && backGroundClip.isOpen()) {
                backGroundClip.close();
            }

            //Start clip and loop infinitely
            backgroundAudioIn = AudioSystem.getAudioInputStream(url);
            backGroundClip = AudioSystem.getClip();
            if(backGroundClip != null) {
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
            if (backGroundClip.isRunning())
                backGroundClip.stop();
            else {
                backGroundClip.start();
                backGroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }
}
