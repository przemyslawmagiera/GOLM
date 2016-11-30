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
    public static final Color APPLICATION_BACKGROUND = Color.ORANGE;
    public static final Integer PANEL_PLAYERS_INFO_HEIGHT = 50;
    public static final Integer PANEL_PLAYER_HEIGHT = 150;

    public static void main(String[] args)
    {
        MainWindow mainWindow = new MainWindow(null);
    }

}
