package TPLab4.ChineseCheckersBackend.Room;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Request.CreateRoomRequest;
import TPLab4.ChineseCheckersBackend.Request.JoinRequest;
import TPLab4.ChineseCheckersBackend.Request.MessageResponse;
import TPLab4.ChineseCheckersBackend.Request.PlayersInRoomRequest;
import TPLab4.ChineseCheckersBackend.Tile.TileColor;
import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class RoomController 
{
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private UserRepository userRepository;
	
    @PostMapping(value = "/createRoom")
    public ResponseEntity<?> createNewGame(@RequestBody CreateRoomRequest createGameRequest) 
    {
    	Room room = roomService.createRoom(userRepository.findByUsername(createGameRequest.getUsername()).get());
    	
		return ResponseEntity.ok(room.getId());
    }
	
    @GetMapping(value = "/roomList", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Room> getGamesToJoin() 
    {
        return roomService.getRoomsToJoin();
    }
    
    @PostMapping(value = "/playerList", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getPlayersInRoom(@RequestBody PlayersInRoomRequest playersInRoomRequest) 
    {
        return roomRepository.getById(playersInRoomRequest.getRoomId()).getPlayers();
    }
    
    @PostMapping(value = "/joinRoom")
    public ResponseEntity<?> joinGame(@RequestBody JoinRequest joinRequest) 
    {
    	roomService.joinGame(userRepository.findByUsername(joinRequest.getUsername()).get(), roomRepository.getById(joinRequest.getRoomId()));
		return ResponseEntity.ok(new MessageResponse("Successfully joined room!"));
    }
}
