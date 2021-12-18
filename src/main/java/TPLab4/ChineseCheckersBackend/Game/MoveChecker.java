package TPLab4.ChineseCheckersBackend.Game;

import java.util.Optional;

import TPLab4.ChineseCheckersBackend.Tile.Tile;
import TPLab4.ChineseCheckersBackend.Tile.TileRepository;

public class MoveChecker 
{
	MoveChecker() {};
	
	public boolean checkMove(Tile firstTile, Tile secondTile, TileRepository tileRepository)
	{	
		Long distance = Math.max(Math.abs(firstTile.getX() - secondTile.getX()), Math.abs(firstTile.getY() - secondTile.getY()));
		String firstTileColor = firstTile.getColor();
		String secondTileColor = secondTile.getColor();
		
		return correctColors(firstTileColor, secondTileColor) 
				&& (distanceOneMove(distance) || distanceTwoMove(distance, firstTile, secondTile, tileRepository));
	}
	
	private boolean correctColors(String firstTileColor, String secondTileColor)
	{
		return !firstTileColor.equals("white") && secondTileColor.equals("white");
	}
	
	private boolean distanceOneMove(Long distance)
	{
		return distance == 1;
	}
	
	private boolean distanceTwoMove(Long distance, Tile firstTile, Tile secondTile, TileRepository tileRepository)
	{
		Long sum = Math.abs(firstTile.getX() - secondTile.getX()) + Math.abs(firstTile.getY() - secondTile.getY());
		
		return distance == 2 && (sum == 2 || sum == 4)
				? isMiddleNotWhite(firstTile, secondTile, tileRepository) : false;
	}
	
	private boolean isMiddleNotWhite(Tile firstTile, Tile secondTile, TileRepository tileRepository)
	{
		Long gameId = firstTile.getGame().getId();
		Long middleTileX = Math.abs(firstTile.getX() + secondTile.getX()) / 2;
		Long middleTileY = Math.abs(firstTile.getY() + secondTile.getY()) / 2;
		Optional<Tile> middleTile = tileRepository.findByXAndYAndGameId(middleTileX, middleTileY, gameId);
		
		return !middleTile.get().getColor().equals("white");
	}
}
