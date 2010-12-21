package k5m.wsserver.slide.websocket;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

class SlideManager
{
	static private HashMap< String, SlideGroup > manager = new HashMap<String, SlideGroup>();
	
	static synchronized SlideGroup getGroup( String key )
	{
		SlideGroup group = manager.get( key );
		if ( group == null )
		{
			group = new SlideGroup();
			manager.put( key, group );
		}
		
		return group;
	}
	
	static synchronized SlideWebSocket getNewRemoteWebSocket( HttpServletRequest request, String protocol )
	{
		String key = request.getRequestURL().toString();
		SlideGroup group = manager.get( key );
		if ( group == null ) group = new SlideGroup();

		SlideWebSocket socket = new SlideWebSocket( request, protocol, SlideRemoteUser.class );
		
		return socket;
	}

	static synchronized SlideWebSocket getNewClientWebSocket( HttpServletRequest request, String protocol )
	{
		String key = request.getRequestURL().toString();
		SlideGroup group = manager.get( key );
		if ( group == null ) group = new SlideGroup();

		SlideWebSocket socket = new SlideWebSocket( request, protocol, SlideClientUser.class );
		return socket;
	}

}
