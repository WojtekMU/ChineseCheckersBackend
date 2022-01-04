package TPLab4.ChineseCheckersBackend.Room;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TPLab4.ChineseCheckersBackend.Game.Game;
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
	
    public void joinRoom(User player, Room room) 
    {
	    room.getPlayers().add(player);
	 	   
	    roomRepository.save(room);
    }
    
    public void leaveRoom(User player, Room room) 
    {
	    room.getPlayers().remove(player);
	    
	    if(room.getPlayers().size() == 0)
	    {
	    	roomRepository.delete(room);
	    }
	    else
	    {
	    	roomRepository.save(room);
	    }
    }
    
    public void startGame(Room room, Game game) 
    {
    	room.setGameStarted(true);
    	room.setGame(game);
    	
    	roomRepository.save(room);
    }
    
    public void detachGame(Room room) 
    {
    	room.setGame(null);
    	room.setGameStarted(false);
    	
    	roomRepository.save(room);
    }
}
