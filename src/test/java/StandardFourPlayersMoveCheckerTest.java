import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Game.GameService;
import TPLab4.ChineseCheckersBackend.Game.GameStatus;
import TPLab4.ChineseCheckersBackend.MoveChecker.StandardFourPlayersMoveChecker;
import TPLab4.ChineseCheckersBackend.MoveChecker.StandardSixPlayersMoveChecker;
import TPLab4.ChineseCheckersBackend.MoveChecker.StandardThreePlayersMoveChecker;
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
import java.util.List;

@ExtendWith(SpringExtension.class)
public class StandardFourPlayersMoveCheckerTest
{
    @Mock
    protected TileRepository tileRepository;

    @Mock
    protected GameService gameService;

    @InjectMocks
    private StandardFourPlayersMoveChecker standardFourPlayersMoveChecker;

    @Mock
    private User user;

    @Mock
    private Game game;

    @Mock
    private Tile tile;

    @Mock
    private Tile chosenTile;

    @Test
    public void checkMoveFourPlayersPlayerOneOutOfOppositeCornerTest()
    {
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

        List<Tile> secondCorner = new ArrayList<Tile>();
        List<Tile> thirdCorner = new ArrayList<Tile>();
        List<Tile> fifthCorner = new ArrayList<Tile>();
        List<Tile> sixthCorner = new ArrayList<Tile>();
        thirdCorner.add(chosenTile);

        Mockito.when(tileRepository.getSecondCorner(game.getId())).thenReturn(secondCorner);
        Mockito.when(tileRepository.getThirdCorner(game.getId())).thenReturn(thirdCorner);
        Mockito.when(tileRepository.getSixthCorner(game.getId())).thenReturn(sixthCorner);
        Mockito.when(tileRepository.getFifthCorner(game.getId())).thenReturn(fifthCorner);

        Pair<Boolean, Boolean> result = standardFourPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(false, result.getFirst());
        Assertions.assertEquals(false, result.getSecond());
    }

    @Test
    public void checkMoveFourPlayersPlayerOneInsideOppositeCornerTest()
    {
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

        List<Tile> secondCorner = new ArrayList<Tile>();
        List<Tile> thirdCorner = new ArrayList<Tile>();
        List<Tile> fifthCorner = new ArrayList<Tile>();
        List<Tile> sixthCorner = new ArrayList<Tile>();
        thirdCorner.add(chosenTile);
        thirdCorner.add(tile);

        Mockito.when(tileRepository.getSecondCorner(game.getId())).thenReturn(secondCorner);
        Mockito.when(tileRepository.getThirdCorner(game.getId())).thenReturn(thirdCorner);
        Mockito.when(tileRepository.getSixthCorner(game.getId())).thenReturn(sixthCorner);
        Mockito.when(tileRepository.getFifthCorner(game.getId())).thenReturn(fifthCorner);

        Pair<Boolean, Boolean> result = standardFourPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(true, result.getFirst());
        Assertions.assertEquals(true, result.getSecond());
    }

    @Test
    public void checkMoveFourPlayersPlayerTwoOutOfOppositeCornerTest()
    {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.ONGOING);
        Mockito.when(game.getDuringMove()).thenReturn(false);
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(tile.getColor()).thenReturn(TileColor.WHITE);
        Mockito.when(game.getPlayerTurn()).thenReturn(2);
        Mockito.when(tile.getX()).thenReturn(6L);
        Mockito.when(tile.getY()).thenReturn(6L);
        Mockito.when(chosenTile.getX()).thenReturn(6L);
        Mockito.when(chosenTile.getY()).thenReturn(7L);
        Mockito.when(game.getId()).thenReturn(1L);

        List<Tile> secondCorner = new ArrayList<Tile>();
        List<Tile> thirdCorner = new ArrayList<Tile>();
        List<Tile> fifthCorner = new ArrayList<Tile>();
        List<Tile> sixthCorner = new ArrayList<Tile>();
        secondCorner.add(chosenTile);

        Mockito.when(tileRepository.getSecondCorner(game.getId())).thenReturn(secondCorner);
        Mockito.when(tileRepository.getThirdCorner(game.getId())).thenReturn(thirdCorner);
        Mockito.when(tileRepository.getSixthCorner(game.getId())).thenReturn(sixthCorner);
        Mockito.when(tileRepository.getFifthCorner(game.getId())).thenReturn(fifthCorner);

