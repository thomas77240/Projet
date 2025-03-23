package SAE31_2024.controller;

import SAE31_2024.toolbox.ToolBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The HexFrame class represents the main application window for the Hex Tiles game.
 * It sets up the window, listeners, and layout for the game interface.
 */
public class HexFrame extends JFrame implements MouseWheelListener, MouseMotionListener, MouseListener {

    private final HexPanelManagerControler manager;

    /**
     * Constructs a new HexFrame instance.
     *
     * @param MainFrame the main game controller
     * @param serie the series identifier for the game
     */
    public HexFrame(GameController MainFrame, Integer serie){
        super("Hex Tiles");

        // --------------------> Set up the window
        setIconImage(ToolBox.logoLofix64.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(800,600);
        setBackground(ToolBox.WINDOW_BG);

        // -------------------> Listener
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
        setLocationRelativeTo(null);

        setExtendedState(JFrame.MAXIMIZED_BOTH); // Permet le Full-screan

        // Set up the GridBagConstraints
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.weightx = 0f;
        c.weighty = 0f;

        // Top Menu
        JTopMenu topMenu = new JTopMenu(MainFrame);
        add(topMenu,c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_END;
        c.weightx = 0.0f;
        c.weighty = 0.0f;
        // Tile preview
        HexPanelTilePreview preview = new HexPanelTilePreview(serie);
        add(preview,c);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 3;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1.0f;
        c.weighty = 1.0f;
        // Add some tiles
        this.manager = new HexPanelManagerControler(this, preview,MainFrame,serie);
        add(this.manager.getView(),c);

        // finis initialisation de la fenètre
        setVisible(true);

        this.manager.reload();
    }

    /**
     * Handles mouse wheel moved events for zooming.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        this.manager.mouseWheelaction(e);
    }

    /**
     * Handles mouse dragged events for panning.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        this.manager.MouseDraggedAction(e);
    }

    /**
     * Handles mouse pressed events.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        this.manager.MousePressed(e);
    }

    /**
     * Handles mouse released events.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.manager.MouseReleased(e);
    }

    /**
     * Handles mouse moved events.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        this.manager.MouseMovedAction(e);
    }

    /**
     * Handles mouse clicked events.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        this.manager.MouseClicked(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}