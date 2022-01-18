package TPLab4.ChineseCheckersBackend.Tile;

import TPLab4.ChineseCheckersBackend.Game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tile service class
 */
@Service
@Transactional
public class TileService {
    /**
     * Tile repository
     */
    @Autowired
    private TileRepository tileRepository;

    /**
     * Method which loads tile by id.
     *
     * @param tileId Tile id
     * @return Tile
     * @throws TileNotFoundException When tile was not found.
     */
    public Tile loadTileById(Long tileId) throws TileNotFoundException {
        Tile tile = tileRepository.findById(tileId).orElseThrow(() -> new TileNotFoundException("Tile does not exist!"));

        return tile;
    }

    /**
     * Method for creating a tile.
     *
     * @param x     X coordinate
     * @param y     Y coordinate
     * @param color TileColor
     * @param game  Game
     * @return New tile
     */
    public Tile createTile(Long x, Long y, TileColor color, Game game) {
        Tile tile = new Tile();
        tile.setX(x);
        tile.setY(y);
        tile.setColor(color);
        tile.setGame(game);

        tileRepository.save(tile);

        return tile;
    }

    /**
     * Method which updates tile color
     *
     * @param tile  Tile
     * @param color TileColor
     */
    public void updateTileColor(Tile tile, TileColor color) {
        tile.setColor(color);

        tileRepository.save(tile);
    }
}
