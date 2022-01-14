package TPLab4.ChineseCheckersBackend.Room;

public class CantJoinRoomException extends RuntimeException
{
    public CantJoinRoomException(String errorMessage)
    {
        super(errorMessage);
    }
}