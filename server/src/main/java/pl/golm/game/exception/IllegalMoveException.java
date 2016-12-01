package pl.golm.game.exception;

/**
 * Created by Ktoś on 29.11.2016.
 */
public class IllegalMoveException extends RuntimeException
{
    public IllegalMoveException()
    {
        super("Illegal move");
    }

    public IllegalMoveException(String message)
    {
        super(message);
    }

}
