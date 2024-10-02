package server;

import java.awt.*;

public class CommonConstants {
    // File paths
    public static final String DATA_PATH = "/resources/data.txt";
    public static final String IMAGE_PATH = "/resources/1.png";
    public static final String FONT_PATH = "/resources/Cartoonero.ttf";

    // Color config
    public static final Color PRIMARY_COLOR = Color.LIGHT_GRAY;
    public static final Color SECONDARY_COLOR = Color.BLACK;
    public static final Color BACKGROUND_COLOR = Color.decode("#101820");

    // Size config
    public static final Dimension FRAME_SIZE = new Dimension(550, 760);
    public static final Dimension BUTTON_PANEL_SIZE = new Dimension(FRAME_SIZE.width, (int)(FRAME_SIZE.height * 0.42));
    public static final Dimension RESULT_DIALOG_SIZE = new Dimension((int)(FRAME_SIZE.width / 2), (int)(FRAME_SIZE.height / 6));
}