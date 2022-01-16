package TPLab4.ChineseCheckersBackend.MoveChecker;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Game.GameService;
import TPLab4.ChineseCheckersBackend.Game.GameStatus;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.Tile.TileColor;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import TPLab4.ChineseCheckersBackend.User.User;
import org.springframework.stereotype.Service;

/**
 * Abstract move checker class
 */
@Service
public abstract class AbstractMoveChecker 
{
	/**
	 * Color list
	 */
    protected final List<TileColor> colorOrder = List.of(TileColor.WHITE, TileColor.RED, TileColor.BLUE, TileColor.GREEN, TileColor.PURPLE, TileColor.BROWN, TileColor.ORANGE);

	/**
	 * Tile repository
	 */
	@Autowired
	protected TileRepository tileRepository;

	/**
	 * Game service
	 */
    @Autowired
    protected GameService gameService;

	/**
	 * Method checking moves
	 * @param user User
	 * @param game Game
	 * @param tile Tile
	 * @return Two Boolean values whether move is correct and whether the turn has ended.
	 */
	public Pair<Boolean, Boolean> checkMove(User user, Game game, Tile tile)
	{
		Boolean correctMove = false;
		Boolean endTurn = false;
		
		if(correctPlayer(user, game) && isOngoingGame(game))
		{
			if(isDuringMove(game))
			{
				if(isTileWhite(tile) && !isMoveFromCorner(game, tile))
				{
					if (isDistanceTwoMove(game.getChosenTile(), tile) && isCorrectTileBetween(game.getChosenTile(), tile, game))
					{
						correctMove = true;
					}
				}
			}
			else
			{
				if(!isFirstTileChosen(game))
				{
					if(isCurrentMovingPlayerColor(tile, game))
					{
						gameService.updateChosenTile(game, tile);
					}
				}
				else if(isTileWhite(tile) && !isMoveFromCorner(game, tile))
				{
					if(isDistanceOneMove(game.getChosenTile(), tile))
					{
						correctMove = true;
						endTurn = true;
					}
					else if(isDistanceTwoMove(game.getChosenTile(), tile) && isCorrectTileBetween(game.getChosenTile(), tile, game))
					{
						correctMove = true;
						gameService.updateDuringMove(game, Boolean.TRUE);
					}
					else
					{
						gameService.updateChosenTile(game, null);
					}
				}
				else
				{
					gameService.updateChosenTile(game, null);
				}
			}
		}
		
		return Pair.of(correctMove, endTurn);
	}

	/**
	 * Method for checking end turn request validity.
	 * @param user User
	 * @param game Game
	 * @return True on request accepted else false
	 */
	public boolean checkEndTurn(User user, Game game)
	{
		return isOngoingGame(game) && correctPlayer(user, game);
	}

	/**
	 * Method checking whether the game is finished
	 * @param game Game
	 * @return True when the game is finished, else false.
	 */
	public boolean isGameFinished(Game game)
	{
		return game.getPlayers().size() == (game.getHistory().getLeaderboard().size() + 1);
	}

	/**
	 * Method checking whether the correct player requested a move.
	 * @param user User
	 * @param game Game
	 * @return True on correct player, else false
	 */
	protected boolean correctPlayer(User user, Game game)
	{
		return game.getPlayerWithTurn().equals(user);
	}

	/**
	 * Method checking whether the game is ongoing requested a move.
	 * @param game Game
	 * @return True on ongoing game, else false
	 */
	protected boolean isOngoingGame(Game game)
	{
		return game.getGameStatus().equals(GameStatus.ONGOING);
	}

	/**
	 * Method checking whether someone is during move.
	 * @param game Game
	 * @return True on during move, else false
	 */
	protected boolean isDuringMove(Game game)
	{
		return game.getDuringMove();
	}

	/**
	 * Method checking whether first tile was chosen.
	 * @param game Game
	 * @return True when first tile was chosen, else false.
	 */
	protected boolean isFirstTileChosen(Game game)
	{
		return game.getChosenTile() != null;
	}

	/**
	 * Method checking whether tile is white.
	 * @param tile Tile
	 * @return True when tile is white, else false.
	 */
	protected boolean isTileWhite(Tile tile)
	{
		TileColor tileColor = tile.getColor();
		
		return tileColor.equals(TileColor.WHITE);
	}

	/**
	 * Method checking whether the current player is moving with the correctly colored tile.
	 * @param tile Tile
	 * @param game Game
	 * @return True when the current player is moving with the correctly colored tile, else false.
	 */
	protected boolean isCurrentMovingPlayerColor(Tile tile, Game game)
	{
		TileColor tileColor = tile.getColor();
		
		return tileColor.equals(colorOrder.get(game.getPlayerTurn()));
	}

	/**
	 * Method checking whether move is a distance one move.
	 * @param firstTile First tile
	 * @param secondTile Second tile
	 * @return True when the move is a distance one move, else false.
	 */
	protected boolean isDistanceOneMove(Tile firstTile, Tile secondTile)
	{
		return Math.max(Math.abs(firstTile.getX() - secondTile.getX()), Math.abs(firstTile.getY() - secondTile.getY())) == 1
				&& Math.abs(firstTile.getX() - secondTile.getX() + firstTile.getY() - secondTile.getY()) != 2;
	}

	/**
	 * Method checking whether move is a distance two move.
	 * @param firstTile First tile
	 * @param secondTile Second tile
	 * @return True when the move is a distance two move, else false.
	 */
	protected boolean isDistanceTwoMove(Tile firstTile, Tile secondTile)
	{
		return Math.max(Math.abs(firstTile.getX() - secondTile.getX()), Math.abs(firstTile.getY() - secondTile.getY())) == 2
				&& (Math.abs(firstTile.getX() - secondTile.getX()) + Math.abs(firstTile.getY() - secondTile.getY()) == 2
				|| Math.abs(firstTile.getX() - secondTile.getX()) + Math.abs(firstTile.getY() - secondTile.getY()) == 4);
	}

	/**
	 * Method checking whether there is a correctly colored tile between.
	 * @param firstTile First tile
	 * @param secondTile Second tile
	 * @return True when there is a correctly colored tile between, else false.
	 */
	protected boolean isCorrectTileBetween(Tile firstTile, Tile secondTile, Game game)
	{
		Tile middleTile = tileRepository.getByXAndYAndGameId((firstTile.getX() + secondTile.getX()) / 2, (firstTile.getY() + secondTile.getY()) / 2, game.getId());
		
		return !middleTile.getColor().equals(colorOrder.get(0));
	}

	/**
	 * Color order getter.
	 * @return Color order
	 */
	public List<TileColor> getColorOrder() 
	{
		return colorOrder;
	}

	/**
	 * Method checking whether currently moving player is a winner.
	 * @param game Game
	 * @return True when currently moving player is a winner, else flase.
	 */
	public abstract boolean isCurrentPlayerWinner(Game game);

	/**
	 * Method checking whether player is trying to leave the opposite corner.
	 * @param game Game
	 * @param tile Tile
	 * @return True when player is trying to leave the opposite corner, else false.
	 */
	protected abstract boolean isMoveFromCorner(Game game, Tile tile);
}
