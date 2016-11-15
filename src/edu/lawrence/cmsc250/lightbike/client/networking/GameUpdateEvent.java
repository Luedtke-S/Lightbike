package edu.lawrence.cmsc250.lightbike.client.networking;

/**
 * An event fired when a game update occurs
 *
 * @author thislooksfun
 */
public final class GameUpdateEvent implements PacketEvent
{
	/** The number of the last update received - used to make sure no update was missed */
	private static int lastUpdateNumber = 0;
	
	/**
	 * Create a new GameUpdateEvent instance
	 *
	 * @param updateNumber The update number for this update
	 */
	GameUpdateEvent(int updateNumber)
	{
		if (updateNumber != lastUpdateNumber + 1)
			throw new IllegalStateException("Update numbers must be sequential - expected '" + (lastUpdateNumber + 1) + "' got '" + updateNumber + "'");
		lastUpdateNumber = updateNumber;
	}
}