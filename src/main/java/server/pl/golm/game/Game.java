package server.pl.golm.game;

import server.pl.golm.game.model.Board;
import server.pl.golm.game.model.Player;
import server.pl.golm.game.model.PlayerColor;
import server.pl.golm.game.model.impl.BoardImpl;
import server.pl.golm.game.model.impl.PlayerImpl;

/**
 * class linking all the model aspects of a single game
 * @author Dominik Lachowicz, Przemysław Magiera
 * @version 1.0
 */
public class Game
{
    private GameState gameState;
    private Player player1, player2;
    private Board board;
    private Result result;

    /**
     * constructor that prepares a game to be ready to start
     * @param boardSize size of a single dimension of the board
     * @param player1Name player1's nick name
     * @param player2Name player2's nick name
     */
    public Game(int boardSize, String player1Name, String player2Name)
    {
        setGameState(GameState.WAITING_FOR_START);
        setResult(null);
        setBoard(new BoardImpl(boardSize));
        setPlayer1(new PlayerImpl(player1Name, PlayerColor.BLACK)); // player1 is always black and gets first move
        setPlayer2(new PlayerImpl(player2Name, PlayerColor.WHITE)); // while player2 is white and gets komi bonus
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

