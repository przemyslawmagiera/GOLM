package pl.golm.server;

import pl.golm.bot.Bot;
import pl.golm.bot.impl.Ticobot;
import pl.golm.game.GameSettings;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Created by Przemek on 04.12.2016.
 */
public class GameServer
{
    private Map<GameSettings, ClientSettings> gameClientSettingsMap;

    public static void main(String[] args)
    {
        new GameServer().start();
    }

    private GameServer()
    {
        gameClientSettingsMap = new HashMap<GameSettings, ClientSettings>();
    }

    private void start()
    {
        try (ServerSocket serverSocket = new ServerSocket(5000); ServerSocket botSocket = new ServerSocket(5724))
        {
            while (true)
            {
                Socket playerSocket = serverSocket.accept();
                PrintWriter playerWritter = new PrintWriter(playerSocket.getOutputStream());
                //playerWritter.println("Connected to server. Please specify game settings.");
                //playerWritter.flush();
                BufferedReader playerReader = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
                int boardSize = Integer.parseInt(playerReader.readLine());
                boolean isMultiplayer = Boolean.parseBoolean(playerReader.readLine());
                String playerName = playerReader.readLine();
                GameSettings gameSettings = new GameSettings(boardSize, isMultiplayer, playerName);
                ClientSettings clientSettings = new ClientSettings(playerSocket, playerReader, playerWritter);
                if (!gameSettings.isMultiPlayer())
                {
                    Bot bot = new Ticobot(gameSettings);
                    new Thread(bot).start();
                    Socket socket = botSocket.accept();
                    PrintWriter botWriter = new PrintWriter(socket.getOutputStream());
                    botWriter.println("Connected to server.");
                    botWriter.flush();
                    BufferedReader botReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    GameService gameService;
                    Random random = new Random();
                    if (random.nextBoolean())
                        gameService = new GameService( new ClientSettings(socket, botReader, botWriter), new GameSettings(gameSettings.getBoardSize(), false, Ticobot.botName), clientSettings, gameSettings);
                    else
                        gameService = new GameService(clientSettings, gameSettings, new ClientSettings(socket, botReader, botWriter), new GameSettings(gameSettings.getBoardSize(), false, Ticobot.botName));
                    new Thread (gameService).start();
                }
                else
                {
                    boolean foundOpponent = false;
                    Iterator<Map.Entry<GameSettings, ClientSettings>> entries = gameClientSettingsMap.entrySet().iterator();
                    while (!foundOpponent && entries.hasNext())
                    {
                        Map.Entry<GameSettings, ClientSettings> entry = entries.next();
                        if (entry.getKey().equals(gameSettings))
                        {
                            foundOpponent = true;
                            GameService gameService = new GameService(entry.getValue(), entry.getKey(), clientSettings, gameSettings);
                            new Thread (gameService).start();
                            entries.remove();
                        }
                    }
                    if (!foundOpponent)
                        gameClientSettingsMap.put(gameSettings, clientSettings);
                }
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
