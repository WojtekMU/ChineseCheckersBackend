package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

public class CreateRoomRequest 
{
	@NotBlank
	private String username;

	public String getUsername() 
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username = username;
	}
}
