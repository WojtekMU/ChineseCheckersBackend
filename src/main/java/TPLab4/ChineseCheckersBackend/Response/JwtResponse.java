package TPLab4.ChineseCheckersBackend.Response;

import java.util.List;

/**
 * Jwt response class
 */
public class JwtResponse
{
	/**
	 * Jwt token
	 */
	private String token;

	/**
	 * Token type
	 */
	private String type = "Bearer";

	/**
	 * User id
	 */
	private Long id;

	/**
	 * User username
	 */
	private String username;

	/**
	 * User roles
	 */
	private final List<String> roles;

	/**
	 * Jwt response constructor.
	 * @param accessToken Token
	 * @param id User id
	 * @param username User username
	 * @param roles User roles
	 */
	public JwtResponse(String accessToken, Long id, String username, List<String> roles)
	{
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}

	/**
	 * Access token getter.
	 * @return Access token
	 */
	public String getAccessToken()
	{
		return token;
	}

	/**
	 * Access token setter.
	 * @param accessToken Access token
	 */
	public void setAccessToken(String accessToken)
	{
		this.token = accessToken;
	}

	/**
	 * Token type getter.
	 * @return Token type
	 */
	public String getTokenType()
	{
		return type;
	}

	/**
	 * Token type setter.
	 * @param tokenType Token type
	 */
	public void setTokenType(String tokenType)
	{
		this.type = tokenType;
	}

	/**
	 * User id getter.
	 * @return User id
	 */
	public Long getId() 
	{
		return id;
	}

	/**
	 * User id setter.
	 * @param id Long value
	 */
	public void setId(Long id) 
	{
		this.id = id;
	}

	/**
	 * USer username getter.
	 * @return Username
	 */
	public String getUsername() 
	{
		return username;
	}

	/**
	 * Username setter.
	 * @param username Username
	 */
	public void setUsername(String username) 
	{
		this.username = username;
	}

	/**
	 * User roles getter.
	 * @return User roles
	 */
	public List<String> getRoles()
	{
		return roles;
	}
}