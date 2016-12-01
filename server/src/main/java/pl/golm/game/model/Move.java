package pl.golm.game.model;

import java.util.List;

/**
 * Created by Ktoś on 29.11.2016.
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
