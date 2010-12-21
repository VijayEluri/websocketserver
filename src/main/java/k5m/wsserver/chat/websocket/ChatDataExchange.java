package k5m.wsserver.chat.websocket;

import java.util.Date;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;
import jp.co.sunseed.seedlib.stringutil.*;
import k5m.wsserver.acommon.SocketDataExchange;
import k5m.wsserver.acommon.SocketMessage;
import k5m.wsserver.acommon.SocketUser;

public class ChatDataExchange extends SocketDataExchange
{
	private final String dateFormat = "yyyy/MM/dd/HH:mm:ss";
	
	public String getWelcomMessage( SocketUser user )
	{
		SocketMessage socketMessage = new SocketMessage();
		
		socketMessage.command = "chat";
		String date = StringUtil.getDateFormat( socketMessage.date, dateFormat );

		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setName( "Administrator" );
		chatMessage.setMessage( "Welcome!" );
		chatMessage.setDate( date );
		chatMessage.setConnectNum( ChatManager.onlineMemberNum() );
		
		socketMessage.responseJson = JSON.encode( chatMessage );
		
		return JSON.encode( socketMessage );
	}
	

	public String chat( String messegeJson )
	{
		SocketMessage socketMessage = super.command( messegeJson );

        ChatMessage chatMessage = null;
		try
		{
			chatMessage = JSON.decode( socketMessage.requestJson, ChatMessage.class );
		}
		catch ( JSONException e )
		{
			e.printStackTrace();
			socketMessage.setException( e );
			socketMessage.date = new Date();
			return JSON.encode( socketMessage );
		}
		
        String date = StringUtil.getDateFormat( socketMessage.date, dateFormat );
		chatMessage.setName( Sanitizer.htmlSanitize( chatMessage.getName() ) );
		chatMessage.setMessage( Sanitizer.htmlSanitize( chatMessage.getMessage() ) );
		chatMessage.setDate( date );
        
		socketMessage.responseJson = JSON.encode( chatMessage );
		
		return JSON.encode( socketMessage );
	}
	

	public String getConnectedUserInfo()
	{
		SocketMessage socketMessage = new SocketMessage();
		socketMessage.command = "info";
		
        String date = StringUtil.getDateFormat( socketMessage.date, dateFormat );
		
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setName( "Administrator" );
		chatMessage.setMessage( "Connected to one person." );
		chatMessage.setConnectNum( ChatManager.onlineMemberNum() );
		chatMessage.setDate( date );
		
		socketMessage.responseJson = JSON.encode( chatMessage );
		
		return JSON.encode( socketMessage );
	}

}
