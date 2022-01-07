package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

public class EndTurnRequest 
{
	@NotBlank
	private Long gameId;
	
	@NotBlank
	private Long userId;

	public EndTurnRequest() {};
	
	public EndTurnRequest(Long gameId, Long userId) 
	{
		this.gameId = gameId;
		this.userId = userId;
	}

	public Long getGameId() 
	{
		return gameId;
	}

	public void setgameId(Long gameId) 
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
