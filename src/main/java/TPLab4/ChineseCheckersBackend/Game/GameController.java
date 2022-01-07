package TPLab4.ChineseCheckersBackend.Game;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
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
import TPLab4.ChineseCheckersBackend.Request.GameStatusRequest;
import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.History.HistoryRepository;
import TPLab4.ChineseCheckersBackend.MoveChecker.AbstractMoveChecker;
import TPLab4.ChineseCheckersBackend.MoveChecker.MoveCheckerGetter;
import TPLab4.ChineseCheckersBackend.MoveChecker.StandardThreePlayersMoveChecker;
import TPLab4.ChineseCheckersBackend.MoveChecker.StandardTwoPlayersMoveChecker;
import TPLab4.ChineseCheckersBackend.Request.CanSeeGameRequest;
import TPLab4.ChineseCheckersBackend.Request.CanSeeRoomRequest;
import TPLab4.ChineseCheckersBackend.Request.ChosenTileRequest;
import TPLab4.ChineseCheckersBackend.Request.CreateGameRequest;
import TPLab4.ChineseCheckersBackend.Request.CreateRoomRequest;
import TPLab4.ChineseCheckersBackend.Request.CurrentPlayerTurnRequest;
import TPLab4.ChineseCheckersBackend.Request.EndTurnRequest;
import TPLab4.ChineseCheckersBackend.Request.JoinRequest;
import TPLab4.ChineseCheckersBackend.Request.LastGameUpdateRequest;
import TPLab4.ChineseCheckersBackend.Request.LeaderboardRequest;
import TPLab4.ChineseCheckersBackend.Request.MoveRequest;
import TPLab4.ChineseCheckersBackend.Request.PlayerBoardRequest;
import TPLab4.ChineseCheckersBackend.Request.RoomIdRequest;
import TPLab4.ChineseCheckersBackend.Response.MessageResponse;
import TPLab4.ChineseCheckersBackend.Room.Room;
import TPLab4.ChineseCheckersBackend.Room.RoomRepository;
import TPLab4.ChineseCheckersBackend.Room.RoomService;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.Tile.TileColor;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import TPLab4.ChineseCheckersBackend.Tile.TileService;
import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserRepository;

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
	
	protected final List<TileColor> colorOrder = List.of(TileColor.WHITE, TileColor.RED, TileColor.BLUE, TileColor.GREEN, TileColor.PURPLE, TileColor.BROWN, TileColor.ORANGE);
	
    @PostMapping(value = "/createGame")
    public ResponseEntity<?> createNewGame(@RequestBody CreateGameRequest createGameRequest) 
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
    public List<Tile> getBoard(@RequestBody GameBoardRequest boardRequest) 
    {
    	Optional<Game> game = gameRepository.findById(boardRequest.getGameId());

    	return gameService.getBoard(game.get());
    }
    
    @PostMapping(value = "/lastGameUpdate")
    public ResponseEntity<?> getLastGameUpdate(@RequestBody LastGameUpdateRequest lastGameUpdateRequest) 
    {
    	Optional<Game> game = gameRepository.findById(lastGameUpdateRequest.getGameId());

    	return ResponseEntity.ok(game.get().getLastUpdate());
    }
    
    @PostMapping(value = "/chosenTile")
    public ResponseEntity<?> getChosenTileId(@RequestBody ChosenTileRequest chosenTileRequest) 
    {
    	Optional<Game> game = gameRepository.findById(chosenTileRequest.getGameId());
    	
    	if(game.isEmpty())
    	{
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Game does not exist!"));
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
    public List<User> getPlayerBoard(@RequestBody PlayerBoardRequest playerBoardRequest) 
    {
    	return gameRepository.findById(playerBoardRequest.getGameId()).get().getPlayers();
    }
    
    @PostMapping(value = "/leaderboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getPlayerBoard(@RequestBody LeaderboardRequest leaderboardRequest) 
    {
    	return gameRepository.findById(leaderboardRequest.getGameId()).get().getHistory().getLeaderboard();
    }
    	
    @PostMapping(value = "/roomId")
    public ResponseEntity<?> getRoomId(@RequestBody RoomIdRequest roomIdRequest) 
    {
    	return ResponseEntity.ok(gameRepository.findById(roomIdRequest.getGameId()).get().getRoom().getId());
    }
    
    @PostMapping(value = "/gameStatus")
    public ResponseEntity<?> getGameStatus(@RequestBody GameStatusRequest gameStatusRequest) 
    {
    	return ResponseEntity.ok(gameRepository.findById(gameStatusRequest.getGameId()).get().getGameStatus());
    }
    
    @GetMapping(value = "/colorOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TileColor> getPlayerBoard() 
    {
    	return colorOrder;
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
    	
    	Pair<Boolean, Boolean> result = moveCheckerGetter.getMoveChecker(game.get()).checkMove(player.get(), game.get(), tile.get());
    	
    	if(result.getFirst())
    	{
    		gameService.move(game.get().getChosenTile(), tile.get(), game.get());
    	}

    	if(result.getSecond())
    	{
        	this.endTurn(new EndTurnRequest(game.get().getId(), player.get().getId()));
    	}
    	
    	return ResponseEntity.ok(new MessageResponse("Ok"));
    }
    
    @PostMapping(value = "/endTurn")
    public ResponseEntity<?> endTurn(@RequestBody EndTurnRequest endTurnRequest) 
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
    	
    	if(game.get().getPlayerWithTurn().getId().equals(endTurnRequest.getUserId()))
    	{
    		gameService.updateChosenTile(game.get(), null);
    		gameService.updateDuringMove(game.get(), Boolean.FALSE);
    		
			if(moveCheckerGetter.getMoveChecker(game.get()).isCurrenPlayerWinner(game.get()))
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
    public ResponseEntity<?> canSeeGame(@RequestBody CanSeeGameRequest canSeeGameRequest) 
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