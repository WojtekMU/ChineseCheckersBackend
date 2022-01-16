package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

/**
 * End turn request class
 */
public class EndTurnRequest 
{
	/**
	 * Game id
	 */
	@NotBlank
	private Long gameId;

	/**
	 * Game id getter.
	 * @return Game id
	 */
	public Long getGameId() 
	{
		return gameId;
	}

	/**
	 * Game id setter.
	 * @param gameId Game id
	 */
	public void setgameId(Long gameId) 
	{
		this.gameId = gameId;
	}
}
