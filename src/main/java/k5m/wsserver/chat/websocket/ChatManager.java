package k5m.wsserver.chat.websocket;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import k5m.wsserver.acommon.SocketUser;


class ChatManager
{
	/**
	 * 接続先すべてを保持
	 * _membersはメッセージ送信のたびに参照されるため，「Set」クラスではなく，
	 * 参照系に有利な「CopyOnWriteArraySet」クラスを使用
	 */
	static private Set<SocketUser> _members = new CopyOnWriteArraySet<SocketUser>();

	/**
	 * 接続数
	 * @return
	 */
	static public int onlineMemberNum()
	{
		return _members.size();	
	}
	
	static public void addMembers( SocketUser user )
	{
		_members.add( user );
	}
	
	static public void removeMembers( SocketUser menber )
	{
		_members.remove( menber );
	}
	
	static public Set<SocketUser> getMembers()
	{
		return _members;
	}
}
