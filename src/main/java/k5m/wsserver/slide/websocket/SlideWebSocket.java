package k5m.wsserver.slide.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import k5m.wsserver.acommon.SocketUser;

import org.eclipse.jetty.websocket.WebSocket;

public class SlideWebSocket implements WebSocket
{
	/** websocket接続リクエスト */
	private HttpServletRequest request;
	
	/** websocket接続プロトコル */
	private String protocol;
	
	/** websocket接続グループ */
	private SlideGroup group;
	
	/** websocket接続ユーザー */
	private SocketUser user;
		
	/** json変換 */
	private SlideDataExchange exchange;
	
	/**
	 * コンストラクタ
	 */
	public SlideWebSocket()
	{
	}
	
	/**
	 * コンストラクタ
	 * @param request
	 * @param protocol
	 */
	public SlideWebSocket( HttpServletRequest request, String protocol )
	{
		this.request = request;
		this.protocol = protocol;
	}
	
	/**
	 * コンストラクタ
	 * @param request
	 * @param protocol
	 */
	public SlideWebSocket( HttpServletRequest request, String protocol, Class<?> clazz )
	{
		this.request = request;
		this.protocol = protocol;
		String key = request.getParameter( "key" );
		
		group = SlideManager.getGroup( key );
		user = group.createUser( clazz );
	}
	
	/**
	 * WebSocket接続時にコール
	 * 
	 * @param Outbound
	 *            org.eclipse.jetty.websocket.WebSocketConnection
	 *            「WebSocketConnection」はWebSocketの接続先を表すクラス
	 */
	@Override
	public void onConnect( Outbound outbound )
	{
		exchange = new SlideDataExchange( group );
		user.setOutbound( outbound );		
		
		if ( group.isClientUser( user ) )
		{
			ArrayList<String> list = new ArrayList<String>();
			list.add( exchange.getSyncModeMessage( group.isSyncMode ) );
			list.add( exchange.getLockModeMessage( group.isLockMode ) );

			for ( String message : list )
			{
				try
				{
					outbound.sendMessage( message );
				}
				catch ( IOException e )
				{
					e.printStackTrace();
				}
			}
		}	
	}

	@Override
	public void onDisconnect()
	{
		group.removeMembers( user );
	}

	@Override
	public void onMessage( byte opcode, String data )
	{
		//解析
		String result = exchange.slide( data );
		
		//自分以外のスライド閲覧者全てへ送る
		Set<SocketUser> members = group.getMembers( SlideClientUser.class );
		for ( SocketUser member : members )
		{

			if ( this.user.equals( member ) ) continue;
			
			try
			{
				member.sendMessage( opcode, result );
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onMessage( byte opcode, byte[] data, int offset, int length )
	{
		//自分以外のスライド閲覧者全てへ送る
		Set<SocketUser> members = group.getMembers( SlideClientUser.class );
		for ( SocketUser member : members )
		{
			if ( this.user.equals( member ) ) continue;
			
			try
			{
				member.sendMessage( opcode, data, offset, length );
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onFragment( boolean more, byte opcode, byte[] data, int offset, int length )
	{
		//自分以外のスライド閲覧者全てへ送る
		Set<SocketUser> members = group.getMembers( SlideClientUser.class );
		for ( SocketUser member : members )
		{
			if ( this.user.equals( member ) ) continue;
			
			try
			{
				member.sendFragment( more, opcode, data, offset, length );
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
	}
}
