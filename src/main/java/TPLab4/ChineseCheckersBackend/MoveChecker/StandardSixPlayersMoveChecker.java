package TPLab4.ChineseCheckersBackend.MoveChecker;

import java.util.List;

import org.springframework.stereotype.Component;

import TPLab4.ChineseCheckersBackend.Game.Game;
import TPLab4.ChineseCheckersBackend.Tile.Tile;

@Component
public class StandardSixPlayersMoveChecker extends AbstractMoveChecker
{
	@Override
	public boolean isCurrenPlayerWinner(Game game) 
	{
		List<Tile> firstCorner = tileRepository.getFirstCorner(game.getId());
		List<Tile> secondCorner = tileRepository.getSecondCorner(game.getId());
		List<Tile> thirdCorner = tileRepository.getThirdCorner(game.getId());
		List<Tile> fourthCorner = tileRepository.getFourthCorner(game.getId());
		List<Tile> fifthCorner = tileRepository.getFifthCorner(game.getId());
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
		
		return counter == 10;
	}

	@Override
	public boolean isMoveFromCorner(Game game, Tile tile) 
	{
		List<Tile> firstCorner = tileRepository.getFirstCorner(game.getId());
		List<Tile> secondCorner = tileRepository.getSecondCorner(game.getId());
		List<Tile> thirdCorner = tileRepository.getThirdCorner(game.getId());
		List<Tile> fourthCorner = tileRepository.getFourthCorner(game.getId());
		List<Tile> fifthCorner = tileRepository.getFifthCorner(game.getId());
		List<Tile> sixthCorner = tileRepository.getSixthCorner(game.getId());
		
		boolean ret = false;
		
		if(game.getPlayerTurn() == 1)
		{
			ret = fourthCorner.contains(game.getChosenTile()) && !fourthCorner.contains(tile);
		}
		else if(game.getPlayerTurn() == 2)
		{
			ret = fifthCorner.contains(game.getChosenTile()) && !fifthCorner.contains(tile);
		}
		else if(game.getPlayerTurn() == 3)
		{
			ret = sixthCorner.contains(game.getChosenTile()) && !sixthCorner.contains(tile);
		}
		else if(game.getPlayerTurn() == 4)
		{
			ret = firstCorner.contains(game.getChosenTile()) && !firstCorner.contains(tile);
		}
		else if(game.getPlayerTurn() == 5)
		{
			ret = secondCorner.contains(game.getChosenTile()) && !secondCorner.contains(tile);
		}
		else if(game.getPlayerTurn() == 6)
		{
			ret = thirdCorner.contains(game.getChosenTile()) && !thirdCorner.contains(tile);
		}
		
		return ret;
	}
}
