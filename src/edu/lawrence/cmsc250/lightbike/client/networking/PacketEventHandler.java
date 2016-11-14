package edu.lawrence.cmsc250.lightbike.client.networking;

/**
 * A handler to be used when a certain type of packet is received
 *
 * @param <T> The type of the event to handle
 *
 * @author thislooksfun
 */
@FunctionalInterface
public interface PacketEventHandler<T extends PacketEvent>
{
	/**
	 * Handle the event
	 * <p>
	 * NOTE: This will always be run on the main thread
	 *
	 * @param event The event to handle
	 */
	void handleEvent(T event);
}