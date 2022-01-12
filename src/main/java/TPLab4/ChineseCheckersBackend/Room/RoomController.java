package TPLab4.ChineseCheckersBackend.Room;


import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import TPLab4.ChineseCheckersBackend.Request.JoinRequest;
import TPLab4.ChineseCheckersBackend.Request.LeaveRoomRequest;
import TPLab4.ChineseCheckersBackend.Response.MessageResponse;
import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserRepository;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/chineseCheckers/room")
public class RoomController 
{
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private UserRepository userRepository;

	public void validate(Optional<Room> room, Optional<User> user) throws AccessDeniedException
	{
		if(room.isEmpty())
		{
			throw new AccessDeniedException("Room does not exist!");
		}

		if(user.isEmpty())
		{
			throw new AccessDeniedException("User does not exist!");
		}

		if(!room.get().getPlayers().contains(user.get()))
		{
			throw new AccessDeniedException("User does not belong to this room!");
		}
	}

	@PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/create")
    public ResponseEntity<?> createNewGame(Principal principal)
    {
    	Optional<User> user = userRepository.findByUsername(principal.getName());
    	
    	if(user.isEmpty())
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("User does not exist!"));
    	}
    	
    	if(user.get().getRooms().size() != 0)
    	{
        	return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("You cannot be in more than one room!"));
    	}
    	
    	Room room = roomService.createRoom(user.get());
    	
		return ResponseEntity.ok(room.getId());
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/roomList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getGamesToJoin()
    {
        return ResponseEntity.ok(roomRepository.findAll());
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/playerList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPlayersInRoom(@RequestParam Long roomId, Principal principal)
    {
		Optional<Room> room = roomRepository.findById(roomId);
		Optional<User> user = userRepository.findByUsername(principal.getName());

		try
		{
			validate(room, user);
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}

        return ResponseEntity.ok(room.get().getPlayers());
    }

	@PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/join")
    public ResponseEntity<?> joinGame(@Valid @RequestBody JoinRequest joinRequest, Principal principal)
    {
    	Optional<Room> room = roomRepository.findById(joinRequest.getRoomId());
    	Optional<User> user = userRepository.findByUsername(principal.getName());
    	
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

	@PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/leave")
    public ResponseEntity<?> leaveRoom(@Valid @RequestBody LeaveRoomRequest leaveRoomRequest, Principal principal)
    {
    	Optional<Room> room = roomRepository.findById(leaveRoomRequest.getRoomId());
    	Optional<User> user = userRepository.findByUsername(principal.getName());

		try
		{
			validate(room, user);
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
    	
    	if(room.get().isGameStarted())
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("You cannot leave while in game!"));
    	}

	    roomService.leaveRoom(user.get(), room.get());
	    	
		return ResponseEntity.ok(new MessageResponse("Successfully left room!"));
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/gameStarted")
    public ResponseEntity<?> gameStarted(@RequestParam Long roomId, Principal principal)
    {
		Optional<Room> room = roomRepository.findById(roomId);
		Optional<User> user = userRepository.findByUsername(principal.getName());

		try
		{
			validate(room, user);
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}

		return ResponseEntity.ok(room.get().isGameStarted().toString());
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/gameId")
    public ResponseEntity<?> getGameId(@RequestParam Long roomId, Principal principal)
    {
		Optional<Room> room = roomRepository.findById(roomId);
		Optional<User> user = userRepository.findByUsername(principal.getName());

		try
		{
			validate(room, user);
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}

		return ResponseEntity.ok(room.get().getGame().getId());
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/lastUpdate")
    public ResponseEntity<?> getLastUpdate(@RequestParam Long roomId, Principal principal)
    {
    	Optional<Room> room = roomRepository.findById(roomId);
		Optional<User> user = userRepository.findByUsername(principal.getName());

		try
		{
			validate(room, user);
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}

    	return ResponseEntity.ok(room.get().getLastUpdate());
    }
}
