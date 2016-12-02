package pl.golm;

import pl.golm.gui.MainWindow;

import java.awt.*;

/**
 * Created by Przemek on 30.11.2016.
 */
public class Main
{
    public static final String APPLICATION_NAME = "GO - eine fantastische chinese spiel";
    public static final Integer APPLICATION_WIDTH = 600;
    public static final Integer APPLICATION_HEIGHT = 800;
    public static final Color APPLICATION_BACKGROUND = new Color(255, 204, 153);
    public static final Color BOARD_BACKGROUND = new Color(204, 153, 0);
    public static final Integer PANEL_PLAYERS_INFO_HEIGHT = 50;
    public static final Integer PANEL_PLAYER_HEIGHT = 130;

    public static void main(String[] args)
    {
        MainWindow mainWindow = new MainWindow(null);
    }

}
