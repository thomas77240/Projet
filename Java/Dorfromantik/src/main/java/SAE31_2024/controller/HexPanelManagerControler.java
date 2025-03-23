package SAE31_2024.controller;

import SAE31_2024.toolbox.ToolBox;
import SAE31_2024.model.HexPanelManagerModel;
import SAE31_2024.model.HexTileModel;
import SAE31_2024.view.HexButton;
import SAE31_2024.view.HexPanelManagerView;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.rmi.AlreadyBoundException;
import java.sql.*;

/**
 * This class manages the interaction between the HexPanelManagerModel and the HexPanelManagerView.
 * It serves as the controller in an MVC architecture, coordinating updates and interaction
 * between the model (data) and view (UI presentation).
 */
public class HexPanelManagerControler {

    private final HexPanelManagerModel model;
    private final HexPanelManagerView view;
    private final HexPanelTilePreview hexTilePreviewManager;
    private final GameController Gcontroller;
    private HexTileController hexTilePreview;
    private final HexFrame Window;
    private long lastTimeZoom;

    private int LastPressX;
    private int LastPressY;

    private boolean inGame;

    /**
     * Constructs a new HexPanelManagerControler.
     * Initializes the HexPanelManagerModel and HexPanelManagerView components.
     *
     * @param frame the main frame of the application
     * @param preview the preview manager for hex tiles
     * @param Gcontroller the main game controller
     */
    public HexPanelManagerControler(HexFrame frame, HexPanelTilePreview preview, GameController Gcontroller,Integer serie) {
        this.model = new HexPanelManagerModel();
        this.view = new HexPanelManagerView(this, frame,serie);
        this.lastTimeZoom = System.currentTimeMillis();
        this.hexTilePreviewManager = preview;
        this.Window = frame;
        this.inGame = true;
        this.Gcontroller = Gcontroller;

        try {
            this.add(preview.getFirstTile().getModel(), 0, 0, true);
        } catch (AlreadyBoundException e) {
            System.err.println(e.getMessage());
        }

        this.hexTilePreview = this.hexTilePreviewManager.getFirstTile();
    }

    /**
     * Adds a hex tile to the model at the specified coordinates.
     *
     * @param tile the hex tile model to add
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param force whether to force the addition
     * @throws AlreadyBoundException if the tile is already bound
     * @throws IndexOutOfBoundsException if the coordinates are out of bounds
     */
    public void add(HexTileModel tile, int x, int y, boolean force) throws AlreadyBoundException, IndexOutOfBoundsException {
        this.model.addTile(tile, x, y, force);
        hexTilePreviewManager.roll();
    }

    /**
     * Retrieves the HexPanelManagerModel instance used by the controller.
     *
     * @return the HexPanelManagerModel instance
     */
    public HexPanelManagerModel getModel() {
        return model;
    }

    /**
     * Retrieves the HexPanelManagerView instance used by the controller.
     *
     * @return the HexPanelManagerView instance
     */
    public HexPanelManagerView getView() {
        return view;
    }

    /**
     * Retrieves an array of hexagonal tiles managed by the model.
     *
     * @return an array of HexTileModel instances representing the tiles
     */
    public HexTileModel[] getTiles() {
        return model.getTiles();
    }

    /**
     * Calculates the hexagonal coordinates based on a mouse event.
     *
     * @param e the mouse event
     * @return an array containing the x and y coordinates
     */
    public int[] calculateHexCoord(MouseEvent e) {
        double adjustedX = (e.getX() - this.view.getOriginX()) / this.view.getScale();
        double adjustedY = (e.getY() - this.view.getOriginY() - ToolBox.COLIDE_AJUST) / this.view.getScale();
        int Y = (int) Math.round(adjustedY / ToolBox.yOfset);

        for (int i = 0; i < Math.abs(Y); i++) {
            adjustedX += (double) ToolBox.xOfset / 2;
        }
        int X = (int) Math.round(adjustedX / ToolBox.xOfset);
        return new int[]{X, Y};
    }

    /**
     * Checks if the game is currently in progress.
     *
     * @return true if the game is in progress, false otherwise
     */
    public boolean inGame() {
        return this.inGame;
    }

