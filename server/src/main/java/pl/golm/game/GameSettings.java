package pl.golm.game;

/**
 * Created by Dominik on 2016-12-09.
 */
public class GameSettings
{
    private int boardSize;
    private boolean multiPlayer;

    public GameSettings(String gameSettings)
    {
        if (gameSettings.startsWith("9"))
            setBoardSize(9);
        else if (gameSettings.startsWith("13"))
            setBoardSize(13);
        else
            setBoardSize(19);
        if (gameSettings.endsWith("true"))
            setMultiPlayer(true);
        else
            setMultiPlayer(false);
    }

    public int getBoardSize()
    {
        return boardSize;
    }

    public void setBoardSize(int boardSize)
    {
        this.boardSize = boardSize;
    }

    public boolean isMultiPlayer()
    {
        return multiPlayer;
    }

    public void setMultiPlayer(boolean multiPlayer)
    {
        this.multiPlayer = multiPlayer;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GameSettings that = (GameSettings) o;
        if (getBoardSize() != that.getBoardSize())
            return false;
        return isMultiPlayer() == that.isMultiPlayer();
    }

    @Override
    public int hashCode()
    {
        int result = getBoardSize();
        result = 31 * result + (isMultiPlayer() ? 1 : 0);
        return result;
    }
}
