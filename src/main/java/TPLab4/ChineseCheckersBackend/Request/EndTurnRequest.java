package TPLab4.ChineseCheckersBackend.Request;

public class EndTurnRequest 
{
	private Long gameId;
	private String username;

	public EndTurnRequest() {};
	
	public EndTurnRequest(Long gameId, String username) 
	{
		this.gameId = gameId;
		this.username = username;
	}

	public Long getGameId() 
	{
		return gameId;
	}

	public void setgameId(Long gameId) 
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
