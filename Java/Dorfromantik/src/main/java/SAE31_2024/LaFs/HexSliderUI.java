package SAE31_2024.LaFs;

import SAE31_2024.toolbox.ToolBox;
import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

/**
 * Custom UI for a hexagonal slider.
 */
public class HexSliderUI extends BasicSliderUI {

    /**
     * Constructs a HexSliderUI for the specified slider.
     *
     * @param slider the JSlider to customize
     */
    public HexSliderUI(JSlider slider) {
        super(slider);
        slider.setOpaque(true);
        slider.setBackground(new Color(0, 0, 0, 0));
    }

    /**
     * Paints the thumb of the slider.
     *
     * @param g the Graphics context in which to paint
     */
    @Override
    public void paintThumb(Graphics g) {
        slider.repaint();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Polygon thumb = new Polygon();

        int width = thumbRect.width;
        int height = thumbRect.height;
        int xOffset = thumbRect.x;
        int yOffset = thumbRect.y;

        for (int i = 0; i < ToolBox.NB_SIDES; i++) {
            double angle = i * Math.PI / 3;
            double x = (width / 2.0 - 2) * Math.cos(angle) + width / 2.0 - 0.5;
            double y = (width - 2) * Math.sin(angle) + height / 2.0;
            thumb.addPoint((int) (x + xOffset), (int) (y + yOffset));
        }

        g2d.setColor(ToolBox.BG_COLOR1);
        g2d.fillPolygon(thumb);
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(ToolBox.FG_COLOR1);
        g2d.drawPolygon(thumb);
    }

    /**
     * Paints the track of the slider.
     *
     * @param g the Graphics context in which to paint
     */
    @Override
    public void paintTrack(Graphics g) {
        slider.repaint();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(5));

        int midY = trackRect.y + trackRect.height / 2;
        int thumbX = thumbRect.x + 6;

        g2d.setColor(ToolBox.FG_COLOR1);
        g2d.drawLine(trackRect.x, midY, thumbX, midY);
        g2d.setColor(ToolBox.BG_COLOR1);
        g2d.drawLine(thumbX, midY, trackRect.width + 3, midY);
    }

    /**
     * Paints the slider component.
     *
     * @param g the Graphics context in which to paint
     * @param c the component being painted
     */
    @Override
    public void paint(Graphics g, JComponent c) {
        g.setColor(ToolBox.WINDOW_BG);
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
        g.setColor(ToolBox.OverlayBGAlphaColor);
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
        paintTrack(g);
        paintThumb(g);
    }
}