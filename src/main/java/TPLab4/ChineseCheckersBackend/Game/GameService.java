package TPLab4.ChineseCheckersBackend.Game;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.Tile.TileColor;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import TPLab4.ChineseCheckersBackend.Tile.TileService;
import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserRepository;

@Service
@Transactional
public class GameService 
{
	@Autowired
	private GameRepository gameRepository;    

	@Autowired
	private TileRepository tileRepository;
	
		@Autowired
		private TileService tileService;
	
	@Autowired
	private TwoPlayerGameFactory twoPlayerGameFactory;
	
	@Autowired
	private ThreePlayerGameFactory threePlayerGameFactory;
	
	@Autowired
	private FourPlayerGameFactory fourPlayerGameFactory;
	
	@Autowired
	private SixPlayerGameFactory sixPlayerGameFactory;

	public Game createGame(List<User> players) 
	{
		if(players.size() == 2)
		{
			return twoPlayerGameFactory.createGame(players);
		}
		else if(players.size() == 3)
		{
			return threePlayerGameFactory.createGame(players);
		}
		else if(players.size() == 4)
		{
			return fourPlayerGameFactory.createGame(players);
		}
		else if(players.size() == 6)
		{
			return sixPlayerGameFactory.createGame(players);
		}
		else
		{
			throw new IllegalArgumentException("Wrong player number!");
		}
	}

	public void updatePlayerTurn(Game game) 
	{
		Integer playerTurn = game.getPlayerTurn();
		
		if(game.getPlayers().size() == playerTurn)
		{
			game.setPlayerTurn(1);
		}
		else
		{
			game.setPlayerTurn(++playerTurn);
		}
		
		gameRepository.save(game);
	}
	
	public List<Tile> getBoard(Game game) 
	{
		return game.getTileList();
	}
}