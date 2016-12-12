package pl.golm.bot;

import pl.golm.game.GameSettings;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * Created by Dominik on 2016-12-12.
 */
public interface Bot extends Runnable
{
    @Override
    public void run();

    public BufferedReader getReader();

    public void setReader(BufferedReader reader);

    public BufferedWriter getWriter();

    public void setWriter(BufferedWriter writer);

    public GameSettings getGameSettings();

    public void setGameSettings(GameSettings gameSettings);
}
