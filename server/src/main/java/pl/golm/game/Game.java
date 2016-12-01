package pl.golm.game;

import pl.golm.game.model.Board;
import pl.golm.game.model.Player;
import pl.golm.game.model.PlayerColor;
import pl.golm.game.model.impl.BoardImpl;
import pl.golm.game.model.impl.PlayerImpl;

/**
 * Created by Ktoś on 29.11.2016.
 */
public class Game
{
    private GameState gameState;
    private Player player1, player2;
    private Board board;
    private Result result;

    public Game(int boardSize, String player1Name, String player2Name)
    {
        setGameState(GameState.WAITING_FOR_START);
        setResult(null);
        setBoard(new BoardImpl(boardSize));
        setPlayer1(new PlayerImpl(player1Name, PlayerColor.BLACK));
        setPlayer2(new PlayerImpl(player2Name, PlayerColor.WHITE));
    }

    public Player getPlayer1()
    {
        return player1;
    }

    public void setPlayer1(Player player1)
    {
        this.player1 = player1;
    }

    public Player getPlayer2()
    {
        return player2;
    }

    public void setPlayer2(Player player2)
    {
        this.player2 = player2;
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;
    }

    public Board getBoard()
    {
        return board;
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }

    public Result getResult()
    {
        return result;
    }

    public void setResult(Result result)
    {
        this.result = result;
    }
}

