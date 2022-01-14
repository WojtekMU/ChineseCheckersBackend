package TPLab4.ChineseCheckersBackend.Tile;

import TPLab4.ChineseCheckersBackend.Room.Room;
import TPLab4.ChineseCheckersBackend.Room.RoomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TPLab4.ChineseCheckersBackend.Game.Game;

@Service
@Transactional
public class TileService 
{
	@Autowired
	private TileRepository tileRepository;

	public Tile loadTileById(Long tileId) throws TileNotFoundException
	{
		Tile tile = tileRepository.findById(tileId).orElseThrow(() -> new TileNotFoundException("Tile does not exist!"));

		return tile;
	}

	public Tile createTile(Long x, Long y, TileColor color, Game game) 
	{
		Tile tile = new Tile();
		tile.setX(x);
		tile.setY(y);
		tile.setColor(color);
		tile.setGame(game);
		
		tileRepository.save(tile);

		return tile;
	}
	
	public void updateTileColor(Tile tile, TileColor color)
	{
		tile.setColor(color);
		
		tileRepository.save(tile);
	}
}
