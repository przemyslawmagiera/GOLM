package server.pl.golm.game.model;

/**
 * interface of functions that every Player implementation must have
 * @author Dominik Lachowicz, Przemysław Magiera
 * @version 1.0
 */
public interface Player
{

    String getName();

    void setName(String name);

    int getPrisonerAmount();

    void setPrisonerAmount(int prisonerAmount);

    int getTerritoryAmount();

    void setTerritoryAmount(int territoryAmount);

    PlayerColor getColor();

    void setColor(PlayerColor color);
}
