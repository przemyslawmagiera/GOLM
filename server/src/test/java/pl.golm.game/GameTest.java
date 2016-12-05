package pl.golm.game;

/**
 * Created by Dominik on 2016-12-05.
 */

import static org.junit.Assert.*;
import org.junit.Test;
import pl.golm.game.model.Board;
import pl.golm.game.model.Player;

public class GameTest
{
    @Test
    public void getPlayerTest()
    {
        Game game = new Game(19, "Player1", "Some other player");
        assertNotNull(game.getPlayer1());
        assertNotNull(game.getPlayer2());
        assertEquals("Player1", game.getPlayer1().getName());
        assertEquals("Some other player", game.getPlayer2().getName());
    }
    @Test
    public void getGameStateTest()
    {
        Game game = new Game(9, "P", "Some player");
        assertEquals(GameState.WAITING_FOR_START, game.getGameState());
        game.setGameState(GameState.RUNNING);
        assertEquals(GameState.RUNNING, game.getGameState());
    }
    @Test
    public void getBoardTest()
    {
        Game game = new Game(13, "P", "S");
        assertNotNull(game.getBoard());
    }
    @Test
    public void getResultTest()
    {
        Game game = new Game(13, "P", "S");
        assertEquals(null, game.getResult());
        game.setResult(Result.PLAYER1_WON);
        assertEquals(Result.PLAYER1_WON, game.getResult());
    }


}
