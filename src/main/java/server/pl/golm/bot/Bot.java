package server.pl.golm.bot;

import server.pl.golm.game.GameSettings;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Created by Dominik on 2016-12-12.
 */
public interface Bot extends Runnable
{
    @Override
    public void run();

    public BufferedReader getReader();

    public void setReader(BufferedReader reader);

    public PrintWriter getWriter();

    public void setWriter(PrintWriter writer);

    public GameSettings getGameSettings();

    public void setGameSettings(GameSettings gameSettings);
}
