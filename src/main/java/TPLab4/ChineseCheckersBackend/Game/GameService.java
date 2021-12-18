package TPLab4.ChineseCheckersBackend.Game;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import TPLab4.ChineseCheckersBackend.Tile.TileService;
import TPLab4.ChineseCheckersBackend.User.User;

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
	
	public List<Tile> getBoard(Game game) 
	{
		return game.getTileList();
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
	
	public void createClearBoard(Game game, TileService tileService)
	{
		for(Long i = 1L; i <= 17L; i++)
		{
			for(Long j = 1L; j <= 17L; j++)
			{
				if(!(i + j < 14 && j < 5) 
                   && !(i >= 14 && j < 5)
                   && !(i < 5 && j < 10)
                   && !(i + j < 14 && j < 13 && j > 9)
                   && !(i + j > 22 && j < 10)
                   && !(i > 13 && j > 9)
                   && !(i < 5 && j > 13)
                   && !(i + j > 22 && j > 13))
				{
					tileService.createTile(i, j, "white", game);
				}
			}
		}
	}
	
	public void createRedTiles(Game game, TileRepository tileRepository, TileService tileService)
	{
		Long gameId = game.getId();
		
		Optional<Tile> tile = tileRepository.findByXAndYAndGameId(13L, 1L, gameId);
		tileService.updateTileColor(tile.get(), "red");
		
		tile = tileRepository.findByXAndYAndGameId(13L, 2L, gameId);
		tileService.updateTileColor(tile.get(), "red");
		
		tile = tileRepository.findByXAndYAndGameId(13L, 3L, gameId);
		tileService.updateTileColor(tile.get(), "red");
		
		tile = tileRepository.findByXAndYAndGameId(13L, 4L, gameId);
		tileService.updateTileColor(tile.get(), "red");
		
		tile = tileRepository.findByXAndYAndGameId(12L, 2L, gameId);
		tileService.updateTileColor(tile.get(), "red");
		
		tile = tileRepository.findByXAndYAndGameId(12L, 3L, gameId);
		tileService.updateTileColor(tile.get(), "red");
		
		tile = tileRepository.findByXAndYAndGameId(12L, 4L, gameId);
		tileService.updateTileColor(tile.get(), "red");
		
		tile = tileRepository.findByXAndYAndGameId(11L, 3L, gameId);
		tileService.updateTileColor(tile.get(), "red");
		
		tile = tileRepository.findByXAndYAndGameId(11L, 4L, gameId);
		tileService.updateTileColor(tile.get(), "red");
		
		tile = tileRepository.findByXAndYAndGameId(10L, 4L, gameId);
		tileService.updateTileColor(tile.get(), "red");
	}
}