package TPLab4.ChineseCheckersBackend.Tile;

/**
 * Exception thrown when tile was not found.
 */
public class TileNotFoundException extends RuntimeException {
    /**
     * Exception constructor.
     *
     * @param errorMessage Error message
     */
    public TileNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}