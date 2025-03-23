package SAE31_2024.controller;

import SAE31_2024.view.LaunchPage;
import SAE31_2024.view.RulesPage;

import javax.swing.*;
import java.awt.*;

/**
 * The GameController class manages the main application flow and navigation between different pages.
 */
public class GameController {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final JFrame mainFrame;
    private boolean gameStarted = false;
    private HexFrame hexFrame;

    /**
     * Constructs a new GameController instance and initializes the main application window.
     */
    public GameController() {
        mainFrame = createMainFrame();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        setupPages();
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
        cardLayout.show(mainPanel, "Launch");
    }

    /**
     * Creates and configures the main application frame.
     *
     * @return the configured JFrame instance
     */
    private JFrame createMainFrame() {
        JFrame frame = new JFrame("Hex Tiles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        return frame;
    }

    /**
     * Sets up the different pages and adds them to the main panel.
     */
    private void setupPages() {
        mainPanel.add(new LaunchPage(this), "Launch");
        mainPanel.add(new RulesPage(this), "Rules");
        mainPanel.add(new ScoresPage(this), "Scores");
        mainPanel.add(new SeriePage(this), "Series");
    }

    /**
     * Navigates to the series selection page.
     */
    public void chooseSerie() {
        cardLayout.show(mainPanel, "Series");
    }

    /**
     * Starts the game with the specified series.
     *
     * @param serie the series identifier
     */
    public void startGame(Integer serie) {
        gameStarted = true;
        mainFrame.setVisible(false);
        hexFrame = new HexFrame(this, serie);
        hexFrame.setVisible(true);
        hexFrame.revalidate();
        hexFrame.repaint();
    }

    /**
     * Displays the rules page.
     */
    public void showRules() {
        cardLayout.show(mainPanel, "Rules");
    }

    /**
     * Returns to the launch page, disposing of the game frame if a game was started.
     */
    public void returnToLaunchPage() {
        if (gameStarted) {
            gameStarted = false;
            mainFrame.setVisible(true);
            mainFrame.revalidate();
            mainFrame.repaint();
            hexFrame.dispose();
        }
        cardLayout.show(mainPanel, "Launch");
    }

    /**
     * Displays the scores page.
     */
    public void showScores() {
        cardLayout.show(mainPanel, "Scores");
    }
}