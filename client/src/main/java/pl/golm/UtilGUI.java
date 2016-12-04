package pl.golm;

import pl.golm.controller.GameController;
import pl.golm.gui.Circle;
import pl.golm.gui.MainWindow;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Przemek on 30.11.2016.
 */
public class UtilGUI
{
    public static final String APPLICATION_NAME = "GO - eine fantastische chinese spiel";
    public static final Integer APPLICATION_WIDTH = 600;
    public static final Integer APPLICATION_HEIGHT = 800;
    public static final Color APPLICATION_BACKGROUND = new Color(255, 204, 153);
    public static final Color BOARD_BACKGROUND = new Color(204, 153, 0);
    public static final Integer PANEL_PLAYERS_INFO_HEIGHT = 50;
    public static final Integer PANEL_PLAYER_HEIGHT = 130;
    public static final Double DEFAULT_CIRCLE_SIZE = 10.0;

    public static void main(String[] args)
    {
        GameController controller = GameController.getInstance();
        controller.init();
    }



}
