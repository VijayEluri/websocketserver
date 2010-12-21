package k5m.wsserver.slide.websocket;

import org.eclipse.jetty.websocket.WebSocket.Outbound;

import k5m.wsserver.acommon.SocketUser;

public class SlideRemoteUser extends SocketUser
{
	
	/**
	 * コンストラクタ
	 * @param outbound
	 */
	public SlideRemoteUser()
	{
		super();
	}
	
	/**
	 * コンストラクタ
	 * @param outbound
	 */
	public SlideRemoteUser( Outbound outbound )
	{
		super( outbound );
	}


}
