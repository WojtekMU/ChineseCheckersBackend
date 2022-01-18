import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Game.GameService;
import TPLab4.ChineseCheckersBackend.Game.GameStatus;
import TPLab4.ChineseCheckersBackend.History.History;
import TPLab4.ChineseCheckersBackend.MoveChecker.StandardTwoPlayersMoveChecker;
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
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class MoveCheckerTest {
    @Mock
    protected TileRepository tileRepository;

    @Mock
    protected GameService gameService;

    @InjectMocks
    private StandardTwoPlayersMoveChecker standardTwoPlayersMoveChecker;

    @Mock
    private User user;

    @Mock
    private Game game;

    @Mock
    private Tile tile;

    @Mock
    private Tile chosenTile;

    @Test
    public void checkMoveDistanceOneDuringMoveFalseTest() {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.ONGOING);
        Mockito.when(game.getDuringMove()).thenReturn(false);
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(tile.getColor()).thenReturn(TileColor.WHITE);
        Mockito.when(game.getPlayerTurn()).thenReturn(1);
        Mockito.when(tile.getX()).thenReturn(6L);
        Mockito.when(tile.getY()).thenReturn(6L);
        Mockito.when(chosenTile.getX()).thenReturn(6L);
        Mockito.when(chosenTile.getY()).thenReturn(7L);
        Mockito.when(game.getId()).thenReturn(1L);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();

        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);

        Pair<Boolean, Boolean> result = standardTwoPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(true, result.getFirst());
        Assertions.assertEquals(true, result.getSecond());
    }

    @Test
    public void checkMoveDistanceOneDuringMoveTrueTest() {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.ONGOING);
        Mockito.when(game.getDuringMove()).thenReturn(true);
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(tile.getColor()).thenReturn(TileColor.WHITE);
        Mockito.when(game.getPlayerTurn()).thenReturn(1);
        Mockito.when(tile.getX()).thenReturn(6L);
        Mockito.when(tile.getY()).thenReturn(6L);
        Mockito.when(chosenTile.getX()).thenReturn(6L);
        Mockito.when(chosenTile.getY()).thenReturn(7L);
        Mockito.when(game.getId()).thenReturn(1L);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();

        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);

        Pair<Boolean, Boolean> result = standardTwoPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(false, result.getFirst());
        Assertions.assertEquals(false, result.getSecond());
    }

    @Test
    public void checkMoveWrongPlayerTest() {
        User user2 = Mockito.mock(User.class);

        Mockito.when(game.getPlayerWithTurn()).thenReturn(user2);
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.ONGOING);
        Mockito.when(game.getDuringMove()).thenReturn(false);
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(tile.getColor()).thenReturn(TileColor.WHITE);
        Mockito.when(game.getPlayerTurn()).thenReturn(1);
        Mockito.when(tile.getX()).thenReturn(6L);
        Mockito.when(tile.getY()).thenReturn(6L);
        Mockito.when(chosenTile.getX()).thenReturn(6L);
        Mockito.when(chosenTile.getY()).thenReturn(7L);
        Mockito.when(game.getId()).thenReturn(1L);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();

        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);

        Pair<Boolean, Boolean> result = standardTwoPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(false, result.getFirst());
        Assertions.assertEquals(false, result.getSecond());
    }

    @Test
    public void checkMoveWrongGameStatusTest() {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.FINISHED);
        Mockito.when(game.getDuringMove()).thenReturn(false);
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(tile.getColor()).thenReturn(TileColor.WHITE);
        Mockito.when(game.getPlayerTurn()).thenReturn(1);
        Mockito.when(tile.getX()).thenReturn(6L);
        Mockito.when(tile.getY()).thenReturn(6L);
        Mockito.when(chosenTile.getX()).thenReturn(6L);
        Mockito.when(chosenTile.getY()).thenReturn(7L);
        Mockito.when(game.getId()).thenReturn(1L);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();

        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);

        Pair<Boolean, Boolean> result = standardTwoPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(false, result.getFirst());
        Assertions.assertEquals(false, result.getSecond());
    }

    @Test
    public void checkMoveWrongTileColorTest() {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.FINISHED);
        Mockito.when(game.getDuringMove()).thenReturn(false);
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(tile.getColor()).thenReturn(TileColor.RED);
        Mockito.when(game.getPlayerTurn()).thenReturn(1);
        Mockito.when(tile.getX()).thenReturn(6L);
        Mockito.when(tile.getY()).thenReturn(6L);
        Mockito.when(chosenTile.getX()).thenReturn(6L);
        Mockito.when(chosenTile.getY()).thenReturn(7L);
        Mockito.when(game.getId()).thenReturn(1L);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();

        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);

        Pair<Boolean, Boolean> result = standardTwoPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(false, result.getFirst());
        Assertions.assertEquals(false, result.getSecond());
    }

    @Test
    public void checkMoveChosenTileNullTest() {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.ONGOING);
        Mockito.when(game.getDuringMove()).thenReturn(false);
        Mockito.when(game.getChosenTile()).thenReturn(null);
        Mockito.when(tile.getColor()).thenReturn(TileColor.WHITE);
        Mockito.when(game.getPlayerTurn()).thenReturn(1);
        Mockito.when(tile.getX()).thenReturn(6L);
        Mockito.when(tile.getY()).thenReturn(6L);
        Mockito.when(chosenTile.getX()).thenReturn(6L);
        Mockito.when(chosenTile.getY()).thenReturn(7L);
        Mockito.when(game.getId()).thenReturn(1L);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();

        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);

        Pair<Boolean, Boolean> result = standardTwoPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(false, result.getFirst());
        Assertions.assertEquals(false, result.getSecond());
    }

    @Test
    public void checkMoveWrongDistanceTest() {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.ONGOING);
        Mockito.when(game.getDuringMove()).thenReturn(false);
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(tile.getColor()).thenReturn(TileColor.WHITE);
        Mockito.when(game.getPlayerTurn()).thenReturn(1);
        Mockito.when(tile.getX()).thenReturn(6L);
        Mockito.when(tile.getY()).thenReturn(6L);
        Mockito.when(chosenTile.getX()).thenReturn(6L);
        Mockito.when(chosenTile.getY()).thenReturn(11L);
        Mockito.when(game.getId()).thenReturn(1L);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();

        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);

        Pair<Boolean, Boolean> result = standardTwoPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(false, result.getFirst());
        Assertions.assertEquals(false, result.getSecond());
    }

    @Test
    public void checkMoveDistanceTwoTest() {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.ONGOING);
        Mockito.when(game.getDuringMove()).thenReturn(false);
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(tile.getColor()).thenReturn(TileColor.WHITE);
        Mockito.when(game.getPlayerTurn()).thenReturn(1);
        Mockito.when(tile.getX()).thenReturn(6L);
        Mockito.when(tile.getY()).thenReturn(8L);
        Mockito.when(chosenTile.getX()).thenReturn(6L);
        Mockito.when(chosenTile.getY()).thenReturn(6L);
        Mockito.when(game.getId()).thenReturn(1L);

        Tile middleTile = Mockito.mock(Tile.class);

        Mockito.when(middleTile.getColor()).thenReturn(TileColor.RED);

        Mockito.when(tileRepository.getByXAndYAndGameId((chosenTile.getX() + tile.getX()) / 2, (chosenTile.getY() + tile.getY()) / 2, game.getId())).thenReturn(middleTile);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();

        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);

        Pair<Boolean, Boolean> result = standardTwoPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(true, result.getFirst());
        Assertions.assertEquals(false, result.getSecond());
    }

    @Test
    public void checkMoveDistanceTwoWrongTileBetweenTest() {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.ONGOING);
        Mockito.when(game.getDuringMove()).thenReturn(false);
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(tile.getColor()).thenReturn(TileColor.WHITE);
        Mockito.when(game.getPlayerTurn()).thenReturn(1);
        Mockito.when(tile.getX()).thenReturn(6L);
        Mockito.when(tile.getY()).thenReturn(8L);
        Mockito.when(chosenTile.getX()).thenReturn(6L);
        Mockito.when(chosenTile.getY()).thenReturn(6L);
        Mockito.when(game.getId()).thenReturn(1L);

        Tile middleTile = Mockito.mock(Tile.class);

        Mockito.when(middleTile.getColor()).thenReturn(TileColor.WHITE);

        Mockito.when(tileRepository.getByXAndYAndGameId((chosenTile.getX() + tile.getX()) / 2, (chosenTile.getY() + tile.getY()) / 2, game.getId())).thenReturn(middleTile);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();

        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);

        Pair<Boolean, Boolean> result = standardTwoPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(false, result.getFirst());
        Assertions.assertEquals(false, result.getSecond());
    }

    @Test
    public void checkMoveDistanceTwoWrongTileTest() {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.ONGOING);
        Mockito.when(game.getDuringMove()).thenReturn(false);
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(tile.getColor()).thenReturn(TileColor.RED);
        Mockito.when(game.getPlayerTurn()).thenReturn(1);
        Mockito.when(tile.getX()).thenReturn(6L);
        Mockito.when(tile.getY()).thenReturn(8L);
        Mockito.when(chosenTile.getX()).thenReturn(6L);
        Mockito.when(chosenTile.getY()).thenReturn(6L);
        Mockito.when(game.getId()).thenReturn(1L);

        Tile middleTile = Mockito.mock(Tile.class);

        Mockito.when(middleTile.getColor()).thenReturn(TileColor.RED);

        Mockito.when(tileRepository.getByXAndYAndGameId((chosenTile.getX() + tile.getX()) / 2, (chosenTile.getY() + tile.getY()) / 2, game.getId())).thenReturn(middleTile);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();

        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);

        Pair<Boolean, Boolean> result = standardTwoPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(false, result.getFirst());
        Assertions.assertEquals(false, result.getSecond());
    }

    @Test
    public void checkMoveDistanceTwoDuringMoveTest() {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.ONGOING);
        Mockito.when(game.getDuringMove()).thenReturn(true);
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(tile.getColor()).thenReturn(TileColor.WHITE);
        Mockito.when(game.getPlayerTurn()).thenReturn(1);
        Mockito.when(tile.getX()).thenReturn(6L);
        Mockito.when(tile.getY()).thenReturn(8L);
        Mockito.when(chosenTile.getX()).thenReturn(6L);
        Mockito.when(chosenTile.getY()).thenReturn(6L);
        Mockito.when(game.getId()).thenReturn(1L);

        Tile middleTile = Mockito.mock(Tile.class);

        Mockito.when(middleTile.getColor()).thenReturn(TileColor.RED);

        Mockito.when(tileRepository.getByXAndYAndGameId((chosenTile.getX() + tile.getX()) / 2, (chosenTile.getY() + tile.getY()) / 2, game.getId())).thenReturn(middleTile);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();

        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);

        Pair<Boolean, Boolean> result = standardTwoPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(true, result.getFirst());
        Assertions.assertEquals(false, result.getSecond());
    }

    @Test
    public void checkEndTurnTest() {
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.ONGOING);
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);

        boolean result = standardTwoPlayersMoveChecker.checkEndTurn(user, game);

        Assertions.assertEquals(true, result);
    }

    @Test
    public void checkEndTurnWrongGameStatusTest() {
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.FINISHED);
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);

        boolean result = standardTwoPlayersMoveChecker.checkEndTurn(user, game);

        Assertions.assertEquals(false, result);
    }

    @Test
    public void checkEndTurnWrongUserTest() {
        User user1 = Mockito.mock(User.class);

        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.ONGOING);
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user1);

        boolean result = standardTwoPlayersMoveChecker.checkEndTurn(user, game);

        Assertions.assertEquals(false, result);
    }

    @Test
    public void isGameFinishedTrueTest() {
        User user1 = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);
        History history = Mockito.mock(History.class);

        List<User> userList = Arrays.asList(user1, user2);
        List<User> leaderboard = Arrays.asList(user1);

        Mockito.when(game.getPlayers()).thenReturn(userList);
        Mockito.when(game.getHistory()).thenReturn(history);
        Mockito.when(history.getLeaderboard()).thenReturn(leaderboard);

        boolean result = standardTwoPlayersMoveChecker.isGameFinished(game);

        Assertions.assertEquals(true, result);
    }

    @Test
    public void isGameFinishedFalseTest() {
        User user1 = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);
        History history = Mockito.mock(History.class);

        List<User> userList = Arrays.asList(user1, user2);
        List<User> leaderboard = Arrays.asList();

        Mockito.when(game.getPlayers()).thenReturn(userList);
        Mockito.when(game.getHistory()).thenReturn(history);
        Mockito.when(history.getLeaderboard()).thenReturn(leaderboard);

        boolean result = standardTwoPlayersMoveChecker.isGameFinished(game);

        Assertions.assertEquals(false, result);
    }
}
