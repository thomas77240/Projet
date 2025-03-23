package SAE31_2024.view;

import SAE31_2024.toolbox.ToolBox;
import javax.swing.*;
import java.awt.*;

/**
 * The HexButton class extends JButton to create a custom button with a hexagonal appearance.
 */
public class HexButton extends JButton {
    private final int size;

    /**
     * Constructs a HexButton with the specified name and size.
     *
     * @param name the name of the button
     * @param size the font size of the button text
     */
    public HexButton(String name, int size) {
        super(name);
        this.size = size;
    }

    /**
     * Constructs a HexButton with the specified name, size, and icon.
     *
     * @param name the name of the button
     * @param size the font size of the button text
     * @param icon the icon to be displayed on the button
     */
    public HexButton(String name, int size, ImageIcon icon) {
        super(icon);
        setActionCommand(name);
        this.size = size;
    }

    /**
     * Paints the component with custom graphics.
     *
     * @param g the Graphics object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw the button background
        g2d.setColor(ToolBox.FG_COLOR_3);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(ToolBox.BG_COLOR_3);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(0, 0, getWidth(), getHeight());

        // Draw the icon or text
        if (getIcon() != null) {
            getIcon().paintIcon(this, g2d, (getWidth() / 2) - (getIcon().getIconWidth() / 2), 2);
        } else {
            g2d.setFont(new Font("ArialBlack", Font.BOLD, size));
            if (getName() == null) {
                g2d.drawString(getActionCommand(), (getWidth() / 2) - (g2d.getFontMetrics().stringWidth(getActionCommand()) / 2), getHeight() / 2 + ((size - 4) / 2));
            }
        }
    }
}