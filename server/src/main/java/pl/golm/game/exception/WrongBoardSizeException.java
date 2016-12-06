package pl.golm.game.exception;

/**
 * exception thrown when intended to use illegal board sizes
 * @author Dominik Lachowicz, Przemysław Magiera
 * @version 1.0
 */
public class WrongBoardSizeException extends RuntimeException
{
    public WrongBoardSizeException(String message)
    {
        super(message);
    }
}


