package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

public class GameIdRequest 
{
	@NotBlank
	private Long roomId;

	public Long getRoomId() 
	{
		return roomId;
	}

	public void setRoomId(Long roomId) 
	{
		this.roomId = roomId;
	}
}
