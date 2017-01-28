package client.pl.golm.communication;

import client.pl.golm.controller.WebController;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by Przemek on 04.12.2016.
 */
public class Client
{
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private WebController controller = WebController.getInstance();

    public void configure() throws Exception
    {
        socket = new Socket("localhost", 5000);
        InputStreamReader readStream = new InputStreamReader(socket.getInputStream());
        reader = new BufferedReader(readStream);
        writer = new PrintWriter(socket.getOutputStream());
    }

    public void sendMessage(List<String> message)
    {
        message.forEach(m ->
        {
            writer.println(m);
            writer.flush();
        });
    }

    public String readMessage()
    {
        String message = null;
        try
        {
            message = reader.readLine();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        if(message.equals("Opponent surrendered"))
        {
            //todo collect info from server(nie wiem co mi wysylasz tu)
            //message = you won, your points: client.readline() ..i tak dalej
            controller.opponentSurrendered(message);
        }
        return message;
    }
}
