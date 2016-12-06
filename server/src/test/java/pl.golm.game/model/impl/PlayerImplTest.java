package pl.golm.game.model.impl;

/**
 * Created by Dominik on 2016-12-05.
 */

import static org.junit.Assert.*;
import org.junit.Test;
import pl.golm.game.model.Player;
import pl.golm.game.model.PlayerColor;

public class PlayerImplTest
{
    @Test
    public void getNameTest()
    {
        Player p1 = new PlayerImpl("P1", PlayerColor.BLACK);
        Player p2 = new PlayerImpl("P2", PlayerColor.WHITE);
        assertEquals("P1", p1.getName());
        assertEquals("P2", p2.getName());
    }
    @Test
    public void getColorTest()
    {
        Player p1 = new PlayerImpl("P1", PlayerColor.BLACK);
        Player p2 = new PlayerImpl("P2", PlayerColor.WHITE);
        assertEquals(PlayerColor.BLACK, p1.getColor());
        assertEquals(PlayerColor.WHITE, p2.getColor());
    }
    @Test
    public void getPrisonersAmount()
    {
        Player p1 = new PlayerImpl("P1", PlayerColor.BLACK);
        Player p2 = new PlayerImpl("P2", PlayerColor.WHITE);
        assertEquals(0, p1.getPrisonerAmount());
        assertEquals(0, p2.getPrisonerAmount());
        p1.setPrisonerAmount(12);
        p2.setPrisonerAmount(3);
        assertEquals(12, p1.getPrisonerAmount());
        assertEquals(3, p2.getPrisonerAmount());
    }
    @Test
    public void getTerritoryAmount()
    {
        Player p1 = new PlayerImpl("P1", PlayerColor.BLACK);
        Player p2 = new PlayerImpl("P2", PlayerColor.WHITE);
        assertEquals(0, p1.getTerritoryAmount());
        assertEquals(0, p2.getTerritoryAmount());
        p1.setTerritoryAmount(2);;
        p2.setTerritoryAmount(7);
        assertEquals(2, p1.getTerritoryAmount());
        assertEquals(7, p2.getTerritoryAmount());
    }
}
