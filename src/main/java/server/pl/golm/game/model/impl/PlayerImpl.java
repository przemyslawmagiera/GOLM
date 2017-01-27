package server.pl.golm.game.model.impl;

import server.pl.golm.game.model.Player;
import server.pl.golm.game.model.PlayerColor;

/**
 * Class implementation of Player interface
 * @author Dominik Lachowicz, Przemysław Magiera
 * @version 1.0
 */
public class PlayerImpl implements Player
{
    private int prisonerAmount;
    private int territoryAmount;
    private PlayerColor color;
    private String name;

    /**
     * constructor preparing instance to use
     * @param name player's name
     * @param color player's colour (black or white)
     */
    public PlayerImpl(String name, PlayerColor color)
    {
        setPrisonerAmount(0);
        setTerritoryAmount(0);
        setColor(color);
        setName(name);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPrisonerAmount()
    {
        return prisonerAmount;
    }

    public void setPrisonerAmount(int prisonerAmount)
    {
        this.prisonerAmount = prisonerAmount;
    }

    public int getTerritoryAmount()
    {
        return territoryAmount;
    }

    public void setTerritoryAmount(int territoryAmount)
    {
        this.territoryAmount = territoryAmount;
    }

    public PlayerColor getColor()
    {
        return color;
    }

    public void setColor(PlayerColor color)
    {
        this.color = color;
    }
}
