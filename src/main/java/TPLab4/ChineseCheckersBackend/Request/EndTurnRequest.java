package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

public class EndTurnRequest 
{
	@NotBlank
	private Long gameId;

	public EndTurnRequest() {};
	
	public EndTurnRequest(Long gameId)
	{
		this.gameId = gameId;
	}

	public Long getGameId() 
	{
		return gameId;
	}

	public void setgameId(Long gameId) 
	{
		this.gameId = gameId;
	}
}
