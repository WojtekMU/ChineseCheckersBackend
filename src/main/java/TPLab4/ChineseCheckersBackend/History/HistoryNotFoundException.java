package TPLab4.ChineseCheckersBackend.History;

public class HistoryNotFoundException extends RuntimeException
{
    public HistoryNotFoundException(String errorMessage)
    {
        super(errorMessage);
    }
}
