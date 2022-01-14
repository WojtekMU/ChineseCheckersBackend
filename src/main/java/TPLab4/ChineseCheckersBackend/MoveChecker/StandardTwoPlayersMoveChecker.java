package TPLab4.ChineseCheckersBackend.MoveChecker;

import java.util.List;

import org.springframework.stereotype.Component;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Tile.Tile;
import org.springframework.stereotype.Service;

@Service
public class StandardTwoPlayersMoveChecker extends AbstractMoveChecker
{
	@Override
	public boolean isCurrentPlayerWinner(Game game)
	{
		List<Tile> firstCorner = tileRepository.getFirstCorner(game.getId());
		List<Tile> fourthCorner = tileRepository.getFourthCorner(game.getId());
				
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
		
		return counter == 10;
	}

	@Override
	protected boolean isMoveFromCorner(Game game, Tile tile)
	{
		List<Tile> firstCorner = tileRepository.getFirstCorner(game.getId());
		List<Tile> fourthCorner = tileRepository.getFourthCorner(game.getId());
		
		boolean ret = false;
		
		if(game.getPlayerTurn() == 1)
		{
			ret = fourthCorner.contains(game.getChosenTile()) && !fourthCorner.contains(tile);
		}
		else if(game.getPlayerTurn() == 2)
		{
			ret = firstCorner.contains(game.getChosenTile()) && !firstCorner.contains(tile);			
		}
		
		return ret;
	}
}
