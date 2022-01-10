package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

public class ChosenTileRequest 
{
	@NotBlank
	private Long gameId;

	public Long getGameId() 
	{
		return gameId;
	}

	public void setgameId(Long gameId) 
	{
		this.gameId = gameId;
	}
}
