package TPLab4.ChineseCheckersBackend.History;

import java.security.Principal;
import java.util.Optional;

import TPLab4.ChineseCheckersBackend.Game.BaseGameGetter;
import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Game.GameRepository;
import TPLab4.ChineseCheckersBackend.Response.MessageResponse;
import TPLab4.ChineseCheckersBackend.Room.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/chineseCheckers/history")
public class HistoryController 
{
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private HistoryRepository historyRepository;

	@Autowired
	private BaseGameGetter baseGameGetter;

	public void validate(Optional<History> history, Optional<User> user) throws AccessDeniedException
	{
		if(history.isEmpty())
		{
			throw new AccessDeniedException("Replay does not exist!");
		}

		if(user.isEmpty())
		{
			throw new AccessDeniedException("User does not exist!");
		}

		if(!user.get().getHistory().contains(history.get()))
		{
			throw new AccessDeniedException("Replay does not belong to you!");
		}
	}

	@PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/replays", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReplays(Principal principal)
    {
    	Optional<User> user = userRepository.findByUsername(principal.getName());

		if(user.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not exist!");
		}

    	return ResponseEntity.ok(historyRepository.findByLeaderboard_Id(user.get().getId()));
    }

	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/moves", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMoves(@RequestParam Long historyId, Principal principal)
	{
		Optional<History> history = historyRepository.findById(historyId);
		Optional<User> user = userRepository.findByUsername(principal.getName());

		try
		{
			validate(history, user);
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}

		return ResponseEntity.ok(history.get().getMoves());
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/replayBoard", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getReplayBoard(@RequestParam Long historyId, Principal principal)
	{
		Optional<History> history = historyRepository.findById(historyId);
		Optional<Game> game = gameRepository.findById(baseGameGetter.getBaseGame(history.get().getGame()));
		Optional<User> user = userRepository.findByUsername(principal.getName());

		try
		{
			validate(history, user);
		}
		catch(AccessDeniedException ex)
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(ex.getMessage()));
		}

		return ResponseEntity.ok(game.get().getTileList());
	}
}
