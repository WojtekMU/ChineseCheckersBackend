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

@Component
public abstract class AbstractMoveChecker 
{
    protected final List<TileColor> colorOrder = List.of(TileColor.WHITE, TileColor.RED, TileColor.BLUE, TileColor.GREEN, TileColor.PURPLE, TileColor.BROWN, TileColor.ORANGE);
	
	@Autowired
	TileRepository tileRepository;
	
    @Autowired
    GameService gameService;
	
	public Pair<Boolean, Boolean> checkMove(User player, Game game, Tile tile)
	{
		Boolean correctMove = false;
		Boolean endTurn = false;
		
		if(correctPlayer(player, game) && isOngoingGame(game))
		{
			if(isDuringMove(game))
			{
				if(isTileWhite(tile) && !isMoveFromCorner(game, tile) && isDistanceTwoMove(game.getChosenTile(), tile) && isCorrectTileBetween(game.getChosenTile(), tile, game))
				{
					correctMove = true;
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
				else if(isTileWhite(tile) && !isMoveFromCorner(game, tile) && isDistanceOneMove(game.getChosenTile(), tile))
				{
					correctMove = true;
					endTurn = true;
				}
				else if(isTileWhite(tile) && !isMoveFromCorner(game, tile) && isDistanceTwoMove(game.getChosenTile(), tile) && isCorrectTileBetween(game.getChosenTile(), tile, game))
				{
					correctMove = true;
					gameService.updateDuringMove(game, Boolean.TRUE);
				}
				else
				{
					gameService.updateChosenTile(game, null);
				}
			}
		}
		
		return Pair.of(correctMove, endTurn);
	}
	
	protected boolean correctPlayer(User player, Game game)
	{
		return game.getPlayerWithTurn().equals(player);
	}
	
	protected boolean isOngoingGame(Game game)
	{
		return game.getGameStatus().equals(GameStatus.ONGOING);
	}
	
	protected boolean isDuringMove(Game game)
	{
		return game.getDuringMove();
	}
	
	protected boolean isFirstTileChosen(Game game)
	{
		return game.getChosenTile() != null;
	}
	
	protected boolean isTileWhite(Tile tile)
	{
		TileColor tileColor = tile.getColor();
		
		return tileColor.equals(TileColor.WHITE);
	}
	
	protected boolean isCurrentMovingPlayerColor(Tile tile, Game game)
	{
		TileColor tileColor = tile.getColor();
		
		return tileColor.equals(colorOrder.get(game.getPlayerTurn()));
	}
	
	protected boolean isDistanceOneMove(Tile firstTile, Tile secondTile)
	{
		return Math.max(Math.abs(firstTile.getX() - secondTile.getX()), Math.abs(firstTile.getY() - secondTile.getY())) == 1
				&& Math.abs(firstTile.getX() - secondTile.getX() + firstTile.getY() - secondTile.getY()) != 2;
	}
	
	protected boolean isDistanceTwoMove(Tile firstTile, Tile secondTile)
	{
		return Math.max(Math.abs(firstTile.getX() - secondTile.getX()), Math.abs(firstTile.getY() - secondTile.getY())) == 2
				&& (Math.abs(firstTile.getX() - secondTile.getX()) + Math.abs(firstTile.getY() - secondTile.getY()) == 2
				|| Math.abs(firstTile.getX() - secondTile.getX()) + Math.abs(firstTile.getY() - secondTile.getY()) == 4);
	}
	
	protected boolean isCorrectTileBetween(Tile firstTile, Tile secondTile, Game game)
	{
		Optional<Tile> middleTile = tileRepository.findByXAndYAndGameId((firstTile.getX() + secondTile.getX()) / 2, (firstTile.getY() + secondTile.getY()) / 2, game.getId());
		
		return !middleTile.get().getColor().equals(colorOrder.get(0));
	}
	
	public List<TileColor> getColorOrder() 
	{
		return colorOrder;
	}
	
	public abstract boolean isCurrenPlayerWinner(Game game);
	protected abstract boolean isMoveFromCorner(Game game, Tile tile);
}
