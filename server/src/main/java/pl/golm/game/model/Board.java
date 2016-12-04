package pl.golm.game.model;

import java.util.List;

/**
 * Created by Ktoś on 29.11.2016.
 */
public interface Board
{
    int getSize();

    void setSize(int size);

    List<List<Field>> getBoard();

    void setBoard(List<List<Field>> board);

    List<Move> getHistory();

    void setHistory(List<Move> history);

    Field getLastMoved();

    void setLastMoved(Field lastMoved);
}
