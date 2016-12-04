package pl.golm.game.model;

/**
 * Created by Ktoś on 29.11.2016.
 */
public interface Player
{

    int getPrisonerAmount();

    void setPrisonerAmount(int prisonerAmount);

    int getTerritoryAmount();

    void setTerritoryAmount(int territoryAmount);

    PlayerColor getColor();

    void setColor(PlayerColor color);
}
