package k5m.wsserver.slide.websocket;

import org.eclipse.jetty.websocket.WebSocket.Outbound;

import k5m.wsserver.acommon.SocketUser;

public class SlideClientUser extends SocketUser
{
	/**
	 * コンストラクタ
	 */
	public SlideClientUser()
	{
		super();
	}
	
	/**
	 * コンストラクタ
	 * @param outbound
	 */
	public SlideClientUser( Outbound outbound )
	{
		super( outbound );
	}


}
