package server.pl.golm.bot.impl;

import server.pl.golm.bot.Bot;
import server.pl.golm.game.GameSettings;
import server.pl.golm.game.GameState;
import server.pl.golm.game.mechanics.util.GameUtils;
import server.pl.golm.game.model.Board;
import server.pl.golm.game.model.Field;
import server.pl.golm.game.model.Player;
import server.pl.golm.game.model.PlayerColor;
import server.pl.golm.game.model.impl.BoardImpl;
import server.pl.golm.game.model.impl.MoveImpl;
import server.pl.golm.game.model.impl.PlayerImpl;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Dominik on 2016-12-12.
 */
public class Ticobot implements Bot
{

    public static final String botName = "Ticobot";

    private BufferedReader reader;
    private PrintWriter writer;

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
            writer = new PrintWriter(socket.getOutputStream());
            String line;
            if(!reader.readLine().startsWith("Connected to server"))
                throw new Exception("Unexpected message from server");
            setGameState(GameState.RUNNING);
            line = reader.readLine();
            if (line.equals("Game started. You are player 1. Black"))
            {
                setBot(new PlayerImpl(Ticobot.botName, PlayerColor.BLACK));
                setOpponent(new PlayerImpl(reader.readLine(), PlayerColor.WHITE));
            }
            else
            {
                setBot(new PlayerImpl(Ticobot.botName, PlayerColor.WHITE));
                setOpponent(new PlayerImpl(reader.readLine(), PlayerColor.BLACK));
            }
            // all below should be in a while (!GameState.FINISHED)
            while (!getGameSettings().equals(GameState.FINISHED))
                communicateWithServer();
        }
        catch (Exception Exception)
        {
            Exception.printStackTrace();
        }
    }

    private void communicateWithServer()
    {
        if (getGameState().equals(GameState.RUNNING)) {
            try {
                if (getBot().getColor().equals(PlayerColor.WHITE) || (getBoard().getHistory().size() > 0 && getBoard().getHistory().get(getBoard().getHistory().size() - 1).getPlayer().equals(getBot())))
                    getMove();
                while (getGameState().equals(GameState.RUNNING)) {
                    makeMove();
                    if (getGameState().equals(GameState.RUNNING))
                        getMove();
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
        else if (getGameState().equals(GameState.COUNTING_TERRITORIES))
        {
            try {
                if (getBot().getColor().equals(PlayerColor.BLACK)) // bot is black
                {
                    String line = reader.readLine();
                    if (line.contains("Pick opponents dead groups"))
                    {
                        reader.readLine(); // "suggested"
                        List<String> deadGroups = new ArrayList<>();
                        line = reader.readLine();
                        while(!line.contains("End"))
                        {
                            deadGroups.add(line);
                            line = reader.readLine();
                        }
                        writer.println("Dead groups");
                        writer.flush();
                        deadGroups.forEach(message -> {try {
                            writer.println(message);
                            writer.flush();
                        }
                        catch(Exception exception)
                        {
                            exception.printStackTrace();
                        }
                        });
                        writer.println("End dead groups");
                        writer.flush();
                        line = reader.readLine();
                        if (line.equals("true"))// opponent agreed on your dead groups
                        {
                            reader.readLine(); // "Opponent suggested ..."
                            line = reader.readLine();
                            while (!line.equals("End dead groups"))
                            {
                                line = reader.readLine();
                            }
                            writer.println("true");
                            writer.flush();
                            reader.readLine(); // agreed
                            // now territories
                            reader.readLine();//"Pick opponents territories"
                            List<String> territories = new ArrayList<>();
                            reader.readLine();// suggested
                            line = reader.readLine();
                            while (!line.contains("End"))
                            {
                                if (line.indexOf(",") != -1)
                                    territories.add(line);
                                line = reader.readLine();
                            }
                            writer.println("Territories");
                            writer.flush();
                            territories.forEach(message ->{try {
                                writer.println(message);
                                writer.flush();
                            }
                            catch(Exception exception)
                            {
                                exception.printStackTrace();
                            }
                            });
                            writer.println("end territories");
                            writer.flush();
                            line = reader.readLine();
                            if (line.equals("true"))
                            {
                                reader.readLine(); // "Opponent suggested ..."
                                line = reader.readLine();
                                while (!line.toLowerCase().equals("end territories"))
                                {
                                    line = reader.readLine();
                                }
                                writer.println("true");
                                writer.flush();
                                setGameState(GameState.FINISHED);
                            }
                            else
                            {
                                board.getHistory().add(new MoveImpl(getBot(),null, new ArrayList<Field>()));
                                setGameState(GameState.RUNNING);
                            }
                        }
                        else // opponent discarded your proposal game resumed
                        {
                            board.getHistory().add(new MoveImpl(getBot(),null, new ArrayList<Field>()));
                            setGameState(GameState.RUNNING);
                        }
                    }
                    else
                        throw new IOException("unexpected input from server: " + line);
                } else //bot is white
                {
                    reader.readLine(); // "Opponent suggested ..."
                    String line = reader.readLine();
                    while (!line.equals("End dead groups"))
                    {
                        line = reader.readLine();
                    }
                    writer.println("true");
                    writer.flush();
                    line = reader.readLine();//"Pick..."
                    reader.readLine(); // "suggested"
                    List<String> deadGroups = new ArrayList<>();
                    line = reader.readLine();
                    while(!line.contains("End"))
                    {
                        deadGroups.add(line);
                        line = reader.readLine();
                    }
                    writer.println("Dead groups");
                    writer.flush();
                    deadGroups.forEach(message -> {try {
                        writer.println(message);
                        writer.flush();
                    }
                    catch(Exception exception)
                    {
                           exception.printStackTrace();
                    }
                    });
                    writer.println("End dead groups");
                    writer.flush();
                    line = reader.readLine();
                    if (line.equals("true"))
                    {
                        reader.readLine(); // "Opponent suggested ..."
                        line = reader.readLine();
                        while (!line.contains("End"))
                        {
                            line = reader.readLine();
                        }
                        writer.println("true");
                        writer.flush();
                        line = reader.readLine();//"Pick..."
                        reader.readLine(); // "suggested"
                        List<String> territories = new ArrayList<>();
                        line = reader.readLine();
                        while(!line.contains("End"))
                        {
                            if (line.indexOf(",") != -1)
                                territories.add(line);
                            line = reader.readLine();
                        }
                        writer.println("Territories");
                        writer.flush();
                        territories.forEach(message -> {try {
                            writer.println(message);
                            writer.flush();
                        }
                        catch(Exception exception)
                        {
                            exception.printStackTrace();
                        }
                        });
                        writer.println("end territories");
                        writer.flush();
                        line = reader.readLine();
                        if (line.equals("true"))
                        {
                            setGameState(GameState.FINISHED);
                        }
                        else
                        {
                            board.getHistory().add(new MoveImpl(getBot(),null, new ArrayList<Field>()));
                            setGameState(GameState.RUNNING);
                        }
                    }
                    else
                    {
                        board.getHistory().add(new MoveImpl(getBot(),null, new ArrayList<Field>()));
                        setGameState(GameState.RUNNING);
                    }
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
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
    public PrintWriter getWriter()
    {
        return writer;
    }
    @Override
    public void setWriter(PrintWriter writer)
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

    private void getMove() throws Exception
    {
        String line = reader.readLine();
        if(line.contains("Second")) //second pass
        {
            setGameState(GameState.COUNTING_TERRITORIES);
        }
        else if (line.contains("Fields"))
        {
            List<String> occupiedFields = new ArrayList<>();
            line = reader.readLine();
            while (!line.contains("End"))
            {
                occupiedFields.add(line);
                String[] props = line.split(",");
                if (board.getBoard().get(Integer.parseInt(props[2])).get(Integer.parseInt(props[1])).getPlayer() == null)
                    board.getHistory().add(new MoveImpl(getOpponent(),board.getBoard().get(Integer.parseInt(props[2])).get(Integer.parseInt(props[1])), new ArrayList<Field>(GameUtils.moveKills(board, board.getBoard().get(Integer.parseInt(props[2])).get(Integer.parseInt(props[1])), opponent))));
                line = reader.readLine();
            }
            for (List<Field> fields : board.getBoard())
            {
                for (Field field : fields)
                {
                    field.setPlayer(null);
                }
            }
            for (String field : occupiedFields)
            {
                String[] props = field.split(",");
                if (props[0].contains("b"))
                {
                    if (getBot().getColor().equals(PlayerColor.WHITE))
                    {
                        board.getBoard().get(Integer.parseInt(props[2])).get(Integer.parseInt(props[1])).setPlayer(getOpponent());
                    }
                    else // bot is black
                    {
                        board.getBoard().get(Integer.parseInt(props[2])).get(Integer.parseInt(props[1])).setPlayer(getBot());
                    }
                }
                else // white
                {
                    if (getBot().getColor().equals(PlayerColor.WHITE))
                    {
                        board.getBoard().get(Integer.parseInt(props[2])).get(Integer.parseInt(props[1])).setPlayer(getBot());
                    }
                    else // bot is black
                    {
                        board.getBoard().get(Integer.parseInt(props[2])).get(Integer.parseInt(props[1])).setPlayer(getOpponent());
                    }
                }
            }
        }
        else
        {
            throw new IOException("Unexpected input from server");
        }
    }

    private void makeMove() throws Exception
    {
        Random random = new Random();
        int moveX = random.nextInt(board.getSize()), moveY = random.nextInt(board.getSize()), counter = 16;
        while (!GameUtils.moveIsLegal(board, board.getBoard().get(moveY).get(moveX), bot) && counter > 0)
        {
            moveX = random.nextInt(board.getSize());
            moveY = random.nextInt(board.getSize());
            counter--;
        }
        if (counter > 0)
        {
            writer.println(Integer.toString(moveY) + "," + Integer.toString(moveX));
            writer.flush();
            String line = reader.readLine();
            if (line.equals("Legal move"))
            {
                board.getBoard().get(moveY).get(moveX).setPlayer(bot);
                board.getHistory().add(new MoveImpl(bot, board.getBoard().get(moveY).get(moveX), new ArrayList<Field>(GameUtils.moveKills(board, board.getBoard().get(moveY).get(moveX), bot))));
                line = reader.readLine();
                if (line.contains("Fields"))
                {
                    List<String> occupiedFields = new ArrayList<>();
                    line = reader.readLine();
                    while (!line.contains("End"))
                    {
                        occupiedFields.add(line);
                        line = reader.readLine();
                    }
                    for (List<Field> fields : board.getBoard())
                    {
                        for (Field field : fields)
                        {
                            field.setPlayer(null);
                        }
                    }
                    for (String field : occupiedFields)
                    {
                        String[] props = field.split(",");
                        if (props[0].contains("b"))
                        {
                            if (getBot().getColor().equals(PlayerColor.WHITE))
                            {
                                board.getBoard().get(Integer.parseInt(props[2])).get(Integer.parseInt(props[1])).setPlayer(getOpponent());
                            } else // bot is black
                            {
                                board.getBoard().get(Integer.parseInt(props[2])).get(Integer.parseInt(props[1])).setPlayer(getBot());
                            }
                        } else // white
                        {
                            if (getBot().getColor().equals(PlayerColor.WHITE))
                            {
                                board.getBoard().get(Integer.parseInt(props[2])).get(Integer.parseInt(props[1])).setPlayer(getBot());
                            } else // bot is black
                            {
                                board.getBoard().get(Integer.parseInt(props[2])).get(Integer.parseInt(props[1])).setPlayer(getOpponent());
                            }
                        }
                    }
                }
                else
                    throw new IOException("BADBADNOTGOOD");
            }
            else if (line.contains("Second"))
                setGameState(GameState.COUNTING_TERRITORIES);
            else
                makeMove();
        }
        else //pass
        {
            writer.println("pass");
            writer.flush();
            String line = reader.readLine();
            if (line.equals("Legal move"))
            {
                board.getHistory().add(new MoveImpl(bot, null, new ArrayList<Field>()));
            }
            else if (line.contains("Second"))
                setGameState(GameState.COUNTING_TERRITORIES);
            else
            {
                throw new Exception("Unexpected input from server");
            }
        }

    }
}
