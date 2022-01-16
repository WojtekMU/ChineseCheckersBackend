package TPLab4.ChineseCheckersBackend.History;

/**
 * Exception for cannot add player to history.
 */
public class CannotAddPlayerToHistoryException extends RuntimeException
{
    /**
     * Exception constructor.
     * @param errorMessage Error message
     */
    public CannotAddPlayerToHistoryException(String errorMessage)
    {
        super(errorMessage);
    }
}
