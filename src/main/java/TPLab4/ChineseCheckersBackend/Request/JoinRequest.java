package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

/**
 * Join request class
 */
public class JoinRequest 
{
	/**
	 * Room id
	 */
	@NotBlank
	private Long roomId;

	/**
	 * Room id getter.
	 * @return Room id
	 */
	public Long getRoomId() 
	{
		return roomId;
	}

	/**
	 * Room id setter.
	 * @param roomId Room id
	 */
	public void setRoomId(Long roomId) 
	{
		this.roomId = roomId;
	}
}
