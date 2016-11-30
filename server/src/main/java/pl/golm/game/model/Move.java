package pl.golm.game.model;

/**
 * Created by Ktoś on 29.11.2016.
 */
public interface Move {
    Player getPlayer();

    void setPlayer(Player player);

    Field getField();

    void setField(Field field);
}
