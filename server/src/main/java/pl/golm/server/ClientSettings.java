package pl.golm.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

/**
 * Created by Dominik on 2016-12-09.
 */
public class ClientSettings
{
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public ClientSettings(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
        setSocket(socket);
        setBufferedReader(bufferedReader);
        setBufferedWriter(bufferedWriter);
    }

    public Socket getSocket()
    {
        return socket;
    }

    public void setSocket(Socket socket)
    {
        this.socket = socket;
    }

    public BufferedReader getBufferedReader()
    {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader)
    {
        this.bufferedReader = bufferedReader;
    }

    public BufferedWriter getBufferedWriter()
    {
        return bufferedWriter;
    }

    public void setBufferedWriter(BufferedWriter bufferedWriter)
    {
        this.bufferedWriter = bufferedWriter;
    }
}
