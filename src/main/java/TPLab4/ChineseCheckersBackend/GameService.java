package TPLab4.ChineseCheckersBackend;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GameService 
{
	private final GameRepository gameRepository;


	@Autowired
	public GameService(GameRepository gameRepository) 
	{
		this.gameRepository = gameRepository;
	}

	public Game createNewGame(User player) 
	{
		Game game = new Game();
		game.setFirstPlayer(player);
		game.setGameStatus(GameStatus.WAITING_FOR_PLAYERS);

		gameRepository.save(game);

		return game;
	}


	public void updateGameStatus(Game game, GameStatus gameStatus) 
	{
		game.setGameStatus(gameStatus);
	}

	public List<Game> getGamesToJoin() 
	{
		return gameRepository.findByGameStatus(GameStatus.WAITING_FOR_PLAYERS).stream().collect(Collectors.toList());
	}

    public void joinGame(User player, Game game) 
    {
        game.setSecondPlayer(player);
        updateGameStatus(game, GameStatus.IN_PROGRESS);
        
        gameRepository.save(game);
    }

	public Optional<Game> getGame(Long id) 
	{
		return gameRepository.findById(id);
	}
}