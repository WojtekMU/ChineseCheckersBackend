package TPLab4.ChineseCheckersBackend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class GameController 
{
    @Autowired
    GameService gameService;
    
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GameRepository gameRepository;	

    @PostMapping(value = "/create")
    public ResponseEntity<?> createNewGame(@RequestBody CreateGameRequest createGameRequest) 
    {
    	gameService.createNewGame(userRepository.findByUsername(createGameRequest.getUsername()).get());
		return ResponseEntity.ok(new MessageResponse("Game created successfully!"));
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
}