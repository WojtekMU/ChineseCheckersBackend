import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Tile.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class TileServiceTest {
    @Mock
    private TileRepository tileRepository;

    @InjectMocks
    private TileService tileService;

    @Test
    public void loadTileByIdTest() {
        Long tileId = 1L;
        Tile tile1 = Mockito.mock(Tile.class);
        Mockito.when(tileRepository.findById(tileId)).thenReturn(Optional.of(tile1));
        Tile tile2 = tileService.loadTileById(tileId);
        Assertions.assertEquals(tile1, tile2);
    }

    @Test
    public void loadTileByIdTileNotFoundTest() {
        TileNotFoundException thrown = Assertions.assertThrows(TileNotFoundException.class, () -> {
            Long tileId = 1L;
            Mockito.when(tileRepository.findById(tileId)).thenReturn(Optional.empty());
            tileService.loadTileById(tileId);
        });
    }

    @Test
    public void createTileTest() {
        Long x = 1L;
        Long y = 2L;
        Game game = Mockito.mock(Game.class);

        Tile tile = tileService.createTile(x, y, TileColor.WHITE, game);
        Assertions.assertEquals(1L, tile.getX());
        Assertions.assertEquals(2L, tile.getY());
        Assertions.assertEquals(TileColor.WHITE, tile.getColor());
        Assertions.assertEquals(game, tile.getGame());
    }
}
