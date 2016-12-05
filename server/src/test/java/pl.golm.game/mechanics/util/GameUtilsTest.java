package pl.golm.game.mechanics.util;

/**
 * Created by Dominik on 2016-12-05.
 */

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import pl.golm.game.model.*;
import pl.golm.game.model.impl.BoardImpl;
import pl.golm.game.model.impl.MoveImpl;
import pl.golm.game.model.impl.PlayerImpl;

import java.util.ArrayList;
import java.util.List;

public class GameUtilsTest
{
    @Test
    public void moveIsLegalTest()
    {
        Player playerBlack = new PlayerImpl("Black", PlayerColor.BLACK);
        Player playerWhite = new PlayerImpl("White", PlayerColor.WHITE);
        Board board19 = new BoardImpl(19);
        Board board9 = new BoardImpl(9);
        for (int i = 0; i < board9.getSize(); i++)
            for (int j = 0; j < board9.getSize(); j++)
            {
                Move move = new MoveImpl(playerBlack, board9.getBoard().get(i).get(j), new ArrayList<Field>());
                assertTrue(GameUtils.moveIsLegal(board9, move.getField(), move.getPlayer()));
           }
        for (int i = 0; i < board9.getSize(); i++)
            for (int j = 0; j < board9.getSize(); j++)
            {
                Move move = new MoveImpl(playerWhite, board9.getBoard().get(i).get(j), new ArrayList<Field>());
                assertFalse(GameUtils.moveIsLegal(board9, move.getField(), move.getPlayer()));
            }
        for (int i = 0; i < board19.getSize(); i++)
        {
            Move move;
            Move counterMove;
            if (i % 2 == 0)
            {
                move = new MoveImpl(playerBlack, board19.getBoard().get(i).get(i), new ArrayList<Field>());
                counterMove = new MoveImpl(playerWhite, board19.getBoard().get(i).get(i), new ArrayList<Field>());
                assertTrue(GameUtils.moveIsLegal(board19, move.getField(), move.getPlayer()));
                board19.getHistory().add(move);
                board19.getBoard().get(i).get(i).setPlayer((playerBlack));
                assertFalse(GameUtils.moveIsLegal(board19, counterMove.getField(), counterMove.getPlayer()));
            }
            else
            {
                move = new MoveImpl(playerWhite, board19.getBoard().get(i).get(i), new ArrayList<Field>());
                assertTrue(GameUtils.moveIsLegal(board19, move.getField(), move.getPlayer()));
                board19.getHistory().add(move);
                board19.getBoard().get(i).get(i).setPlayer((playerWhite));
                assertFalse(GameUtils.moveIsLegal(board19, move.getField(), move.getPlayer()));
            }
        }
        Board board1sui = new BoardImpl(13);
        board1sui.getBoard().get(0).get(1).setPlayer(playerWhite);
        board1sui.getBoard().get(1).get(0).setPlayer(playerWhite);
        assertFalse(GameUtils.moveIsLegal(board1sui, board1sui.getBoard().get(0).get(0), playerBlack));
        Board board2sui = new BoardImpl(13);
        board2sui.getBoard().get(0).get(0).setPlayer(playerWhite);
        board2sui.getBoard().get(1).get(1).setPlayer(playerWhite);
        board2sui.getBoard().get(2).get(0).setPlayer(playerWhite);
        assertFalse(GameUtils.moveIsLegal(board2sui, board2sui.getBoard().get(1).get(0), playerBlack));
        Board board3sui = new BoardImpl(13);
        board3sui.getBoard().get(6).get(6).setPlayer(playerWhite);
        board3sui.getBoard().get(6).get(8).setPlayer(playerWhite);
        board3sui.getBoard().get(5).get(7).setPlayer(playerWhite);
        board3sui.getBoard().get(7).get(7).setPlayer(playerWhite);
        assertFalse(GameUtils.moveIsLegal(board3sui, board3sui.getBoard().get(6).get(7), playerBlack));
        Board board4sui = new BoardImpl(13);
        board4sui.getBoard().get(6).get(12).setPlayer(playerWhite);
        board4sui.getBoard().get(7).get(11).setPlayer(playerWhite);
        assertTrue(GameUtils.moveIsLegal(board4sui, board4sui.getBoard().get(7).get(12), playerBlack));
        Board board5sui = new BoardImpl(13);
        board5sui.getBoard().get(11).get(0).setPlayer(playerWhite);
        assertTrue(GameUtils.moveIsLegal(board5sui, board5sui.getBoard().get(12).get(0), playerBlack));
        Board board6sui = new BoardImpl(13);
        board6sui.getBoard().get(12).get(4).setPlayer(playerWhite);
        board6sui.getBoard().get(12).get(6).setPlayer(playerWhite);
        assertTrue(GameUtils.moveIsLegal(board6sui, board6sui.getBoard().get(12).get(7), playerBlack));
        Board board7sui = new BoardImpl(13);
        board7sui.getBoard().get(0).get(11).setPlayer(playerWhite);
        board7sui.getBoard().get(1).get(12).setPlayer(playerWhite);
        board7sui.getBoard().get(0).get(10).setPlayer(playerBlack);
        board7sui.getBoard().get(1).get(11).setPlayer(playerBlack);
        assertTrue(GameUtils.moveIsLegal(board7sui, board7sui.getBoard().get(0).get(12), playerBlack));
        Board board8sui = new BoardImpl(13);
        board8sui.getBoard().get(7).get(4).setPlayer(playerWhite);
        board8sui.getBoard().get(8).get(3).setPlayer(playerWhite);
        board8sui.getBoard().get(8).get(5).setPlayer(playerWhite);
        board8sui.getBoard().get(9).get(4).setPlayer(playerWhite);
        board8sui.getBoard().get(9).get(3).setPlayer(playerBlack);
        board8sui.getBoard().get(10).get(4).setPlayer(playerBlack);
        board8sui.getBoard().get(9).get(5).setPlayer(playerBlack);
        assertTrue(GameUtils.moveIsLegal(board8sui, board8sui.getBoard().get(8).get(4), playerBlack));
        List<Field> koKilled = new ArrayList<Field>();
        koKilled.add(board8sui.getBoard().get(8).get(4));
        board8sui.getHistory().add(new MoveImpl(playerWhite, board8sui.getBoard().get(9).get(4),koKilled));
        assertFalse(GameUtils.moveIsLegal(board8sui, board8sui.getBoard().get(8).get(4), playerBlack));
        Board board9sui = new BoardImpl(19);
        board9sui.getBoard().get(0).get(1).setPlayer(playerWhite);
        board9sui.getBoard().get(0).get(2).setPlayer(playerWhite);
        board9sui.getBoard().get(1).get(0).setPlayer(playerWhite);
        board9sui.getBoard().get(1).get(3).setPlayer(playerWhite);
        board9sui.getBoard().get(2).get(1).setPlayer(playerWhite);
        board9sui.getBoard().get(2).get(2).setPlayer(playerWhite);
        board9sui.getBoard().get(1).get(2).setPlayer(playerBlack);
        assertFalse(GameUtils.moveIsLegal(board9sui, board9sui.getBoard().get(1).get(1), playerBlack));
        board9sui.getBoard().get(2).get(2).setPlayer(null);
        assertTrue(GameUtils.moveIsLegal(board9sui, board9sui.getBoard().get(1).get(1), playerBlack));
    }
    @Test
    public void getFieldNeighboursTest()
    {
        Board board19 = new BoardImpl(19);
        Board board9 = new BoardImpl(9);
        assertEquals(2, GameUtils.getFieldNeighbors(board9, board9.getBoard().get(0).get(0)).size());
        assertEquals(2, GameUtils.getFieldNeighbors(board9, board9.getBoard().get(0).get(8)).size());
        assertEquals(2, GameUtils.getFieldNeighbors(board9, board9.getBoard().get(8).get(0)).size());
        assertEquals(2, GameUtils.getFieldNeighbors(board9, board9.getBoard().get(8).get(8)).size());
        assertEquals(4, GameUtils.getFieldNeighbors(board19, board19.getBoard().get(8).get(8)).size());
        assertEquals(3, GameUtils.getFieldNeighbors(board19, board19.getBoard().get(18).get(8)).size());
    }
    @Test
    public void moveKillsTest()
    {
        Player black = new PlayerImpl("black", PlayerColor.BLACK);
        Player white = new PlayerImpl("white", PlayerColor.WHITE);
        Board board19 = new BoardImpl(19);
        for (int i = 0; i < board19.getSize(); i++)
            for (int j = 0; j < board19.getSize(); j++)
            {
                Move move = new MoveImpl(black, board19.getBoard().get(i).get(j), null);
                assertTrue(GameUtils.moveKills(board19, board19.getBoard().get(i).get(j), black).isEmpty());
            }
        Board board0 = new BoardImpl(9);
        board0.getBoard().get(0).get(0).setPlayer(white);
        board0.getBoard().get(1).get(0).setPlayer(black);
        assertEquals(1, GameUtils.moveKills(board0, board0.getBoard().get(0).get(1), black).size());
        assertTrue(GameUtils.moveKills(board0, board0.getBoard().get(0).get(1), black).contains(board0.getBoard().get(0).get(0)));
        Board board1 = new BoardImpl(9);
        board1.getBoard().get(3).get(2).setPlayer(black);
        board1.getBoard().get(3).get(3).setPlayer(black);
        board1.getBoard().get(4).get(1).setPlayer(black);
        board1.getBoard().get(4).get(4).setPlayer(black);
        board1.getBoard().get(5).get(1).setPlayer(black);
        board1.getBoard().get(5).get(4).setPlayer(black);
        board1.getBoard().get(6).get(3).setPlayer(black);
        board1.getBoard().get(4).get(2).setPlayer(white);
        board1.getBoard().get(4).get(3).setPlayer(white);
        board1.getBoard().get(5).get(2).setPlayer(white);
        board1.getBoard().get(5).get(3).setPlayer(white);
        assertEquals(4, GameUtils.moveKills(board1, board1.getBoard().get(6).get(2), black).size());
        assertTrue(GameUtils.moveKills(board1, board1.getBoard().get(6).get(2), black).contains(board1.getBoard().get(4).get(2)));
        assertTrue(GameUtils.moveKills(board1, board1.getBoard().get(6).get(2), black).contains(board1.getBoard().get(4).get(3)));
        assertTrue(GameUtils.moveKills(board1, board1.getBoard().get(6).get(2), black).contains(board1.getBoard().get(5).get(2)));
        assertTrue(GameUtils.moveKills(board1, board1.getBoard().get(6).get(2), black).contains(board1.getBoard().get(5).get(3)));
        Board board2 = new BoardImpl(9);
        board2.getBoard().get(0).get(0).setPlayer(black);
        board2.getBoard().get(0).get(1).setPlayer(black);
        board2.getBoard().get(1).get(2).setPlayer(black);
        board2.getBoard().get(2).get(3).setPlayer(black);
        board2.getBoard().get(3).get(1).setPlayer(black);
        board2.getBoard().get(3).get(2).setPlayer(black);
        board2.getBoard().get(1).get(0).setPlayer(white);
        board2.getBoard().get(1).get(1).setPlayer(white);
        board2.getBoard().get(2).get(1).setPlayer(white);
        board2.getBoard().get(2).get(2).setPlayer(white);
        assertEquals(4, GameUtils.moveKills(board2, board2.getBoard().get(2).get(0), black).size());
        assertTrue(GameUtils.moveKills(board2, board2.getBoard().get(2).get(0), black).contains(board2.getBoard().get(1).get(0)));
        assertTrue(GameUtils.moveKills(board2, board2.getBoard().get(2).get(0), black).contains(board2.getBoard().get(1).get(1)));
        assertTrue(GameUtils.moveKills(board2, board2.getBoard().get(2).get(0), black).contains(board2.getBoard().get(2).get(1)));
        assertTrue(GameUtils.moveKills(board2, board2.getBoard().get(2).get(0), black).contains(board2.getBoard().get(2).get(2)));
        Board board3 = new BoardImpl(9);
        board3.getBoard().get(2).get(2).setPlayer(black);
        board3.getBoard().get(2).get(3).setPlayer(black);
        board3.getBoard().get(3).get(1).setPlayer(black);
        board3.getBoard().get(3).get(4).setPlayer(black);
        board3.getBoard().get(4).get(3).setPlayer(black);
        board3.getBoard().get(5).get(1).setPlayer(black);
        board3.getBoard().get(5).get(4).setPlayer(black);
        board3.getBoard().get(6).get(2).setPlayer(black);
        board3.getBoard().get(6).get(3).setPlayer(black);
        board3.getBoard().get(3).get(2).setPlayer(white);
        board3.getBoard().get(3).get(3).setPlayer(white);
        board3.getBoard().get(5).get(2).setPlayer(white);
        board3.getBoard().get(5).get(3).setPlayer(white);
        assertEquals(4, GameUtils.moveKills(board3, board3.getBoard().get(4).get(2), black).size());
        assertTrue(GameUtils.moveKills(board3, board3.getBoard().get(4).get(2), black).contains(board3.getBoard().get(3).get(2)));
        assertTrue(GameUtils.moveKills(board3, board3.getBoard().get(4).get(2), black).contains(board3.getBoard().get(3).get(3)));
        assertTrue(GameUtils.moveKills(board3, board3.getBoard().get(4).get(2), black).contains(board3.getBoard().get(5).get(2)));
        assertTrue(GameUtils.moveKills(board3, board3.getBoard().get(4).get(2), black).contains(board3.getBoard().get(5).get(3)));
    }
}
