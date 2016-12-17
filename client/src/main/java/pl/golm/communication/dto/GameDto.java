package pl.golm.communication.dto;

import pl.golm.gui.PlayerColor;

/**
 * Created by Przemek on 30.11.2016.
 */
public class GameDto
{
    private Integer size;
    private String type;
    private String playerName;
    private String opponentName;
    private PlayerColor playerColor;

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
}
