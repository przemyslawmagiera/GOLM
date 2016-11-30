package pl.golm.game.model.impl;

import pl.golm.game.exception.WrongBoardSizeException;
import pl.golm.game.model.Board;
import pl.golm.game.model.Field;
import pl.golm.game.model.Move;
import pl.golm.game.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ktoś on 29.11.2016.
 */

public class BoardImpl implements Board
{
    private int size; // size of one dimension
    private List<List<Field>> board;
    private List<Move> history;
    private Field lastMoved;

    public BoardImpl (int size)
    {
        if (size != 9 && size != 13 && size != 19)
            throw new WrongBoardSizeException("Board could not be created. GO board must be size 9, 13 or 19");
        setSize(size);
        setLastMoved(null);
        setBoard(new ArrayList<List<Field>>());
        setHistory(new ArrayList<Move>());
        createFields();
    }

    private void createFields()
    {
        for(int i = 0; i < size; i++)
        {
            board.add(new ArrayList<Field>());
            for (int j = 0; j < size; j++)
                board.get(i).add(new FieldImpl(i, j));
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<List<Field>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Field>> board) {
        this.board = board;
    }

    public List<Move> getHistory() {
        return history;
    }

    public void setHistory(List<Move> history) {
        this.history = history;
    }

    public Field getLastMoved() {
        return lastMoved;
    }

    public void setLastMoved(Field lastMoved) {
        this.lastMoved = lastMoved;
    }

    public boolean is_legal(Field field, Player player)
    {
        //TODO implement(check if [player] can place pawn on [field])
        return false;
    }
}
