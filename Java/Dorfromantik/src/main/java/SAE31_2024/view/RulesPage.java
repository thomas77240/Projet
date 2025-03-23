package SAE31_2024.view;

import SAE31_2024.controller.GameController;
import SAE31_2024.controller.ReturnToMainFrameActionHandler;
import SAE31_2024.toolbox.ToolBox;

import javax.swing.*;
import java.awt.*;

/**
 * The RulesPage class represents a JPanel displaying the rules of the game.
 * It includes a background image, game rules, and a button to return to the main menu.
 */
public class RulesPage extends JPanel {

    /**
     * The maximum size of the screen, used for scaling.
     */
    private final Dimension maxSize;

    /**
     * The text array containing the game rules and instructions.
     */
    private final String[] rulesText;

    /**
     * Constructor for the RulesPage.
     * Initializes the layout, loads the rules text, and sets up the back button.
     *
     * @param controller the GameController instance to handle interactions.
     */
    public RulesPage(GameController controller) {
        // Retrieve the screen dimensions to scale the UI elements.
        this.maxSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLayout(new BorderLayout());

        // Define the rules and instructions to be displayed on the page.
        this.rulesText = new String[]{
                "Règles du jeu :",
                " ",
                "- Une partie peut durer jusqu'à 50 tuiles posées.",
                "- Une partie sera proposée tous les jours pour vous permettre",
                "de compétiter avec les autres joueurs.",
                " ",
                "Comment jouer :",
                " ",
                "- Pour poser une tuile : Cliquez avec le bouton gauche.\n",
                "- Pour tourner une tuile : Cliquez avec le bouton droit.\n",
                "- Pour dézoomer : Utilisez la molette de la souris.\n",
                "- Pour déplacer la vue : Maintenez le clic gauche ou droit et bougez la souris.\n"
        };

        // Create a back button to return to the main menu.
        HexButton backButton = new HexButton("Retour au menu principal", 18);
        backButton.setPreferredSize(new Dimension(200, 50));
        backButton.addActionListener(new ReturnToMainFrameActionHandler(controller));

        // Add the back button to the bottom of the panel.
        add(backButton, BorderLayout.SOUTH);
    }

    /**
     * Custom painting for the RulesPage.
     * Draws the background image, logo, and the rules text.
     *
     * @param g the Graphics object used to draw the components.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Load and draw the wallpaper as the background.
        ImageIcon wallpaper = new ImageIcon(ToolBox.loadFile("res/img/wallpaper.png"));
        g2d.drawImage(wallpaper.getImage(), getWidth() - maxSize.width, 0, maxSize.width, maxSize.height, this);

        // Draw the Lofi logo in the top-left corner.
        g2d.drawImage(ToolBox.logoLofi.getImage(), 20, 0, 200, 200, this);

        // Set the font for the rules text.
        g2d.setFont(new Font("Arial", Font.BOLD, 20));

        // Draw each line of the rules text at the specified position.
        for (int i = 0; i < rulesText.length; i++) {
            g2d.drawString(rulesText[i], getWidth() / 4, (getHeight() / 4) + i * 30);
        }
    }
}
