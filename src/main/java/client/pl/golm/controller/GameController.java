package pl.golm.controller;


import pl.golm.communication.Client;
import pl.golm.communication.Player;
import pl.golm.communication.dto.GameDto;
import pl.golm.communication.dto.GameState;
import pl.golm.communication.parser.BasicOperationParser;
import pl.golm.controller.factory.DialogFactory;
import pl.golm.controller.factory.impl.DialogFactoryImpl;
import pl.golm.controller.factory.impl.ErrorDialogFactoryImpl;
import pl.golm.gui.*;
import pl.golm.gui.impl.ConfigurationWindowImpl;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Przemek on 04.12.2016.
 */
public class GameController implements Runnable
{
    private ConfigurationWindowImpl parentFrame;
    private MainWindow mainWindow;
    private Client client;
    private boolean yourTurn;
    private Player player;
    private static volatile GameController instance;
    private GameDto gameDto;
    private DeadGroupsWindow deadGroupsWindow;
    private TerritoriesWindow territoriesWindow;
    private static ConfigurationWindow configurationWindow = null;
    private DialogFactory dialogFactory;
    private DialogFactory errorDialogFactory;

    public static void main(String[] args)
    {
        configurationWindow = new ConfigurationWindowImpl();
    }

    public GameController()
    {
        dialogFactory = new DialogFactoryImpl();
        errorDialogFactory = new ErrorDialogFactoryImpl();
        yourTurn = true;
        player = new Player();
        player.setColor(PlayerColor.BLACK);
    }

