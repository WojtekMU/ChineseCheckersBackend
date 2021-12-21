package TPLab4.ChineseCheckersBackend.Game;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
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

import TPLab4.ChineseCheckersBackend.Request.GameBoardRequest;
import TPLab4.ChineseCheckersBackend.Request.ChosenTileRequest;
import TPLab4.ChineseCheckersBackend.Request.CreateGameRequest;
import TPLab4.ChineseCheckersBackend.Request.CreateRoomRequest;
import TPLab4.ChineseCheckersBackend.Request.CurrentPlayerTurnRequest;
import TPLab4.ChineseCheckersBackend.Request.JoinRequest;
import TPLab4.ChineseCheckersBackend.Request.MoveRequest;
import TPLab4.ChineseCheckersBackend.Request.PlayerBoardRequest;
import TPLab4.ChineseCheckersBackend.Response.MessageResponse;
import TPLab4.ChineseCheckersBackend.Room.Room;
import TPLab4.ChineseCheckersBackend.Room.RoomRepository;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.Tile.TileColor;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import TPLab4.ChineseCheckersBackend.Tile.TileService;
import TPLab4.ChineseCheckersBackend.User.User;
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
	
	@Autowired
	private MoveChecker moveChecker;

    @PostMapping(value = "/createGame")
    public ResponseEntity<?> createNewGame(@RequestBody CreateGameRequest createGameRequest) 
    {
    	try
    	{
	    	Room room = roomRepository.getById(createGameRequest.getRoomId());
	    	Game game = gameService.createGame(room.getPlayers());
	    	room.setGameStarted(true);
	    	room.setGame(game);
	    	
	    	roomRepository.save(room);
	    	
	    	return ResponseEntity.ok(game.getId());
    	}
    	catch(IllegalArgumentException ex)
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
    	}
    }
    
    @PostMapping(value = "/gameBoard", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tile> getBoard(@RequestBody GameBoardRequest boardRequest) 
    {
    	return gameService.getBoard(gameRepository.getById(boardRequest.getGameId()));
    }
    
    @PostMapping(value = "/chosenTile")
    public ResponseEntity<?> getChosenTileId(@RequestBody ChosenTileRequest chosenTileRequest) 
    {
    	Game game = gameRepository.findById(chosenTileRequest.getGameId()).get();
    	if(game.getChosenTile() != null)
    	{
    		return ResponseEntity.ok(game.getChosenTile().getId());
    	}  
    	else
    	{
    		return ResponseEntity.ok("");
    	}
    }
    
    @PostMapping(value = "/playerBoard", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getPlayerBoard(@RequestBody PlayerBoardRequest playerBoardRequest) 
    {
    	return gameRepository.findById(playerBoardRequest.getGameId()).get().getPlayers();
    }
    
    @GetMapping(value = "/colorOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TileColor> getPlayerBoard() 
    {
    	return moveChecker.getColorOrder();
    }
    
    @PostMapping(value = "/currentPlayerTurn", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCurrentPlayerTurn(@RequestBody CurrentPlayerTurnRequest currentPlayerTurnRequest) 
    {
    	return ResponseEntity.ok(gameRepository.findById(currentPlayerTurnRequest.getGameId()).get().getPlayerTurn());
    }
    
    @PostMapping(value = "/move")
    public ResponseEntity<?> getBoard(@RequestBody MoveRequest moveRequest) 
    {
    	Optional<Tile> tile = tileRepository.findById(moveRequest.getTileId());
    	Optional<User> player = userRepository.findByUsername(moveRequest.getUsername());
    	Optional<Game> game = gameRepository.findById(moveRequest.getGameId());
    	
    	if(moveChecker.correctPlayer(player.get(), game.get()))
    	{
	    	if(game.get().getChosenTile() == null)
	    	{
	    		if(moveChecker.isCurrentMovingPlayerColor(tile.get(), game.get()))
	    		{
	    			gameService.updateChosenTile(game.get(), tile.get());
	    		}
	    	}
	    	else if(moveChecker.isTileWhite(tile.get()))
	    	{
	    		if(moveChecker.isDistanceOneMove(game.get().getChosenTile(), tile.get()))
	    		{
	    			gameService.move(game.get().getChosenTile(), tile.get());
	    			game.get().setChosenTile(null);
	    			gameService.updatePlayerTurn(game.get());
	    		}
	    	}
    	}

    	return ResponseEntity.ok(new MessageResponse("Ok"));
    }
}