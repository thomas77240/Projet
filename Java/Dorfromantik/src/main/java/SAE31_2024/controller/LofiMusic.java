package SAE31_2024.controller;

import SAE31_2024.LaFs.HexSliderUI;
import SAE31_2024.toolbox.ToolBox;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The LofiMusic class is responsible for playing Lofi music tracks.
 * It can play either a specific file or randomly choose a track from a directory of audio files.
 */
public class LofiMusic extends JSlider implements ChangeListener, LineListener {

    private FloatControl VolumeControl;
    private String CurrentMusic;
    private final JVolumePanel Parent;

    /**
     * Constructs a LofiMusic instance with the specified parent panel.
     *
     * @param Parent the parent JVolumePanel
     */
    public LofiMusic(JVolumePanel Parent) {
        // Initialisation dU slider
        super(-80, 0, -40);
        this.addChangeListener(this);
        setUI(new HexSliderUI(this));
        setOpaque(false);
        setForeground(new Color(0, 0, 0, 0));
        // Initialisation du parent
        this.Parent = Parent;

        // Recherche des musique du dossiers : "resources/audio/Tracks"
        File dir = new File("res/audio/Tracks");
        if (dir.exists()) try {
            this.PlayMusic(dir.listFiles());
        } catch (NullPointerException e) {
            return;
        }
    }

    /**
     * Plays the specified music file.
     *
     * @param file the music file to play
     * @throws UnsupportedAudioFileException if the audio file format is not supported
     * @throws IOException if an I/O error occurs
     * @throws LineUnavailableException if a line cannot be opened because it is unavailable
     */
    private void actualy_playing_the_Musique(File file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // Initialise la piste musicale
        AudioInputStream musicInputStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.addLineListener(this);
        clip.open(musicInputStream);

        // Start la musique
        clip.start();

        // Initialise le control de volume
        this.VolumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        this.VolumeControl.setValue((float) this.getValue());
    }

    /**
     * Plays a random music file from the provided list of music files.
     *
     * @param musicFiles an array of File objects representing the music files to be played.
     *                   Must not be null.
     * @throws NullPointerException if the provided musicFiles array is null.
     */
    public void PlayMusic(File[] musicFiles) throws NullPointerException {
        // Si le dossier est null on jète un NullPointerException
        if (musicFiles == null) {
            throw new NullPointerException("Le dossier des musique ne doit pas etre vide");
        }
        try {
            // Choisis un indice de musique aléatoire
            int RandMusic = (int) (Math.random() * (musicFiles.length));
            if (RandMusic >= musicFiles.length) {
                RandMusic--;    // Vérifie qu'il n'est pas en dehors du tableau de fichier
            }

            // Garde en mémoire le nom, et jouée la musique
            this.CurrentMusic = musicFiles[RandMusic].getName();
            this.actualy_playing_the_Musique(musicFiles[RandMusic]);

        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported Audio File : " + (this.CurrentMusic.split("[.]")[this.CurrentMusic.split("[.]").length - 1]).toUpperCase());
            System.err.println("Supported Audio File : ");
            for (AudioFileFormat.Type type : AudioSystem.getAudioFileTypes()) {
                System.err.println(type.toString());
            }
        } catch (LineUnavailableException e) {
            System.err.println("Unavailable line : " + this.CurrentMusic);
        } catch (IOException e) {
            System.err.println("OI Exception : " + this.CurrentMusic);
        }
    }

    /**
     * Getter for the CurrentMusic field.
     *
     * @return the currently playing music file path as a String
     */
    public String getCurrentMusic() {
        if (this.CurrentMusic == null) {
            return "No Music Found";
        } else {
            return this.CurrentMusic.substring(0, this.CurrentMusic.length() - 4);
        }
    }

    /**
     * Adjusts the volume based on the current value of the slider when its state changes.
     * It calculates the volume based on the slider position.
     *
     * @param e the ChangeEvent object representing the state change of the slider
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (this.VolumeControl != null) {
            this.VolumeControl.setValue((float) this.getValue());
        }
    }

    /**
     * Handles the update event for the LineEvent.
     * If the event type is STOP, it triggers the playback of a new music track from the Tracks directory.
     *
     * @param event the LineEvent that triggers this update method
     */
    @Override
    public void update(LineEvent event) {
        // Si l'évennement est 'STOP'
        if (event.getType() == LineEvent.Type.STOP) {
            // On sélectionne le docier des musique
            File dir = new File(String.valueOf(ToolBox.loadFile("res/audio/Tracks")));
            if (dir.exists()) try {
                // Que l'on va passer pour tirage aléatoire d'une musique
                this.PlayMusic(dir.listFiles());
                this.Parent.ChangeMusicTitle(this.CurrentMusic);
            } catch (NullPointerException e) {
                return;
            }
        }
    }
}