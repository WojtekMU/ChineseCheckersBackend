package TPLab4.ChineseCheckersBackend.Room;

/**
 * Exception thrown when room could not have been created.
 */
public class CantCreateRoomException extends RuntimeException {
    /**
     * Exception constructor.
     *
     * @param errorMessage Error message
     */
    public CantCreateRoomException(String errorMessage) {
        super(errorMessage);
    }
}