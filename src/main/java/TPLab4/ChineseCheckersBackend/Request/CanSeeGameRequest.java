package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

public class CanSeeGameRequest 
{
	@NotBlank
	private Long userId;
	
	@NotBlank
	private Long gameId;
	
	public Long getUserId() 
	{
		return userId;
	}

	public void setUserId(Long userId) 
	{
		this.userId = userId;
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
