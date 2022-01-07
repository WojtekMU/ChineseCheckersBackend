package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

public class MoveRequest 
{
	@NotBlank
	private Long tileId;
	
	@NotBlank
	private Long gameId;
	
	@NotBlank
	private Long userId;

	public Long getTileId() 
	{
		return tileId;
	}

	public void setTileId(Long tileId) 
	{
		this.tileId = tileId;
	}

	public Long getGameId() 
	{
		return gameId;
	}

	public void setGameId(Long gameId) 
	{
		this.gameId = gameId;
	}

	public Long getUserId() 
	{
		return userId;
	}

	public void setUserId(Long userId) 
	{
		this.userId = userId;
	}
}
