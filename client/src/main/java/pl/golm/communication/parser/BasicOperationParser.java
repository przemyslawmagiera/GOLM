package pl.golm.communication.parser;

import pl.golm.communication.dto.GameDto;
import pl.golm.gui.Circle;

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

}
