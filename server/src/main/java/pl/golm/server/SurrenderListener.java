package pl.golm.server;

import pl.golm.game.Game;
import pl.golm.game.GameState;
import pl.golm.game.Result;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Dominik on 2016-12-10.
 */
public class SurrenderListener implements Runnable
{
    private Game game;
    private BufferedReader player1reader;
    private BufferedReader player2reader;
    private int mode; // 1 or 2

    public SurrenderListener(Game game, BufferedReader player1reader, BufferedReader player2reader, int mode)
    {
        setGame(game);
        setPlayer1reader(player1reader);
        setPlayer2reader(player2reader);
        setMode(mode);
    }

    @Override
    public void run()
    {
        while(mode == 1 || mode == 2)
        {
            try
            {
                if (mode == 1 && player1reader.ready())
                {
                    if (player1reader.readLine().equals("surrender"))
                    {
                        synchronized(this)
                        {
                            game.setResult(Result.PLAYER2_WON);
                            game.setGameState(GameState.FINISHED);
                            setMode(0);
                        }
                    }
                }
                else if (mode == 2 && player2reader.ready())
                {
                    if (player2reader.readLine().equals("surrender"))
                    {
                        synchronized(this)
                        {
                            game.setResult(Result.PLAYER1_WON);
                            game.setGameState(GameState.FINISHED);
                            setMode(0);
                        }
                    }
                }
            }
            catch (IOException ioException)
            {
                ioException.printStackTrace();
            }
        }
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    public BufferedReader getPlayer1reader()
    {
        return player1reader;
    }

    public void setPlayer1reader(BufferedReader player1reader)
    {
        this.player1reader = player1reader;
    }

    public BufferedReader getPlayer2reader()
    {
        return player2reader;
    }

    public void setPlayer2reader(BufferedReader player2reader)
    {
        this.player2reader = player2reader;
    }

    public int getMode()
    {
        return mode;
    }

    public void setMode(int mode)
    {
        this.mode = mode;
    }
}
