package TPLab4.ChineseCheckersBackend.Game;

import java.util.List;
import java.util.Optional;

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
import TPLab4.ChineseCheckersBackend.Request.JoinRequest;
import TPLab4.ChineseCheckersBackend.Request.MessageResponse;
import TPLab4.ChineseCheckersBackend.Request.MoveRequest;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import TPLab4.ChineseCheckersBackend.Tile.TileService;
import TPLab4.ChineseCheckersBackend.User.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class GameController 
{
    @Autowired
    GameService gameService;
    
    @Autowired
    TileService tileService;
    
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GameRepository gameRepository;	
	
	@Autowired
	private TileRepository tileRepository;
	
	private static MoveChecker moveChecker = new MoveChecker();

    @PostMapping(value = "/create")
    public ResponseEntity<?> createNewGame(@RequestBody CreateGameRequest createGameRequest) 
    {
    	Game game = gameService.createNewGame(userRepository.findByUsername(createGameRequest.getUsername()).get());
    	gameService.createClearBoard(game, tileService);
    	gameService.createRedTiles(game, tileRepository, tileService);
    	
		return ResponseEntity.ok(game.getId());
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Game> getGamesToJoin() 
    {
        return gameService.getGamesToJoin();
    }
    
    @PostMapping(value = "/join")
    public ResponseEntity<?> joinGame(@RequestBody JoinRequest joinRequest) 
    {
    	gameService.joinGame(userRepository.findByUsername(joinRequest.getUsername()).get(), gameRepository.getById(joinRequest.getGameId()));
		return ResponseEntity.ok(new MessageResponse("Successfully joined game!"));
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
    		String color = firstTile.get().getColor();
    		
    		tileService.updateTileColor(firstTile.get(), "white");
    		tileService.updateTileColor(secondTile.get(), color);
    	}
    	
    	return ResponseEntity.ok(new MessageResponse("Ok"));
    }
}