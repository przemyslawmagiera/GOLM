package pl.golm.game.model.impl;

import pl.golm.game.mechanics.util.GameUtils;
import pl.golm.game.model.Field;
import pl.golm.game.model.Move;
import pl.golm.game.model.Player;
import java.util.List;

/**
 * Class implementation of Move interface
 * @author Dominik Lachowicz, Przemysław Magiera
 * @version 1.0
 */
public class MoveImpl implements Move
{
    private Player player;
    private Field field;
    private List<Field> killed;

    /**
     * constructor preparing instance to use
     * @param player player who places a stone
     * @param field filed on which a stone was placed, null if player passsed
     * @param killed list of killed stones (fields of where they where)
     */

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
