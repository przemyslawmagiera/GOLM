package pl.golm.game.model.impl;

/**
 * Created by Dominik on 2016-12-05.
 */

import static org.junit.Assert.*;
import org.junit.Test;
import pl.golm.game.exception.WrongBoardSizeException;
import pl.golm.game.model.Board;
import pl.golm.game.model.Field;
import pl.golm.game.model.Move;

import java.util.List;

public class BoardImplTest
{
    @Test(expected = WrongBoardSizeException.class)
    public void getSizeTest()
    {
        Board board1 = new BoardImpl(19);
        assertEquals(19, board1.getSize());
        Board board2 = new BoardImpl(5);
    }
    @Test
    public void getBoardTest()
    {
        Board board1 = new BoardImpl(19);
        assertNotNull(board1.getBoard());
    }
    @Test
    public void getHistoryTest()
    {
        Board board = new BoardImpl(9);
        assertNotNull(board.getHistory());
        assertEquals(0, board.getHistory().size());
    }

}
