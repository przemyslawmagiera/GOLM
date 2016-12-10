package pl.golm.server;

import pl.golm.game.Game;
import pl.golm.game.GameSettings;
import pl.golm.game.GameState;
import pl.golm.game.Result;
import pl.golm.game.mechanics.util.GameUtils;
import pl.golm.game.model.impl.MoveImpl;

import java.io.IOException;

/**
 * Created by Przemek on 04.12.2016.
 */
public class GameService implements Runnable
{
    private ClientSettings client1Settings;
    private GameSettings game1Settings;
    private ClientSettings client2Settings;
    private GameSettings game2Settings;
    private SurrenderListener surrenderListener;
    private Game game;

    public GameService(ClientSettings client1Settings, GameSettings game1Settings, ClientSettings client2Settings, GameSettings game2Settings)
    {
        setClient1Settings(client1Settings);
        setGame1Settings(game1Settings);
        setClient2Settings(client2Settings);
        setGame2Settings(game2Settings);
        setGame(new Game(game1Settings.getBoardSize(), game1Settings.getPlayerName(), game2Settings.getPlayerName()));
        setSurrenderListener(new SurrenderListener(getGame(), client1Settings.getBufferedReader(), client2Settings.getBufferedReader(), 2));
    }

    @Override
    public void run()
    {
        try
        {
            //SENDING INFO ABOUT GAME START
            game.setGameState(GameState.RUNNING);
            client1Settings.getBufferedWriter().write("Game started. You are player 1. Black");
            client2Settings.getBufferedWriter().write("Game started. You are player 2. White");
            // STARTING LISTENING IF SOMEONE HAS SURRENDERED
            getSurrenderListener().run();
            // WHILE GAME IS IN PLAYING PHASE
            while (game.getGameState().equals(GameState.RUNNING))
            {
                int lastMoveX, lastMoveY, currMoveX, currMoveY;
                // WHILE YOU DONT GET LEGAL MOVE FROM BLACK ASK HIM FOR IT
                while (!getNextMove());
                // SEND OPPONENTS MOVE TO PLAYER AND GET HIS MOVE
                while(game.getGameState().equals(GameState.RUNNING))
                {
                    if (surrenderListener.getMode() == 1) // it was white's move. Now it will be black's
                    {
                        surrenderListener.setMode(2);
                        sendLastMove();
                        while(!getNextMove());
                    }
                    else if (surrenderListener.getMode() == 2) // it was black's move. now white
                    {
                        surrenderListener.setMode(1);
                        sendLastMove();
                        while(!getNextMove());
                    }
                }
            }
            // both passed or someone surrendered.
            //So while game is in counting terrirtories state
            while (game.getGameState().equals(GameState.COUNTING_TERRITORIES))
            {
                //TODO implement
            }
            //damn it it can go back from here to running again so make it in another function.

            //END OF GAME
            //send info to players.
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    public ClientSettings getClient1Settings()
    {
        return client1Settings;
    }

    public void setClient1Settings(ClientSettings client1Settings)
    {
        this.client1Settings = client1Settings;
    }

    public GameSettings getGame1Settings()
    {
        return game1Settings;
    }

    public void setGame1Settings(GameSettings game1Settings)
    {
        this.game1Settings = game1Settings;
    }

    public ClientSettings getClient2Settings()
    {
        return client2Settings;
    }

    public void setClient2Settings(ClientSettings client2Settings)
    {
        this.client2Settings = client2Settings;
    }

    public GameSettings getGame2Settings()
    {
        return game2Settings;
    }

    public void setGame2Settings(GameSettings game12Settings)
    {
        this.game2Settings = game12Settings;
    }

    public SurrenderListener getSurrenderListener()
    {
        return surrenderListener;
    }

    public void setSurrenderListener(SurrenderListener surrenderListener)
    {
        this.surrenderListener = surrenderListener;
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    private boolean getNextMove () // true on success false on illegal move
    {
        return false; // TODO implement
    }
    private void sendLastMove()
    {
        //TODO implement
    }
}
