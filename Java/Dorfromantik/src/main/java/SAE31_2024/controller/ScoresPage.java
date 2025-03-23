package SAE31_2024.controller;

import SAE31_2024.toolbox.ToolBox;
import SAE31_2024.view.HexButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * The ScoresPage class represents a panel that displays the scores of different game series.
 */
public class ScoresPage extends JPanel implements ActionListener {

    private final JLabel nb_Partie;
    private final Object[] entete;
    private final JTable table;
    private int game;
    private int lastPartie;

    /**
     * Constructs a ScoresPage with the specified GameController.
     *
     * @param gameController the game controller to use
     */
    public ScoresPage(GameController gameController) {
        this.setLayout(new BorderLayout());

        int i = 0;
        this.game = 1;
        Object[][] donnees = this.getBdDScores();
        this.entete = new Object[]{"Classement", "Score"};

        try {
            Connection DB = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/hochlaf", "hochlaf", "4472135955");

            PreparedStatement pst = DB.prepareStatement("Select ID_Game from LT_Games");
            ResultSet rs = pst.executeQuery();
            rs.last();

            this.lastPartie = rs.getInt(1);

            rs.close();
            pst.close();
            DB.close();
        } catch (SQLException e) {
            System.out.println("Problème de connection");
        }

        this.table = new JTable(donnees, entete);
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.table.setFont(new Font("Aria Black", Font.PLAIN, 20));
        this.table.setEnabled(false);
        this.table.getTableHeader().setFont(new Font("Aria Black", Font.BOLD, 20));
        this.table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);

        // Bouton de retour
        HexButton backButton = new HexButton("Revenir au menu principal", 18);
        backButton.setPreferredSize(new Dimension(200, 50));
        backButton.addActionListener(new ReturnToMainFrameActionHandler(gameController));

        // panel de navigation
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(ToolBox.OverlayBGFullColor);
        topPanel.setBorder(BorderFactory.createLineBorder(ToolBox.OverlayBorderColor));
        topPanel.setPreferredSize(new Dimension(100, 70));

        HexButton nextButton = new HexButton("Next", 18, new ImageIcon(ToolBox.loadFile("res/next.png")));
        HexButton previousButton = new HexButton("Previous", 18, new ImageIcon(ToolBox.loadFile("res/prev.png")));
        this.nb_Partie = new JLabel();
        this.nb_Partie.setFont(new Font("Aria Black", Font.PLAIN, 24));
        this.nb_Partie.setText("Partie n° " + this.game);
        this.nb_Partie.setHorizontalAlignment(SwingConstants.CENTER);

        nextButton.addActionListener(this);
        previousButton.addActionListener(this);

        topPanel.add(nextButton, BorderLayout.EAST);
        topPanel.add(nb_Partie, BorderLayout.CENTER);
        topPanel.add(previousButton, BorderLayout.WEST);

        // panel du tableau
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        // Ajouter les composants
        add(topPanel, BorderLayout.NORTH);
        add(backButton, BorderLayout.SOUTH);
        scrollPane.add(table.getTableHeader());
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(table, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Retrieves the scores from the database for the current game.
     *
     * @return a 2D array containing the scores
     */
    private Object[][] getBdDScores() {
        int i = 0;
        Object[][] donnees = new Object[30][2];
        try {
            Connection DB = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/hochlaf", "hochlaf", "4472135955");

            PreparedStatement pst = DB.prepareStatement("Select ID,ID_PreparedGames,score from LT_scores " +
                    "JOIN LT_Games on LT_scores.ID_PreparedGames = LT_Games.ID_Game " +
                    "where LT_scores.ID_PreparedGames = ? ORDER BY score desc ;");
            pst.setInt(1, this.game);
            ResultSet rs = pst.executeQuery();

            while (rs.next() || i >= donnees.length) {
                donnees[i] = new Object[]{i + 1, rs.getInt("score")};
                i++;
            }

            pst.close();
            rs.close();
            DB.close();
        } catch (SQLException e) {
            System.err.println("Problème de base de donnée : " + e.getMessage());
        }
        return donnees;
    }

    /**
     * Handles action events for the navigation buttons.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Next":
                if (this.lastPartie > this.game) {
                    this.game += 1;
                    this.nb_Partie.setText("Partie n° " + this.game);
                    this.nb_Partie.repaint();

                    this.table.setModel(new DefaultTableModel(this.getBdDScores(), this.entete));
                    this.table.repaint();
                    this.table.revalidate();
                }
                break;
            case "Previous":
                if (this.game > 1) {
                    this.game -= 1;
                    this.nb_Partie.setText("Partie n° " + this.game);
                    this.nb_Partie.repaint();

                    this.table.setModel(new DefaultTableModel(this.getBdDScores(), this.entete));
                    this.table.repaint();
                    this.table.revalidate();
                }
                break;
            default:
                break;
        }
    }
}