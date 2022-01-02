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
		room.setGameStarted(false);

		roomRepository.save(room);

		return room;
	}
	
    public void joinGame(User player, Room room) 
    {
    	if(room.getPlayers().contains(player))
    	{
    		throw new IllegalArgumentException("Can't join your own room!");
    	}
    	else if(room.getPlayers().size() == 6)
    	{
    		throw new IllegalArgumentException("Room is full!");
    	}
    	else
    	{
	    	room.getPlayers().add(player);
	 	   
	    	roomRepository.save(room);
    	}
    }
    
    public void detachGame(Room room) 
    {
    	room.setGame(null);
    	room.setGameStarted(false);
    	
    	roomRepository.save(room);
    }
}
