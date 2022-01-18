package TPLab4.ChineseCheckersBackend.Room;

/**
 * Exception thrown when room could not have been found.
 */
public class RoomNotFoundException extends RuntimeException {
    /**
     * Exception constructor.
     *
     * @param errorMessage Error message
     */
    public RoomNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}