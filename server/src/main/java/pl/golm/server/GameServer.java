package pl.golm.server;

import pl.golm.game.GameSettings;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
        try (ServerSocket serverSocket = new ServerSocket(5000))
        {
            while (true)
            {
                Socket playerSocket = serverSocket.accept();
                BufferedWriter playerWritter = new BufferedWriter(new PrintWriter(playerSocket.getOutputStream()));
                playerWritter.write("Connected to server. Please specify game settings.");
                playerWritter.flush();
                BufferedReader playerReader = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
                int boardSize = Integer.parseInt(playerReader.readLine());
                boolean isMultiplayer = Boolean.parseBoolean(playerReader.readLine());
                String playerName = playerReader.readLine();
                GameSettings gameSettings = new GameSettings(boardSize, isMultiplayer, playerName);
                ClientSettings clientSettings = new ClientSettings(playerSocket, playerReader, playerWritter);
                if (!gameSettings.isMultiPlayer())
                {
                    //TODO implement running a single player game
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
                            gameService.run();
                            entries.remove();
                        }
                    }
                    if (!foundOpponent)
                        gameClientSettingsMap.put(gameSettings, clientSettings);
                }
            }
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }
}
