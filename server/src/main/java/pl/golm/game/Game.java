package pl.golm.game;

import pl.golm.game.model.Board;
import pl.golm.game.model.Player;
import pl.golm.game.model.PlayerColor;
import pl.golm.game.model.impl.BoardImpl;
import pl.golm.game.model.impl.PlayerImpl;

/**
 * Created by Ktoś on 29.11.2016.
 */
public class Game {
    private GameState gameState;
    private Player player1, player2;
    private Board board;
    private Result result;

    public Game(int boardSize)
    {
        setGameState(GameState.WAITING_FOR_START);
        setResult(null);
        setBoard(new BoardImpl(boardSize));
        setPlayer1(new PlayerImpl(PlayerColor.BLACK));
        setPlayer2(new PlayerImpl(PlayerColor.WHITE));
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void makeMove(int playerID, String fieldName)
    {
        //TODO check if possible and then move
    }
}

}
