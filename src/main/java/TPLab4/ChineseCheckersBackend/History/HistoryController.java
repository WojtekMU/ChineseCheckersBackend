package TPLab4.ChineseCheckersBackend.History;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TPLab4.ChineseCheckersBackend.Request.ReplaysRequest;
import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/chineseCheckers")
public class HistoryController 
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HistoryRepository historyRepository;
	
    @PostMapping(value = "/replays", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<History> getReplays(@RequestBody ReplaysRequest replaysRequest) 
    {
    	Optional<User> user = userRepository.findById(replaysRequest.getUserId());

    	return historyRepository.findByLeaderboard_Id(user.get().getId());
    }
}
