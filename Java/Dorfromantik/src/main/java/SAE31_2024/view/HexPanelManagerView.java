package SAE31_2024.view;

import SAE31_2024.toolbox.ToolBox;
import SAE31_2024.controller.HexFrame;
import SAE31_2024.controller.HexPanelManagerControler;
import SAE31_2024.model.HexTileModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.sql.*;

/**
 * The HexPanelManagerView class is responsible for managing and displaying the hexagonal tiles on the panel.
 */
public class HexPanelManagerView extends JComponent {

    private final HexPanelManagerControler controler;
    private final HexFrame frame;
    private double scale;
    private int originX = 0, originY = 0;
    private HexTileView preview;
    private int prevX = 0, prevY = 0;
    private final Integer serie;

    /**
     * Constructs a HexPanelManagerView with the specified controller and frame.
     *
     * @param controler the controller to manage the hex panel
     * @param frame the frame containing the hex panel
     */
    public HexPanelManagerView(HexPanelManagerControler controler, HexFrame frame, Integer serie) {
        this.controler = controler;
        this.scale = 1;
        this.frame = frame;
        this.serie = serie;
    }

    /**
     * Paints a hexagonal tile on the panel.
     *
     * @param g2d the Graphics2D object to protect
     * @param tile the hexagonal tile model to be painted
     */
    private void paintTile(Graphics2D g2d, HexTileModel tile) {
        Polygon hexagon = getHexagon(tile);
        g2d.setColor(Color.black);
        g2d.fillPolygon(hexagon);

        int[] xpList = hexagon.xpoints;
        int[] ypList = hexagon.ypoints;
        int tileCenterX = (xpList[3] - xpList[0]) / 2 + xpList[0];
        int tileCenterY = (ypList[3] - ypList[0]) / 2 + ypList[0];

        for (int i = 0; i < tile.getSidesBiomes().length; i++) {
            int j = (i + 1) % tile.getSidesBiomes().length;
            Polygon triangle = new Polygon(new int[]{tileCenterX, xpList[i], xpList[j]}, new int[]{tileCenterY, ypList[i], ypList[j]}, 3);
            g2d.setColor(ToolBox.BiomesColors[tile.getSidesBiomes()[i]]);
            g2d.fillPolygon(triangle);
            g2d.setColor(ToolBox.BiomesColors2[tile.getSidesBiomes()[i]]);
            g2d.setStroke(new BasicStroke((float) (2 * this.scale)));
            g2d.drawPolygon(triangle);
            g2d.setStroke(new BasicStroke((float) (1 * this.scale)));
        }
    }

