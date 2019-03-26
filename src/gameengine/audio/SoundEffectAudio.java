package gameengine.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundEffectAudio {
    private AudioInputStream soundEffectAudioIn;
    private Clip soundEffectClip;
    private static boolean isMute;

    public SoundEffectAudio(URL url) {
        try{
            soundEffectAudioIn = AudioSystem.getAudioInputStream(url);
            soundEffectClip = AudioSystem.getClip();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        try {
            if (!isMute) {
                if(soundEffectClip != null && soundEffectAudioIn != null) {
                    soundEffectClip.open(soundEffectAudioIn);
                    soundEffectClip.start();
                    soundEffectClip.addLineListener(listener -> {
                        if (listener.getType() == LineEvent.Type.STOP)
                            soundEffectClip.close();
                    });
                }
            }
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeMuteState(boolean isMuted) {
        isMute = isMuted;
    }
}
