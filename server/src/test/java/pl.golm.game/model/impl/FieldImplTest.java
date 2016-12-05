package pl.golm.game.model.impl;

/**
 * Created by Dominik on 2016-12-05.
 */
        import static org.junit.Assert.*;
        import org.junit.Test;
        import pl.golm.game.model.Field;
        import pl.golm.game.model.Player;
        import pl.golm.game.model.PlayerColor;

public class FieldImplTest
{

    @Test
    public void toStringTEST()
    {
        Field fieldA1 = new FieldImpl(0, 0);
        Field fieldS13 = new FieldImpl(18, 12);
        assertEquals("A1", fieldA1.toString());
        assertEquals("S13", fieldS13.toString());
    }
    @Test
    public void getPlayerTEST()
    {
        Field fieldA1 = new FieldImpl(0, 0);
        Field fieldS13 = new FieldImpl(18, 12);
        assertEquals(null, fieldA1.getPlayer());
        assertEquals(null, fieldS13.getPlayer());
        Player playerA1 = new PlayerImpl("Player1", PlayerColor.BLACK);
        Player playerS13 = new PlayerImpl("Player2", PlayerColor.WHITE);
        fieldA1.setPlayer(playerA1);
        fieldS13.setPlayer(playerS13);
        assertEquals(playerA1, fieldA1.getPlayer());
        assertEquals(playerS13, fieldS13.getPlayer());
    }
    @Test
    public void getColumnTEST()
    {
        Field fieldA1 = new FieldImpl(0, 0);
        Field fieldS13 = new FieldImpl(18, 12);
        assertEquals(0, fieldA1.getColumn());
        assertEquals(18, fieldS13.getColumn());
    }
    @Test
    public void getRowTEST()
    {
        Field fieldA1 = new FieldImpl(0, 0);
        Field fieldS13 = new FieldImpl(18, 12);
        assertEquals(0, fieldA1.getRow());
        assertEquals(12, fieldS13.getRow());
    }
}
