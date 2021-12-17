package TPLab4.ChineseCheckersBackend;

public class JoinRequest 
{
	String username;
	Long gameId;

	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) 
	{
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
}
