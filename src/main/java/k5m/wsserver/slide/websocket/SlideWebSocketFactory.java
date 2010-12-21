package k5m.wsserver.slide.websocket;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;

public class SlideWebSocketFactory
{
	
	public static WebSocket getInstance()
	{
		return ( new SlideWebSocket() );
	}

	public static WebSocket getInstance( HttpServletRequest request, String protocol )
	{		
		return ( new SlideWebSocket( request, protocol ) );
	}
	
	public static WebSocket getInstance( HttpServletRequest request, String protocol, Class<?> clazz )
	{
		return ( new SlideWebSocket( request, protocol, clazz ) );
	}

}
