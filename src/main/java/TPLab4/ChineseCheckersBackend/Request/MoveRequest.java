package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

public class MoveRequest 
{
	@NotBlank
	private Long firstTileId;
	
	@NotBlank
	private Long secondTileId;
	
	@NotBlank
	private Long gameId;
	
	@NotBlank
	private String username;

	public Long getFirstTileId() 
	{
		return firstTileId;
	}

	public void setFirstTileId(Long firstTileId)
	{
		this.firstTileId = firstTileId;
	}

	public Long getSecondTileId() 
	{
		return secondTileId;
	}

	public void setSecondTileId(Long secondTileId) 
	{
		this.secondTileId = secondTileId;
	}

	public Long getGameId() 
	{
		return gameId;
	}

	public void setGameId(Long gameId) 
	{
		this.gameId = gameId;
	}

	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username = username;
	}
}
