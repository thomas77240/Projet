package SAE31_2024.controller;

import SAE31_2024.toolbox.ToolBox;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * The JVolumePanel class represents a JPanel that contains a volume slider and displays the current song name.
 */
public class JVolumePanel extends JPanel {

    private final JLabel songName;

    /**
     * Creates a new JVolumePanel instance.
     *
     * This constructor initializes the JVolumePanel component with a border layout and an empty border.
     * It adds three elements to the panel:
     * - A JLabel containing an icon for high volume
     * - A LofiMusic slider for volume control
     * - A JLabel displaying the name of the currently playing music
     */
    public JVolumePanel() {
        super();
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, ToolBox.MARGIN_LEFT_TOP_MENU, 0, 0));

        // Initialize the volume slider
        LofiMusic songVolume = new LofiMusic(this);
        // Icon for the volume slider
        JLabel icoSlider = new JLabel(new ImageIcon(ToolBox.loadFile("res/high-volume.png")));
        // Label to display the current song name
        this.songName = new JLabel(songVolume.getCurrentMusic());
        this.songName.setFont(new Font(ToolBox.FONT_NAME, Font.BOLD, 16));
        this.songName.setForeground(ToolBox.FONT_COLOR1);

        // Add components to the panel
        this.add(icoSlider, BorderLayout.WEST);
        this.add(songVolume, BorderLayout.CENTER);
        this.add(this.songName, BorderLayout.EAST);
    }

    /**
     * Changes the title of the currently playing music.
     *
     * @param newTitle the new title to be set for the music
     */
    public void ChangeMusicTitle(String newTitle) {
        // Remove the existing label
        this.remove(this.songName);

        // Replace it with a new one
        this.songName.setText(newTitle);
        this.add(this.songName, BorderLayout.EAST);

        // Revalidate the display
        this.revalidate();
    }
}