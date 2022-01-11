package TPLab4.ChineseCheckersBackend.GameFactory;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Game.GameRepository;
import TPLab4.ChineseCheckersBackend.Game.GameStatus;
import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.History.HistoryRepository;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.Tile.TileColor;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import TPLab4.ChineseCheckersBackend.Tile.TileService;
import TPLab4.ChineseCheckersBackend.User.User;
import TPLab4.ChineseCheckersBackend.User.UserRepository;
	
@Component
public abstract class GameFactory 
{    
	@Autowired
	protected TileService tileService;
	
	@Autowired
	protected TileRepository tileRepository;
	
	@Autowired
	protected GameRepository gameRepository;
	
	@Autowired
	protected HistoryRepository historyRepository;
	
    protected final List<TileColor> colorOrder = List.of(TileColor.WHITE, TileColor.RED, TileColor.BLUE, TileColor.GREEN, TileColor.PURPLE, TileColor.BROWN, TileColor.ORANGE);

    protected static Random random = new Random();
    
	public abstract Game createGame(List<User> players);
	
	protected void createClearBoard(Game game)
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
					tileService.createTile(i, j, TileColor.WHITE, game);
				}
			}
		}
	}
	
	protected void fillFirstCorner(Game game, TileColor color)
	{
		Long gameId = game.getId();
		
		List<Tile> firstCorner = tileRepository.getFirstCorner(gameId);
		
		for(Tile tile : firstCorner)
		{
			tileService.updateTileColor(tile, color);
		}
	}
	
	protected void fillSecondCorner(Game game, TileColor color)
	{
		Long gameId = game.getId();
		
		List<Tile> secondCorner = tileRepository.getSecondCorner(gameId);
		
		for(Tile tile : secondCorner)
		{
			tileService.updateTileColor(tile, color);
		}
	}
	
	protected void fillThirdCorner(Game game, TileColor color)
	{
		Long gameId = game.getId();
		
		List<Tile> thirdCorner = tileRepository.getThirdCorner(gameId);
		
		for(Tile tile : thirdCorner)
		{
			tileService.updateTileColor(tile, color);
		}
	}
	
	protected void fillFourthCorner(Game game, TileColor color)
	{
		Long gameId = game.getId();
		
		List<Tile> fourthCorner = tileRepository.getFourthCorner(gameId);
		
		for(Tile tile : fourthCorner)
		{
			tileService.updateTileColor(tile, color);
		}
	}
	
	protected void fillFifthCorner(Game game, TileColor color)
	{
		Long gameId = game.getId();
		
		List<Tile> fifthCorner = tileRepository.getFifthCorner(gameId);
		
		for(Tile tile : fifthCorner)
		{
			tileService.updateTileColor(tile, color);
		}
	}
	
	protected void fillSixthCorner(Game game, TileColor color)
	{
		Long gameId = game.getId();
		
		List<Tile> sixthCorner = tileRepository.getSixthCorner(gameId);
		
		for(Tile tile : sixthCorner)
		{
			tileService.updateTileColor(tile, color);
		}
	}
	
	protected void setGameProperties(List<User> players, Game game)
	{
		game.getPlayers().addAll(players);
		if(players.size() != 0)
		{
			game.setPlayerTurn(random.nextInt(players.size()) + 1);
		}
		else
		{
			game.setPlayerTurn(0);
		}
		game.setChosenTile(null);
		game.setDuringMove(false);
		game.setGameStatus(GameStatus.ONGOING);

		gameRepository.save(game);

		createClearBoard(game);
		
		History history = new History();
		history.setGame(game);
		
		historyRepository.save(history);
	}
}
