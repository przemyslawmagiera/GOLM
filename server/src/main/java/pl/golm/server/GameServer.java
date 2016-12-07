package pl.golm.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Przemek on 04.12.2016.
 */
public class GameServer
{
    ArrayList clientOutputStreams;

    public static void main(String[] args)
    {
        new GameServer().start();
    }

    public void start()
    {
        clientOutputStreams = new ArrayList();
        try
        {
            ServerSocket serverSocket = new ServerSocket(5000);
            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);

                Thread t = new Thread(new ClientService(clientSocket));
                t.start();
                System.out.println("polaczono z klientem");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
