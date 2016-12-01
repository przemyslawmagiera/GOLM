package pl.golm.game.model.impl;

import pl.golm.game.model.Field;
import pl.golm.game.model.Player;

/**
 * Created by Ktoś on 29.11.2016.
 */
public class FieldImpl implements Field
{
    private int column;
    private int row;
    private Player player; // null - empty

    public FieldImpl(int column, int row)
    {
        setColumn(column);
        setRow(row);
    }

    public int getColumn()
    {
        return column;
    }

    public void setColumn(int column)
    {
        this.column = column;
    }

    public int getRow()
    {
        return row;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    @Override
    public String toString()
    {
        StringBuilder name_builder = new StringBuilder();
        name_builder.append((char) ('A' + column));
        name_builder.append(row + 1);
        return name_builder.toString();
    }

}

