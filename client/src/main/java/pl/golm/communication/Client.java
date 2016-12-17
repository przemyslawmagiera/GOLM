package pl.golm.communication;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import pl.golm.controller.GameController;
import pl.golm.gui.ConfigurationWindow;
import pl.golm.gui.impl.ConfigurationWindowImpl;

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

    public void configure() throws IOException
    {
        socket = new Socket("localhost", 5000);
        InputStreamReader readStream = new InputStreamReader(socket.getInputStream());
        reader = new BufferedReader(readStream);
        writer = new PrintWriter(socket.getOutputStream());
    }

    public void sendMessage(String message)
    {
        try
        {
            writer.println(message);
            writer.flush();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public class MessageReciver implements Runnable
    {
        String message;

        public void run()
        {
            try
            {
                while ((message = reader.readLine()) != null)
                {
                    //TODO parse messages, interpretation
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }
}
