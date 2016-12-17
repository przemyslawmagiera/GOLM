package pl.golm.controller;


import pl.golm.communication.Client;
import pl.golm.communication.dto.GameDto;
import pl.golm.communication.parser.BasicOperationParser;
import pl.golm.communication.Player;
import pl.golm.gui.*;
import pl.golm.gui.Circle;

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
    private Client client;
    private boolean yourTurn;
    private Player player;
    private static volatile GameController instance;

    public GameController()
    {
        yourTurn = true;
        player = new Player();
        player.setColor(PlayerColor.BLACK);
    }

    public void initMainWindow(GameDto gameDto)
    {
        this.mainWindow = new MainWindow(gameDto);
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
        Circle actual = mainWindow.getBoard().getCircles().get(y).get(x);
        if (player.getColor().equals(PlayerColor.BLACK))
        {
            actual.setColor(Color.BLACK);
            actual.setOccupied(true);
        } else
        {
            actual.setColor(Color.WHITE);
            actual.setOccupied(true);
        }
        //TODO send request
    }

    public void requestGame(GameDto gameDto)
    {
        client = new Client();
        try
        {
            client.configure();
            client.sendMessage(BasicOperationParser.parseRequestGame(gameDto));
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        if(client.readMessage().contains("White"))
        {
            gameDto.setPlayerColor(PlayerColor.WHITE);
        }
        else
        {
            gameDto.setPlayerColor(PlayerColor.BLACK);
        }
        startGame(gameDto);
    }

    public void startGame(GameDto gameDto)
    {
        initMainWindow(gameDto);
    }

    public BoardPanel getBoardPanel()
    {
        return mainWindow.getBoard();
    }
}
