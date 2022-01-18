package TPLab4.ChineseCheckersBackend.History;

/**
 * Exception for history not found.
 */
public class HistoryNotFoundException extends RuntimeException {
    /**
     * Exception constructor.
     *
     * @param errorMessage Error message
     */
    public HistoryNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
