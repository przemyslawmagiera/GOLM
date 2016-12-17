package pl.golm.communication;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import pl.golm.controller.GameController;
import pl.golm.gui.ConfigurationWindow;
import pl.golm.gui.impl.ConfigurationWindowImpl;

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
        return message;
    }
}
