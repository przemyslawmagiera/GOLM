package pl.golm.server;

import pl.golm.game.Game;
import pl.golm.game.GameState;
import pl.golm.game.Result;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Created by Dominik on 2016-12-10.
 */
public class SurrenderListener implements Runnable
{
    private Game game;
    private BufferedReader player1reader;
    private BufferedReader player2reader;
    private PrintWriter player1writer;
    private PrintWriter player2writer;
    private int mode; // 1 or 2

    public SurrenderListener(Game game, BufferedReader player1reader, PrintWriter player1writer, BufferedReader player2reader, PrintWriter player2writer, int mode)
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
                    synchronized(this)
                    {
                        if (player1reader.readLine().equals("surrender"))
                        {
                            game.setResult(Result.PLAYER2_WON);
                            game.setGameState(GameState.FINISHED);
                            player1writer.println("You surrendered");
                            player2writer.println("Opponent surrendered");
                            setMode(0);
                        }
                    }
                }
                else if (mode == 2 && player2reader.ready())
                {
                    synchronized(this)
                    {
                        if (player2reader.readLine().equals("surrender"))
                        {
                            game.setResult(Result.PLAYER1_WON);
                            game.setGameState(GameState.FINISHED);
                            player2writer.println("You surrendered");
                            player1writer.println("Opponent surrendered");
                            setMode(0);

                        }
                    }
                }
            }
            catch (Exception Exception)
            {
                Exception.printStackTrace();
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

    public PrintWriter getPlayer1writer()
    {
        return player1writer;
    }

    public void setPlayer1writer(PrintWriter player1writer)
    {
        this.player1writer = player1writer;
    }

    public PrintWriter getPlayer2writer()
    {
        return player2writer;
    }

    public void setPlayer2writer(PrintWriter player2writer)
    {
        this.player2writer = player2writer;
    }
}
