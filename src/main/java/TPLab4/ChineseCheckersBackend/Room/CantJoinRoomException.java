package TPLab4.ChineseCheckersBackend.Room;

/**
 * Exception thrown when room could not have been joined to.
 */
public class CantJoinRoomException extends RuntimeException
{
    /**
     * Exception constructor.
     * @param errorMessage Error message
     */
    public CantJoinRoomException(String errorMessage)
    {
        super(errorMessage);
    }
}