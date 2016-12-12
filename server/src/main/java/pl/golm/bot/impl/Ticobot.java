package pl.golm.bot.impl;

import pl.golm.bot.Bot;
import pl.golm.game.GameSettings;
import pl.golm.game.GameState;
import pl.golm.game.mechanics.util.GameUtils;
import pl.golm.game.model.Board;
import pl.golm.game.model.Field;
import pl.golm.game.model.Player;
import pl.golm.game.model.PlayerColor;
import pl.golm.game.model.impl.BoardImpl;
import pl.golm.game.model.impl.MoveImpl;
import pl.golm.game.model.impl.PlayerImpl;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Dominik on 2016-12-12.
 */
public class Ticobot implements Bot
{
    public static final String botName = "Ticobot";

    private BufferedReader reader;
    private BufferedWriter writer;

    private GameSettings gameSettings;

    private Board board;
    private Player bot;

    private Player opponent;
    private GameState gameState;

    public Ticobot (GameSettings gameSettings)
    {
        setGameSettings(gameSettings);
        setBoard(new BoardImpl(gameSettings.getBoardSize()));
        setGameState(GameState.WAITING_FOR_START);
    }

    @Override
    public void run()
    {
        // bot can act like every other client
        try
        {
            Socket socket = new Socket("localhost", 5724);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new PrintWriter(socket.getOutputStream()));
            String line;
            if(!reader.readLine().startsWith("Connected to server"))
                throw new IOException("Unexpected message from server");
            setGameState(GameState.RUNNING);
            line = reader.readLine();
            if (line.equals("Game started. You are player 1. Black"))
            {
                setBot(new PlayerImpl(Ticobot.botName, PlayerColor.BLACK));
                setOpponent(new PlayerImpl("???", PlayerColor.WHITE));
            }
            else
            {
                setBot(new PlayerImpl(Ticobot.botName, PlayerColor.WHITE));
                setOpponent(new PlayerImpl("???", PlayerColor.BLACK));
            }
            // all below should be in a while (!GameState.FINISHED)
            if (getBot().getColor().equals(PlayerColor.WHITE))
                getMove();
            while(getGameState().equals(GameState.RUNNING))
            {
                makeMove();
                getMove();
            }
            // counting territories and dead groups
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }
    @Override
    public BufferedReader getReader()
    {
        return reader;
    }
    @Override
    public void setReader(BufferedReader reader)
    {
        this.reader = reader;
    }
    @Override
    public BufferedWriter getWriter()
    {
        return writer;
    }
    @Override
    public void setWriter(BufferedWriter writer)
    {
        this.writer = writer;
    }
    @Override
    public GameSettings getGameSettings()
    {
        return gameSettings;
    }
    @Override
    public void setGameSettings(GameSettings gameSettings)
    {
        this.gameSettings = gameSettings;
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }

    public Board getBoard()
    {
        return board;
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;
    }

    public Player getOpponent()
    {
        return opponent;
    }

    public void setOpponent(Player opponent)
    {
        this.opponent = opponent;
    }

    public Player getBot()
    {
        return bot;
    }

    public void setBot(Player bot)
    {
        this.bot = bot;
    }

    private void getMove() throws IOException
    {
        String line = reader.readLine();
        line = line.substring("Opponent played: ".length());
        if (line.equals("Opponent surrendered"))
            setGameState(GameState.FINISHED);
        else if (line.equals("pass"))
        {
            if (board.getHistory().size() > 0 && board.getHistory().get(board.getHistory().size() - 1).getField() == null) // a second pass
            {
                board.getHistory().add(new MoveImpl(opponent, null, null));
                setGameState(GameState.COUNTING_TERRITORIES);
            }
            else // a first pass
            {
                board.getHistory().add(new MoveImpl(opponent, null, null));
            }
        }
        else
        {
            int moveX = Integer.parseInt(line.substring(0, line.indexOf(",")));
            int moveY = Integer.parseInt(line.substring(line.indexOf(",") + 1));
            board.getBoard().get(moveY).get(moveX).setPlayer(opponent);
            board.getHistory().add(new MoveImpl(opponent, board.getBoard().get(moveY).get(moveX), new ArrayList<Field>(GameUtils.moveKills(board, board.getBoard().get(moveY).get(moveX), opponent))));
        }
    }

    private void makeMove() throws IOException
    {
        Random random = new Random();
        int moveX = random.nextInt(board.getSize()), moveY = random.nextInt(board.getSize()), counter = 16;
        while (GameUtils.moveIsLegal(board, board.getBoard().get(moveY).get(moveX), bot) && counter > 0)
        {
            moveX = random.nextInt(board.getSize());
            moveY = random.nextInt(board.getSize());
            counter--;
        }
        if (counter > 0)
        {
            writer.write(Integer.toString(moveX) + "," + Integer.toString(moveY));
            writer.flush();
            if (reader.readLine().equals("Legal move"))
            {
                board.getBoard().get(moveY).get(moveX).setPlayer(bot);
                board.getHistory().add(new MoveImpl(bot, board.getBoard().get(moveY).get(moveX), new ArrayList<Field>(GameUtils.moveKills(board, board.getBoard().get(moveY).get(moveX), bot))));
            }
            else
                throw new IOException("Unexpected input from server");
        }
        else //pass
        {
            writer.write("pass");
            writer.flush();
            if (reader.readLine().equals("Legal move"))
            {
                board.getHistory().add(new MoveImpl(bot, null, null));
                if (board.getHistory().size() > 0 && board.getHistory().get(board.getHistory().size() - 1).getField() == null) // a second pass
                    setGameState(GameState.COUNTING_TERRITORIES);
            }
            else
                throw new IOException("Unexpected input from server");
        }

    }
}
