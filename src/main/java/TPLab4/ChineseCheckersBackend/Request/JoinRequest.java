package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

public class JoinRequest 
{
	@NotBlank
	private String username;
	
	@NotBlank
	private Long gameId;

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
