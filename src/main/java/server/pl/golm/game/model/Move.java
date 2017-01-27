package pl.golm.game.model;

import java.util.List;

/**
 * interface of functions that every Move implementation must have
 * @author Dominik Lachowicz, Przemysław Magiera
 * @version 1.0
 */
public interface Move
{
    Player getPlayer();

    void setPlayer(Player player);

    Field getField();

    void setField(Field field);

    public List<Field> getKilled();

    public void setKilled(List<Field> killed);

}
