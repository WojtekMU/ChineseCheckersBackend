package TPLab4.ChineseCheckersBackend.Room;

public class CantCreateRoomException extends RuntimeException
{
    public CantCreateRoomException(String errorMessage)
    {
        super(errorMessage);
    }
}