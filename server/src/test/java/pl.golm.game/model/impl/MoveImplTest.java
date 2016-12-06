package pl.golm.game.model.impl;

/**
 * Created by Dominik on 2016-12-05.
 */

import static org.junit.Assert.*;
import org.junit.Test;
import pl.golm.game.model.Field;
import pl.golm.game.model.Move;
import pl.golm.game.model.Player;
import pl.golm.game.model.PlayerColor;

import java.util.List;

public class MoveImplTest
{
    @Test
    public void getPlayerTest()
    {
        Move move = new MoveImpl(new PlayerImpl("Name1", PlayerColor.BLACK), new FieldImpl(2,4), null);
        assertNotNull(move.getPlayer());
        assertEquals(2, move.getField().getColumn());
        assertEquals(4, move.getField().getRow());
        assertEquals(null, move.getKilled());
    }
}
