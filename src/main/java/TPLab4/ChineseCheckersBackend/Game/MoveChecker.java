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
	
	public boolean checkMove(Tile firstTile, Tile secondTile, User player, Game game)
	{			
		return correctPlayer(player, game)
				&& correctColors(firstTile, secondTile, game) 
				&& distanceOneMove(firstTile, secondTile);
	}
	
	private boolean correctPlayer(User player, Game game)
	{
		return game.getPlayerWithTurn().equals(player);
	}
	
	private boolean correctColors(Tile firstTile, Tile secondTile, Game game)
	{
		TileColor firstTileColor = firstTile.getColor();
		TileColor secondTileColor = secondTile.getColor();
		
		return firstTileColor.equals(colorOrder.get(game.getPlayerTurn())) && secondTileColor.equals(TileColor.WHITE);
	}
	
	private boolean distanceOneMove(Tile firstTile, Tile secondTile)
	{
		return Math.max(Math.abs(firstTile.getX() - secondTile.getX()), Math.abs(firstTile.getY() - secondTile.getY())) == 1;
	}
}