    /**
     * Handles mouse wheel actions for zooming.
     *
     * @param e the mouse wheel event
     */
    public void mouseWheelaction(MouseWheelEvent e) {
        int iter = 1;
        if ((System.currentTimeMillis() - this.lastTimeZoom) < ToolBox.ZOOM_TIME_FRAME) {
            iter = (int) ((ToolBox.ZOOM_TIME_FRAME - (System.currentTimeMillis() - this.lastTimeZoom)) / ToolBox.ZOOM_SPEED);
            if (iter == 0) {
                iter = 1;
            }
        }
        this.lastTimeZoom = System.currentTimeMillis();

        for (int i = 0; i < iter; i++) {
            this.view.changeScale(e);
        }
    }

    /**
     * Handles mouse pressed events.
     *
     * @param e the mouse event
     */
    public void MousePressed(MouseEvent e) {
        this.LastPressX = e.getX();
        this.LastPressY = e.getY();
    }

    /**
     * Handles mouse released events.
     *
     * @param e the mouse event
     */
    public void MouseReleased(MouseEvent e) {
        this.LastPressX = e.getX();
        this.LastPressY = e.getY();
    }

    /**
     * Handles mouse dragged events.
     *
     * @param e the mouse event
     */
    public void MouseDraggedAction(MouseEvent e) {
        int x = e.getX() - this.LastPressX, y = e.getY() - this.LastPressY;
        this.view.setOrigin(x, y);
        this.LastPressX = e.getX();
        this.LastPressY = e.getY();
    }

    /**
     * Reloads the view to its initial position.
     */
    public void reload() {
        this.view.resetPosition();
    }

    /**
     * Handles mouse clicked events.
     *
     * @param e the mouse event
     */
    public void MouseClicked(MouseEvent e) {
        int[] tabCoord = this.calculateHexCoord(e);

        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                try {
                    this.model.addTile(this.hexTilePreview.getModel(), tabCoord[0], tabCoord[1], false);
                    hexTilePreviewManager.roll();
                    this.hexTilePreview = this.hexTilePreviewManager.getFirstTile();
                    this.Window.revalidate();
                } catch (AlreadyBoundException | IndexOutOfBoundsException ex) {
                    handleException(ex);
                }
                if (this.model.allTilesPlaced()) {
                    handleAllTilesPlaced();
                }
                this.view.repaint();
                this.Window.revalidate();
                break;

            case MouseEvent.BUTTON3:
                this.hexTilePreview.getView().setHexRotation(this.hexTilePreview.getView().getHexRotation() + 1);
                if (this.model.CheckCanBePlaced(tabCoord[0], tabCoord[1])) {
                    this.view.repaintPrev(hexTilePreview.getView(), tabCoord[0], tabCoord[1]);
                }
                break;
        }
    }

    /**
     * Handles mouse moved events.
     *
     * @param e the mouse event
     */
    public void MouseMovedAction(MouseEvent e) {
        int[] tabCoord = this.calculateHexCoord(e);
        this.view.repaintPrev(hexTilePreview.getView(), tabCoord[0], tabCoord[1]);
    }

    /**
     * Handles exceptions that occur during mouse click events.
     *
     * @param ex the exception to handle
     */
    private void handleException(Exception ex) {
        if (ex instanceof IndexOutOfBoundsException) {
            this.inGame = false;
            HexButton button = new HexButton("Retourner à l'accueil", 18);
            button.addActionListener(new ReturnToMainFrameActionHandler(this.Gcontroller));
            this.view.add(button);
            this.view.repaint();
        }
    }

    /**
     * Handles the scenario when all tiles are placed.
     */
    private void handleAllTilesPlaced() {
        this.inGame = false;
        if (this.hexTilePreviewManager.getPartieEnCour() != null) {
            try (Connection DB = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/hochlaf", "hochlaf", "4472135955");
                 PreparedStatement pst = DB.prepareStatement("insert into LT_scores (ID_PreparedGames, score) values (?, ?)")) {
                pst.setInt(1, this.hexTilePreviewManager.getPartieEnCour());
                pst.setInt(2, this.model.getScore());
                pst.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Probleme de base de données");
            }
        }
    }
}