package k5m.wsserver.acommon;

import java.util.Date;

public class SocketMessage
{
	public String command = "";
	public String requestJson = "";
	public String responseJson = "";
	public Date date = new Date();
	public boolean isError = false;
	public String errorMsg = "";
	
	public void setException( Exception e )
	{
		isError = true;
		errorMsg = e.getMessage();
	}
}
