package TPLab4.ChineseCheckersBackend.Game;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	
    protected final List<TileColor> colorOrder = List.of(TileColor.WHITE, TileColor.RED, TileColor.BLUE, TileColor.GREEN, TileColor.PURPLE, TileColor.BROWN, TileColor.ORANGE);

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
		
		Optional<Tile> tile;
		
		tile = tileRepository.findByXAndYAndGameId(13L, 1L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(13L, 2L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(13L, 3L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(13L, 4L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(12L, 2L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(12L, 3L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(12L, 4L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(11L, 3L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(11L, 4L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(10L, 4L, gameId);
		tileService.updateTileColor(tile.get(), color);
	}
	
	protected void fillSecondCorner(Game game, TileColor color)
	{
		Long gameId = game.getId();
		
		Optional<Tile> tile;
		
		tile = tileRepository.findByXAndYAndGameId(14L, 5L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(15L, 5L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(16L, 5L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(17L, 5L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(14L, 6L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(15L, 6L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(16L, 6L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(14L, 7L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(15L, 7L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(14L, 8L, gameId);
		tileService.updateTileColor(tile.get(), color);
	}
	
	protected void fillThirdCorner(Game game, TileColor color)
	{
		Long gameId = game.getId();
		
		Optional<Tile> tile;
		
		tile = tileRepository.findByXAndYAndGameId(10L, 13L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(11L, 12L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(11L, 13L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(12L, 11L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(12L, 12L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(12L, 13L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(13L, 10L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(13L, 11L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(13L, 12L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(13L, 13L, gameId);
		tileService.updateTileColor(tile.get(), color);
	}
	
	protected void fillFourthCorner(Game game, TileColor color)
	{
		Long gameId = game.getId();
		
		Optional<Tile> tile;
		
		tile = tileRepository.findByXAndYAndGameId(5L, 14L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(6L, 14L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(7L, 14L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(8L, 14L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(5L, 15L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(6L, 15L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(7L, 15L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(5L, 16L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(6L, 16L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(5L, 17L, gameId);
		tileService.updateTileColor(tile.get(), color);
	}
	
	protected void fillFifthCorner(Game game, TileColor color)
	{
		Long gameId = game.getId();
		
		Optional<Tile> tile;
		
		tile = tileRepository.findByXAndYAndGameId(1L, 13L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(2L, 13L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(3L, 13L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(4L, 13L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(2L, 12L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(3L, 12L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(4L, 12L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(3L, 11L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(4L, 11L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(4L, 10L, gameId);
		tileService.updateTileColor(tile.get(), color);
	}
	
	protected void fillSixthCorner(Game game, TileColor color)
	{
		Long gameId = game.getId();
		
		Optional<Tile> tile;
		
		tile = tileRepository.findByXAndYAndGameId(5L, 5L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(6L, 5L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(7L, 5L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(8L, 5L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(5L, 6L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(6L, 6L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(7L, 6L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(5L, 7L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(6L, 7L, gameId);
		tileService.updateTileColor(tile.get(), color);
		
		tile = tileRepository.findByXAndYAndGameId(5L, 8L, gameId);
		tileService.updateTileColor(tile.get(), color);
	}
}
