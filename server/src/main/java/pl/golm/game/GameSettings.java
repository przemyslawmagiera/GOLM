package pl.golm.game;

/**
 * Created by Dominik on 2016-12-09.
 */
public class GameSettings
{
    private int boardSize;
    private boolean multiPlayer;
    private String playerName;

    public GameSettings(int boardSize, boolean multiPlayer, String playerName)
    {
        setBoardSize(boardSize);
        setMultiPlayer(multiPlayer);
        setPlayerName(playerName);
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

    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
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
