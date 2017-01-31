package client.pl.golm.communication.parser;

import client.pl.golm.communication.dto.GameDto;
import client.pl.golm.gui.Circle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Przemek on 30.11.2016.
 */
public class BasicOperationParser
{
    public static List<String> parseRequestGame(GameDto gameDto)
    {
        List<String> message = new ArrayList<>();
        message.add(gameDto.getSize().toString());
        if(gameDto.getType().equals("Multi player"))
        {
            message.add("true");
        }
        else
        {
            message.add("false");
        }
        message.add(gameDto.getPlayerName());

        return message;
    }

    public static void parseMappingToCircles(String message, ArrayList<ArrayList<Circle>> circles)
    {
        String[] props = message.split(",");
        Circle actual = circles.get(Integer.parseInt(props[1])).get(Integer.parseInt(props[2]));
        if(props[0].equals("b"))
        {
            actual.setColor(Color.BLACK);
            actual.setOccupied(true);
        }
        else
        {
            actual.setColor(Color.WHITE);
            actual.setOccupied(true);
        }
    }

    public static void prepareMappingForCounting(String message, ArrayList<ArrayList<Circle>> circles)
    {
        String[] props = message.split(",");
        Circle actual = circles.get(Integer.parseInt(props[0])).get(Integer.parseInt(props[1]));
        actual.setSignature(Color.GREEN);
        //actual.setOccupied(true);
    }

    public static List<String> prepareCountedTerritoriesMessage(ArrayList<ArrayList<Circle>> circles, int size)
    {
        List<String> messages = new ArrayList<>();
        messages.add("Dead groups");
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {//get y, get x
                if(Color.GREEN.equals(circles.get(j).get(i).getSignature()))
                {
                    messages.add(j + "," + i);
                }
            }
        }
        messages.add("End dead groups");
        return messages;
    }
    public static List<String> prepareCountedTerritoriesMessage(String selected, int size)
    {
        List<String> messages = new ArrayList<>();
        messages.add("Dead groups");
        String[] selectedPoints = selected.split(" ");
        for (String selectedPoint : selectedPoints)
        {
            String[] xy = selectedPoint.split(",");
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            messages.add(y + "," + x);
        }
        messages.add("End dead groups");
        return messages;
    }

    public static List<String> prepareCountedTerritoriesMessageWhichIsIndeedTerritories(ArrayList<ArrayList<Circle>> circles, int size)
    {
        List<String> messages = new ArrayList<>();
        messages.add("territories");
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {//get y, get x
                if(Color.GREEN.equals(circles.get(j).get(i).getSignature()))
                {
                    messages.add(j + "," + i);
                }
            }
        }
        messages.add("end territories");
        return messages;
    }

}
