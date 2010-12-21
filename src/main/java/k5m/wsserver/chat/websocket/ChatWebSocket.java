package k5m.wsserver.chat.websocket;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import k5m.wsserver.acommon.SocketUser;

import org.eclipse.jetty.websocket.WebSocket;

public class ChatWebSocket implements WebSocket
{
	/** websocket接続リクエスト */
	private HttpServletRequest request;
	
	/** 接続プロトコル */
	private String protocol;
	
	/**
	 * websocketに接続したユーザー
	 */
	private SocketUser user;

	private ChatDataExchange exchange = new ChatDataExchange();
	
	/**
	 * コンストラクタ
	 */
	public ChatWebSocket( )
	{
	}
	
	/**
	 * コンストラクタ
	 * @param request
	 * @param protocol
	 */
	public ChatWebSocket( HttpServletRequest request, String protocol )
	{
		this.request = request;
		this.protocol = protocol;
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
		user = new SocketUser( outbound );
		ChatManager.addMembers( user );
				
		Set<SocketUser> members = ChatManager.getMembers();

		// すべての接続先に送る
		for ( SocketUser member : members )
		{
			try
			{
				member.sendMessage( exchange.getConnectedUserInfo() );
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
		
		
		try
		{
			user.sendMessage( exchange.getWelcomMessage( user ) );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * WebSocket切断時にコール
	 */
	@Override
	public void onDisconnect()
	{
		ChatManager.removeMembers( user );
		user.dispose();
		
		Set<SocketUser> members = ChatManager.getMembers();

		// 残りの接続先に送る
		for ( SocketUser member : members )
		{
			try
			{
				member.sendMessage( exchange.getConnectedUserInfo() );
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 接続先からメッセージ受取時にコール
	 * 
	 * @param frame
	 *            データ送信の詳細 WebSocket.LENGTH_FRAME - テキストフレーム用
	 *            WebSocket.SENTINEL_FRAME - バイナリフレーム用
	 */
	@Override
	public void onMessage( byte frame, String data )
	{

		String result = exchange.chat( data );
		
		Set<SocketUser> members = ChatManager.getMembers();

		// すべての接続先に送る
		for ( SocketUser member : members )
		{
			try
			{
				member.sendMessage( frame, result );
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 接続先からメッセージ受取時にコール
	 * 
	 * @param frame
	 *            データ送信の詳細
	 */
	@Override
	public void onMessage( byte frame, byte[] data, int offset, int length )
	{
		Set<SocketUser> members = ChatManager.getMembers();

		// すべての接続先に送る
		for ( SocketUser member : members )
		{
			try
			{
				member.sendMessage( frame, data, offset, length );
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 接続先からメッセージ受取時にコール
	 */
	@Override
	public void onFragment( boolean more, byte opcode, byte[] data, int offset, int length )
	{
		Set<SocketUser> members = ChatManager.getMembers();

		// すべての接続先に送る
		for ( SocketUser member : members )
		{
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
