package pl.golm.game.exception;

/**
 * Created by Ktoś on 29.11.2016.
 */
public class WrongBoardSizeException extends RuntimeException
{

    public WrongBoardSizeException()
    {
        super("At least one of the field coordinates was out of [0-18] range. All fields must be in in that range");
    }

    public WrongBoardSizeException(String message)
    {
        super(message);
    }
}


