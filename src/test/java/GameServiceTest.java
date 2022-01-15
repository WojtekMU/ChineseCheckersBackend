import TPLab4.ChineseCheckersBackend.Game.*;
import TPLab4.ChineseCheckersBackend.GameFactory.StandardFourPlayersGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.StandardSixPlayersGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.StandardThreePlayersGameFactory;
import TPLab4.ChineseCheckersBackend.GameFactory.StandardTwoPlayersGameFactory;
import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.History.HistoryRepository;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import TPLab4.ChineseCheckersBackend.User.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class GameServiceTest
{
    @Mock
    private GameRepository gameRepository;

    @Mock
    private TileRepository tileRepository;

    @Mock
    private HistoryRepository historyRepository;

    @Mock
    private StandardTwoPlayersGameFactory standardTwoPlayersGameFactory;

    @Mock
    private StandardThreePlayersGameFactory standardThreePlayersGameFactory;

    @Mock
    private StandardFourPlayersGameFactory standardFourPlayersGameFactory;

    @Mock
    private StandardSixPlayersGameFactory standardSixPlayersGameFactory;

    @InjectMocks
    private GameService gameService;

    @Mock
    private User user;

    @Test
    public void loadGameByIdTest()
    {
        Long gameId  = 1L;
        Game game1 = Mockito.mock(Game.class);
        Mockito.when(gameRepository.findById(gameId)).thenReturn(Optional.of(game1));
        Game game2 = gameService.loadGameById(gameId);
        Assertions.assertEquals(game1, game2);
    }

    @Test
    public void loadGameByIdGameNotFoundTest()
    {
        GameNotFoundException thrown = Assertions.assertThrows(GameNotFoundException.class, () -> {
            Long gameId  = 1L;
            Mockito.when(gameRepository.findById(gameId)).thenReturn(Optional.empty());
            gameService.loadGameById(gameId);
        });
    }

    @Test
    public void createGameSixPlayersTest()
    {
        User user1 = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);
        User user3 = Mockito.mock(User.class);
        User user4 = Mockito.mock(User.class);
        User user5 = Mockito.mock(User.class);
        User user6 = Mockito.mock(User.class);

        List<User> userList = new ArrayList<User>();

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        userList.add(user6);

        gameService.createGame(userList, user1);
        verify(standardSixPlayersGameFactory, times(1)).createGame(userList);
    }

    @Test
    public void createGameFivePlayersTest()
    {
        CantCreateGameException thrown = Assertions.assertThrows(CantCreateGameException.class, () -> {
            User user1 = Mockito.mock(User.class);
            User user2 = Mockito.mock(User.class);
            User user3 = Mockito.mock(User.class);
            User user4 = Mockito.mock(User.class);
            User user5 = Mockito.mock(User.class);

            List<User> userList = new ArrayList<User>();

            userList.add(user1);
            userList.add(user2);
            userList.add(user3);
            userList.add(user4);
            userList.add(user5);

            gameService.createGame(userList, user1);
        });
    }

    @Test
    public void createGameFourPlayersTest()
    {
        User user1 = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);
        User user3 = Mockito.mock(User.class);
        User user4 = Mockito.mock(User.class);

        List<User> userList = new ArrayList<User>();

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        gameService.createGame(userList, user1);
        verify(standardFourPlayersGameFactory, times(1)).createGame(userList);
    }

    @Test
    public void createGameThreePlayersTest()
    {
        User user1 = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);
        User user3 = Mockito.mock(User.class);

        List<User> userList = new ArrayList<User>();

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        gameService.createGame(userList, user1);
        verify(standardThreePlayersGameFactory, times(1)).createGame(userList);
    }

    @Test
    public void createGameTwoPlayersTest()
    {
        User user1 = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);

        List<User> userList = new ArrayList<User>();

        userList.add(user1);
        userList.add(user2);

        gameService.createGame(userList, user1);
        verify(standardTwoPlayersGameFactory, times(1)).createGame(userList);
    }

    @Test
    public void createGameOnePlayerTest()
    {
        CantCreateGameException thrown = Assertions.assertThrows(CantCreateGameException.class, () -> {
            User user1 = Mockito.mock(User.class);

            List<User> userList = new ArrayList<User>();

            userList.add(user1);

            gameService.createGame(userList, user1);
        });
    }

    @Test
    public void createGameNotTheHostTest()
    {
        CantCreateGameException thrown = Assertions.assertThrows(CantCreateGameException.class, () -> {
            User user1 = Mockito.mock(User.class);
            User user2 = Mockito.mock(User.class);

            List<User> userList = new ArrayList<User>();

            userList.add(user1);
            userList.add(user2);

            gameService.createGame(userList, user2);
        });
    }

    @Test
    public void createGameNotInRoomTest()
    {
        AccessDeniedException thrown = Assertions.assertThrows(AccessDeniedException.class, () -> {
            User user1 = Mockito.mock(User.class);
            User user2 = Mockito.mock(User.class);

            List<User> userList = new ArrayList<User>();

            userList.add(user1);
            userList.add(user2);

            gameService.createGame(userList, user);
        });
    }

    @Test
    public void updatePlayerTurnTest()
    {
        Game game = Mockito.mock(Game.class);
        History history = Mockito.mock(History.class);
        User user1 = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);
        User user3 = Mockito.mock(User.class);

        List<User> userList = new ArrayList<User>();

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        List<User> leaderboard = new ArrayList<User>();

        leaderboard.add(user2);

        Mockito.when(game.getPlayerTurn()).thenReturn(1);
        Mockito.when(game.getHistory()).thenReturn(history);
        Mockito.when(game.getPlayers()).thenReturn(userList);
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user2, user3);
        Mockito.when(history.getLeaderboard()).thenReturn(leaderboard);

        Integer playerTurn = gameService.updatePlayerTurn(game);
        Assertions.assertEquals(3, playerTurn);
    }

    @Test
    public void updatePlayerTurnWithReturnTest()
    {
        Game game = Mockito.mock(Game.class);
        History history = Mockito.mock(History.class);
        User user1 = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);
        User user3 = Mockito.mock(User.class);

        List<User> userList = new ArrayList<User>();

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        List<User> leaderboard = new ArrayList<User>();

        leaderboard.add(user3);

        Mockito.when(game.getPlayerTurn()).thenReturn(2);
        Mockito.when(game.getHistory()).thenReturn(history);
        Mockito.when(game.getPlayers()).thenReturn(userList);
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user3, user1);
        Mockito.when(history.getLeaderboard()).thenReturn(leaderboard);

        Integer playerTurn = gameService.updatePlayerTurn(game);
        Assertions.assertEquals(1, playerTurn);
    }

//    @Test
//    public void moveTest()
//    {
//        Tile firstTile = Mockito.mock(Tile.class);
//        Tile secondTile = Mockito.mock(Tile.class);
//        Game game = Mockito.mock(Game.class);
//
//        Mockito.when(secondTile.getColor()).thenReturn(TileColor.RED);
//
//        gameService.move(firstTile, secondTile, game);
//    }
}
