package pl.golm.game.model.impl;

import pl.golm.game.model.Player;
import pl.golm.game.model.PlayerColor;

/**
 * Created by Ktoś on 29.11.2016.
 */
public class PlayerImpl implements Player {
    private int prisonerAmount;
    private int territoryAmount;
    private PlayerColor color;

    public PlayerImpl(PlayerColor color) {
        setPrisonerAmount(0);
        setTerritoryAmount(0);
        setColor(color);
    }

//        public int count_points()
//        {
//            return territories + prisoners;
//        }


    public int getPrisonerAmount() {
        return prisonerAmount;
    }

    public void setPrisonerAmount(int prisonerAmount) {
        this.prisonerAmount = prisonerAmount;
    }

    public int getTerritoryAmount() {
        return territoryAmount;
    }

    public void setTerritoryAmount(int territoryAmount) {
        this.territoryAmount = territoryAmount;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }
}