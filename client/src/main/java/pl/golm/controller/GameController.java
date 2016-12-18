package pl.golm.controller;


import pl.golm.communication.Client;
import pl.golm.communication.dto.GameDto;
import pl.golm.communication.dto.GameState;
import pl.golm.communication.parser.BasicOperationParser;
import pl.golm.communication.Player;
import pl.golm.gui.*;
import pl.golm.gui.Circle;

import javax.swing.*;
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
    private GameDto gameDto;
    private CountTerritoriesWindow countTerritoriesWindow;

    public GameController()
    {
        yourTurn = true;
        player = new Player();
        player.setColor(PlayerColor.BLACK);
    }

    public void initMainWindow(GameDto gameDto)
    {
        this.gameDto = gameDto;
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
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        waitForOpponent();
                    }
                }).start();
            }
        }
    }

    public void passRequest()
    {
        if(isYourTurn())
        {
            String message = "pass";
            List<String> msg = new ArrayList<>();
            msg.add(message);
            client.sendMessage(msg);
            message = client.readMessage();//legal move
            setYourTurn(false);
            String answer = client.readMessage();
            if(answer.contains("Fields"))
            {
                message = client.readMessage();
                clearCircles();
                while (!message.equals("End fields"))
                {
                    BasicOperationParser.parseMappingToCircles(message, mainWindow.getBoard().getCircles());
                    message = client.readMessage();
                }
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        waitForOpponent();
                    }
                }).start();
            }
            else if(answer.contains("Second"))
            {
                handleCountTerritories();
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
        String message = client.readMessage();
        if (message.contains("Fields"))
        {
            clearCircles();
            String answer = client.readMessage();
            while (!answer.equals("End fields"))
            {
                BasicOperationParser.parseMappingToCircles(answer, mainWindow.getBoard().getCircles());
                answer = client.readMessage();
            }
            mainWindow.repaint();
            setYourTurn(true);
        }
        else if(message.contains("Second"))
        {
            handleCountTerritories();
        }
    }

    private void handleCountTerritories()
    {
        if(gameDto.getPlayerColor().equals(PlayerColor.BLACK))
        {
            mainWindow.setEnabled(false);
            client.readMessage(); //pick opponents
            client.readMessage();//suggested:
            gameDto.setGameState(GameState.COUNTING_TERRITORIES);
            String answer = client.readMessage(); //first suggested or end
            countTerritoriesWindow = new CountTerritoriesWindow(gameDto);
            ArrayList<ArrayList<Circle>> circlesToCount = countTerritoriesWindow.getBoard().getCircles();
            copyBoard(circlesToCount);
            while (!answer.contains("End"))
            {
                BasicOperationParser.prepareMappingForCounting(answer,circlesToCount);
                answer = client.readMessage();
            }
            countTerritoriesWindow.getBoard().setCircles(circlesToCount);
        }
        else
        {
            JOptionPane.showMessageDialog(mainWindow, "Please wait for opponent to select dead groups...");

            while (!answer.contains("End"))
            {
                BasicOperationParser.prepareMappingForCounting(answer,circlesToCount);
                answer = client.readMessage();
            }
        }
    }
    
    public void requestDeadTerritories()
    {
        List<String> messages = BasicOperationParser.prepareCountedTerritoriesMessage(countTerritoriesWindow.getBoard().getCircles(), gameDto.getSize());
        client.sendMessage(messages);
        //// TODO: 18.12.2016 false/true
    }

    private void copyBoard(ArrayList<ArrayList<Circle>> circlesToCount)
    {
        for (int i = 0; i < mainWindow.getBoard().getOption(); i++)
        {
            for (int j = 0; j < mainWindow.getBoard().getOption(); j++)
            {//get y, get x
                if(mainWindow.getBoard().getCircles().get(j).get(i).isOccupied())
                {
                    circlesToCount.get(j).get(i).setOccupied(true);
                    circlesToCount.get(j).get(i).setColor(mainWindow.getBoard().getCircles().get(j).get(i).getColor());
                }
            }
        }
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
            gameDto.setGameState(GameState.RUNNING);
            gameDto.setPlayerColor(PlayerColor.WHITE);
            setYourTurn(false);
            startGame(gameDto);
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    waitForOpponent();
                }
            }).start();

        }
        else
        {
            gameDto.setGameState(GameState.RUNNING);
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