    public void run()
    {
        configurationWindow = new ConfigurationWindowImpl();
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
        if (isYourTurn())
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
            } else if(!answer.equals("Opponent surrendered"))
            {
                errorDialogFactory.showMessageDialog(mainWindow, "Your move is illegal");
            }
        }
    }

    public void passRequest()
    {
        if (isYourTurn())
        {
            String message = "pass";
            List<String> msg = new ArrayList<>();
            msg.add(message);
            client.sendMessage(msg);
            message = client.readMessage();//legal move
            setYourTurn(false);
            String answer = client.readMessage();
            if (answer.contains("Fields"))
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
            } else if (answer.contains("Second"))
            {
                handleCountDeadGroups();
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
            dialogFactory.showMessageDialog(mainWindow, "Your turn");
        } else if (message.contains("Second"))
        {
            handleCountDeadGroups();
        }
    }

    public void surrender()
    {
        List<String> message = new ArrayList<>();
        message.add("surrender");
        client.sendMessage(message);
        dialogFactory.showMessageDialog(mainWindow, "You surrendered, you lost...");
        mainWindow.setVisible(false);
    }

    public void opponentSurrendered(String message)
    {
        dialogFactory.showMessageDialog(mainWindow, message);
        mainWindow.setVisible(false);
    }

    private void prepareDeadGroupsFrame()
    {
        mainWindow.setEnabled(false);
        client.readMessage(); //pick opponents
        client.readMessage();//suggested:
        gameDto.setGameState(GameState.COUNTING_DEAD_GROUPS);
        String answer = client.readMessage(); //first suggested or end
        deadGroupsWindow = new DeadGroupsWindow(gameDto);
        ArrayList<ArrayList<Circle>> circlesToCount = deadGroupsWindow.getBoard().getCircles();
        copyBoard(circlesToCount);
        while (!answer.contains("End"))
        {
            BasicOperationParser.prepareMappingForCounting(answer, circlesToCount);
            answer = client.readMessage();
        }
        deadGroupsWindow.getBoard().setCircles(circlesToCount);
    }

    private void waitForSelectingDeadGroups()
    {
        mainWindow.setEnabled(false);
        if (deadGroupsWindow == null || PlayerColor.BLACK.equals(gameDto.getPlayerColor()))//check if we are first time here
        {
            dialogFactory.showMessageDialog(mainWindow, "Please wait for opponent to select dead groups.");
        }
        String answer = client.readMessage();//opponent suggested or accepted
        if (answer.equals("agreed")) // you are probably white
        {
            gameDto.setGameState(GameState.ACCEPTING_DEAD_GROUPS);
            handleCountTerritories();
        } else
        {
            gameDto.setGameState(GameState.ACCEPTING_DEAD_GROUPS);
            deadGroupsWindow = new DeadGroupsWindow(gameDto);
            ArrayList<ArrayList<Circle>> circlesToAccept = deadGroupsWindow.getBoard().getCircles();
            answer = client.readMessage();
            copyBoard(circlesToAccept);
            while (!answer.contains("End"))
            {
                BasicOperationParser.prepareMappingForCounting(answer, circlesToAccept);
                answer = client.readMessage();
            }
        }
    }

    private void handleCountDeadGroups()
    {
        if (gameDto.getPlayerColor().equals(PlayerColor.BLACK))
        {
            prepareDeadGroupsFrame();
        } else
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    waitForSelectingDeadGroups();
                }
            }).start();
        }
    }

    public void acceptDeadGroups()
    {
        List<String> message = new ArrayList<>();
        message.add("true");
        client.sendMessage(message);
        if (gameDto.getPlayerColor().equals(PlayerColor.WHITE))
        {
            prepareDeadGroupsFrame();
        } else // you are black
        {
            if (client.readMessage().equals("agreed"))
            {
                gameDto.setGameState(GameState.COUNTING_DEAD_GROUPS);
                handleCountTerritories();
            }
        }
    }

    private void handleCountTerritories()
    {
        if (gameDto.getPlayerColor().equals(PlayerColor.BLACK))
        {
            prepareTerritoriesFrame();
        } else
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    waitForSelectingTerritories();
                }
            }).start();
        }
    }

    public void acceptTerritories()
    {
        List<String> message = new ArrayList<>();
        message.add("true");
        client.sendMessage(message);
        if (gameDto.getPlayerColor().equals(PlayerColor.WHITE))
        {
            prepareTerritoriesFrame();
        } else // you are black
        {
            if (client.readMessage().equals("agreed"))
            {
                endGame();
            }
        }
    }

    private void waitForSelectingTerritories()
    {
        mainWindow.setEnabled(false);
        if (territoriesWindow == null || PlayerColor.BLACK.equals(gameDto.getPlayerColor()))
        {
            dialogFactory.showMessageDialog(mainWindow, "Please wait for opponent to suggest territories.");
        }
        String answer = client.readMessage();//opponent suggested or accepted
        if (answer.equals("agreed")) // you are probably white
        {
            endGame();
        } else
        {
            gameDto.setGameState(GameState.ACCEPTING_DEAD_GROUPS);
            territoriesWindow = new TerritoriesWindow(gameDto);
            ArrayList<ArrayList<Circle>> circlesToAccept = territoriesWindow.getBoard().getCircles();
            answer = client.readMessage();
            copyBoard(circlesToAccept);
            while (!answer.contains("End"))
            {
                BasicOperationParser.prepareMappingForCounting(answer, circlesToAccept);
                answer = client.readMessage();
            }
        }
    }

    private void endGame()
    {
        dialogFactory.showMessageDialog(mainWindow, client.readMessage() + '\n' + client.readMessage() + '\n' + client.readMessage());
        mainWindow.dispatchEvent(new WindowEvent(mainWindow, WindowEvent.WINDOW_CLOSING));
        ((ConfigurationWindowImpl) configurationWindow).dispatchEvent(
                new WindowEvent((ConfigurationWindowImpl) configurationWindow, WindowEvent.WINDOW_CLOSING));

    }

    private void prepareTerritoriesFrame()
    {
        mainWindow.setEnabled(false);
        client.readMessage(); //pick opponents
        client.readMessage();//suggested:
        gameDto.setGameState(GameState.COUNTING_DEAD_GROUPS); // i didnt change that becouse what for
        String answer = client.readMessage(); //first suggested or end
        territoriesWindow = new TerritoriesWindow(gameDto);
        ArrayList<ArrayList<Circle>> circlesToCount = territoriesWindow.getBoard().getCircles();
        copyBoard(circlesToCount);
        while (!answer.contains("End"))
        {
            BasicOperationParser.prepareMappingForCounting(answer, circlesToCount);
            answer = client.readMessage();
        }
        territoriesWindow.getBoard().setCircles(circlesToCount);
    }

    public void declineTerritories()
    {
        List<String> message = new ArrayList<>();
        message.add("false");
        client.sendMessage(message);
        mainWindow.setEnabled(true);
        gameDto.setGameState(GameState.RUNNING);
        setYourTurn(true);
    }

    public void declineDeadGroups()
    {
        List<String> message = new ArrayList<>();
        message.add("false");
        client.sendMessage(message);
        mainWindow.setEnabled(true);
        gameDto.setGameState(GameState.RUNNING);
        setYourTurn(true);
    }

    public void requestDeadGroups()
    {
        List<String> messages = BasicOperationParser.prepareCountedTerritoriesMessage(deadGroupsWindow.getBoard().getCircles(), gameDto.getSize());
        client.sendMessage(messages);
        deadGroupsWindow.setVisible(false);
        dialogFactory.showMessageDialog(mainWindow, "Wait for acceptance");
        String message = client.readMessage();
        if (message.equals("true"))
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    waitForSelectingDeadGroups();
                }
            }).start();
        } else if (message.equals("false"))
        {
            mainWindow.setEnabled(true);
            gameDto.setGameState(GameState.RUNNING);
            setYourTurn(false);
            dialogFactory.showMessageDialog(mainWindow, "Opponent declined your dead groups request, his turn.");
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

    public void requestTerritories()
    {
        List<String> messages = BasicOperationParser.prepareCountedTerritoriesMessageWhichIsIndeedTerritories(territoriesWindow.getBoard().getCircles(), gameDto.getSize());
        client.sendMessage(messages);
        territoriesWindow.setVisible(false);
        dialogFactory.showMessageDialog(mainWindow, "Wait for acceptance");
        String message = client.readMessage();
        if (message.equals("true"))
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    waitForSelectingTerritories();
                }
            }).start();
        } else if (message.equals("false"))
        {
            mainWindow.setEnabled(true);
            gameDto.setGameState(GameState.RUNNING);
            setYourTurn(false);
            dialogFactory.showMessageDialog(mainWindow, "Opponent declined your territories request, his turn.");
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

    private void copyBoard(ArrayList<ArrayList<Circle>> circlesToCount)
    {
        for (int i = 0; i < mainWindow.getBoard().getOption(); i++)
        {
            for (int j = 0; j < mainWindow.getBoard().getOption(); j++)
            {//get y, get x
                if (mainWindow.getBoard().getCircles().get(j).get(i).isOccupied())
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
        if (client.readMessage().contains("White"))
        {
            gameDto.setGameState(GameState.RUNNING);
            gameDto.setPlayerColor(PlayerColor.WHITE);
            setYourTurn(false);
            gameDto.setOpponentName(client.readMessage());
            startGame(gameDto);
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    waitForOpponent();
                }
            }).start();

        } else
        {
            gameDto.setGameState(GameState.RUNNING);
            gameDto.setPlayerColor(PlayerColor.BLACK);
            setYourTurn(true);
            gameDto.setOpponentName(client.readMessage());
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

    public void setParentFrame(ConfigurationWindowImpl parentFrame)
    {
        this.parentFrame = parentFrame;
    }
}
