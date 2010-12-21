package k5m.wsserver.acommon;

import java.io.IOException;
import java.util.Date;

import k5m.wsserver.slide.websocket.SlideGroup;

import org.eclipse.jetty.websocket.WebSocket.Outbound;

public class SocketUser
{
	/**
	 * コネクションボード
	 */
	private Outbound outbound;
	
	/**
	 * 接続日時
	 */
	private Date connectionDate;
	
	/**
	 * ユニークキー
	 */
	private String key;

	/**
	 * group
	 */
	private SlideGroup parentGroup;
	
	/**
	 * 
	 * @param outbound
	 */
	public SocketUser()
	{
		connectionDate = new Date();
	}
	
	/**
	 * 
	 * @param outbound
	 */
	public SocketUser( Outbound outbound )
	{
		this.outbound = outbound;
		connectionDate = new Date();
	}


	/**
	 * @return the outbound
	 */
	public Outbound getOutbound()
	{
		return outbound;
	}

	/**
	 * @param outbound the outbound to set
	 */
	public void setOutbound( Outbound outbound )
	{
		this.outbound = outbound;
	}

	/**
	 * 
	 * @return
	 */
	public Date getUserConnectedDate()
	{
		return ( connectionDate );
	}
	
	/**
	 * 
	 * @param data
	 * @throws IOException
	 */
	public void sendMessage( String data ) throws IOException
	{
		outbound.sendMessage( data );
	}
	
	/**
	 * 
	 * @param frame
	 * @param data
	 * @throws IOException
	 */
	public void sendMessage( byte frame, String data ) throws IOException
	{
		outbound.sendMessage( frame, data );
		
	}

	/**
	 * 
	 * @param frame
	 * @param data
	 * @param offset
	 * @param length
	 * @throws IOException
	 */
	public void sendMessage( byte frame, byte[] data, int offset, int length ) throws IOException
	{
		outbound.sendMessage( frame, data, offset, length );
		
	}

	/**
	 * 
	 * @param more
	 * @param opcode
	 * @param data
	 * @param offset
	 * @param length
	 * @throws IOException
	 */
	public void sendFragment( boolean more, byte opcode, byte[] data, int offset, int length ) throws IOException
	{
		outbound.sendFragment( more, opcode, data, offset, length );
	}

	/**
	 * 
	 */
	public void dispose()
	{
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 * @param group
	 */
	public void setParentGroup( SlideGroup group )
	{
		parentGroup = group;
	}
	
	/**
	 * 
	 * @return
	 */
	public SlideGroup getParentGroup()
	{
		return ( parentGroup );
	}
	
}
