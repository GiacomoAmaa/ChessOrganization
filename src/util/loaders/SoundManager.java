package util.loaders;

import java.io.File;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import util.Sound;


/**
 * Loads sounds and handles tracks playing stopping and volume changes requests.
 */
public class SoundManager {

    private static final String SEPARATOR = System.getProperty("file.separator");
    /**
     * absolute path of the directory containing all game's sounds.
     */
    public static final String DEFAULT_DIR = System.getProperty("user.dir") + SEPARATOR
            + "resources" + SEPARATOR + "sounds" + SEPARATOR;
    /**
     * associates the sounds to their specific track.
     */
    private final Map<Sound, Clip> clips;

    public SoundManager() {
        super();
        this.clips = new HashMap<>();
    }

    /**
     * Plays the specified sound one time.
     * @param sound to play
     */
    public void play(final Sound sound) {
        this.removePlayed();
        setTrack(sound);
        clips.get(sound).start();
    }

    private void setTrack(final Sound sound) {
        if (!clips.keySet().contains(sound)) {
            try {
                this.clips.put(sound, AudioSystem.getClip());
                this.clips.get(sound).open(AudioSystem.getAudioInputStream(
                    new File(DEFAULT_DIR + sound.toString())));
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    private void removePlayed() {
        this.clips.entrySet().stream()
            .filter(entry -> !entry.getValue().isRunning())
            .forEach(entry -> entry.getValue().close());
        this.clips.entrySet().removeIf(entry -> !entry.getValue().isRunning());
    }
}
