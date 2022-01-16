package TPLab4.ChineseCheckersBackend.Room;

/**
 * Exception thrown when room could not have been left from.
 */
public class CantLeaveRoomException extends RuntimeException
{
    /**
     * Exception constructor.
     * @param errorMessage Error message
     */
    public CantLeaveRoomException(String errorMessage)
    {
        super(errorMessage);
    }
}