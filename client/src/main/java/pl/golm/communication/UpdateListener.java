package pl.golm.communication;

import pl.golm.controller.GameController;

import java.io.BufferedReader;
import java.io.InterruptedIOException;

/**
 * Created by Przemek on 17.12.2016.
 */
public class UpdateListener implements Runnable
{
    private BufferedReader reader;
    private GameController controller;

    public UpdateListener(BufferedReader reader, GameController controller)
    {
        this.reader = reader;
        this.controller = controller;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                Thread.sleep(100);
                String line = reader.readLine();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
