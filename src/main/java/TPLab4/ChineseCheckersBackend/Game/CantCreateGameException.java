package TPLab4.ChineseCheckersBackend.Game;

public class CantCreateGameException extends RuntimeException
{
    public CantCreateGameException(String errorMessage)
    {
        super(errorMessage);
    }
}

