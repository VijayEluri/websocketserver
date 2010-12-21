package k5m.wsserver.slide;

import javax.servlet.http.HttpServletRequest;

import k5m.wsserver.slide.websocket.SlideClientUser;
import k5m.wsserver.slide.websocket.SlideWebSocket;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

public class WebSocketSlideClientServlet extends WebSocketServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected WebSocket doWebSocketConnect( HttpServletRequest request, String protocol )
	{
		return ( new SlideWebSocket( request, protocol, SlideClientUser.class ) );
	}
}
