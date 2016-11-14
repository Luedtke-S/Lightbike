package edu.lawrence.cmsc250.lightbike.client.networking;

/**
 * @author thislooksfun
 */
public class Room
{
	/** The ID of the room */
	final int id;
	/** The display name of the room */
	final String name;
	
	/**
	 * Make a new Room object
	 *
	 * @param id   The ID of the room
	 * @param name The display name of the room
	 */
	Room(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
}