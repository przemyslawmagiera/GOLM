package pl.golm.controller;


import pl.golm.gui.Player;
import pl.golm.gui.*;

import java.awt.*;

/**
 * Created by Przemek on 04.12.2016.
 */
public class GameController
{
    private BoardPanel boardPanel;
    private PlayerInfoPanel playerInfoPanel;
    private PlayerPanel playerPanel;
    private MainWindow mainWindow;
    private boolean yourTurn;
    private Player player;
    private static volatile GameController instance;

    public GameController()
    {
        yourTurn = true;
        player = new Player();
        player.setColor(PlayerColor.BLACK);
    }

    public void init()
    {
        this.mainWindow = new MainWindow(null);
    }

    public boolean isYourTurn()
    {
        return yourTurn;
    }

    public void setYourTurn(boolean yourTurn)
    {
        this.yourTurn = yourTurn;
    }

    public static GameController getInstance()
    {
        if (instance == null)
        {
            if (instance == null)
            {
                instance = new GameController();
            }
        }
        return instance;
    }

    public void moveRequest(int x, int y)
    {
        System.out.println("wysylam do serwera zapytanie o ruch x:" + x + " y: " + y);
        Circle actual = mainWindow.getBoard().getCircles().get(y).get(x);
        if(player.getColor().equals(PlayerColor.BLACK))
        {
            actual.setColor(Color.BLACK);
            actual.setOccupied(true);
        }
        else
        {
            actual.setColor(Color.WHITE);
            actual.setOccupied(true);
        }

        //TODO send request
    }

    public BoardPanel getBoardPanel()
    {
        return mainWindow.getBoard();
    }
}
