package TPLab4.ChineseCheckersBackend.Game;

import java.util.Date;
import java.util.List;

import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TPLab4.ChineseCheckersBackend.GameFactory.FourPlayerGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.SixPlayerGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.ThreePlayerGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.TwoPlayerGameFactory;
import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.History.HistoryRepository;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.Tile.TileColor;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import TPLab4.ChineseCheckersBackend.User.User;

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

	private void validate(Game game, User user) throws AccessDeniedException
	{
		if(!game.getPlayers().contains(user))
		{
			throw new AccessDeniedException("User does not belong to this game!");
		}
	}

	public Game loadGameById(Long gameId) throws GameNotFoundException
	{
		Game game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException("Game does not exist!"));

		return game;
	}

	public Game createGame(List<User> players, User user) throws AccessDeniedException, CantCreateGameException
	{
		if(!players.contains(user))
		{
			throw new AccessDeniedException("Cannot start game!");
		}

		if(!players.get(0).equals(user))
		{
			throw new CantCreateGameException("You are not the host!");
		}

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
			throw new CantCreateGameException("Wrong player number!");
		}
	}

	public Integer updatePlayerTurn(Game game)
	{
		Integer playerTurn = game.getPlayerTurn();
		History history = game.getHistory();
		
		do
		{
			if(game.getPlayers().size() == playerTurn)
			{
				playerTurn = 1;
			}
			else
			{
				playerTurn++;
			}

			game.setPlayerTurn(playerTurn);
		}
		while(history.getLeaderboard().contains(game.getPlayerWithTurn()));

		gameRepository.save(game);

		return playerTurn;
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
	
	public List<Tile> getBoard(Game game, User user) throws AccessDeniedException
	{
		validate(game, user);

		return game.getTileList();
	}

	public Date getLastUpdate(Game game, User user) throws AccessDeniedException
	{
		validate(game, user);

		return game.getLastUpdate();
	}

	public GameStatus getGameStatus(Game game, User user) throws AccessDeniedException
	{
		validate(game, user);

		return game.getGameStatus();
	}

	public Integer getPlayerTurn(Game game, User user) throws AccessDeniedException
	{
		validate(game, user);

		return game.getPlayerTurn();
	}

	public Tile getChosenTile(Game game, User user) throws AccessDeniedException
	{
		validate(game, user);

		return game.getChosenTile();
	}

	public List<User> getPlayerBoard(Game game, User user) throws AccessDeniedException
	{
		validate(game, user);

		return game.getPlayers();
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
		return game.getPlayers().size() == (game.getHistory().getLeaderboard().size() + 1);
	}
	
	public void setStatus(Game game, GameStatus gameStatus) 
	{
		game.setGameStatus(gameStatus);
		
		gameRepository.save(game);
	}
}