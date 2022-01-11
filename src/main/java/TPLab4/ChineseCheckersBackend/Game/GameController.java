package TPLab4.ChineseCheckersBackend.Game;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TPLab4.ChineseCheckersBackend.Request.GameBoardRequest;
import TPLab4.ChineseCheckersBackend.Request.GameStatusRequest;
import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.History.HistoryRepository;
import TPLab4.ChineseCheckersBackend.Move.MoveService;
import TPLab4.ChineseCheckersBackend.MoveChecker.MoveCheckerGetter;
import TPLab4.ChineseCheckersBackend.Request.CanSeeGameRequest;
import TPLab4.ChineseCheckersBackend.Request.ChosenTileRequest;
import TPLab4.ChineseCheckersBackend.Request.ColorOrderRequest;
import TPLab4.ChineseCheckersBackend.Request.CreateGameRequest;
import TPLab4.ChineseCheckersBackend.Request.CurrentPlayerTurnRequest;
import TPLab4.ChineseCheckersBackend.Request.EndTurnRequest;
import TPLab4.ChineseCheckersBackend.Request.LastGameUpdateRequest;
import TPLab4.ChineseCheckersBackend.Request.LeaderboardRequest;
import TPLab4.ChineseCheckersBackend.Request.MoveRequest;
import TPLab4.ChineseCheckersBackend.Request.PlayerBoardRequest;
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
@RequestMapping("/api/chineseCheckers")
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
	
    @PostMapping(value = "/createGame")
    public ResponseEntity<?> createNewGame(@Valid @RequestBody CreateGameRequest createGameRequest)
    {
    	try
    	{
	    	Optional<Room> room = roomRepository.findById(createGameRequest.getRoomId());
	    	Optional<User> user = userRepository.findById(createGameRequest.getUserId());
	    	
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
    
    @PostMapping(value = "/gameBoard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBoard(@Valid @RequestBody GameBoardRequest boardRequest)
    {
    	Optional<Game> game = gameRepository.findById(boardRequest.getGameId());

		if(game.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Game does not exist!"));
		}

    	return ResponseEntity.ok(gameService.getBoard(game.get()));
    }
    
    @PostMapping(value = "/lastGameUpdate")
    public ResponseEntity<?> getLastGameUpdate(@Valid @RequestBody LastGameUpdateRequest lastGameUpdateRequest)
    {
    	Optional<Game> game = gameRepository.findById(lastGameUpdateRequest.getGameId());

		if(game.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Game does not exist!"));
		}

    	return ResponseEntity.ok(game.get().getLastUpdate());
    }
    
    @PostMapping(value = "/chosenTile")
    public ResponseEntity<?> getChosenTileId(@Valid @RequestBody ChosenTileRequest chosenTileRequest)
    {
    	Optional<Game> game = gameRepository.findById(chosenTileRequest.getGameId());
    	
    	if(game.isEmpty())
    	{
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Game does not exist!"));
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
    
    @PostMapping(value = "/playerBoard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPlayerBoard(@Valid @RequestBody PlayerBoardRequest playerBoardRequest)
    {
		Optional<Game> game = gameRepository.findById(playerBoardRequest.getGameId());

		if(game.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Game does not exist!"));
		}

    	return ResponseEntity.ok(game.get().getPlayers());
    }
    
    @PostMapping(value = "/leaderboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPlayerBoard(@Valid @RequestBody LeaderboardRequest leaderboardRequest)
    {
		Optional<Game> game = gameRepository.findById(leaderboardRequest.getGameId());

		if(game.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Game does not exist!"));
		}

    	return ResponseEntity.ok(game.get().getHistory().getLeaderboard());
    }
    	
//    @PostMapping(value = "/roomId")
//    public ResponseEntity<?> getRoomId(@RequestBody RoomIdRequest roomIdRequest) 
//    {
//    	return ResponseEntity.ok(gameRepository.findById(roomIdRequest.getGameId()).get().getRoom().getId());
//    }
    
    @PostMapping(value = "/gameStatus")
    public ResponseEntity<?> getGameStatus(@Valid @RequestBody GameStatusRequest gameStatusRequest)
    {
		Optional<Game> game = gameRepository.findById(gameStatusRequest.getGameId());

		if(game.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Game does not exist!"));
		}

    	return ResponseEntity.ok(game.get().getGameStatus());
    }
    
    @PostMapping(value = "/colorOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPlayerBoard(@Valid @RequestBody ColorOrderRequest colorOrderRequest)
    {
		Optional<Game> game = gameRepository.findById(colorOrderRequest.getGameId());

		if(game.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Game does not exist!"));
		}
    	
    	return ResponseEntity.ok(moveCheckerGetter.getMoveChecker(game.get()).getColorOrder());
    }
    
    @PostMapping(value = "/currentPlayerTurn", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCurrentPlayerTurn(@Valid @RequestBody CurrentPlayerTurnRequest currentPlayerTurnRequest)
    {
		Optional<Game> game = gameRepository.findById(currentPlayerTurnRequest.getGameId());

		if(game.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Game does not exist!"));
		}

    	return ResponseEntity.ok(game.get().getPlayerTurn());
    }
    
    @PostMapping(value = "/move")
    public ResponseEntity<?> getBoard(@Valid @RequestBody MoveRequest moveRequest)
    {
    	Optional<Tile> tile = tileRepository.findById(moveRequest.getTileId());
    	Optional<User> player = userRepository.findById(moveRequest.getUserId());
    	Optional<Game> game = gameRepository.findById(moveRequest.getGameId());
    	
    	if(game.isEmpty())
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Game does not exist!"));
    	}
    	
    	if(player.isEmpty())
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Player does not exist!"));
    	}
    	
    	if(tile.isEmpty())
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Tile does not exist!"));
    	}
    	
    	if(!game.get().getPlayers().contains(player.get()))
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Player does not belong to this game!"));
    	}
    	
    	if(!game.get().getTileList().contains(tile.get()))
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Tile does not belong to this game!"));
    	}
    	
    	Optional<History> history = historyRepository.findByGameId(moveRequest.getGameId());
    	
    	Pair<Boolean, Boolean> result = moveCheckerGetter.getMoveChecker(game.get()).checkMove(player.get(), game.get(), tile.get());
    	
    	if(result.getFirst())
    	{
    		moveService.saveMove(player.get(), history.get(), game.get().getChosenTile(), tile.get());
    		gameService.move(game.get().getChosenTile(), tile.get(), game.get());
    	}

    	if(result.getSecond())
    	{
        	this.endTurn(new EndTurnRequest(game.get().getId(), player.get().getId()));
    	}
    	
    	return ResponseEntity.ok(new MessageResponse("Ok"));
    }
    
    @PostMapping(value = "/endTurn")
    public ResponseEntity<?> endTurn(@Valid @RequestBody EndTurnRequest endTurnRequest)
    {
    	Optional<Game> game = gameRepository.findById(endTurnRequest.getGameId());
    	Optional<User> player = userRepository.findById(endTurnRequest.getUserId());
    	
    	if(player.isEmpty())
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Player does not exist!"));
    	}
    	
    	if(game.isEmpty())
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Game does not exist!"));
    	}
    	
    	if(!game.get().getPlayers().contains(player.get()))
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Player does not belong to this game!"));
    	}

    	if(game.get().getPlayerWithTurn().getId().equals(endTurnRequest.getUserId()) && game.get().getGameStatus().equals(GameStatus.ONGOING))
    	{
    		gameService.updateChosenTile(game.get(), null);
    		gameService.updateDuringMove(game.get(), Boolean.FALSE);
    		
			if(moveCheckerGetter.getMoveChecker(game.get()).isCurrentPlayerWinner(game.get()))
			{
				Optional<History> history = historyRepository.findByGameId(game.get().getId());
				
				history.get().getLeaderboard().add(player.get());
				
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
    
    @PostMapping(value = "/canSeeGame")
    public ResponseEntity<?> canSeeGame(@Valid @RequestBody CanSeeGameRequest canSeeGameRequest)
    {
		Optional<Game> game = gameRepository.findById(canSeeGameRequest.getGameId());
		Optional<User> user = userRepository.findById(canSeeGameRequest.getUserId());
		
    	if(user.isEmpty())
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("User does not exist!"));
    	}
		
		if(game.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Game does not exist!"));
		}
		
		if(!game.get().getPlayers().contains(user.get()))
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("You are not in this game!"));
		}
		
		if(!game.get().getGameStatus().equals(GameStatus.ONGOING))
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Cannot access this game!"));
		}
		
		return ResponseEntity.ok(new MessageResponse("ok")); 
	}
}