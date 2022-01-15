import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Game.GameRepository;
import TPLab4.ChineseCheckersBackend.Game.GameService;
import TPLab4.ChineseCheckersBackend.Game.GameStatus;
import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.History.HistoryService;
import TPLab4.ChineseCheckersBackend.Move.MoveRepository;
import TPLab4.ChineseCheckersBackend.Move.MoveService;
import TPLab4.ChineseCheckersBackend.MoveChecker.MoveCheckerGetter;
import TPLab4.ChineseCheckersBackend.MoveChecker.StandardThreePlayersMoveChecker;
import TPLab4.ChineseCheckersBackend.MoveChecker.StandardTwoPlayersMoveChecker;
import TPLab4.ChineseCheckersBackend.Room.RoomService;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.Tile.TileColor;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import TPLab4.ChineseCheckersBackend.User.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class MoveServiceTest
{
    @Mock
    private MoveRepository moveRepository;

    @Mock
    private TileRepository tileRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameService gameService;

    @Mock
    private RoomService roomService;

    @Mock
    private HistoryService historyService;

    @Mock
    private MoveCheckerGetter moveCheckerGetter;

    @Spy
    @InjectMocks
    private MoveService moveService;

    @Mock
    private User user;

    @Mock
    private Game game;

    @Mock
    private Tile tile;

    @Mock
    private History history;

    @Test
    public void endTurnAddWinnerWithoutFinishTest()
    {
        StandardThreePlayersMoveChecker standardThreePlayersMoveChecker = Mockito.mock(StandardThreePlayersMoveChecker.class);

        User user1 = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);

        List<User> userList = new ArrayList<User>();
        userList.add(user);
        userList.add(user1);
        userList.add(user2);

        List<User> leaderboard = new ArrayList<User>();

        Mockito.when(moveCheckerGetter.getMoveChecker(game)).thenReturn(standardThreePlayersMoveChecker);
        Mockito.when(standardThreePlayersMoveChecker.checkEndTurn(user, game)).thenReturn(true);
        Mockito.when(standardThreePlayersMoveChecker.isCurrentPlayerWinner(game)).thenReturn(true);
        Mockito.when(standardThreePlayersMoveChecker.isGameFinished(game)).thenReturn(false);
        Mockito.when(game.getPlayers()).thenReturn(userList);
        Mockito.when(history.getLeaderboard()).thenReturn(leaderboard);

        doAnswer(invocation -> {
            leaderboard.add(user);

            return null;
        }).when(historyService).addPlayerToLeaderboard(history, user);

        doAnswer(invocation -> {
            leaderboard.add(user1);

            return null;
        }).when(historyService).addPlayerToLeaderboard(history, user1);

        doAnswer(invocation -> {
            leaderboard.add(user2);

            return null;
        }).when(historyService).addPlayerToLeaderboard(history, user2);

        moveService.endTurn(user, game, history);

        verify(historyService, times(1)).addPlayerToLeaderboard(history, user);
        verify(historyService, times(0)).addPlayerToLeaderboard(history, user1);
        verify(historyService, times(0)).addPlayerToLeaderboard(history, user2);
        verify(gameService, times(1)).updatePlayerTurn(game);
    }

    @Test
    public void endTurnNoNewWinnerTest()
    {
        StandardThreePlayersMoveChecker standardThreePlayersMoveChecker = Mockito.mock(StandardThreePlayersMoveChecker.class);

        User user1 = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);

        List<User> userList = new ArrayList<User>();
        userList.add(user);
        userList.add(user1);
        userList.add(user2);

        List<User> leaderboard = new ArrayList<User>();

        Mockito.when(moveCheckerGetter.getMoveChecker(game)).thenReturn(standardThreePlayersMoveChecker);
        Mockito.when(standardThreePlayersMoveChecker.checkEndTurn(user, game)).thenReturn(true);
        Mockito.when(standardThreePlayersMoveChecker.isCurrentPlayerWinner(game)).thenReturn(false);
        Mockito.when(standardThreePlayersMoveChecker.isGameFinished(game)).thenReturn(false);
        Mockito.when(game.getPlayers()).thenReturn(userList);
        Mockito.when(history.getLeaderboard()).thenReturn(leaderboard);

        doAnswer(invocation -> {
            leaderboard.add(user);

            return null;
        }).when(historyService).addPlayerToLeaderboard(history, user);

        doAnswer(invocation -> {
            leaderboard.add(user1);

            return null;
        }).when(historyService).addPlayerToLeaderboard(history, user1);

        doAnswer(invocation -> {
            leaderboard.add(user2);

            return null;
        }).when(historyService).addPlayerToLeaderboard(history, user2);

        moveService.endTurn(user, game, history);

        verify(historyService, times(0)).addPlayerToLeaderboard(history, user);
        verify(historyService, times(0)).addPlayerToLeaderboard(history, user1);
        verify(historyService, times(0)).addPlayerToLeaderboard(history, user2);
        verify(gameService, times(1)).updatePlayerTurn(game);
    }

    @Test
    public void endTurnFinishedGameTest()
    {
        StandardThreePlayersMoveChecker standardThreePlayersMoveChecker = Mockito.mock(StandardThreePlayersMoveChecker.class);

        User user1 = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);

        List<User> userList = new ArrayList<User>();
        userList.add(user);
        userList.add(user1);
        userList.add(user2);

        List<User> leaderboard = new ArrayList<User>();
        leaderboard.add(user1);

        Mockito.when(moveCheckerGetter.getMoveChecker(game)).thenReturn(standardThreePlayersMoveChecker);
        Mockito.when(standardThreePlayersMoveChecker.checkEndTurn(user, game)).thenReturn(true);
        Mockito.when(standardThreePlayersMoveChecker.isCurrentPlayerWinner(game)).thenReturn(true);
        Mockito.when(standardThreePlayersMoveChecker.isGameFinished(game)).thenReturn(true);
        Mockito.when(game.getPlayers()).thenReturn(userList);
        Mockito.when(history.getLeaderboard()).thenReturn(leaderboard);

        doAnswer(invocation -> {
            leaderboard.add(user);

            return null;
        }).when(historyService).addPlayerToLeaderboard(history, user);

        doAnswer(invocation -> {
            leaderboard.add(user1);

            return null;
        }).when(historyService).addPlayerToLeaderboard(history, user1);

        doAnswer(invocation -> {
            leaderboard.add(user2);

            return null;
        }).when(historyService).addPlayerToLeaderboard(history, user2);

        moveService.endTurn(user, game, history);

        verify(historyService, times(1)).addPlayerToLeaderboard(history, user);
        verify(historyService, times(0)).addPlayerToLeaderboard(history, user1);
        verify(historyService, times(1)).addPlayerToLeaderboard(history, user2);
        verify(gameService, times(0)).updatePlayerTurn(game);
    }

    @Test
    public void moveGoodMoveWithEndTurnTest()
    {
        StandardThreePlayersMoveChecker standardThreePlayersMoveChecker = Mockito.mock(StandardThreePlayersMoveChecker.class);

        Tile chosenTile = Mockito.mock(Tile.class);

        Mockito.when(moveCheckerGetter.getMoveChecker(game)).thenReturn(standardThreePlayersMoveChecker);
        Mockito.when(standardThreePlayersMoveChecker.checkMove(user, game, tile)).thenReturn(Pair.of(Boolean.TRUE, Boolean.TRUE));
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(chosenTile.getColor()).thenReturn(TileColor.RED);
        Mockito.when(moveCheckerGetter.getMoveChecker(game)).thenReturn(standardThreePlayersMoveChecker);

        moveService.move(user, game, tile,  history);

        verify(tileRepository, times(1)).save(game.getChosenTile());
        verify(tileRepository, times(1)).save(tile);
        verify(gameRepository, times(1)).save(game);
        verify(moveService, times(1)).endTurn(user, game, history);
    }

    @Test
    public void moveGoodMoveWithoutEndTurnTest()
    {
        StandardThreePlayersMoveChecker standardThreePlayersMoveChecker = Mockito.mock(StandardThreePlayersMoveChecker.class);

        Tile chosenTile = Mockito.mock(Tile.class);

        Mockito.when(moveCheckerGetter.getMoveChecker(game)).thenReturn(standardThreePlayersMoveChecker);
        Mockito.when(standardThreePlayersMoveChecker.checkMove(user, game, tile)).thenReturn(Pair.of(Boolean.TRUE, Boolean.FALSE));
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(chosenTile.getColor()).thenReturn(TileColor.RED);
        Mockito.when(moveCheckerGetter.getMoveChecker(game)).thenReturn(standardThreePlayersMoveChecker);

        moveService.move(user, game, tile,  history);

        verify(tileRepository, times(1)).save(game.getChosenTile());
        verify(tileRepository, times(1)).save(tile);
        verify(gameRepository, times(1)).save(game);
        verify(moveService, times(0)).endTurn(user, game, history);
    }

    @Test
    public void moveBadMoveTest()
    {
        StandardThreePlayersMoveChecker standardThreePlayersMoveChecker = Mockito.mock(StandardThreePlayersMoveChecker.class);

        Tile chosenTile = Mockito.mock(Tile.class);

        Mockito.when(moveCheckerGetter.getMoveChecker(game)).thenReturn(standardThreePlayersMoveChecker);
        Mockito.when(standardThreePlayersMoveChecker.checkMove(user, game, tile)).thenReturn(Pair.of(Boolean.FALSE, Boolean.FALSE));
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(chosenTile.getColor()).thenReturn(TileColor.RED);
        Mockito.when(moveCheckerGetter.getMoveChecker(game)).thenReturn(standardThreePlayersMoveChecker);

        moveService.move(user, game, tile,  history);

        verify(tileRepository, times(0)).save(game.getChosenTile());
        verify(tileRepository, times(0)).save(tile);
        verify(gameRepository, times(0)).save(game);
        verify(moveService, times(0)).endTurn(user, game, history);
    }
}
