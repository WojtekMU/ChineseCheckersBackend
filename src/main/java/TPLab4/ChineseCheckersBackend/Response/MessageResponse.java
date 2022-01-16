package TPLab4.ChineseCheckersBackend.Response;

/**
 * Response message class.
 */
public class MessageResponse 
{
	/**
	 * Message string
	 */
	private String message;

	/**
	 * Message response constructor
	 * @param message Message
	 */
	public MessageResponse(String message) 
	{
		this.message = message;
	}

	/**
	 * Message getter.
	 * @return Message
	 */
	public String getMessage() 
	{
		return message;
	}

	/**
	 * Message setter.
	 * @param message String message
	 */
	public void setMessage(String message) 
	{
		this.message = message;
	}
}