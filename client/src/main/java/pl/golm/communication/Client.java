package pl.golm.communication;

import pl.golm.controller.GameController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Przemek on 04.12.2016.
 */
public class Client
{
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public static void main(String[] args)
    {
        Client client = new Client();
        client.configure();
        GameController controller = GameController.getInstance();
        controller.init();
    }

    private void configure()
    {
        try
        {
            socket = new Socket("localhost", 5000);
            InputStreamReader readStream = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(readStream);
            writer = new PrintWriter(socket.getOutputStream());
        }
        catch (IOException ex)
        {
            System.out.println("nie moge polaczyc sie z serwerem");
        }
    }
}
