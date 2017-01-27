package pl.golm.game.model;

import java.util.List;

/**
 * interface of functions that every Board implementation must have
 * @author Dominik Lachowicz, Przemysław Magiera
 * @version 1.01
 */
public interface Board
{
    int getSize();

    void setSize(int size);

    List<List<Field>> getBoard();

    void setBoard(List<List<Field>> board);

    List<Move> getHistory();

    void setHistory(List<Move> history);

}
