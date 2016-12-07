package pl.golm.server;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Przemek on 04.12.2016.
 */
public class ClientService implements Runnable
{
    BufferedReader reader;
    Socket socket;

    public ClientService(Socket clientSocket)
    {
        try
        {
            socket = clientSocket;
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(inputStreamReader);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        String message;
        try
        {
            while ((message = reader.readLine()) != null)
            {
                System.out.println("otrzymalem: " + message);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
