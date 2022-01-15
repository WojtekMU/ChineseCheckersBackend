package TPLab4.ChineseCheckersBackend.Game;

import java.security.Principal;

import TPLab4.ChineseCheckersBackend.History.HistoryNotFoundException;
import TPLab4.ChineseCheckersBackend.History.HistoryService;
import TPLab4.ChineseCheckersBackend.Room.RoomNotFoundException;
import TPLab4.ChineseCheckersBackend.Tile.TileNotFoundException;
import TPLab4.ChineseCheckersBackend.Tile.TileService;
import TPLab4.ChineseCheckersBackend.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.Move.MoveService;
import TPLab4.ChineseCheckersBackend.MoveChecker.MoveCheckerGetter;
import TPLab4.ChineseCheckersBackend.Request.CreateGameRequest;
import TPLab4.ChineseCheckersBackend.Request.EndTurnRequest;
import TPLab4.ChineseCheckersBackend.Request.MoveRequest;
import TPLab4.ChineseCheckersBackend.Response.MessageResponse;
import TPLab4.ChineseCheckersBackend.Room.Room;
import TPLab4.ChineseCheckersBackend.Room.RoomService;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.User.User;

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
	private UserService userService;

	@Autowired
	private TileService tileService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private MoveCheckerGetter moveCheckerGetter;

	@PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/create")
    public ResponseEntity<?> createNewGame(@Valid @RequestBody CreateGameRequest createGameRequest, Principal principal)
    {
    	try
    	{
	    	Room room = roomService.loadRoomById(createGameRequest.getRoomId());
			User user = userService.loadUserByUsername(principal.getName());
	    	Game game = gameService.createGame(room.getPlayers(), user);
	    	
	    	roomService.setGameStarted(room, game);
	    	
	    	return ResponseEntity.ok(game.getId());
    	}
		catch(UsernameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(RoomNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(GameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(CantCreateGameException ex)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(ex.getMessage()));
		}
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/gameBoard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBoard(@RequestParam Long gameId, Principal principal)
    {
		try
		{
			User user = userService.loadUserByUsername(principal.getName());
			Game game = gameService.loadGameById(gameId);

			return ResponseEntity.ok(gameService.getBoard(game,user));
		}
		catch(UsernameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(GameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/lastUpdate")
    public ResponseEntity<?> getLastGameUpdate(@RequestParam Long gameId, Principal principal)
    {
		try
		{
			User user = userService.loadUserByUsername(principal.getName());
			Game game = gameService.loadGameById(gameId);

			return ResponseEntity.ok(gameService.getLastUpdate(game, user));
		}
		catch(UsernameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(GameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/chosenTile")
    public ResponseEntity<?> getChosenTileId(@RequestParam Long gameId, Principal principal)
    {
		try
		{
			User user = userService.loadUserByUsername(principal.getName());
			Game game = gameService.loadGameById(gameId);

			Tile chosenTile = gameService.getChosenTile(game, user);

			return ResponseEntity.ok(chosenTile != null ? chosenTile.getId() : "");
		}
		catch(UsernameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(GameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/playerBoard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPlayerBoard(@RequestParam Long gameId, Principal principal)
    {
		try
		{
			User user = userService.loadUserByUsername(principal.getName());
			Game game = gameService.loadGameById(gameId);

			return ResponseEntity.ok(gameService.getPlayerBoard(game, user));
		}
		catch(UsernameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(GameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/leaderboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLeaderboard(@RequestParam Long gameId, Principal principal)
    {
		try
		{
			User user = userService.loadUserByUsername(principal.getName());
			Game game = gameService.loadGameById(gameId);

			History history = game.getHistory();

			return ResponseEntity.ok(historyService.getLeaderboard(history, user));
		}
		catch(UsernameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(GameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/gameStatus")
    public ResponseEntity<?> getGameStatus(@RequestParam Long gameId, Principal principal)
    {
		try
		{
			User user = userService.loadUserByUsername(principal.getName());
			Game game = gameService.loadGameById(gameId);

			return ResponseEntity.ok(gameService.getGameStatus(game, user));
		}
		catch(UsernameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(GameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/colorOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getColorOrder(@RequestParam Long gameId, Principal principal)
    {
		try
		{
			Game game = gameService.loadGameById(gameId);

			return ResponseEntity.ok(moveCheckerGetter.getMoveChecker(game).getColorOrder());
		}
		catch(UsernameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(GameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
    }

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/currentPlayerTurn", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCurrentPlayerTurn(@RequestParam Long gameId, Principal principal)
    {
		try
		{
			User user = userService.loadUserByUsername(principal.getName());
			Game game = gameService.loadGameById(gameId);

			return ResponseEntity.ok(gameService.getPlayerTurn(game, user));
		}
		catch(UsernameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(GameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
    }

	@PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/move")
    public ResponseEntity<?> move(@Valid @RequestBody MoveRequest moveRequest, Principal principal)
    {
		try
		{
			User user = userService.loadUserByUsername(principal.getName());
			Game game = gameService.loadGameById(moveRequest.getGameId());
			Tile tile = tileService.loadTileById(moveRequest.getTileId());
			History history = historyService.loadHistoryByGameId(moveRequest.getGameId());

			moveService.move(user, game, tile, history);

			return ResponseEntity.ok(new MessageResponse("Ok"));
		}
		catch(UsernameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(GameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(TileNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(HistoryNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
    }

	@PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/endTurn")
    public ResponseEntity<?> endTurn(@Valid @RequestBody EndTurnRequest endTurnRequest, Principal principal)
    {
		try
		{
			User user = userService.loadUserByUsername(principal.getName());
			Game game = gameService.loadGameById(endTurnRequest.getGameId());
			History history = historyService.loadHistoryByGameId(game.getId());

			moveService.endTurn(user, game, history);

			return ResponseEntity.ok(new MessageResponse("Ok"));
		}
		catch(UsernameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(GameNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(HistoryNotFoundException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}
    }
}