package TPLab4.ChineseCheckersBackend.Game;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.History.HistoryRepository;
import TPLab4.ChineseCheckersBackend.Move.MoveService;
import TPLab4.ChineseCheckersBackend.MoveChecker.MoveCheckerGetter;
import TPLab4.ChineseCheckersBackend.Request.CreateGameRequest;
import TPLab4.ChineseCheckersBackend.Request.EndTurnRequest;
import TPLab4.ChineseCheckersBackend.Request.MoveRequest;
import TPLab4.ChineseCheckersBackend.Response.MessageResponse;
import TPLab4.ChineseCheckersBackend.Room.Room;
import TPLab4.ChineseCheckersBackend.Room.RoomRepository;
import TPLab4.ChineseCheckersBackend.Room.RoomService;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserRepository;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/chineseCheckers/game")
public class GameController 
{
    @Autowired
    private GameService gameService;
    
    @Autowired
    private RoomService roomService;
    
    @Autowired
    private MoveService moveService;
    
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GameRepository gameRepository;	
	
	@Autowired
	private RoomRepository roomRepository;	
	
	@Autowired
	private TileRepository tileRepository;
	
	@Autowired
	private HistoryRepository historyRepository;
	
	@Autowired
	private MoveCheckerGetter moveCheckerGetter;

	public void validate(Optional<Game> game, Optional<User> user) throws AccessDeniedException
	{
		if(game.isEmpty())
		{
			throw new AccessDeniedException("Game does not exist!");
		}

		if(user.isEmpty())
		{
			throw new AccessDeniedException("User does not exist!");
		}

		if(!game.get().getPlayers().contains(user.get()))
		{
			throw new AccessDeniedException("User does not belong to this game!");
		}
	}

	@PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/create")
    public ResponseEntity<?> createNewGame(@Valid @RequestBody CreateGameRequest createGameRequest, Principal principal)
    {
    	try
    	{
	    	Optional<Room> room = roomRepository.findById(createGameRequest.getRoomId());
	    	Optional<User> user = userRepository.findByUsername(principal.getName());
	    	
	    	if(user.isEmpty())
	    	{
	    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("User does not exist!"));
	    	}

			if(room.isEmpty())
	    	{
	    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Room does not exist!"));
	    	}
	    	
	    	if(!room.get().getPlayers().get(0).equals(user.get()))
	    	{
	    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("You are not the host!"));
	    	}
	    	
	    	if(room.get().isGameStarted())
	    	{
	    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Game already started!"));
	    	}
	    	
	    	Game game = gameService.createGame(room.get().getPlayers());
	    	
	    	roomService.startGame(room.get(), game);
	    	
