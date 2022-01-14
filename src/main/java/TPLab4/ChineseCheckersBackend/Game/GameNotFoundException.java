package TPLab4.ChineseCheckersBackend.Game;

public class GameNotFoundException extends RuntimeException
{
    public GameNotFoundException(String errorMessage)
    {
        super(errorMessage);
    }
}

