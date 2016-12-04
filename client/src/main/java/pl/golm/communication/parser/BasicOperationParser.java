package pl.golm.communication.parser;

import pl.golm.communication.dto.GameDto;

/**
 * Created by Przemek on 30.11.2016.
 */
public class BasicOperationParser
{
    public static String parseGameDto(GameDto gameDto)
    {
        return "START GAME " + gameDto.getType() + gameDto.getSize() + gameDto.getPlayerName();
    }
}