	    	return ResponseEntity.ok(game.getId());
    	}
    	catch(IllegalArgumentException ex)
    	{
    		return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    	}
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/gameBoard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBoard(@RequestParam Long gameId, Principal principal)
    {
    	Optional<Game> game = gameRepository.findById(gameId);
		Optional<User> user = userRepository.findByUsername(principal.getName());

		try
		{
			validate(game, user);
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}

    	return ResponseEntity.ok(gameService.getBoard(game.get()));
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/lastUpdate")
    public ResponseEntity<?> getLastGameUpdate(@RequestParam Long gameId, Principal principal)
    {
    	Optional<Game> game = gameRepository.findById(gameId);
		Optional<User> user = userRepository.findByUsername(principal.getName());

		try
		{
			validate(game, user);
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}

    	return ResponseEntity.ok(game.get().getLastUpdate());
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/chosenTile")
    public ResponseEntity<?> getChosenTileId(@RequestParam Long gameId, Principal principal)
    {
    	Optional<Game> game = gameRepository.findById(gameId);
		Optional<User> user = userRepository.findByUsername(principal.getName());

		try
		{
			validate(game, user);
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
    	
    	if(game.get().getChosenTile() != null)
    	{
    		return ResponseEntity.ok(game.get().getChosenTile().getId());
    	}  
    	else
    	{
    		return ResponseEntity.ok("");
    	}
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/playerBoard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPlayerBoard(@RequestParam Long gameId, Principal principal)
    {
		Optional<Game> game = gameRepository.findById(gameId);
		Optional<User> user = userRepository.findByUsername(principal.getName());

		try
		{
			validate(game, user);
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}

    	return ResponseEntity.ok(game.get().getPlayers());
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/leaderboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLeaderboard(@RequestParam Long gameId, Principal principal)
    {
		Optional<Game> game = gameRepository.findById(gameId);
		Optional<User> user = userRepository.findByUsername(principal.getName());

		try
		{
			validate(game, user);
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}

    	return ResponseEntity.ok(game.get().getHistory().getLeaderboard());
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/gameStatus")
    public ResponseEntity<?> getGameStatus(@RequestParam Long gameId, Principal principal)
    {
		Optional<Game> game = gameRepository.findById(gameId);
		Optional<User> user = userRepository.findByUsername(principal.getName());

		try
		{
			validate(game, user);
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}

    	return ResponseEntity.ok(game.get().getGameStatus());
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/colorOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getColorOrder(@RequestParam Long gameId, Principal principal)
    {
		Optional<Game> game = gameRepository.findById(gameId);
		Optional<User> user = userRepository.findByUsername(principal.getName());

		try
		{
			validate(game, user);
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
    	
    	return ResponseEntity.ok(moveCheckerGetter.getMoveChecker(game.get()).getColorOrder());
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/currentPlayerTurn", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCurrentPlayerTurn(@RequestParam Long gameId, Principal principal)
    {
		Optional<Game> game = gameRepository.findById(gameId);
		Optional<User> user = userRepository.findByUsername(principal.getName());

		try
		{
			validate(game, user);
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}

    	return ResponseEntity.ok(game.get().getPlayerTurn());
    }

	@PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/move")
    public ResponseEntity<?> getBoard(@Valid @RequestBody MoveRequest moveRequest, Principal principal)
    {
    	Optional<Tile> tile = tileRepository.findById(moveRequest.getTileId());
    	Optional<User> user = userRepository.findByUsername(principal.getName());
    	Optional<Game> game = gameRepository.findById(moveRequest.getGameId());

		try
		{
			validate(game, user);
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
    	
    	if(tile.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Tile does not exist!"));
		}
    	
    	if(!game.get().getTileList().contains(tile.get()))
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Tile does not belong to this game!"));
    	}
    	
    	Optional<History> history = historyRepository.findByGameId(moveRequest.getGameId());
    	
    	Pair<Boolean, Boolean> result = moveCheckerGetter.getMoveChecker(game.get()).checkMove(user.get(), game.get(), tile.get());
    	
    	if(result.getFirst())
    	{
    		moveService.saveMove(user.get(), history.get(), game.get().getChosenTile(), tile.get());
    		gameService.move(game.get().getChosenTile(), tile.get(), game.get());
    	}

    	if(result.getSecond())
    	{
        	this.endTurn(new EndTurnRequest(game.get().getId()), principal);
    	}
    	
    	return ResponseEntity.ok(new MessageResponse("Ok"));
    }

	@PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/endTurn")
    public ResponseEntity<?> endTurn(@Valid @RequestBody EndTurnRequest endTurnRequest, Principal principal)
    {
    	Optional<Game> game = gameRepository.findById(endTurnRequest.getGameId());
    	Optional<User> user = userRepository.findByUsername(principal.getName());

		try
		{
			validate(game, user);
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}

    	if(game.get().getPlayerWithTurn().getId().equals(user.get().getId()) && game.get().getGameStatus().equals(GameStatus.ONGOING))
    	{
    		gameService.updateChosenTile(game.get(), null);
    		gameService.updateDuringMove(game.get(), Boolean.FALSE);
    		
			if(moveCheckerGetter.getMoveChecker(game.get()).isCurrentPlayerWinner(game.get()))
			{
				Optional<History> history = historyRepository.findByGameId(game.get().getId());
				
				history.get().getLeaderboard().add(user.get());
				
				historyRepository.save(history.get());
			}
    		
			if(gameService.isFinished(game.get()))
			{
				Optional<History> history = historyRepository.findByGameId(game.get().getId());
				
				for(User p : game.get().players)
				{
					if(!history.get().getLeaderboard().contains(p))
					{
						history.get().getLeaderboard().add(p);
					}
				}
				
				gameService.setStatus(game.get(), GameStatus.FINISHED);
				roomService.detachGame(game.get().getRoom());
			}
			else
			{
				gameService.updatePlayerTurn(game.get());
			}
    	}
    		
    	return ResponseEntity.ok(new MessageResponse("Ok"));
    }
}