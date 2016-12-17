package pl.golm.communication.parser;

import pl.golm.communication.dto.GameDto;

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
}