        Pair<Boolean, Boolean> result = standardFourPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(false, result.getFirst());
        Assertions.assertEquals(false, result.getSecond());
    }

    @Test
    public void checkMoveFourPlayersPlayerTwoInsideOppositeCornerTest()
    {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.ONGOING);
        Mockito.when(game.getDuringMove()).thenReturn(false);
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(tile.getColor()).thenReturn(TileColor.WHITE);
        Mockito.when(game.getPlayerTurn()).thenReturn(2);
        Mockito.when(tile.getX()).thenReturn(6L);
        Mockito.when(tile.getY()).thenReturn(6L);
        Mockito.when(chosenTile.getX()).thenReturn(6L);
        Mockito.when(chosenTile.getY()).thenReturn(7L);
        Mockito.when(game.getId()).thenReturn(1L);

        List<Tile> secondCorner = new ArrayList<Tile>();
        List<Tile> thirdCorner = new ArrayList<Tile>();
        List<Tile> fifthCorner = new ArrayList<Tile>();
        List<Tile> sixthCorner = new ArrayList<Tile>();
        secondCorner.add(chosenTile);
        secondCorner.add(tile);

        Mockito.when(tileRepository.getSecondCorner(game.getId())).thenReturn(secondCorner);
        Mockito.when(tileRepository.getThirdCorner(game.getId())).thenReturn(thirdCorner);
        Mockito.when(tileRepository.getSixthCorner(game.getId())).thenReturn(sixthCorner);
        Mockito.when(tileRepository.getFifthCorner(game.getId())).thenReturn(fifthCorner);

        Pair<Boolean, Boolean> result = standardFourPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(true, result.getFirst());
        Assertions.assertEquals(true, result.getSecond());
    }

    @Test
    public void checkMoveFourPlayersPlayerThreeOutOfOppositeCornerTest()
    {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.ONGOING);
        Mockito.when(game.getDuringMove()).thenReturn(false);
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(tile.getColor()).thenReturn(TileColor.WHITE);
        Mockito.when(game.getPlayerTurn()).thenReturn(3);
        Mockito.when(tile.getX()).thenReturn(6L);
        Mockito.when(tile.getY()).thenReturn(6L);
        Mockito.when(chosenTile.getX()).thenReturn(6L);
        Mockito.when(chosenTile.getY()).thenReturn(7L);
        Mockito.when(game.getId()).thenReturn(1L);

        List<Tile> secondCorner = new ArrayList<Tile>();
        List<Tile> thirdCorner = new ArrayList<Tile>();
        List<Tile> fifthCorner = new ArrayList<Tile>();
        List<Tile> sixthCorner = new ArrayList<Tile>();
        sixthCorner.add(chosenTile);

        Mockito.when(tileRepository.getSecondCorner(game.getId())).thenReturn(secondCorner);
        Mockito.when(tileRepository.getThirdCorner(game.getId())).thenReturn(thirdCorner);
        Mockito.when(tileRepository.getSixthCorner(game.getId())).thenReturn(sixthCorner);
        Mockito.when(tileRepository.getFifthCorner(game.getId())).thenReturn(fifthCorner);

        Pair<Boolean, Boolean> result = standardFourPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(false, result.getFirst());
        Assertions.assertEquals(false, result.getSecond());
    }

    @Test
    public void checkMoveFourPlayersPlayerThreeInsideOppositeCornerTest()
    {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.ONGOING);
        Mockito.when(game.getDuringMove()).thenReturn(false);
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(tile.getColor()).thenReturn(TileColor.WHITE);
        Mockito.when(game.getPlayerTurn()).thenReturn(3);
        Mockito.when(tile.getX()).thenReturn(6L);
        Mockito.when(tile.getY()).thenReturn(6L);
        Mockito.when(chosenTile.getX()).thenReturn(6L);
        Mockito.when(chosenTile.getY()).thenReturn(7L);
        Mockito.when(game.getId()).thenReturn(1L);

        List<Tile> secondCorner = new ArrayList<Tile>();
        List<Tile> thirdCorner = new ArrayList<Tile>();
        List<Tile> fifthCorner = new ArrayList<Tile>();
        List<Tile> sixthCorner = new ArrayList<Tile>();
        sixthCorner.add(chosenTile);
        sixthCorner.add(tile);

        Mockito.when(tileRepository.getSecondCorner(game.getId())).thenReturn(secondCorner);
        Mockito.when(tileRepository.getThirdCorner(game.getId())).thenReturn(thirdCorner);
        Mockito.when(tileRepository.getSixthCorner(game.getId())).thenReturn(sixthCorner);
        Mockito.when(tileRepository.getFifthCorner(game.getId())).thenReturn(fifthCorner);

        Pair<Boolean, Boolean> result = standardFourPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(true, result.getFirst());
        Assertions.assertEquals(true, result.getSecond());
    }

    @Test
    public void checkMoveFourPlayersPlayerFourOutOfOppositeCornerTest()
    {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.ONGOING);
        Mockito.when(game.getDuringMove()).thenReturn(false);
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(tile.getColor()).thenReturn(TileColor.WHITE);
        Mockito.when(game.getPlayerTurn()).thenReturn(4);
        Mockito.when(tile.getX()).thenReturn(6L);
        Mockito.when(tile.getY()).thenReturn(6L);
        Mockito.when(chosenTile.getX()).thenReturn(6L);
        Mockito.when(chosenTile.getY()).thenReturn(7L);
        Mockito.when(game.getId()).thenReturn(1L);

        List<Tile> secondCorner = new ArrayList<Tile>();
        List<Tile> thirdCorner = new ArrayList<Tile>();
        List<Tile> fifthCorner = new ArrayList<Tile>();
        List<Tile> sixthCorner = new ArrayList<Tile>();
        fifthCorner.add(chosenTile);

        Mockito.when(tileRepository.getSecondCorner(game.getId())).thenReturn(secondCorner);
        Mockito.when(tileRepository.getThirdCorner(game.getId())).thenReturn(thirdCorner);
        Mockito.when(tileRepository.getSixthCorner(game.getId())).thenReturn(sixthCorner);
        Mockito.when(tileRepository.getFifthCorner(game.getId())).thenReturn(fifthCorner);

        Pair<Boolean, Boolean> result = standardFourPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(false, result.getFirst());
        Assertions.assertEquals(false, result.getSecond());
    }

    @Test
    public void checkMoveFourPlayersPlayerFourInsideOppositeCornerTest()
    {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(game.getGameStatus()).thenReturn(GameStatus.ONGOING);
        Mockito.when(game.getDuringMove()).thenReturn(false);
        Mockito.when(game.getChosenTile()).thenReturn(chosenTile);
        Mockito.when(tile.getColor()).thenReturn(TileColor.WHITE);
        Mockito.when(game.getPlayerTurn()).thenReturn(4);
        Mockito.when(tile.getX()).thenReturn(6L);
        Mockito.when(tile.getY()).thenReturn(6L);
        Mockito.when(chosenTile.getX()).thenReturn(6L);
        Mockito.when(chosenTile.getY()).thenReturn(7L);
        Mockito.when(game.getId()).thenReturn(1L);

        List<Tile> secondCorner = new ArrayList<Tile>();
        List<Tile> thirdCorner = new ArrayList<Tile>();
        List<Tile> fifthCorner = new ArrayList<Tile>();
        List<Tile> sixthCorner = new ArrayList<Tile>();
        fifthCorner.add(chosenTile);
        fifthCorner.add(tile);

        Mockito.when(tileRepository.getSecondCorner(game.getId())).thenReturn(secondCorner);
        Mockito.when(tileRepository.getThirdCorner(game.getId())).thenReturn(thirdCorner);
        Mockito.when(tileRepository.getSixthCorner(game.getId())).thenReturn(sixthCorner);
        Mockito.when(tileRepository.getFifthCorner(game.getId())).thenReturn(fifthCorner);

        Pair<Boolean, Boolean> result = standardFourPlayersMoveChecker.checkMove(user, game, tile);

        Assertions.assertEquals(true, result.getFirst());
        Assertions.assertEquals(true, result.getSecond());
    }

    @Test
    public void checkMoveFourPlayersPlayerOneIsNotWinnerTest()
    {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(tile.getColor()).thenReturn(TileColor.RED);
        Mockito.when(game.getPlayerTurn()).thenReturn(1);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> secondCorner = new ArrayList<Tile>();
        List<Tile> thirdCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();
        List<Tile> fifthCorner = new ArrayList<Tile>();
        List<Tile> sixthCorner = new ArrayList<Tile>();

        for(int i = 0; i < 9; i++)
        {
            thirdCorner.add(tile);
        }


        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getSecondCorner(game.getId())).thenReturn(secondCorner);
        Mockito.when(tileRepository.getThirdCorner(game.getId())).thenReturn(thirdCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);
        Mockito.when(tileRepository.getFifthCorner(game.getId())).thenReturn(fifthCorner);
        Mockito.when(tileRepository.getSixthCorner(game.getId())).thenReturn(sixthCorner);


        boolean result = standardFourPlayersMoveChecker.isCurrentPlayerWinner(game);

        Assertions.assertEquals(false, result);
    }

    @Test
    public void checkMoveFourPlayersPlayerOneIsWinnerTest()
    {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(tile.getColor()).thenReturn(TileColor.RED);
        Mockito.when(game.getPlayerTurn()).thenReturn(1);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> secondCorner = new ArrayList<Tile>();
        List<Tile> thirdCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();
        List<Tile> fifthCorner = new ArrayList<Tile>();
        List<Tile> sixthCorner = new ArrayList<Tile>();

        for(int i = 0; i < 10; i++)
        {
            thirdCorner.add(tile);
        }


        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getSecondCorner(game.getId())).thenReturn(secondCorner);
        Mockito.when(tileRepository.getThirdCorner(game.getId())).thenReturn(thirdCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);
        Mockito.when(tileRepository.getFifthCorner(game.getId())).thenReturn(fifthCorner);
        Mockito.when(tileRepository.getSixthCorner(game.getId())).thenReturn(sixthCorner);


        boolean result = standardFourPlayersMoveChecker.isCurrentPlayerWinner(game);

        Assertions.assertEquals(true, result);
    }

    @Test
    public void checkMoveFourPlayersPlayerTwoIsNotWinnerTest()
    {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(tile.getColor()).thenReturn(TileColor.BLUE);
        Mockito.when(game.getPlayerTurn()).thenReturn(2);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> secondCorner = new ArrayList<Tile>();
        List<Tile> thirdCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();
        List<Tile> fifthCorner = new ArrayList<Tile>();
        List<Tile> sixthCorner = new ArrayList<Tile>();

        for(int i = 0; i < 9; i++)
        {
            secondCorner.add(tile);
        }


        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getSecondCorner(game.getId())).thenReturn(secondCorner);
        Mockito.when(tileRepository.getThirdCorner(game.getId())).thenReturn(thirdCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);
        Mockito.when(tileRepository.getFifthCorner(game.getId())).thenReturn(fifthCorner);
        Mockito.when(tileRepository.getSixthCorner(game.getId())).thenReturn(sixthCorner);


        boolean result = standardFourPlayersMoveChecker.isCurrentPlayerWinner(game);

        Assertions.assertEquals(false, result);
    }

    @Test
    public void checkMoveFourPlayersPlayerTwoIsWinnerTest()
    {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(tile.getColor()).thenReturn(TileColor.BLUE);
        Mockito.when(game.getPlayerTurn()).thenReturn(2);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> secondCorner = new ArrayList<Tile>();
        List<Tile> thirdCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();
        List<Tile> fifthCorner = new ArrayList<Tile>();
        List<Tile> sixthCorner = new ArrayList<Tile>();

        for(int i = 0; i < 10; i++)
        {
            secondCorner.add(tile);
        }


        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getSecondCorner(game.getId())).thenReturn(secondCorner);
        Mockito.when(tileRepository.getThirdCorner(game.getId())).thenReturn(thirdCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);
        Mockito.when(tileRepository.getFifthCorner(game.getId())).thenReturn(fifthCorner);
        Mockito.when(tileRepository.getSixthCorner(game.getId())).thenReturn(sixthCorner);


        boolean result = standardFourPlayersMoveChecker.isCurrentPlayerWinner(game);

        Assertions.assertEquals(true, result);
    }

    @Test
    public void checkMoveFourPlayersPlayerThreeIsNotWinnerTest()
    {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(tile.getColor()).thenReturn(TileColor.GREEN);
        Mockito.when(game.getPlayerTurn()).thenReturn(3);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> secondCorner = new ArrayList<Tile>();
        List<Tile> thirdCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();
        List<Tile> fifthCorner = new ArrayList<Tile>();
        List<Tile> sixthCorner = new ArrayList<Tile>();

        for(int i = 0; i < 9; i++)
        {
            sixthCorner.add(tile);
        }


        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getSecondCorner(game.getId())).thenReturn(secondCorner);
        Mockito.when(tileRepository.getThirdCorner(game.getId())).thenReturn(thirdCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);
        Mockito.when(tileRepository.getFifthCorner(game.getId())).thenReturn(fifthCorner);
        Mockito.when(tileRepository.getSixthCorner(game.getId())).thenReturn(sixthCorner);


        boolean result = standardFourPlayersMoveChecker.isCurrentPlayerWinner(game);

        Assertions.assertEquals(false, result);
    }

    @Test
    public void checkMoveFourPlayersPlayerThreeIsWinnerTest()
    {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(tile.getColor()).thenReturn(TileColor.GREEN);
        Mockito.when(game.getPlayerTurn()).thenReturn(3);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> secondCorner = new ArrayList<Tile>();
        List<Tile> thirdCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();
        List<Tile> fifthCorner = new ArrayList<Tile>();
        List<Tile> sixthCorner = new ArrayList<Tile>();

        for(int i = 0; i < 10; i++)
        {
            sixthCorner.add(tile);
        }


        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getSecondCorner(game.getId())).thenReturn(secondCorner);
        Mockito.when(tileRepository.getThirdCorner(game.getId())).thenReturn(thirdCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);
        Mockito.when(tileRepository.getFifthCorner(game.getId())).thenReturn(fifthCorner);
        Mockito.when(tileRepository.getSixthCorner(game.getId())).thenReturn(sixthCorner);


        boolean result = standardFourPlayersMoveChecker.isCurrentPlayerWinner(game);

        Assertions.assertEquals(true, result);
    }

    @Test
    public void checkMoveFourPlayersPlayerFourIsNotWinnerTest()
    {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(tile.getColor()).thenReturn(TileColor.PURPLE);
        Mockito.when(game.getPlayerTurn()).thenReturn(4);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> secondCorner = new ArrayList<Tile>();
        List<Tile> thirdCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();
        List<Tile> fifthCorner = new ArrayList<Tile>();
        List<Tile> sixthCorner = new ArrayList<Tile>();

        for(int i = 0; i < 9; i++)
        {
            fifthCorner.add(tile);
        }


        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getSecondCorner(game.getId())).thenReturn(secondCorner);
        Mockito.when(tileRepository.getThirdCorner(game.getId())).thenReturn(thirdCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);
        Mockito.when(tileRepository.getFifthCorner(game.getId())).thenReturn(fifthCorner);
        Mockito.when(tileRepository.getSixthCorner(game.getId())).thenReturn(sixthCorner);


        boolean result = standardFourPlayersMoveChecker.isCurrentPlayerWinner(game);

        Assertions.assertEquals(false, result);
    }

    @Test
    public void checkMoveFourPlayersPlayerFourIsWinnerTest()
    {
        Mockito.when(game.getPlayerWithTurn()).thenReturn(user);
        Mockito.when(tile.getColor()).thenReturn(TileColor.PURPLE);
        Mockito.when(game.getPlayerTurn()).thenReturn(4);

        List<Tile> firstCorner = new ArrayList<Tile>();
        List<Tile> secondCorner = new ArrayList<Tile>();
        List<Tile> thirdCorner = new ArrayList<Tile>();
        List<Tile> fourthCorner = new ArrayList<Tile>();
        List<Tile> fifthCorner = new ArrayList<Tile>();
        List<Tile> sixthCorner = new ArrayList<Tile>();

        for(int i = 0; i < 10; i++)
        {
            fifthCorner.add(tile);
        }


        Mockito.when(tileRepository.getFirstCorner(game.getId())).thenReturn(firstCorner);
        Mockito.when(tileRepository.getSecondCorner(game.getId())).thenReturn(secondCorner);
        Mockito.when(tileRepository.getThirdCorner(game.getId())).thenReturn(thirdCorner);
        Mockito.when(tileRepository.getFourthCorner(game.getId())).thenReturn(fourthCorner);
        Mockito.when(tileRepository.getFifthCorner(game.getId())).thenReturn(fifthCorner);
        Mockito.when(tileRepository.getSixthCorner(game.getId())).thenReturn(sixthCorner);


        boolean result = standardFourPlayersMoveChecker.isCurrentPlayerWinner(game);

        Assertions.assertEquals(true, result);
    }
}