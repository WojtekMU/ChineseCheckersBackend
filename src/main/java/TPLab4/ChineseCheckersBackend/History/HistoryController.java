package TPLab4.ChineseCheckersBackend.History;

import java.util.Optional;

import TPLab4.ChineseCheckersBackend.Game.BaseGameGetter;
import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Game.GameRepository;
import TPLab4.ChineseCheckersBackend.Request.MovesRequest;
import TPLab4.ChineseCheckersBackend.Request.ReplayBoardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TPLab4.ChineseCheckersBackend.Request.ReplaysRequest;
import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserRepository;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/chineseCheckers")
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
	
    @PostMapping(value = "/replays", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReplays(@Valid @RequestBody ReplaysRequest replaysRequest)
    {
    	Optional<User> user = userRepository.findById(replaysRequest.getUserId());

    	return ResponseEntity.ok(historyRepository.findByLeaderboard_Id(user.get().getId()));
    }

	@PostMapping(value = "/moves", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMoves(@Valid @RequestBody MovesRequest movesRequest)
	{
		Optional<History> history = historyRepository.findById(movesRequest.getHistoryId());

		return ResponseEntity.ok(history.get().getMoves());
	}

	@PostMapping(value = "/replayBoard", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getReplayBoard(@Valid @RequestBody ReplayBoardRequest replayBoardRequest)
	{
		Optional<History> history = historyRepository.findById(replayBoardRequest.getHistoryId());
		Optional<Game> game = gameRepository.findById(baseGameGetter.getBaseGame(history.get().getGame())) ;

		return ResponseEntity.ok(game.get().getTileList());
	}
}
