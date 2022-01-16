package TPLab4.ChineseCheckersBackend.Request;

import javax.validation.constraints.NotBlank;

/**
 * Login request class
 */
public class LoginRequest 
{
	/**
	 * User username
	 */
	@NotBlank
	private String username;

	/**
	 * User password
	 */
	@NotBlank
	private String password;

	/**
	 * User username getter.
	 * @return User username
	 */
	public String getUsername() 
	{
		return username;
	}

	/**
	 * User username setter.
	 * @param username Username
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * User password getter.
	 * @return User password
	 */
	public String getPassword() 
	{
		return password;
	}

	/**
	 * User password setter.
	 * @param password Password
	 */
	public void setPassword(String password) 
	{
		this.password = password;
	}
}