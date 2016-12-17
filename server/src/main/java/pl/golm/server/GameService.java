package pl.golm.server;

import pl.golm.game.Game;
import pl.golm.game.GameSettings;
import pl.golm.game.GameState;
import pl.golm.game.Result;
import pl.golm.game.mechanics.util.GameUtils;
import pl.golm.game.model.Field;
import pl.golm.game.model.PlayerColor;
import pl.golm.game.model.impl.MoveImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Przemek on 04.12.2016.
 */
public class GameService implements Runnable
{
    private ClientSettings client1Settings;
    private GameSettings game1Settings;
    private ClientSettings client2Settings;
    private GameSettings game2Settings;
    private SurrenderListener surrenderListener;
    private Game game;

    public GameService(ClientSettings client1Settings, GameSettings game1Settings, ClientSettings client2Settings, GameSettings game2Settings)
    {
        setClient1Settings(client1Settings);
        setGame1Settings(game1Settings);
        setClient2Settings(client2Settings);
        setGame2Settings(game2Settings);
        setGame(new Game(game1Settings.getBoardSize(), game1Settings.getPlayerName(), game2Settings.getPlayerName()));
        setSurrenderListener(new SurrenderListener(getGame(), client1Settings.getBufferedReader(), client1Settings.getBufferedWriter(), client2Settings.getBufferedReader(), client2Settings.getBufferedWriter(), 2));
    }

