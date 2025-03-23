package SAE31_2024.controller;

import SAE31_2024.toolbox.ToolBox;
import SAE31_2024.view.HexButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * The JTopMenu class represents a custom menu bar with a volume panel, logo, and back button.
 */
public class JTopMenu extends JMenuBar {

    /**
     * Paints the component with custom graphics.
     *
     * @param g the Graphics object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set background color with alpha
        g2d.setColor(ToolBox.OverlayBGAlphaColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Set border color and stroke
        g2d.setColor(ToolBox.OverlayBorderColor);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(0, -2, getWidth(), getHeight());
    }

    /**
     * Constructs a JTopMenu instance with the specified GameController.
     *
     * @param mainFrame the GameController to use
     */
    public JTopMenu(GameController mainFrame) {
        setLayout(new GridLayout(1, 2));

        // Create left and right menu panels
        JPanel menuLeft = createMenuPanel(FlowLayout.LEFT);
        JPanel menuRight = createMenuPanel(FlowLayout.RIGHT);

        // Initialize volume panel
        JVolumePanel lofiMusic = new JVolumePanel();
        lofiMusic.setOpaque(false);

        // Initialize logo
        JLabel logo = new JLabel(ToolBox.logoLofix96);
        logo.setOpaque(false);

        // Initialize back button
        HexButton backButton = new HexButton("Retour", 18);
        backButton.setPreferredSize(new Dimension(75,40));
        backButton.addActionListener(new ReturnToMainFrameActionHandler(mainFrame));

        // Add components to left and right panels
        menuLeft.add(logo);
        menuLeft.add(lofiMusic);
        menuRight.add(backButton);

        // Add panels to the menu bar
        add(menuLeft);
        add(menuRight);
    }

    /**
     * Creates a menu panel with the specified alignment.
     *
     * @param alignment the alignment for the FlowLayout
     * @return the created JPanel
     */
    private JPanel createMenuPanel(int alignment) {
        JPanel panel = new JPanel(new FlowLayout(alignment));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, ToolBox.MARGIN_LEFT_TOP_MENU, 0, 0));
        return panel;
    }
}