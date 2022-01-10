package TPLab4.ChineseCheckersBackend.Game;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TPLab4.ChineseCheckersBackend.GameFactory.FourPlayerGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.SixPlayerGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.ThreePlayerGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.TwoPlayerGameFactory;
import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.History.HistoryRepository;
import TPLab4.ChineseCheckersBackend.MoveChecker.MoveCheckerGetter;
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
	private HistoryRepository historyRepository;
	
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
		Optional<History> history = historyRepository.findByGameId(game.getId());
		
		do
		{
			if(game.getPlayers().size() == playerTurn)
			{
				playerTurn = 1;
				game.setPlayerTurn(playerTurn);
			}
			else
			{
				game.setPlayerTurn(++playerTurn);
			}
		}
		while(history.get().getLeaderboard().contains(game.getPlayerWithTurn()));
		
		gameRepository.save(game);
	}
	
	public void updateChosenTile(Game game, Tile tile) 
	{
		game.setChosenTile(tile);
		
		gameRepository.save(game);
	}
	
	public void updateDuringMove(Game game, Boolean bool) 
	{
		game.setDuringMove(bool);
		
		gameRepository.save(game);
	}
	
	public List<Tile> getBoard(Game game) 
	{
		return game.getTileList();
	}
	
	public void move(Tile firstTile, Tile secondTile, Game game)
	{
		TileColor firstTileColor = firstTile.getColor();
		
		firstTile.setColor(TileColor.WHITE);
		secondTile.setColor(firstTileColor);
		game.setChosenTile(secondTile);
		
		tileRepository.save(firstTile);
		tileRepository.save(secondTile);
		gameRepository.save(game);
	}
	
	public boolean isFinished(Game game)
	{
		return game.getPlayers().size() == (historyRepository.findByGameId(game.getId()).get().getLeaderboard().size() + 1);
	}
	
	public void setStatus(Game game, GameStatus gameStatus) 
	{
		game.setGameStatus(gameStatus);
		
		gameRepository.save(game);
	}
}