    @Override
    public void run()
    {
        try
        {
            //as game starts information about start is sent to the clients
            game.setGameState(GameState.RUNNING);
            client1Settings.getBufferedWriter().write("Game started. You are player 1. Black");
            client2Settings.getBufferedWriter().write("Game started. You are player 2. White");
            // start listening if any of the players surrendered has already surrendered
            getSurrenderListener().run();
            // while game has not finished talk with the clients
            while (!game.getGameState().equals(GameState.FINISHED))
                communicateWithClients();
            // as game ends proper info about result of the match is sent
            if (game.getResult().equals(Result.DRAW))
            {
                client1Settings.getBufferedWriter().write("Draw");
                client2Settings.getBufferedWriter().write("Draw");
            }
            else if (game.getResult().equals(Result.PLAYER1_WON))
            {
                client1Settings.getBufferedWriter().write("You won");
                client2Settings.getBufferedWriter().write("You lost");
            }
            else
            {
                client1Settings.getBufferedWriter().write("You lost");
                client2Settings.getBufferedWriter().write("You won");
            }
            client1Settings.getBufferedWriter().write("Your points: " + Double.toString(GameUtils.countPoints(game.getPlayer1())));
            client1Settings.getBufferedWriter().write("Opponents points: " + Double.toString(GameUtils.countPoints(game.getPlayer2())));
            client2Settings.getBufferedWriter().write("Your points: " + Double.toString(GameUtils.countPoints(game.getPlayer1())));
            client2Settings.getBufferedWriter().write("Opponents points: " + Double.toString(GameUtils.countPoints(game.getPlayer2())));
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    public ClientSettings getClient1Settings()
    {
        return client1Settings;
    }

    public void setClient1Settings(ClientSettings client1Settings)
    {
        this.client1Settings = client1Settings;
    }

    public GameSettings getGame1Settings()
    {
        return game1Settings;
    }

    public void setGame1Settings(GameSettings game1Settings)
    {
        this.game1Settings = game1Settings;
    }

    public ClientSettings getClient2Settings()
    {
        return client2Settings;
    }

    public void setClient2Settings(ClientSettings client2Settings)
    {
        this.client2Settings = client2Settings;
    }

    public GameSettings getGame2Settings()
    {
        return game2Settings;
    }

    public void setGame2Settings(GameSettings game12Settings)
    {
        this.game2Settings = game12Settings;
    }

    public SurrenderListener getSurrenderListener()
    {
        return surrenderListener;
    }

    public void setSurrenderListener(SurrenderListener surrenderListener)
    {
        this.surrenderListener = surrenderListener;
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    private String getNextMove () // returns either surrender, pass or a legal move
    {
        try
        {
            if (game.getBoard().getHistory().size() == 0 || game.getBoard().getHistory().get(game.getBoard().getHistory().size() - 1).getPlayer().getColor().equals(PlayerColor.WHITE)) // if last move was white's or it is first move read this one from black
            {
                String line = client1Settings.getBufferedReader().readLine();
                int xMove = 0, yMove = 0;
                if (!line.equals("surrender") && !line.equals("pass"))
                {
                    xMove = Integer.parseInt(line.substring(0, line.indexOf(",")));
                    yMove = Integer.parseInt(line.substring(line.indexOf("," + 1)));
                }
                while (!line.equals("surrender") && !line.equals("pass") && !GameUtils.moveIsLegal(game.getBoard(), game.getBoard().getBoard().get(yMove).get(xMove), game.getPlayer1())) // the order of x/y is unknown atm
                {
                    line = client1Settings.getBufferedReader().readLine();
                    if (!line.equals("surrender") && !line.equals("pass"))
                    {
                        xMove = Integer.parseInt(line.substring(0, line.indexOf(",")));
                        yMove = Integer.parseInt(line.substring(line.indexOf("," + 1)));
                    }
                }
                return line;
            }
            else // last move was black's, read from white
            {
                String line = client2Settings.getBufferedReader().readLine();
                int xMove = 0, yMove = 0;
                if (!line.equals("surrender") && !line.equals("pass"))
                {
                    xMove = Integer.parseInt(line.substring(0, line.indexOf(",")));
                    yMove = Integer.parseInt(line.substring(line.indexOf("," + 1)));
                }
                while (!line.equals("surrender") && !line.equals("pass") && !GameUtils.moveIsLegal(game.getBoard(), game.getBoard().getBoard().get(yMove).get(xMove), game.getPlayer2())) // the order of x/y is unknown atm
                {
                    line = client2Settings.getBufferedReader().readLine();
                    if (!line.equals("surrender") && !line.equals("pass"))
                    {
                        xMove = Integer.parseInt(line.substring(0, line.indexOf(",")));
                        yMove = Integer.parseInt(line.substring(line.indexOf("," + 1)));
                    }
                }
                return line;
            }
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
            return "surrender"; // if error occurred it will count like surrender
        }
    }

    private void sendLastMove(String move)
    {
        try
        {
            if (game.getBoard().getHistory().get(game.getBoard().getHistory().size() - 1).getPlayer().getColor().equals(PlayerColor.BLACK)) // if last move was black's send it to white
                client2Settings.getBufferedWriter().write(move);
            else
                client1Settings.getBufferedWriter().write(move);
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    private void communicateWithClients() throws IOException // according to the game phase this functions executes just one of the blocks
    {
        if (game.getGameState().equals(GameState.RUNNING))
        {
            //first move is now read
            String move = getNextMove();
            while (game.getGameState().equals(GameState.RUNNING))
            {
                if (move.equals("surrender"))
                {
                    synchronized (this)
                    {
                        if (game.getBoard().getHistory().size() == 0 || game.getBoard().getHistory().get(game.getBoard().getHistory().size() - 1).getPlayer().getColor().equals(PlayerColor.WHITE)) // black surrendered
                        {
                            if (!game.getGameState().equals(GameState.FINISHED)) // to avoid concurrency
                            {
                                game.setResult(Result.PLAYER2_WON);
                                client1Settings.getBufferedWriter().write("You surrendered");
                                client2Settings.getBufferedWriter().write("Opponent surrendered");
                            }
                            game.setGameState(GameState.FINISHED);
                        }
                        else // white surrendered
                        {
                            if (!game.getGameState().equals(GameState.FINISHED)) // to avoid concurrency
                            {
                                game.setResult(Result.PLAYER1_WON);
                                client2Settings.getBufferedWriter().write("You surrendered");
                                client1Settings.getBufferedWriter().write("Opponent surrendered");
                            }
                            game.setGameState(GameState.FINISHED);
                        }
                        surrenderListener.setMode(0);
                    }
                }
                else if (move.equals("pass"))
                {
                    if (game.getBoard().getHistory().size() > 0 && game.getBoard().getHistory().get(game.getBoard().getHistory().size() - 1).getField() == null) // if is second pass
                    {
                        if (game.getBoard().getHistory().get(game.getBoard().getHistory().size() - 1).getPlayer().getColor().equals(PlayerColor.BLACK)) // white passed just after black
                        {
                            game.getBoard().getHistory().add(new MoveImpl(game.getPlayer2(), null, null));
                            client1Settings.getBufferedWriter().write("Opponent played: pass");
                        }
                        else
                        {
                            game.getBoard().getHistory().add(new MoveImpl(game.getPlayer1(), null, null));
                            client2Settings.getBufferedWriter().write("Opponent played: pass");
                        }
                        game.setGameState(GameState.COUNTING_TERRITORIES);
                        surrenderListener.setMode(2); // as black will now be counting whites territories it is white who will be listened to surrender.
                    }
                    else // is first pass
                    {
                        if (game.getBoard().getHistory().size() == 0 || game.getBoard().getHistory().get(game.getBoard().getHistory().size() - 1).getPlayer().getColor().equals(PlayerColor.WHITE)) // last was white or no one was
                        {
                            game.getBoard().getHistory().add(new MoveImpl(game.getPlayer1(), null, null));
                            surrenderListener.setMode(1);
                            client2Settings.getBufferedWriter().write("Opponent played: pass");
                        }
                        else
                        {
                            game.getBoard().getHistory().add(new MoveImpl(game.getPlayer2(), null, null));
                            surrenderListener.setMode(2);
                            client1Settings.getBufferedWriter().write("Opponent played: pass");
                        }
                        move = getNextMove();
                    }
                }
                else // is a legal not surrender not pass move
                {
                    if (game.getBoard().getHistory().size() == 0 || game.getBoard().getHistory().get(game.getBoard().getHistory().size() - 1).getPlayer().getColor().equals(PlayerColor.WHITE)) // last was white or it is first move
                    {
                        int moveX = Integer.parseInt(move.substring(0, move.indexOf(",")));
                        int moveY = Integer.parseInt(move.substring(move.indexOf("," + 1)));
                        game.getBoard().getHistory().add(new MoveImpl(game.getPlayer1(), game.getBoard().getBoard().get(moveY).get(moveX), new ArrayList<Field>(GameUtils.moveKills(game.getBoard(),  game.getBoard().getBoard().get(moveY).get(moveX), game.getPlayer1()))));
                        surrenderListener.setMode(1);
                        client2Settings.getBufferedWriter().write("Opponent played: " + move);
                    }
                    else
                    {
                        int moveX = Integer.parseInt(move.substring(0, move.indexOf(",")));
                        int moveY = Integer.parseInt(move.substring(move.indexOf("," + 1)));
                        game.getBoard().getHistory().add(new MoveImpl(game.getPlayer2(), game.getBoard().getBoard().get(moveY).get(moveX), new ArrayList<Field>(GameUtils.moveKills(game.getBoard(),  game.getBoard().getBoard().get(moveY).get(moveX), game.getPlayer2()))));
                        surrenderListener.setMode(2);
                        client1Settings.getBufferedWriter().write("Opponent played: " + move);
                    }
                    move = getNextMove();
                }
            }
            return;
        }
        if (game.getGameState().equals(GameState.COUNTING_TERRITORIES)) // in fact it is counting territories and dead groups in the same time
        {
            surrenderListener.setMode(2);
            client1Settings.getBufferedWriter().write("Pick opponents dead groups");
            client1Settings.getBufferedWriter().write("Suggested:");
            // check later if x and y are not switched
            GameUtils.getDeadGroups(game.getBoard(), game.getPlayer2()).forEach((Field field) -> {try {client1Settings.getBufferedWriter().write(Integer.toString(field.getColumn()) + "," + Integer.toString(field.getColumn()));} catch (IOException ioException){ioException.printStackTrace();}});
            client1Settings.getBufferedWriter().write("End suggested");
            String line = client1Settings.getBufferedReader().readLine();
            if (line.equals("surrender"))
                synchronized (this)
                {
                    if (!game.getGameState().equals(GameState.FINISHED)) // to avoid concurrency
                    {
                        game.setResult(Result.PLAYER2_WON);
                        client1Settings.getBufferedWriter().write("You surrendered");
                        client2Settings.getBufferedWriter().write("Opponent surrendered");
                    }
                    game.setGameState(GameState.FINISHED);
                }
            else if (line.equals("Dead groups"))
            {
                List<String> whiteDeadGroupsList = new ArrayList<String>();
                line = client1Settings.getBufferedReader().readLine();
                while (!line.equals("End dead groups"))
                {
                    whiteDeadGroupsList.add(line);
                    line = client1Settings.getBufferedReader().readLine();
                }
                surrenderListener.setMode(1);
                client2Settings.getBufferedWriter().write("Opponent suggested your dead groups:");
                whiteDeadGroupsList.forEach((String s) -> {try {client2Settings.getBufferedWriter().write(s);} catch (IOException ioException){ioException.printStackTrace();}});
                client2Settings.getBufferedWriter().write("End dead groups");
                line = client2Settings.getBufferedReader().readLine();
                if (line.equals("surrender"))
                    synchronized (this)
                    {
                        if (!game.getGameState().equals(GameState.FINISHED)) // to avoid concurrency
                        {
                            game.setResult(Result.PLAYER1_WON);
                            client2Settings.getBufferedWriter().write("You surrendered");
                            client1Settings.getBufferedWriter().write("Opponent surrendered");
                        }
                        game.setGameState(GameState.FINISHED);
                    }
                else if (line.equals("false"))
                {
                    game.getBoard().getHistory().add(new MoveImpl(game.getPlayer1(), null, null)); // so that white may start now
                    client1Settings.getBufferedWriter().write("false");
                    game.setGameState(GameState.RUNNING);
                    surrenderListener.setMode(1);
                    //TODO undo all changes
                }
                else // white agreed on his white groups
                {
                    client1Settings.getBufferedWriter().write("true");
                    for (String s : whiteDeadGroupsList) // dead stones are removed and added as prisoners
                    {
                        int fieldX = Integer.parseInt(s.substring(0, s.indexOf(",")));
                        int fieldY = Integer.parseInt(s.substring(s.indexOf(",") + 1));
                        if (game.getBoard().getBoard().get(fieldY).get(fieldX).getPlayer() != null && game.getBoard().getBoard().get(fieldY).get(fieldX).getPlayer().equals(game.getPlayer2()))
                        {
                            game.getBoard().getBoard().get(fieldY).get(fieldX).setPlayer(null);
                            game.getPlayer1().setPrisonerAmount(game.getPlayer1().getPrisonerAmount() + 1);
                        }
                    }
                    surrenderListener.setMode(1);
                    client2Settings.getBufferedWriter().write("Pick opponents dead groups");
                    client2Settings.getBufferedWriter().write("Suggested:");
                    GameUtils.getDeadGroups(game.getBoard(), game.getPlayer1()).forEach((Field field) -> {try {client2Settings.getBufferedWriter().write(Integer.toString(field.getColumn()) + "," + Integer.toString(field.getColumn()));} catch (IOException ioException){ioException.printStackTrace();}});
                    client2Settings.getBufferedWriter().write("End suggested");
                    line = client2Settings.getBufferedReader().readLine();
                    if (line.equals("surrender"))
                        synchronized (this)
                        {
                            if (!game.getGameState().equals(GameState.FINISHED)) // to avoid concurrency
                            {
                                game.setResult(Result.PLAYER1_WON);
                                client2Settings.getBufferedWriter().write("You surrendered");
                                client1Settings.getBufferedWriter().write("Opponent surrendered");
                            }
                            game.setGameState(GameState.FINISHED);
                        }
                    else if (line.equals("Dead groups"))
                    {
                        List<String> blackDeadGroupsList = new ArrayList<String>();
                        line = client2Settings.getBufferedReader().readLine();
                        while (!line.equals("End dead groups"))
                        {
                            blackDeadGroupsList.add(line);
                            line = client2Settings.getBufferedReader().readLine();
                        }
                        surrenderListener.setMode(2);
                        client1Settings.getBufferedWriter().write("Opponent suggested your dead groups:");
                        whiteDeadGroupsList.forEach((String s) -> {try {client1Settings.getBufferedWriter().write(s);} catch (IOException ioException){ioException.printStackTrace();}});
                        client1Settings.getBufferedWriter().write("End dead groups");
                        line = client1Settings.getBufferedReader().readLine();
                        if (line.equals("surrender"))
                            synchronized (this)
                            {
                                if (!game.getGameState().equals(GameState.FINISHED)) // to avoid concurrency
                                {
                                    game.setResult(Result.PLAYER2_WON);
                                    client1Settings.getBufferedWriter().write("You surrendered");
                                    client2Settings.getBufferedWriter().write("Opponent surrendered");
                                }
                                game.setGameState(GameState.FINISHED);
                            }
                        else if (line.equals("false"))
                        {
                            game.getBoard().getHistory().add(new MoveImpl(game.getPlayer2(), null, null)); // so that black may start now
                            client2Settings.getBufferedWriter().write("false");
                            game.setGameState(GameState.RUNNING);
                            surrenderListener.setMode(2);
                            //TODO undo all changes
                        }
                        else // black agreed on dead groups
                        {
                            client2Settings.getBufferedWriter().write("true");
                            for (String s : blackDeadGroupsList) // dead stones are removed and added as prisoners
                            {
                                int fieldX = Integer.parseInt(s.substring(0, s.indexOf(",")));
                                int fieldY = Integer.parseInt(s.substring(s.indexOf(",") + 1));
                                if (game.getBoard().getBoard().get(fieldY).get(fieldX).getPlayer() != null && game.getBoard().getBoard().get(fieldY).get(fieldX).getPlayer().equals(game.getPlayer1()))
                                {
                                    game.getBoard().getBoard().get(fieldY).get(fieldX).setPlayer(null);
                                    game.getPlayer2().setPrisonerAmount(game.getPlayer2().getPrisonerAmount() + 1);
                                }
                            }
                            surrenderListener.setMode(2);
                            client1Settings.getBufferedWriter().write("Pick opponents territories");
                            client1Settings.getBufferedWriter().write("Suggested:");
                            // check later if x and y are not switched
                            GameUtils.getTerritories(game.getBoard(), game.getPlayer2()).forEach((Field field) -> {try {client1Settings.getBufferedWriter().write(Integer.toString(field.getColumn()) + "," + Integer.toString(field.getColumn()));} catch (IOException ioException){ioException.printStackTrace();}});
                            client1Settings.getBufferedWriter().write("End suggested");
                            line = client1Settings.getBufferedReader().readLine();
                            if (line.equals("surrender"))
                                synchronized (this)
                                {
                                    if (!game.getGameState().equals(GameState.FINISHED)) // to avoid concurrency
                                    {
                                        game.setResult(Result.PLAYER2_WON);
                                        client1Settings.getBufferedWriter().write("You surrendered");
                                        client2Settings.getBufferedWriter().write("Opponent surrendered");
                                    }
                                    game.setGameState(GameState.FINISHED);
                                }
                            else if (line.toLowerCase().equals("territories"))
                            {
                                List<String> whiteTerritoriesList = new ArrayList<String>();
                                line = client1Settings.getBufferedReader().readLine();
                                while (!line.toLowerCase().equals("end territories"))
                                {
                                    whiteTerritoriesList.add(line);
                                    line = client1Settings.getBufferedReader().readLine();
                                }
                                surrenderListener.setMode(1);
                                client2Settings.getBufferedWriter().write("Opponent suggested your territories:");
                                whiteTerritoriesList.forEach((String s) -> {try {client2Settings.getBufferedWriter().write(s);} catch (IOException ioException){ioException.printStackTrace();}});
                                client2Settings.getBufferedWriter().write("End territories");
                                line = client2Settings.getBufferedReader().readLine();
                                if (line.equals("surrender"))
                                    synchronized (this)
                                    {
                                        if (!game.getGameState().equals(GameState.FINISHED)) // to avoid concurrency
                                        {
                                            game.setResult(Result.PLAYER1_WON);
                                            client2Settings.getBufferedWriter().write("You surrendered");
                                            client1Settings.getBufferedWriter().write("Opponent surrendered");
                                        }
                                        game.setGameState(GameState.FINISHED);
                                    }
                                else if (line.equals("false"))
                                {
                                    game.getBoard().getHistory().add(new MoveImpl(game.getPlayer1(), null, null)); // so that white may start now
                                    client1Settings.getBufferedWriter().write("false");
                                    game.setGameState(GameState.RUNNING);
                                    surrenderListener.setMode(1);
                                    //TODO undo all changes
                                }
                                else // white agreed on his territories
                                {
                                    client1Settings.getBufferedWriter().write("true");
                                    for (String s : whiteTerritoriesList) // territories are
                                    {
                                        int fieldX = Integer.parseInt(s.substring(0, s.indexOf(",")));
                                        int fieldY = Integer.parseInt(s.substring(s.indexOf(",") + 1));
                                        if (game.getBoard().getBoard().get(fieldY).get(fieldX).getPlayer() == null)
                                            game.getPlayer2().setTerritoryAmount(game.getPlayer2().getTerritoryAmount() + 1);
                                    }
                                    surrenderListener.setMode(1);
                                    client2Settings.getBufferedWriter().write("Pick opponents territories");
                                    client2Settings.getBufferedWriter().write("Suggested:");
                                    // check later if x and y are not switched
                                    GameUtils.getTerritories(game.getBoard(), game.getPlayer1()).forEach((Field field) -> {try {client2Settings.getBufferedWriter().write(Integer.toString(field.getColumn()) + "," + Integer.toString(field.getColumn()));} catch (IOException ioException){ioException.printStackTrace();}});
                                    client2Settings.getBufferedWriter().write("End suggested");
                                    line = client2Settings.getBufferedReader().readLine();
                                    if (line.equals("surrender"))
                                        synchronized (this)
                                        {
                                            if (!game.getGameState().equals(GameState.FINISHED)) // to avoid concurrency
                                            {
                                                game.setResult(Result.PLAYER1_WON);
                                                client2Settings.getBufferedWriter().write("You surrendered");
                                                client1Settings.getBufferedWriter().write("Opponent surrendered");
                                            }
                                            game.setGameState(GameState.FINISHED);
                                        }
                                    else if (line.toLowerCase().equals("territories"))
                                    {
                                        List<String> blackTerritoriesList = new ArrayList<String>();
                                        line = client2Settings.getBufferedReader().readLine();
                                        while (!line.toLowerCase().equals("end territories"))
                                        {
                                            blackTerritoriesList.add(line);
                                            line = client2Settings.getBufferedReader().readLine();
                                        }
                                        surrenderListener.setMode(2);
                                        client1Settings.getBufferedWriter().write("Opponent suggested your territories:");
                                        whiteTerritoriesList.forEach((String s) -> {try {client1Settings.getBufferedWriter().write(s);} catch (IOException ioException) {ioException.printStackTrace();}});
                                        client1Settings.getBufferedWriter().write("End territories");
                                        line = client1Settings.getBufferedReader().readLine();
                                        if (line.equals("surrender"))
                                            synchronized (this)
                                            {
                                                if (!game.getGameState().equals(GameState.FINISHED)) // to avoid concurrency
                                                {
                                                    game.setResult(Result.PLAYER2_WON);
                                                    client1Settings.getBufferedWriter().write("You surrendered");
                                                    client2Settings.getBufferedWriter().write("Opponent surrendered");
                                                }
                                                game.setGameState(GameState.FINISHED);
                                            }
                                        else if (line.equals("false"))
                                        {
                                            game.getBoard().getHistory().add(new MoveImpl(game.getPlayer2(), null, null)); // so that black may start now
                                            client2Settings.getBufferedWriter().write("false");
                                            game.setGameState(GameState.RUNNING);
                                            surrenderListener.setMode(2);
                                            //TODO undo all changes
                                        }
                                        else // black agreed on his territories
                                        {
                                            client2Settings.getBufferedWriter().write("true");
                                            for (String s : blackTerritoriesList) // territories are
                                            {
                                                int fieldX = Integer.parseInt(s.substring(0, s.indexOf(",")));
                                                int fieldY = Integer.parseInt(s.substring(s.indexOf(",") + 1));
                                                if (game.getBoard().getBoard().get(fieldY).get(fieldX).getPlayer() == null)
                                                    game.getPlayer1().setTerritoryAmount(game.getPlayer1().getTerritoryAmount() + 1);
                                            }
                                            game.setGameState(GameState.FINISHED);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
