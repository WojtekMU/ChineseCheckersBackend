package TPLab4.ChineseCheckersBackend.MoveChecker;

import java.util.List;

import org.springframework.stereotype.Component;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import org.springframework.stereotype.Service;

/**
 * Move checker class for a standard three players game
 */
@Service
public class StandardThreePlayersMoveChecker extends AbstractMoveChecker 
{
	@Override
	public boolean isCurrentPlayerWinner(Game game)
	{
		List<Tile> secondCorner = tileRepository.getSecondCorner(game.getId());
		List<Tile> fourthCorner = tileRepository.getFourthCorner(game.getId());
		List<Tile> sixthCorner = tileRepository.getSixthCorner(game.getId());
				
		int counter = 0;
		
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
		
		return counter == 10;
	}

	@Override
	protected boolean isMoveFromCorner(Game game, Tile tile)
	{
		List<Tile> secondCorner = tileRepository.getSecondCorner(game.getId());
		List<Tile> fourthCorner = tileRepository.getFourthCorner(game.getId());
		List<Tile> sixthCorner = tileRepository.getSixthCorner(game.getId());
		
		boolean ret = false;
		
		if(game.getPlayerTurn() == 1)
		{
			ret = fourthCorner.contains(game.getChosenTile()) && !fourthCorner.contains(tile);
		}
		else if(game.getPlayerTurn() == 2)
		{
			ret = secondCorner.contains(game.getChosenTile()) && !secondCorner.contains(tile);
		}
		else if(game.getPlayerTurn() == 3)
		{
			ret = sixthCorner.contains(game.getChosenTile()) && !sixthCorner.contains(tile);
		}
		
		return ret;
	}
}
