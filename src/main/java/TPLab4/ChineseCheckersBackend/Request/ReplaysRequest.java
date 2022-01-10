package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

public class ReplaysRequest 
{
	@NotBlank
	private Long userId;

	public Long getUserId() 
	{
		return userId;
	}

	public void setUserId(Long userId) 
	{
		this.userId = userId;
	}
}
