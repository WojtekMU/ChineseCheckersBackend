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
		return Math.max(Math.abs(firstTile.getX() - secondTile.getX()), Math.abs(firstTile.getY() - secondTile.getY())) == 1;
	}
	
	public boolean isDistanceTwoMove(Tile firstTile, Tile secondTile)
	{
		return Math.max(Math.abs(firstTile.getX() - secondTile.getX()), Math.abs(firstTile.getY() - secondTile.getY())) == 2;
	}

	public List<TileColor> getColorOrder() 
	{
		return colorOrder;
	}
}
