package TPLab4.ChineseCheckersBackend.Room;

public class CantLeaveRoomException extends RuntimeException
{
    public CantLeaveRoomException(String errorMessage)
    {
        super(errorMessage);
    }
}