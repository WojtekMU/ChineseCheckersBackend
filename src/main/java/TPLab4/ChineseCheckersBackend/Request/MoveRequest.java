package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

/**
 * Move request class.
 */
public class MoveRequest 
{
	/**
	 * Tile id
	 */
	@NotBlank
	private Long tileId;

	/**
	 * Game id
	 */
	@NotBlank
	private Long gameId;

	/**
	 * Tile id getter.
	 * @return Tile id
	 */
	public Long getTileId() 
	{
		return tileId;
	}

	/**
	 * Tile id setter.
	 * @param tileId Tile id
	 */
	public void setTileId(Long tileId) 
	{
		this.tileId = tileId;
	}

	/**
	 * Game id getter
	 * @return Game id
	 */
	public Long getGameId() 
	{
		return gameId;
	}

	/**
	 * Game id setter.
	 * @param gameId Game id
	 */
	public void setGameId(Long gameId) 
	{
		this.gameId = gameId;
	}
}
