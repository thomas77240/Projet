package SAE31_2024.view;

import SAE31_2024.toolbox.ToolBox;

import javax.swing.*;
import java.awt.*;

/**
 * The HexTileView class is a custom JPanel that draws a hexagonal tile.
 */
public class HexTileView extends JPanel {

    /**
     * Array representing the biomes for each side of the hexagonal tile.
     */
    private final int[] sides;

    /**
     * Rotated sides of the hexagonal tile.
     */
    private final int[] sidesR;

    /**
     * Current rotation value of the hexagonal tile.
     */
    private int hexRotation;

    /**
     * Curent preview state
     */
    private boolean selected;

    /**
     * Constructs a HexTileView with specified biomes for each side of the hexagon.
     *
     * @param sidesBiomes an array representing the biomes for each side of the hexagon
     */
    public HexTileView(int[] sidesBiomes) {
        // Set preferred size based on the tile size from ToolBox.
        setPreferredSize(new Dimension(ToolBox.TILE_SIZE * 2 + 3, ToolBox.TILE_SIZE * 2 + 3));
        this.sides = sidesBiomes;
        this.sidesR = this.sides;
        this.hexRotation = 1;
    }

    /**
     * Sets the rotation of the hexagonal tile.
     *
     * @param hexRotation the desired rotation value for the hexagonal tile
     */
    public void setHexRotation(int hexRotation) {
        this.hexRotation = hexRotation;
        hexRotate();
    }

    /**
     * Retrieves the current rotation of the hex tile.
     *
     * @return the current rotation of the hex tile as an integer.
     */
    public int getHexRotation() {
        return hexRotation;
    }

    /**
     * Rotates the hexagonal tile and repaints the component.
     */
    private void hexRotate() {
        int tmp = sides[0];
        for (int i = 0; i < sides.length - 1; i++) {
            sides[i] = sidesR[i + 1];
        }
        sidesR[sides.length - 1] = tmp;
        repaint();
    }

    /**
     * Overridden method to paint the hexagonal tile on the panel.
     * Draws the tile background, biome colors, and borders.
     *
     * @param g the Graphics object used to draw the components.
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Set the background color based on ToolBox.
        setBackground(ToolBox.OverlayBGFullColor);
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (this.selected){
            g2d.setColor(ToolBox.FG_COLOR_2);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        Polygon hexagon = new Polygon();

        // Calculate the points of the hexagon.
        for (int i = 0; i < 6; i++) {
            int x = centerX + (int) (ToolBox.TILE_SIZE * Math.cos(i * Math.PI / 3));
            int y = centerY + (int) (ToolBox.TILE_SIZE * Math.sin(i * Math.PI / 3));
            hexagon.addPoint(x, y);
        }

        // Fill the hexagon with a base color.
        g2d.setColor(Color.black);
        g2d.fillPolygon(hexagon);

        // Fill each side of the hexagon with its corresponding biome color.
        int[] xpList = hexagon.xpoints;
        int[] ypList = hexagon.ypoints;
        int tileCenterX = (xpList[3] - xpList[0]) / 2 + xpList[0];
        int tileCenterY = (ypList[3] - ypList[0]) / 2 + ypList[0];

        for (int i = 0; i < sides.length; i++) {
            int j = (i + 1) % sides.length;
            Polygon triangle = new Polygon(new int[]{tileCenterX, xpList[i], xpList[j]}, new int[]{tileCenterY, ypList[i], ypList[j]}, 3);

            // Draw the triangle of the hexagon side corresponding to the biome.
            g2d.setColor(ToolBox.BiomesColors[sides[i]]);
            g2d.fillPolygon(triangle);

            // Draw the border of the triangle.
            g2d.setColor(ToolBox.BiomesColors2[sides[i]]);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawPolygon(triangle);
            g2d.setStroke(new BasicStroke(1));
        }

        // Draw the border of the hexagon.
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawPolygon(hexagon);
    }

    /**
     * Retrieves the biomes array for the hexagonal tile sides.
     *
     * @return an array representing the biomes for each side.
     */
    public int[] getSidesBiomes() {
        return sidesR;
    }


    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
