package server.pl.golm.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Dominik on 2016-12-09.
 */
public class ClientSettings
{
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter bufferedWriter;

    public ClientSettings(Socket socket, BufferedReader bufferedReader, PrintWriter bufferedWriter)
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

    public PrintWriter getBufferedWriter()
    {
        return bufferedWriter;
    }

    public void setBufferedWriter(PrintWriter bufferedWriter)
    {
        this.bufferedWriter = bufferedWriter;
    }
}
