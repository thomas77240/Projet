package SAE31_2024.toolbox;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * The ToolBox class provides constants used for defining the properties of hexagonal tiles.
 * This class is intended to be a utility class and therefore cannot be instantiated.
 */
public class ToolBox {

    // Frame positioning
    public static final int MARGIN_LEFT_TOP_MENU = 10;

    // Logo
    public static final ImageIcon logoLofi = new ImageIcon(loadFile("res/Lofi.png"));
    public static final ImageIcon logoLofix128 = new ImageIcon(loadFile("res/Lofix128.png"));
    public static final ImageIcon logoLofix96 = new ImageIcon(loadFile("res/Lofix96.png"));
    public static final ImageIcon logoLofix64 = new ImageIcon(loadFile("res/Lofix64.png"));
    public static final ImageIcon logoLofix52 = new ImageIcon(loadFile("res/Lofix52.png"));

    // Colors
    public static final Color[] BiomesColors = {
        new Color(119, 198, 119),   // Plains
        new Color(20, 119, 69),     // Forest
        new Color(235, 222, 33),    // Desert
        new Color(30, 142, 216),    // Sea
        new Color(119, 119, 119)    // Mountains
    };
    public static final Color[] BiomesColors2 = {
        new Color(139, 218, 139),   // Plains
        new Color(40, 139, 89),     // Forest
        new Color(218, 203, 0),     // Desert
        new Color(63, 168, 243),    // Sea
        new Color(139, 139, 139)    // Mountains
    };
    public static final Color FG_COLOR1 = new Color(148, 83, 4);
    public static final Color BG_COLOR1 = new Color(188, 119, 41);
    public static final Color BG_COLOR_2 = new Color(0, 126, 133);
    public static final Color FG_COLOR_2 = new Color(0, 185, 197, 150);
    public static final Color BG_COLOR_3 = new Color(121, 86, 118);
    public static final Color FG_COLOR_3 = new Color(188, 117, 161, 255);
    public static final Color WINDOW_BG = new Color(236, 236, 236);

    // Tiles
    public static final int NB_BIOMES = 5;
    public static final int NB_SIDES = 6;
    public static final int TILE_SIZE = 50;
    public static final int xOfset = 152;
    public static final int yOfset = -45;
    public static final int COLIDE_AJUST = 30;

    // Zoom
    public static final double ZOOM_POWER = 0.02;
    public static final int ZOOM_SPEED = 20;
    public static final int ZOOM_TIME_FRAME = 150;
    public static final double ZOOM_BORNE_MAX = 5;
    public static final double ZOOM_BORNE_MIN = 0.17;

    // Overlay
    public static final Color OverlayBorderColor = new Color(177, 103, 21, 205);
    public static final Color OverlayBGAlphaColor = new Color(234, 150, 39, 150);
    public static final Color OverlayBGFullColor = new Color(234, 185, 120);
    public static final int TOP_MENU_HEIGHT = 108;
    public static final int PREVIEW_WIDTH = 400;
    public static final int PREVIEW_HEIGHT = 110;
    public static final int PREVIEW_NB_TILES = 6;

    // Fonts
    public static final String FONT_NAME = "SansSerif";
    public static final Color FONT_COLOR1 = new Color(149, 84, 4);
    public static final int LAUNCH_MENU_WIDTH = 400;

    // Button edges
    public static final int CORNER_EDGE_SIZE = 5;

    /**
     * Private constructor to prevent instantiation of the ToolBox class.
     */
    private ToolBox() {
        throw new UnsupportedOperationException("ToolBox is a static class that cannot be instantiated.");
    }

    /**
     * Loads a file from the specified address.
     *
     * @param address the address of the file to load
     * @return the URL of the loaded file
     */
    public static URL loadFile(String address) {
        return Thread.currentThread().getContextClassLoader().getResource(address);
    }
}