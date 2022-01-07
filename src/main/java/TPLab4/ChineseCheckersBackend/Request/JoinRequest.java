package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

public class JoinRequest 
{
	@NotBlank
	private Long userId;
	
	@NotBlank
	private Long roomId;

	public Long getUserId() 
	{
		return userId;
	}

	public void setUserId(Long userId) 
	{
		this.userId = userId;
	}
	
	public Long getRoomId() 
	{
		return roomId;
	}

	public void setRoomId(Long roomId) 
	{
		this.roomId = roomId;
	}
}
