package k5m.wsserver.slide.websocket;

import java.util.Date;

import jp.co.sunseed.seedlib.stringutil.Sanitizer;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;
import k5m.wsserver.acommon.SocketDataExchange;
import k5m.wsserver.acommon.SocketMessage;

public class SlideDataExchange extends SocketDataExchange
{
	private SlideGroup group ;
	
	public SlideDataExchange( SlideGroup group )
	{
		this.group = group;
	}
	
	public String getSyncModeMessage( boolean isSyncMode )
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.command = "remote";
		
		SlideMessage sendSyncMode = new SlideMessage();
		String syncMode = isSyncMode ? "sync" : "unsync";
		sendSyncMode.setOperation( syncMode );	
		
		socketMessage.responseJson = JSON.encode( sendSyncMode );
		return JSON.encode( socketMessage );
	}
	
	public String getLockModeMessage( boolean isLockMode )
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.command = "remote";
		
		SlideMessage sendLockMode = new SlideMessage();
		String lockMode = isLockMode ? "lock" : "unlock";
		sendLockMode.setOperation( lockMode );
		
		socketMessage.responseJson = JSON.encode( sendLockMode );
		return JSON.encode( socketMessage );
	}
	
	public String slide( String messegeJson )
	{
		SocketMessage socketMessage = super.command( messegeJson );

        SlideMessage slideMessage = null;
		try
		{
			slideMessage= JSON.decode( socketMessage.requestJson, SlideMessage.class );
		}
		catch ( JSONException e )
		{
			e.printStackTrace();
			socketMessage.setException( e );
			socketMessage.date = new Date();
			return JSON.encode( socketMessage );
		}
		
		String ope = Sanitizer.htmlSanitize( slideMessage.getOperation() );

		group.setLockMode( ope );
		group.setSyncMode( ope );
		
		slideMessage.setOperation( ope );
		socketMessage.responseJson = JSON.encode( slideMessage );
		
		return JSON.encode( socketMessage );
	}

}
