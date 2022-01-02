package TPLab4.ChineseCheckersBackend.Game;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.Tile.TileColor;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;
import TPLab4.ChineseCheckersBackend.User.User;

@Component
public class MoveChecker 
{
    private final List<TileColor> colorOrder = List.of(TileColor.WHITE, TileColor.RED, TileColor.BLUE, TileColor.GREEN, TileColor.PURPLE, TileColor.BROWN, TileColor.ORANGE);
	
	@Autowired
	TileRepository tileRepository;
	
	public boolean correctPlayer(User player, Game game)
	{
		return game.getPlayerWithTurn().equals(player);
	}
	
	public boolean isTileWhite(Tile tile)
	{
		TileColor tileColor = tile.getColor();
		
		return tileColor.equals(TileColor.WHITE);
	}
	
	public boolean isCurrentMovingPlayerColor(Tile tile, Game game)
	{
		TileColor tileColor = tile.getColor();
		
		return tileColor.equals(colorOrder.get(game.getPlayerTurn()));
	}
	
	public boolean isDistanceOneMove(Tile firstTile, Tile secondTile)
	{
		return Math.max(Math.abs(firstTile.getX() - secondTile.getX()), Math.abs(firstTile.getY() - secondTile.getY())) == 1
					&& Math.abs(firstTile.getX() - secondTile.getX() + firstTile.getY() - secondTile.getY()) != 2;
	}
	
	public boolean isDistanceTwoMove(Tile firstTile, Tile secondTile)
	{
		return Math.max(Math.abs(firstTile.getX() - secondTile.getX()), Math.abs(firstTile.getY() - secondTile.getY())) == 2
				&& (Math.abs(firstTile.getX() - secondTile.getX()) + Math.abs(firstTile.getY() - secondTile.getY()) == 2
				|| Math.abs(firstTile.getX() - secondTile.getX()) + Math.abs(firstTile.getY() - secondTile.getY()) == 4);
	}
	
	public boolean CorrectTileBetween(Tile firstTile, Tile secondTile, Game game)
	{
		Optional<Tile> middleTile = tileRepository.findByXAndYAndGameId((firstTile.getX() + secondTile.getX()) / 2, (firstTile.getY() + secondTile.getY()) / 2, game.getId());
		
		return !middleTile.get().getColor().equals(colorOrder.get(0));
	}
	
	public boolean isWinner(Game game)
	{
		List<Tile> firstCorner = tileRepository.getFirstCorner(game.getId());
		List<Tile> secondCorner = tileRepository.getSecondCorner(game.getId());
		List<Tile> thirdCorner = tileRepository.getThirdCorner(game.getId());
		List<Tile> fourthCorner = tileRepository.getFourthCorner(game.getId());
		List<Tile> fifthCorner = tileRepository.getFifthCorner(game.getId());
		List<Tile> sixthCorner = tileRepository.getSixthCorner(game.getId());
				
		int counter = 0;
		
		if(game.getGameType().equals(GameType.STANDARD_TWO_PLAYERS))
		{
			if(game.getPlayerTurn() == 1)
			{
				for(Tile tile : fourthCorner)
				{
					if(tile.getColor().equals(colorOrder.get(game.getPlayerTurn())))
					{
						counter++;
					}
				}
			}
			else
			{
				for(Tile tile : firstCorner)
				{
					if(tile.getColor().equals(colorOrder.get(game.getPlayerTurn())))
					{
						counter++;
					}					
				}				
			}
		}
		else if(game.getGameType().equals(GameType.STANDARD_THREE_PLAYERS))
		{
			if(game.getPlayerTurn() == 1)
			{
				for(Tile tile : fourthCorner)
				{
					if(tile.getColor().equals(colorOrder.get(game.getPlayerTurn())))
					{
						counter++;
					}
				}
			}
			else if(game.getPlayerTurn() == 2)
			{
				for(Tile tile : secondCorner)
				{
					if(tile.getColor().equals(colorOrder.get(game.getPlayerTurn())))
					{
						counter++;
					}
				}
			}
			else if(game.getPlayerTurn() == 3)
			{
				for(Tile tile : sixthCorner)
				{
					if(tile.getColor().equals(colorOrder.get(game.getPlayerTurn())))
					{
						counter++;
					}
				}
			}
		}
		else if(game.getGameType().equals(GameType.STANDARD_FOUR_PLAYERS))
		{
			if(game.getPlayerTurn() == 1)
			{
				for(Tile tile : thirdCorner)
				{
					if(tile.getColor().equals(colorOrder.get(game.getPlayerTurn())))
					{
						counter++;
					}
				}
			}
			else if(game.getPlayerTurn() == 2)
			{
				for(Tile tile : secondCorner)
				{
					if(tile.getColor().equals(colorOrder.get(game.getPlayerTurn())))
					{
						counter++;
					}
				}
			}
			else if(game.getPlayerTurn() == 3)
			{
				for(Tile tile : sixthCorner)
				{
					if(tile.getColor().equals(colorOrder.get(game.getPlayerTurn())))
					{
						counter++;
					}
				}
			}
			else if(game.getPlayerTurn() == 4)
			{
				for(Tile tile : fifthCorner)
				{
					if(tile.getColor().equals(colorOrder.get(game.getPlayerTurn())))
					{
						counter++;
					}
				}
			}
		}
		else if(game.getGameType().equals(GameType.STANDARD_SIX_PLAYERS))
		{
			if(game.getPlayerTurn() == 1)
			{
				for(Tile tile : fourthCorner)
				{
					if(tile.getColor().equals(colorOrder.get(game.getPlayerTurn())))
					{
						counter++;
					}
				}
			}
			else if(game.getPlayerTurn() == 2)
			{
				for(Tile tile : fifthCorner)
				{
					if(tile.getColor().equals(colorOrder.get(game.getPlayerTurn())))
					{
						counter++;
					}
				}
			}
			else if(game.getPlayerTurn() == 3)
			{
				for(Tile tile : sixthCorner)
				{
					if(tile.getColor().equals(colorOrder.get(game.getPlayerTurn())))
					{
						counter++;
					}
				}
			}
			else if(game.getPlayerTurn() == 4)
			{
				for(Tile tile : firstCorner)
				{
					if(tile.getColor().equals(colorOrder.get(game.getPlayerTurn())))
					{
						counter++;
					}
				}
			}
			else if(game.getPlayerTurn() == 5)
			{
				for(Tile tile : secondCorner)
				{
					if(tile.getColor().equals(colorOrder.get(game.getPlayerTurn())))
					{
						counter++;
					}
				}
			}
			else if(game.getPlayerTurn() == 6)
			{
				for(Tile tile : thirdCorner)
				{
					if(tile.getColor().equals(colorOrder.get(game.getPlayerTurn())))
					{
						counter++;
					}
				}
			}
		}
		
		return counter == 1;
	}

	public List<TileColor> getColorOrder() 
	{
		return colorOrder;
	}
}
