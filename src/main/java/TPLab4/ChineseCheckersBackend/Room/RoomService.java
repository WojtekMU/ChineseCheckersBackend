package TPLab4.ChineseCheckersBackend.Room;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import TPLab4.ChineseCheckersBackend.User.User;


@Service
@Transactional
public class RoomService 
{
	@Autowired
	private RoomRepository roomRepository;
	
	public Room createRoom(User player) 
	{
		Room room = new Room();
		room.getPlayers().add(player);

		roomRepository.save(room);

		return room;
	}
	
	public List<Room> getRoomsToJoin() 
	{
		return roomRepository.findAll();
	}

    public void joinGame(User player, Room room) 
    {
    	room.getPlayers().add(player);
   
        roomRepository.save(room);
    }
}
