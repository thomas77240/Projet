package SAE31_2024.controller;

import SAE31_2024.toolbox.ToolBox;
import SAE31_2024.view.HexButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * The SeriePage class represents a panel that allows users to select a game series.
 */
public class SeriePage extends JPanel implements ActionListener {

    private final GameController gameController;

    /**
     * Constructs a SeriePage with the specified GameController.
     *
     * @param gameController the game controller to use
     */
    public SeriePage(GameController gameController) {
        this.gameController = gameController;
        setLayout(new BorderLayout());

        JPanel btnPanel = createButtonPanel();
        JPanel topPanel = createTopPanel();

        add(topPanel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.CENTER);

        HexButton backButton = new HexButton("Revenir au menu principal", 18);
        backButton.setPreferredSize(new Dimension(200, 50));
        backButton.addActionListener(new ReturnToMainFrameActionHandler(gameController));

        add(backButton, BorderLayout.SOUTH);
    }

    /**
     * Creates the panel containing the series selection buttons.
     *
     * @return the panel with the series selection buttons
     */
    private JPanel createButtonPanel() {
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.setBorder(BorderFactory.createLineBorder(ToolBox.OverlayBGFullColor, 75));

        try (Connection DB = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/hochlaf", "hochlaf", "4472135955");
             PreparedStatement pst = DB.prepareStatement("Select count(*) from LT_Games");
             ResultSet rs = pst.executeQuery()) {

            rs.first();
            for (int i = 0; i < rs.getInt(1); i++) {
                HexButton button = new HexButton("Série n° " + (i + 1), 18);
                button.addActionListener(this);
                button.setPreferredSize(new Dimension(100, 50));
                btnPanel.add(button);
            }
        } catch (SQLException e) {
            System.out.println("Problème de connection");
        }

        HexButton randomButton = new HexButton("Série random", 18);
        randomButton.addActionListener(this);
        randomButton.setPreferredSize(new Dimension(150, 50));
        btnPanel.add(randomButton);

        return btnPanel;
    }

    /**
     * Creates the top panel with the title label.
     *
     * @return the top panel with the title label
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel topLabel = new JLabel("Choisissez une série", SwingConstants.CENTER);
        topLabel.setOpaque(true);
        topLabel.setBackground(ToolBox.OverlayBGFullColor);
        topLabel.setFont(new Font("ArialBlack", Font.BOLD, 48));
        topPanel.add(topLabel, BorderLayout.CENTER);
        return topPanel;
    }

    /**
     * Handles action events for the series selection buttons.
     *
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int serieNumber = Integer.parseInt(e.getActionCommand().split(" ")[2]);
            gameController.startGame(serieNumber);
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            gameController.startGame(null);
        }
    }
}