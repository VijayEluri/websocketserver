package k5m.wsserver.chat.websocket;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;

public class ChatWebSocketFactory
{

	public static synchronized WebSocket getInstance()
	{
		return new ChatWebSocket();
	}

	public static synchronized WebSocket getInstance( HttpServletRequest request, String protocol )
	{
		return new ChatWebSocket( request, protocol );
	}

}
