package k5m.wsserver.chat;

import javax.servlet.http.HttpServletRequest;

import k5m.wsserver.chat.websocket.ChatWebSocket;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

public class WebSocketChatServlet extends WebSocketServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected WebSocket doWebSocketConnect( HttpServletRequest request, String protocol )
	{
		return ( new ChatWebSocket( request, protocol ) );
	}
}
