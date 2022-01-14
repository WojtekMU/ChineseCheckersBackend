package TPLab4.ChineseCheckersBackend.Tile;

public class TileNotFoundException extends RuntimeException
{
    public TileNotFoundException(String errorMessage)
    {
        super(errorMessage);
    }
}