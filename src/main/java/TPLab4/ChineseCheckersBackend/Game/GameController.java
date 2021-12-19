package TPLab4.ChineseCheckersBackend.Game;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TPLab4.ChineseCheckersBackend.Request.BoardRequest;
import TPLab4.ChineseCheckersBackend.Request.CreateGameRequest;
import TPLab4.ChineseCheckersBackend.Request.CreateRoomRequest;
import TPLab4.ChineseCheckersBackend.Request.JoinRequest;
import TPLab4.ChineseCheckersBackend.Request.MessageResponse;
import TPLab4.ChineseCheckersBackend.Request.MoveRequest;
import TPLab4.ChineseCheckersBackend.Room.GameStarted;
import TPLab4.ChineseCheckersBackend.Room.Room;
import TPLab4.ChineseCheckersBackend.Room.RoomRepository;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.Tile.TileColor;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import TPLab4.ChineseCheckersBackend.Tile.TileService;
import TPLab4.ChineseCheckersBackend.User.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class GameController 
{
    @Autowired
    private GameService gameService;
    
    @Autowired
    private TileService tileService;
    
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GameRepository gameRepository;	
	
	@Autowired
	private RoomRepository roomRepository;	
	
	@Autowired
	private TileRepository tileRepository;
	
	private static MoveChecker moveChecker = new MoveChecker();

    @PostMapping(value = "/createGame")
    public ResponseEntity<?> createNewGame(@RequestBody CreateGameRequest createGameRequest) 
    {
    	Room room = roomRepository.getById(createGameRequest.getRoomId());
    	Game game = gameService.createGame(room.getPlayers());
    	room.setGameStarted(true);
    	room.setGame(game);
    	roomRepository.save(room);
    	
		return ResponseEntity.ok(game.getId());
    }
    
    @PostMapping(value = "/board", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tile> getBoard(@RequestBody BoardRequest boardRequest) 
    {
    	return gameService.getBoard(gameRepository.getById(boardRequest.getGameId()));
    }
    
    @PostMapping(value = "/move")
    public ResponseEntity<?> getBoard(@RequestBody MoveRequest moveRequest) 
    {
    	Optional<Tile> firstTile = tileRepository.findById(moveRequest.getFirstTileId());
    	Optional<Tile> secondTile = tileRepository.findById(moveRequest.getSecondTileId());
    	
    	if(moveChecker.checkMove(firstTile.get(), secondTile.get(), tileRepository))
    	{
    		TileColor color = firstTile.get().getColor();
    		
    		tileService.updateTileColor(firstTile.get(), TileColor.WHITE);
    		tileService.updateTileColor(secondTile.get(), color);
    	}
    	
    	return ResponseEntity.ok(new MessageResponse("Ok"));
    }
}