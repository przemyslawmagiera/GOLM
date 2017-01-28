package client.pl.golm.communication.dto;

import client.pl.golm.gui.PlayerColor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Przemek on 30.11.2016.
 */
@Component
public class GameDto
{
    private Integer size;
    private String type;
    private String playerName;
    private String opponentName;
    private PlayerColor playerColor;
    private GameState gameState;


    public PlayerColor getPlayerColor()
    {
        return playerColor;
    }

    public void setPlayerColor(PlayerColor playerColor)
    {
        this.playerColor = playerColor;
    }

    public String getOpponentName()
    {
        return opponentName;
    }

    public void setOpponentName(String opponentName)
    {
        this.opponentName = opponentName;
    }

    public Integer getSize()
    {
        return size;
    }

    public void setSize(Integer size)
    {
        this.size = size;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;
    }
}