package pl.golm.game.model.impl;

import pl.golm.game.model.Field;
import pl.golm.game.model.Move;
import pl.golm.game.model.Player;
import java.util.List;

/**
 * Created by Ktoś on 29.11.2016.
 */
public class MoveImpl implements Move
{
    private Player player;
    private Field field;
    private List<Field> killed;

    public MoveImpl(Player player, Field field, List<Field> killed)
    {
        setField(field);
        setPlayer(player);
        setKilled(killed);
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public Field getField()
    {
        return field;
    }

    public void setField(Field field)
    {
        this.field = field;
    }

    public List<Field> getKilled()
    {
        return killed;
    }

    public void setKilled(List<Field> killed)
    {
        this.killed = killed;
    }
}
