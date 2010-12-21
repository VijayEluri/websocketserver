package k5m.wsserver.acommon;

import java.util.Date;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;
import jp.co.sunseed.seedlib.stringutil.*;

public class SocketDataExchange
{

	/**
	 * コマンドベースの変換処理
	 * @param messageData
	 * @return
	 */
	protected SocketMessage command( String messageData )
	{        
		SocketMessage socketMessage = null;
		try
		{
			socketMessage = JSON.decode( messageData, SocketMessage.class );
			socketMessage.isError = false;
		}
		catch ( JSONException e )
		{
			e.printStackTrace();
			
			if ( socketMessage == null ) socketMessage = new SocketMessage();
			socketMessage.setException( e );
		}
		
		//JSON.decodeでnullのため
		socketMessage.date = new Date();
		
		return socketMessage ;
	}
}
