package TPLab4.ChineseCheckersBackend.Game;

/**
 * Exception for game not found.
 */
public class GameNotFoundException extends RuntimeException {
    /**
     * Exception constructor
     *
     * @param errorMessage Error message
     */
    public GameNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