    /**
     * Generates a hexagon polygon based on the tile model.
     *
     * @param tile the hexagonal tile model
     * @return the generated hexagon polygon
     */
    private Polygon getHexagon(HexTileModel tile) {
        int centerX = (int) (this.originX + (tile.getX() * ToolBox.xOfset) * this.scale);
        int centerY = (int) (this.originY + (tile.getY() * ToolBox.yOfset) * this.scale);
        for (int i = 0; i < Math.abs(tile.getY()); i++) {
            centerX -= (int) ((ToolBox.xOfset * this.scale) / 2);
        }
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            int x = centerX + (int) ((ToolBox.TILE_SIZE * this.scale) * Math.cos(i * Math.PI / 3));
            int y = centerY + (int) ((ToolBox.TILE_SIZE * this.scale) * Math.sin(i * Math.PI / 3));
            hexagon.addPoint(x, y);
        }
        return hexagon;
    }

    /**
     * Generates a hexagon polygon based on the x and y coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the generated hexagon polygon
     */
    private Polygon getHexagon(int x, int y) {
        int centerX = (int) (this.originX + (x * ToolBox.xOfset) * this.scale);
        int centerY = (int) (this.originY + (y * ToolBox.yOfset) * this.scale);
        for (int i = 0; i < Math.abs(y); i++) {
            centerX -= (int) ((ToolBox.xOfset * this.scale) / 2);
        }
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            int xPoint = centerX + (int) ((ToolBox.TILE_SIZE * this.scale) * Math.cos(i * Math.PI / 3));
            int yPoint = centerY + (int) ((ToolBox.TILE_SIZE * this.scale) * Math.sin(i * Math.PI / 3));
            hexagon.addPoint(xPoint, yPoint);
        }
        return hexagon;
    }

    /**
     * Paints the component, including the hexagonal tiles and score.
     *
     * @param g the Graphics object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (this.controler.inGame()) {
            if (this.preview != null && this.controler.getModel().CheckCanBePlaced(this.prevX, this.prevY)) {
                this.paintTilePrev(g2d, this.preview, this.prevX, this.prevY);
            }
        }

        for (HexTileModel tile : this.controler.getTiles()) {
            if (tile == null) break;
            this.paintTile(g2d, tile);
        }

        String score = "Score = " + this.controler.getModel().getScore();
        g2d.setFont(new Font(ToolBox.FONT_NAME, Font.PLAIN, 20));
        int strX = (this.frame.getWidth() / 2) - (g2d.getFontMetrics().stringWidth(score) / 2);
        int strY = ToolBox.TOP_MENU_HEIGHT + 20;

        if (!this.controler.inGame()) {
            g2d.setColor(ToolBox.FG_COLOR_2);
            g2d.fillRect(100, 150, this.getWidth() - 200, this.getHeight() - 200);
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(ToolBox.BG_COLOR_2);
            g2d.drawRect(100, 150, this.getWidth() - 200, this.getHeight() - 200);

            String str1 = "Partie terminée";
            String noCo = "Il n'y a aucuns classement pour les séries aléatoires.";
            g2d.setColor(Color.black);
            g2d.setFont(new Font(ToolBox.FONT_NAME, Font.BOLD, 32));
            g2d.drawString(str1, (this.getWidth() / 2) - (g2d.getFontMetrics().stringWidth(str1) / 2), (this.getHeight() / 2) - 20);

            if (this.serie == null) {
                g2d.drawString(noCo, (this.getWidth() / 2) - (g2d.getFontMetrics().stringWidth(noCo) / 2), (this.getHeight() / 2) + 120);
            } else {
                try {
                     Connection db = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/hochlaf", "hochlaf", "4472135955");
                     PreparedStatement ps1 = db.prepareStatement("select score from LT_scores where ID_PreparedGames = ? order by score desc;");
                     ps1.setInt(1,this.serie);

                     PreparedStatement ps2 = db.prepareStatement("select count(*) from LT_scores where ID_PreparedGames = ? order by score desc;");
                     ps2.setInt(1,this.serie);

                     ResultSet rs = ps1.executeQuery();
                     ResultSet tt = ps2.executeQuery();

                    tt.next();
                    int ttCount = tt.getInt("count(*)");

                    int i = 0;
                    while (rs.next()) {
                        i++;
                        if (this.controler.getModel().getScore() >= rs.getInt("score")) break;
                    }
                    int topJoueur = i * 100 / ttCount;

                    String strTop = "Vous vous situez dans le top " + topJoueur + "% des joueurs pour cette série.";
                    g2d.setFont(new Font(ToolBox.FONT_NAME, Font.PLAIN, 20));
                    g2d.drawString("Vous êtes à la position " + i + " du classement de cette série.", (this.getWidth() / 2) - (g2d.getFontMetrics().stringWidth("Vous êtes à la position " + i + " du classement de cette série.") / 2), (this.getHeight() / 2) + 30);
                    g2d.drawString(strTop, (this.getWidth() / 2) - (g2d.getFontMetrics().stringWidth(strTop) / 2), (this.getHeight() / 2) + 55);
                    g2d.drawString("Appuyez sur le bouton 'Retour' pour revenir au menu principal", (this.getWidth() / 2) - (g2d.getFontMetrics().stringWidth("Appuyez sur le bouton 'Retour' pour revenir au menu principal") / 2), (this.getHeight() / 2) + 120);

                } catch (SQLException e) {
                    System.out.println("Problème de base de données : " + e.getMessage());
                }
            }
            strY = this.getHeight() / 2;
        }

        g2d.setFont(new Font(ToolBox.FONT_NAME, Font.PLAIN, 20));
        g2d.setColor(Color.black);
        g2d.drawString(score, strX, strY);
    }

    /**
     * Paints the preview of a hexagonal tile on the panel.
     *
     * @param g2d the Graphics2D object to protect
     * @param preview the hexagonal tile view to be painted
     * @param x the x coordinate
     * @param y the y coordinate
     */
    private void paintTilePrev(Graphics2D g2d, HexTileView preview, int x, int y) {
        Polygon hexagon = getHexagon(x, y);
        int[] xpList = hexagon.xpoints;
        int[] ypList = hexagon.ypoints;
        int tileCenterX = (xpList[3] - xpList[0]) / 2 + xpList[0];
        int tileCenterY = (ypList[3] - ypList[0]) / 2 + ypList[0];

        for (int i = 0; i < preview.getSidesBiomes().length; i++) {
            int j = (i + 1) % preview.getSidesBiomes().length;
            Polygon triangle = new Polygon(new int[]{tileCenterX, xpList[i], xpList[j]}, new int[]{tileCenterY, ypList[i], ypList[j]}, 3);
            g2d.setColor(makeColorTransparent(ToolBox.BiomesColors[preview.getSidesBiomes()[i]]));
            g2d.fillPolygon(triangle);
            g2d.setColor(makeColorTransparentX2(ToolBox.BiomesColors2[preview.getSidesBiomes()[i]]));
            g2d.setStroke(new BasicStroke((float) (2 * this.scale)));
            g2d.drawPolygon(triangle);
            g2d.setStroke(new BasicStroke((float) (1 * this.scale)));
        }
    }

    /**
     * Makes a color transparent with a specified alpha value.
     *
     * @param biomesColor the original color
     * @return the transparent color
     */
    private Color makeColorTransparent(Color biomesColor) {
        return new Color(biomesColor.getRed(), biomesColor.getGreen(), biomesColor.getBlue(), 150);
    }

    /**
     * Makes a color more transparent with a specified alpha value.
     *
     * @param biomesColor the original color
     * @return the more transparent color
     */
    private Color makeColorTransparentX2(Color biomesColor) {
        return new Color(biomesColor.getRed(), biomesColor.getGreen(), biomesColor.getBlue(), 75);
    }

    /**
     * Changes the scale of the hexagonal tiles based on mouse wheel events.
     *
     * @param e the MouseWheelEvent to be processed
     */
    public void changeScale(MouseWheelEvent e) {
        if (e.getWheelRotation() == -1 && this.scale < ToolBox.ZOOM_BORNE_MAX) {
            this.scale += this.scale * ToolBox.ZOOM_POWER;
        } else if (e.getWheelRotation() == 1 && this.scale > ToolBox.ZOOM_BORNE_MIN) {
            this.scale -= this.scale * ToolBox.ZOOM_POWER;
        }
        this.repaint();
    }

    /**
     * Sets the origin coordinates for the hexagonal tiles.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void setOrigin(int x, int y) {
        this.originX += x;
        this.originY += y;
        repaint();
    }

    /**
     * Gets the x coordinate of the origin.
     *
     * @return the x coordinate of the origin
     */
    public int getOriginX() {
        return originX;
    }

    /**
     * Gets the y coordinate of the origin.
     *
     * @return the y coordinate of the origin
     */
    public int getOriginY() {
        return originY;
    }

    /**
     * Gets the current scale of the hexagonal tiles.
     *
     * @return the current scale
     */
    public double getScale() {
        return scale;
    }

    /**
     * Resets the position of the hexagonal tiles to the center of the screen.
     */
    public void resetPosition() {
        this.originX = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2);
        this.originY = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
        this.repaint();
    }

    /**
     * Repaints the preview of a hexagonal tile at the specified coordinates.
     *
     * @param preview the hexagonal tile view to be painted
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void repaintPrev(HexTileView preview, int x, int y) {
        this.preview = preview;
        this.prevX = x;
        this.prevY = y;
        this.repaint();
    }
}