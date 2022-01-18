import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Game.GameRepository;
import TPLab4.ChineseCheckersBackend.Game.StandardThreePlayersGame;
import TPLab4.ChineseCheckersBackend.GameFactory.StandardThreePlayersGameFactory;
import TPLab4.ChineseCheckersBackend.History.HistoryRepository;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import TPLab4.ChineseCheckersBackend.Tile.TileService;
import TPLab4.ChineseCheckersBackend.User.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class StandardThreePlayersGameFactoryTest {
    @Mock
    private TileService tileService;

    @Mock
    private TileRepository tileRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private StandardThreePlayersGameFactory standardThreePlayersGameFactory;

    @Test
    public void standardThreePlayersGameFactoryTest() {
        User user1 = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);
        User user3 = Mockito.mock(User.class);

        List<User> userList = Arrays.asList(user1, user2, user3);

        Game game = standardThreePlayersGameFactory.createGame(userList);

        Assertions.assertEquals(StandardThreePlayersGame.class, game.getClass());
    }
}
