package SAE31_2024.view;

import SAE31_2024.controller.GameController;
import SAE31_2024.toolbox.ToolBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The LaunchPage class represents the main menu page of the game.
 * It contains buttons to start the game, view rules, and view scores.
 */
public class LaunchPage extends JPanel implements ActionListener {
    private final GameController controller;
    private final Dimension MaxSize;

    /**
     * Constructs a LaunchPage with the specified GameController.
     *
     * @param controller the GameController to handle actions
     */
    public LaunchPage(GameController controller) {
        this.controller = controller;

        // Customize the appearance of the page
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Create buttons
        HexButton playButton = new HexButton("Jouer", 18);
        playButton.addActionListener(this); // Start the game
        playButton.setPreferredSize(new Dimension(100, 50));

        HexButton rulesButton = new HexButton("Règles", 18);
        rulesButton.addActionListener(this); // Show rules
        rulesButton.setPreferredSize(new Dimension(100, 50));

        HexButton scoreButton = new HexButton("Scores", 18);
        scoreButton.addActionListener(this); // Show scores
        scoreButton.setPreferredSize(new Dimension(100, 50));

        // Add buttons to the panel
        add(playButton, gbc);
        gbc.gridy = 1;
        add(rulesButton, gbc);
        gbc.gridy = 2;
        add(scoreButton, gbc);

        this.MaxSize = Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * Paints the component, including the background image and logo.
     *
     * @param g the Graphics object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Background image
        ImageIcon wallpaper = new ImageIcon(ToolBox.loadFile("res/img/wallpaper.png"));
        g2d.drawImage(wallpaper.getImage(), this.getWidth() - this.MaxSize.width, 0, this.MaxSize.width, this.MaxSize.height, this);

        // Logo
        g2d.drawImage(ToolBox.logoLofi.getImage(), 20, 0, 200, 200, this);

        // Draw the menu overlay
        g2d.setColor(ToolBox.OverlayBGAlphaColor);
        g2d.fillRect((this.getWidth() / 2) - 75, (this.getHeight() / 2) - 120, 75 * 2, 80 * 3);
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(ToolBox.OverlayBorderColor);
        g2d.drawRect((this.getWidth() / 2) - 75, (this.getHeight() / 2) - 120, 75 * 2, 80 * 3);
    }

    /**
     * Handles button click events.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Jouer":
                controller.chooseSerie();
                break;
            case "Règles":
                controller.showRules();
                break;
            case "Scores":
                controller.showScores();
                break;
            default:
                break;
        }
    }
}