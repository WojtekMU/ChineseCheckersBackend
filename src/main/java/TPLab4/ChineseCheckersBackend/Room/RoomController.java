package TPLab4.ChineseCheckersBackend.Room;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Request.CanSeeRoomRequest;
import TPLab4.ChineseCheckersBackend.Request.CreateRoomRequest;
import TPLab4.ChineseCheckersBackend.Request.GameStartedRequest;
import TPLab4.ChineseCheckersBackend.Request.GameIdRequest;
import TPLab4.ChineseCheckersBackend.Request.JoinRequest;
import TPLab4.ChineseCheckersBackend.Request.LastGameUpdateRequest;
import TPLab4.ChineseCheckersBackend.Request.LastRoomUpdateRequest;
import TPLab4.ChineseCheckersBackend.Request.LeaveRoomRequest;
import TPLab4.ChineseCheckersBackend.Request.PlayersInRoomRequest;
import TPLab4.ChineseCheckersBackend.Response.MessageResponse;
import TPLab4.ChineseCheckersBackend.Tile.TileColor;
import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserRepository;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/chineseCheckers")
public class RoomController 
{
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private UserRepository userRepository;
	
    @PostMapping(value = "/createRoom")
    public ResponseEntity<?> createNewGame(@Valid @RequestBody CreateRoomRequest createRoomRequest)
    {
    	Optional<User> user = userRepository.findById(createRoomRequest.getUserId());
    	
    	if(user.isEmpty())
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("User does not exist!"));
    	}
    	
    	if(user.get().getRooms().size() != 0)
    	{
        	return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("You cannot be in more than one room!"));
    	}
    	
    	Room room = roomService.createRoom(userRepository.findById(createRoomRequest.getUserId()).get());
    	
		return ResponseEntity.ok(room.getId());
    }
	
    @GetMapping(value = "/roomList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getGamesToJoin()
    {
        return ResponseEntity.ok(roomRepository.findAll());
    }
    
    @PostMapping(value = "/playerList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPlayersInRoom(@Valid @RequestBody PlayersInRoomRequest playersInRoomRequest)
    {
		Optional<Room> room = roomRepository.findById(playersInRoomRequest.getRoomId());

		if(room.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Room does not exist!"));
		}

        return ResponseEntity.ok(room.get().getPlayers());
    }
    
    @PostMapping(value = "/joinRoom")
    public ResponseEntity<?> joinGame(@Valid @RequestBody JoinRequest joinRequest)
    {
    	Optional<Room> room = roomRepository.findById(joinRequest.getRoomId());
    	Optional<User> user = userRepository.findById(joinRequest.getUserId());
    	
    	if(user.isEmpty())
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("User does not exist!"));
    	}
    	
    	if(room.isEmpty())
    	{
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Room does not exist!"));
    	}
    	
    	if(room.get().getPlayers().contains(user.get()))
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("You cannot join your own room!"));
    	}
    	
    	if(room.get().getPlayers().size() == 6)
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Room is full!"));
    	}
    	
    	if(user.get().getRooms().size() != 0)
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("You cannot be in more than one room!"));
    	}
    	
    	if(room.get().isGameStarted())
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Game already started!"));
    	}

	    roomService.joinRoom(user.get(), room.get());
	    	
		return ResponseEntity.ok(new MessageResponse("Successfully joined room!"));
    }
    
    @PostMapping(value = "/leaveRoom")
    public ResponseEntity<?> leaveRoom(@Valid @RequestBody LeaveRoomRequest leaveRoomRequest)
    {
    	Optional<Room> room = roomRepository.findById(leaveRoomRequest.getRoomId());
    	Optional<User> user = userRepository.findById(leaveRoomRequest.getUserId());
    	
    	if(user.isEmpty())
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("User does not exist!"));
    	}
    	
    	if(room.isEmpty())
    	{
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Room does not exist!"));
    	}
    	
    	if(!room.get().getPlayers().contains(user.get()))
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("You are not in this room!"));
    	}
    	
    	if(room.get().isGameStarted())
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("You cannot leave while in game!"));
    	}

	    roomService.leaveRoom(user.get(), room.get());
	    	
		return ResponseEntity.ok(new MessageResponse("Successfully left room!"));
    }
    
    @PostMapping(value = "/gameStarted")
    public ResponseEntity<?> gameStarted(@Valid @RequestBody GameStartedRequest gameStartedRequest)
    {
		Optional<Room> room = roomRepository.findById(gameStartedRequest.getRoomId());

		if(room.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Room does not exist!"));
		}

		return ResponseEntity.ok(room.get().isGameStarted().toString());
    }
    
    @PostMapping(value = "/gameId")
    public ResponseEntity<?> getGameId(@Valid @RequestBody GameIdRequest getGameIdRequest)
    {
		Optional<Room> room = roomRepository.findById(getGameIdRequest.getRoomId());

		if(room.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Room does not exist!"));
		}

		return ResponseEntity.ok(room.get().getGame().getId());
    }
    
    @PostMapping(value = "/canSeeRoom")
    public ResponseEntity<?> canSeeRoom(@Valid @RequestBody CanSeeRoomRequest canSeeRoomRequest)
    {
		Optional<Room> room = roomRepository.findById(canSeeRoomRequest.getRoomId());
		Optional<User> user = userRepository.findById(canSeeRoomRequest.getUserId());
		
    	if(user.isEmpty())
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("User does not exist!"));
    	}
		
		if(room.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Room does not exist!"));
		}
		
		if(!room.get().getPlayers().contains(user.get()))
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("You are not in this room!"));
		}
		
		return ResponseEntity.ok(new MessageResponse("ok")); 
	}
    
    @PostMapping(value = "/lastRoomUpdate")
    public ResponseEntity<?> getLastUpdate(@Valid @RequestBody LastRoomUpdateRequest lastRoomUpdateRequest)
    {
    	Optional<Room> room = roomRepository.findById(lastRoomUpdateRequest.getRoomId());

		if(room.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Room does not exist!"));
		}

    	return ResponseEntity.ok(room.get().getLastUpdate());
    }
}
