package pl.golm.game.model;

/**
 * Created by Ktoś on 29.11.2016.
 */
public interface Field {

    int getColumn();

    void setColumn(int column);

    int getRow();

    void setRow(int row);

    Player getPlayer();

    void setPlayer(Player player);
}
