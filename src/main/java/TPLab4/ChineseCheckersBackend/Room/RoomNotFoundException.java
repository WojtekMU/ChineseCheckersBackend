package TPLab4.ChineseCheckersBackend.Room;

public class RoomNotFoundException extends RuntimeException
{
    public RoomNotFoundException(String errorMessage)
    {
        super(errorMessage);
    }
}