package edu.lawrence.cmsc250.lightbike.client.networking;

/**
 * @author thislooksfun
 */
public class SetupEvent implements PacketEvent
{
	public final int playerCount;
	
	public SetupEvent(int playerCount)
	{
		this.playerCount = playerCount;
	}
}