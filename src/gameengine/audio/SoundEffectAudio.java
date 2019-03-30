package gameengine.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundEffectAudio {
    /**
     * The soundEffectAudioIn variable is an object that represents the sound file
     * that will be used for the sound effect
     * */
    private AudioInputStream soundEffectAudioIn;

    /**
     * The soundEffectClip variable is an object that can load and stream audio data from an
     * AudioInputStream object
     */
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

    /**
     * <p>Plays an audio file as the sound effect</p>
     */
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

    /**
     * Changes the state of the mute option and either plays or stops the sound effect audio depending on its state
     * @param isMuted The state to the mute option is to be changed to (true = mute, false = unmute)
     */
    public static void changeMuteState(boolean isMuted) {
        isMute = isMuted;
    }
}
