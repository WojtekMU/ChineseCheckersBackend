package TPLab4.ChineseCheckersBackend.Room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.User.User;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RoomService 
{
	@Autowired
	private RoomRepository roomRepository;

	private void validate(Room room, User user) throws AccessDeniedException
	{
		if(!room.getPlayers().contains(user))
		{
			throw new AccessDeniedException("User does not belong to this room!");
		}
	}

	public Room loadRoomById(Long roomId) throws RoomNotFoundException
	{
		Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("Room does not exist!"));

		return room;
	}

	public List<User> getPlayersInRoom(Room room, User user) throws AccessDeniedException
	{
		validate(room, user);

		return room.getPlayers();
	}

	public String getGameStarted(Room room, User user) throws AccessDeniedException
	{
		validate(room, user);

		return room.isGameStarted().toString();
	}

	public Long getGameId(Room room, User user) throws AccessDeniedException
	{
		validate(room, user);

		return room.getGame().getId();
	}

	public Date getLastUpdate(Room room, User user) throws AccessDeniedException
	{
		validate(room, user);

		return room.getLastUpdate();
	}

	public List<Room> loadRoomList()
	{
		return roomRepository.findAll();
	}

	public Room createRoom(User user) throws AccessDeniedException, CantCreateRoomException
	{
		if(user.getRooms().size() != 0)
		{
			throw new CantCreateRoomException("You cannot be in more than one room!");
		}

		Room room = new Room();
		room.getPlayers().add(user);
		room.setGameStarted(false);

		roomRepository.save(room);

		return room;
	}
	
    public void joinRoom(User user, Room room) throws AccessDeniedException, CantJoinRoomException
    {
		if(user.getRooms().size() != 0)
		{
			throw new CantJoinRoomException("You cannot be in more than one room!");
		}

		if(room.getGameStarted().equals(true))
		{
			throw new CantJoinRoomException("Game has already started in this room!");
		}

		if(room.getPlayers().size() == 6)
		{
			throw new CantJoinRoomException("Room is full!");
		}

	    room.getPlayers().add(user);
		room.setLastUpdate(new Date());

	    roomRepository.save(room);
    }
    
    public void leaveRoom(User user, Room room) throws AccessDeniedException, CantLeaveRoomException
    {
		validate(room, user);

		if(room.getGameStarted().equals(true))
		{
			throw new CantLeaveRoomException("Cannot leave room while in game!");
		}

	    room.getPlayers().remove(user);
		room.setLastUpdate(new Date());
	    
	    if(room.getPlayers().size() == 0)
	    {
	    	roomRepository.delete(room);
	    }
	    else
	    {
	    	roomRepository.save(room);
	    }
    }
    
    public void setGameStarted(Room room, Game game)
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
