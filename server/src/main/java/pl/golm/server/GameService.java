package pl.golm.server;

import pl.golm.game.GameSettings;

/**
 * Created by Przemek on 04.12.2016.
 */
public class GameService implements Runnable
{
    ClientSettings playe1Settings;
    ClientSettings playe2Settings;
    GameSettings gameSettings;

    public GameService(ClientSettings playe1Settings, ClientSettings playe2Settings, GameSettings gameSettings)
    {
        setPlaye1Settings(playe1Settings);
        setPlaye2Settings(playe2Settings);
        setGameSettings(gameSettings);
    }

    @Override
    public void run()
    {
        //TODO implement ;)
    }

    public ClientSettings getPlaye1Settings()
    {
        return playe1Settings;
    }

    public void setPlaye1Settings(ClientSettings playe1Settings)
    {
        this.playe1Settings = playe1Settings;
    }

    public ClientSettings getPlaye2Settings()
    {
        return playe2Settings;
    }

    public void setPlaye2Settings(ClientSettings playe2Settings)
    {
        this.playe2Settings = playe2Settings;
    }

    public GameSettings getGameSettings()
    {
        return gameSettings;
    }

    public void setGameSettings(GameSettings gameSettings)
    {
        this.gameSettings = gameSettings;
    }
}
