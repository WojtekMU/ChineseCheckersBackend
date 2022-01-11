package TPLab4.ChineseCheckersBackend.MoveChecker;

import java.util.List;

import org.springframework.stereotype.Component;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Tile.Tile;

@Component
public class StandardFourPlayersMoveChecker extends AbstractMoveChecker 
{
	@Override
	public boolean isCurrentPlayerWinner(Game game)
	{
		List<Tile> secondCorner = tileRepository.getSecondCorner(game.getId());
		List<Tile> thirdCorner = tileRepository.getThirdCorner(game.getId());
		List<Tile> fifthCorner = tileRepository.getFifthCorner(game.getId());
		List<Tile> sixthCorner = tileRepository.getSixthCorner(game.getId());
		
		int counter = 0;
		
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
		
		return counter == 10;
	}

	@Override
	public boolean isMoveFromCorner(Game game, Tile tile) 
	{
		List<Tile> secondCorner = tileRepository.getSecondCorner(game.getId());
		List<Tile> thirdCorner = tileRepository.getThirdCorner(game.getId());
		List<Tile> fifthCorner = tileRepository.getFifthCorner(game.getId());
		List<Tile> sixthCorner = tileRepository.getSixthCorner(game.getId());
		
		boolean ret = false;
		
		if(game.getPlayerTurn() == 1)
		{
			ret = thirdCorner.contains(game.getChosenTile()) && !thirdCorner.contains(tile);
		}
		else if(game.getPlayerTurn() == 2)
		{
			ret = secondCorner.contains(game.getChosenTile()) && !secondCorner.contains(tile);
		}
		else if(game.getPlayerTurn() == 3)
		{
			ret = sixthCorner.contains(game.getChosenTile()) && !sixthCorner.contains(tile);
		}
		else if(game.getPlayerTurn() == 4)
		{
			ret = fifthCorner.contains(game.getChosenTile()) && !fifthCorner.contains(tile);
		}
		
		return ret;
	}
}
