package pl.golm.controller;


import pl.golm.communication.Client;
import pl.golm.communication.dto.GameDto;
import pl.golm.communication.parser.BasicOperationParser;
import pl.golm.communication.Player;
import pl.golm.gui.*;
import pl.golm.gui.Circle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Przemek on 04.12.2016.
 */
public class GameController
{
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
        if(isYourTurn())
        {
            String answer = null;
            String message = x + "," + y;
            List<String> messages = new ArrayList<>();
            messages.add(message);
            client.sendMessage(messages);
            answer = client.readMessage();
            if (answer.equals("Legal move"))
            {
                client.readMessage();//"fields" override
                answer = client.readMessage();
                clearCircles();
                while (!answer.equals("End fields"))
                {
                    BasicOperationParser.parseMappingToCircles(answer, mainWindow.getBoard().getCircles());
                    answer = client.readMessage();
                }
                setYourTurn(false);
                waitForOpponent();
            }
        }
    }

    private void clearCircles()
    {
        for (int i = 0; i < mainWindow.getBoard().getOption(); i++)
        {
            for (int j = 0; j < mainWindow.getBoard().getOption(); j++)
            {//get y, get x
                mainWindow.getBoard().getCircles().get(j).get(i).setOccupied(false);
            }
        }
    }

    public void waitForOpponent()
    {
        clearCircles();
        client.readMessage(); //"fields" override
        String answer = client.readMessage();
        while (!answer.equals("End fields"))
        {
            BasicOperationParser.parseMappingToCircles(answer, mainWindow.getBoard().getCircles());
            answer = client.readMessage();
        }
        mainWindow.repaint();
        setYourTurn(true);
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
            setYourTurn(false);
            startGame(gameDto);
            waitForOpponent();
        }
        else
        {
            gameDto.setPlayerColor(PlayerColor.BLACK);
            setYourTurn(true);
            startGame(gameDto);
        }

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
