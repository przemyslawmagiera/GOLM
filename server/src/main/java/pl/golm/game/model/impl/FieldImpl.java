package pl.golm.game.model.impl;

import pl.golm.game.model.Field;
import pl.golm.game.model.Player;

/**
 * Class implementation of Field interface
 * @author Dominik Lachowicz, Przemysław Magiera
 * @version 1.0
 */
public class FieldImpl implements Field
{
    private int column;
    private int row;
    private Player player; // null - empty

    /**
     * constructor preparing field to use
     * @param column column on board
     * @param row row on board
     */
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

