package k5m.wsserver.chat.websocket;

public class ChatMessage
{
	private int connectNum;
	private String name;
	private String message;
	private String date;

	/**
	 * @return the connectNum
	 */
	public int getConnectNum()
	{
		return connectNum;
	}
	/**
	 * @param connectNum the connectNum to set
	 */
	public void setConnectNum( int connectNum )
	{
		this.connectNum = connectNum;
	}
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName( String name )
	{
		this.name = name;
	}
	/**
	 * @return the messge
	 */
	public String getMessage()
	{
		return message;
	}
	/**
	 * @param messge the messge to set
	 */
	public void setMessage( String message )
	{
		this.message = message;
	}
	/**
	 * @return the date
	 */
	public String getDate()
	{
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate( String date )
	{
		this.date = date;
	}
	
	
}
