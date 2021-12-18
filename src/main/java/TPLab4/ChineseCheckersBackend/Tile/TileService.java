package TPLab4.ChineseCheckersBackend.Tile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TPLab4.ChineseCheckersBackend.Game.Game;

@Service
@Transactional
public class TileService 
{
	private final TileRepository tileRepository;
	
	@Autowired
	public TileService(TileRepository tileRepository) 
	{
		this.tileRepository = tileRepository;
	}
	
	public Tile createTile(Long x, Long y, String color, Game game) 
	{
		Tile tile = new Tile();
		tile.setX(x);
		tile.setY(y);
		tile.setColor(color);
		tile.setGame(game);
		
		tileRepository.save(tile);

		return tile;
	}
	
	public void updateTileColor(Tile tile, String color)
	{
		tile.setColor(color);
		tileRepository.save(tile);
	}
}
