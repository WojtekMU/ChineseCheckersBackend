package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

public class MoveRequest 
{
	@NotBlank
	private Long tileId;
	
	@NotBlank
	private Long gameId;

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
}
