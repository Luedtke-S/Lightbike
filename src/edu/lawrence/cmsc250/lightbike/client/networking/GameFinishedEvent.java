package edu.lawrence.cmsc250.lightbike.client.networking;

/**
 * @author thislooksfun
 */
public class GameFinishedEvent implements PacketEvent
{
	public final int winner;
	
	public GameFinishedEvent(int winner)
	{
		this.winner = winner;
	}
}