package pl.golm.game.model.impl;

import pl.golm.game.model.Field;
import pl.golm.game.model.Move;
import pl.golm.game.model.Player;

/**
 * Created by Ktoś on 29.11.2016.
 */
public class MoveImpl implements Move
{
    private Player player;
    private Field field;

    public MoveImpl(Player player, Field field)
    {
        setField(field);
        setPlayer(player);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
