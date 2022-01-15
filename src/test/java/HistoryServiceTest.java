
import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Game.GameRepository;
import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.History.HistoryNotFoundException;
import TPLab4.ChineseCheckersBackend.History.HistoryRepository;
import TPLab4.ChineseCheckersBackend.History.HistoryService;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.Tile.TileNotFoundException;
import TPLab4.ChineseCheckersBackend.Tile.TileService;
import TPLab4.ChineseCheckersBackend.User.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class HistoryServiceTest
{
    @Mock
    private HistoryRepository historyRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private HistoryService historyService;

    @Test
    public void loadHistoryByIdTest()
    {
        Long historyId  = 1L;
        History history1 = Mockito.mock(History.class);
        Mockito.when(historyRepository.findById(historyId)).thenReturn(Optional.of(history1));
        History history2 = historyService.loadHistoryById(historyId);
        Assertions.assertEquals(history1, history2);
    }

    @Test
    public void loadHistoryByIdHistoryNotFoundTest()
    {
        HistoryNotFoundException thrown = Assertions.assertThrows(HistoryNotFoundException.class, () -> {
            Long historyId  = 1L;
            Mockito.when(historyRepository.findById(historyId)).thenReturn(Optional.empty());
            historyService.loadHistoryById(historyId);
        });
    }

    @Test
    public void loadHistoryByGameIdTest()
    {
        Long gameId  = 1L;
        History history1 = Mockito.mock(History.class);
        Mockito.when(historyRepository.findByGameId(gameId)).thenReturn(Optional.of(history1));
        History history2 = historyService.loadHistoryByGameId(gameId);
        Assertions.assertEquals(history1, history2);
    }

    @Test
    public void loadHistoryByGameIdHistoryNotFoundTest()
    {
        HistoryNotFoundException thrown = Assertions.assertThrows(HistoryNotFoundException.class, () -> {
            Long gameId  = 1L;
            Mockito.when(gameRepository.findById(gameId)).thenReturn(Optional.empty());
            historyService.loadHistoryByGameId(gameId);
        });
    }

    @Test
    public void addPlayerToLeaderboardTest()
    {
        History history = Mockito.mock(History.class);
        User user = Mockito.mock(User.class);

        Mockito.when(history.getLeaderboard()).thenReturn(new ArrayList<User>());

        historyService.addPlayerToLeaderboard(history, user);

        verify(historyRepository, times(1)).save(history);
    }

    @Test
    public void addPlayerToLeaderboardPlayerAlreadyOnLeaderboardTest()
    {
        AccessDeniedException thrown = Assertions.assertThrows(AccessDeniedException.class, () -> {
            History history = Mockito.mock(History.class);
            User user = Mockito.mock(User.class);

            List<User> leaderboard = new ArrayList<User>();

            leaderboard.add(user);

            Mockito.when(history.getLeaderboard()).thenReturn(leaderboard);

            historyService.addPlayerToLeaderboard(history, user);
        });
    }
}
