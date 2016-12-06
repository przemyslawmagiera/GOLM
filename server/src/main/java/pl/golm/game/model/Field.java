package pl.golm.game.model;

/**
 * interface of functions that every Field implementation must have
 * @author Dominik Lachowicz, Przemysław Magiera
 * @version 1.0
 */
public interface Field
{

    int getColumn();

    void setColumn(int column);

    int getRow();

    void setRow(int row);

    Player getPlayer();

    void setPlayer(Player player);
}
