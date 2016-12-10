package pl.golm.game.model.impl;

import pl.golm.game.exception.WrongBoardSizeException;
import pl.golm.game.model.Board;
import pl.golm.game.model.Field;
import pl.golm.game.model.Move;
import java.util.ArrayList;
import java.util.List;

/**
 * Class implementation of Board interface
 * @author Dominik Lachowicz, Przemysław Magiera
 * @version 1.01
 */

public class BoardImpl implements Board
{
    private int size; // size of one dimension
    private List<List<Field>> board;
    private List<Move> history;

    /**
     * constructor preparing Board to use
     * @throws WrongBoardSizeException when size is not equal to 9, 13 or 19
     * @param size size of one dimension
     */
    public BoardImpl(int size)
    {
        if (size != 9 && size != 13 && size != 19)
            throw new WrongBoardSizeException("Board could not be created. GO board must be size 9, 13 or 19");
        setSize(size);
        setBoard(new ArrayList<List<Field>>());
        setHistory(new ArrayList<Move>());
        createFields();
    }

    private void createFields()
    {
        for (int i = 0; i < size; i++)
        {
            board.add(new ArrayList<Field>());
            for (int j = 0; j < size; j++)
                board.get(i).add(new FieldImpl(i, j));
        }
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public List<List<Field>> getBoard()
    {
        return board;
    }

    public void setBoard(List<List<Field>> board)
    {
        this.board = board;
    }

    public List<Move> getHistory()
    {
        return history;
    }

    public void setHistory(List<Move> history)
    {
        this.history = history;
    }

}
