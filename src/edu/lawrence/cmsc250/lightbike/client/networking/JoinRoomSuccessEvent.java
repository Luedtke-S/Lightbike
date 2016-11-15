package edu.lawrence.cmsc250.lightbike.client.networking;

/**
 * @author thislooksfun
 */
public class JoinRoomSuccessEvent implements PacketEvent
{
	public final int playerNum;
	
	public JoinRoomSuccessEvent(int playerNum)
	{
		this.playerNum = playerNum;
	}
}