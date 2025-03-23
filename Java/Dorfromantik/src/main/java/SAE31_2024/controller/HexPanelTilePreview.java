package SAE31_2024.controller;

import SAE31_2024.toolbox.ToolBox;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * The HexPanelTilePreview class represents a panel that previews hexagonal tiles.
 * It manages the display and rolling of tiles for the game.
 */
public class HexPanelTilePreview extends JPanel {

    private final HexTileController[] tiles;
    private final HexTileController[] GameTiles;
    private final Integer partieEnCour;
    private int idTile;

    /**
     * Constructs a new HexPanelTilePreview instance.
     *
     * @param partie the game series identifier
     */
    public HexPanelTilePreview(Integer partie) {
        super();
        setPreferredSize(new Dimension(ToolBox.PREVIEW_WIDTH, ToolBox.PREVIEW_HEIGHT));
        this.tiles = new HexTileController[ToolBox.PREVIEW_NB_TILES];
        this.GameTiles = new HexTileController[50];
        this.idTile = 0;
        this.partieEnCour = partie;

        try {
            int serie = partie;
            Connection DB = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/hochlaf","hochlaf","4472135955");
            PreparedStatement pst = DB.prepareStatement("Select GTilesString from LT_Games where ID_Game = ?;");
            pst.setInt(1, serie);
            ResultSet rs = pst.executeQuery();
            rs.first();
            String[] strTiles = rs.getString(1).split(";");
            for (int i = 0; i < strTiles.length; i++) {
                this.GameTiles[i] = new HexTileController(Integer.parseInt(strTiles[i].charAt(0)+""),
                                                          Integer.parseInt(strTiles[i].charAt(1)+""),
                                                          Integer.parseInt(strTiles[i].charAt(2)+""));
            }
            pst.close();
            rs.close();
            DB.close();
        } catch (SQLException | NullPointerException e) {
            if (e.getClass() == SQLException.class) {
                System.out.println("Probleme de base de données : " + e.getMessage());
            }

            for (int i = 0; i < this.GameTiles.length; i++) {
                this.GameTiles[i] = new HexTileController();
            }
        }
        this.setOpaque2(false);
        this.initializePreview();
    }

    /**
     * Retrieves the first tile in the preview.
     *
     * @return the first HexTileController instance
     */
    public HexTileController getFirstTile() {
        return this.tiles[0];
    }

    /**
     * Sets the opacity of the panel and its tiles.
     *
     * @param opaque true to make the panel opaque, false otherwise
     */
    public void setOpaque2(boolean opaque) {
        super.setOpaque(opaque);
        for (int i = 0; i < ToolBox.PREVIEW_NB_TILES; i++) {
            if (this.tiles[i] != null) {
                this.tiles[i].getView().setOpaque(opaque);
            }
        }
    }

    /**
     * Rolls the tiles in the preview, removing the first tile and adding a new one.
     */
    public void roll() {
        this.remove(0);
        for (int i = 0; i < ToolBox.PREVIEW_NB_TILES - 1; i++) {
            this.tiles[i] = this.tiles[i + 1];
        }
        if (this.idTile < 50) {
            this.tiles[ToolBox.PREVIEW_NB_TILES - 1] = this.GameTiles[idTile];
            this.idTile++;
            this.add(this.tiles[ToolBox.PREVIEW_NB_TILES - 1].getView());
        }
        this.tiles[0].getView().setSelected(true);
        this.revalidate();
        this.repaint();
    }

    /**
     * Initializes the tile preview by adding the initial set of tiles.
     */
    public void initializePreview() {
        for (int i = 0; i < ToolBox.PREVIEW_NB_TILES; i++) {
            this.tiles[i] = this.GameTiles[idTile];
            this.idTile++;
            this.add(this.tiles[i].getView());
        }
        this.tiles[0].getView().setSelected(true);
        this.revalidate();
        this.repaint();
    }

    /**
     * Removes all tiles from the preview.
     */
    public void removeAllTiles() {
        this.removeAll();
    }

    /**
     * Customizes the painting behavior of the HexPanelTilePreview component.
     * Sets antialiasing for smoother graphics and paints the component
     * with a specified overlay color and border.
     *
     * @param g the Graphics context in which to paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setClip(0,0,this.getWidth(),this.getHeight());
        g2d.setColor(ToolBox.OverlayBGFullColor);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2d.setColor(ToolBox.OverlayBorderColor);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(0, 1, this.getWidth() , this.getHeight());
    }

    /**
     * Retrieves the current game series identifier.
     *
     * @return the game series identifier
     */
    public Integer getPartieEnCour() {
        return partieEnCour;
    }

    /**
     * Returns a string representation of the HexPanelTilePreview.
     *
     * @return a string representation of the tiles in the preview
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (HexTileController tile : this.GameTiles) {
            str.append(tile.toString()).append(";");
        }
        return str.substring(0, str.length()-1);
    }
}