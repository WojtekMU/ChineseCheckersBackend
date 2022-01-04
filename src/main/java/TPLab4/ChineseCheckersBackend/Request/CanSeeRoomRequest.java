package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

public class CanSeeRoomRequest 
{
	@NotBlank
	private String username;
	
	@NotBlank
	private Long roomId;

	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username = username;
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
