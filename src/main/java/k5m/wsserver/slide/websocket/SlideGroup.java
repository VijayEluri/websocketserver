package k5m.wsserver.slide.websocket;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import k5m.wsserver.acommon.SocketUser;

public class SlideGroup
{
	//ロックモード
	public boolean isLockMode = false;
	
	//シンクモード
	public boolean isSyncMode = false;
	
	/**
	 * 接続先すべてを保持
	 * _membersはメッセージ送信のたびに参照されるため，「Set」クラスではなく，
	 * 参照系に有利な「CopyOnWriteArraySet」クラスを使用
	 */
	
	/**
	 * リモート接続者
	 */
	private Set<SocketUser> remoteMembers = new CopyOnWriteArraySet<SocketUser>();

	
	/**
	 * クライアント接続者
	 */
	private Set<SocketUser> clientMembers = new CopyOnWriteArraySet<SocketUser>();

	
	/**
	 * 接続数
	 * @return
	 */
	public int onlineMemberNum( Class<?> clazz )
	{
		String className = clazz.getName();
		if ( SlideRemoteUser.class.getName().equals( className ) )
		{
			return remoteMembers.size();
		}
		else if ( SlideClientUser.class.getName().equals( className ) )
		{
			return clientMembers.size();
		}
		return 0;
	}

	/**
	 * 接続追加
	 * @param user
	 */
	public void addMembers( SocketUser user )
	{
		if ( user instanceof SlideRemoteUser )
		{
			remoteMembers.add( user );
		}
		else if ( user instanceof SlideClientUser )
		{
			clientMembers.add( user );
		}
	}
	
	/**
	 * 接続解除
	 * @param user
	 */
	public void removeMembers( SocketUser user )
	{
		if ( user instanceof SlideRemoteUser )
		{
			remoteMembers.remove( user );
		}
		else if ( user instanceof SlideClientUser )
		{
			clientMembers.remove( user );
		}
	}
	
	/**
	 * 接続者リスト
	 * @param clazz
	 * @return
	 */
	public Set<SocketUser> getMembers( Class<?> clazz )
	{
		String className = clazz.getName();
		
		if ( SlideRemoteUser.class.getName().equals( className ) )
		{
			return remoteMembers;
		}
		else if ( SlideClientUser.class.getName().equals( className ) )
		{
			return clientMembers;
		}
		
		return new CopyOnWriteArraySet<SocketUser>();
	}
	
	/**
	 * クライアントユーザーかどうか
	 * @param user
	 * @return
	 */
	public boolean isClientUser( SocketUser user )
	{
		return ( user instanceof SlideClientUser );
	}

	/**
	 * ロックモード切替
	 * @param ope
	 */
	public void setLockMode( String ope )
	{
		if ( "lock".equals( ope ) )
		{
			isLockMode = true;
		}
		else if( "unlock".equals( ope ) )
		{
			isLockMode = false;
		}	
	}

	/**
	 * シンクモード切替
	 * @param ope
	 */
	public void setSyncMode( String ope )
	{
		if ( "sync".equals( ope ) )
		{
			isSyncMode = true;
		}
		else if( "unsync".equals( ope ) )
		{
			isSyncMode = false;
		}
	}

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public SocketUser createUser( Class<?> clazz )
	{
		try
		{
			SocketUser user = (SocketUser) clazz.newInstance();
			this.addMembers( user );
			user.setParentGroup( this );
			return user;
		}
		catch ( InstantiationException e )
		{
			e.printStackTrace();
		}
		catch ( IllegalAccessException e )
		{
			e.printStackTrace();
		}
		return null;
	}
}
