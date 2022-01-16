package TPLab4.ChineseCheckersBackend.Game;

/**
 * Exception for can not create game.
 */
public class CantCreateGameException extends RuntimeException
{
    /**
     * Exception constructor
     * @param errorMessage Error message
     */
    public CantCreateGameException(String errorMessage)
    {
        super(errorMessage);
    }
}

