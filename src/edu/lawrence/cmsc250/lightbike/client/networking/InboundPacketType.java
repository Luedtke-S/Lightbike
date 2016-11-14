package edu.lawrence.cmsc250.lightbike.client.networking;

/**
 * The types of inbound packets
 *
 * @author thislooksfun
 */
enum InboundPacketType
{
	/** -- 0 -- Setup the initial game state */
	SETUP,
	/** -- 1 -- Update the game state */
	UPDATE,
	/** -- 2 -- The list of the rooms */
	ROOM_LIST,
	/** -- 3 -- The room's state has changed (closed, joined, left, ready, unready) */
	ROOM_UPDATE,
	/** -- 4 -- Respond to client request */
	RESPONSE,
	/** None of the other types match, thus it's invalid */
	INVALID;
	
	/**
	 * Get the InboundPacketType represented by a certain {@code int}
	 *
	 * @param i The {@code int} to convert from
	 *
	 * @return The InboundPacketType represented by the given {@code int}, or {@code null} if the given {@code int} was invalid
	 */
	public static InboundPacketType fromInt(int i)
	{
		switch (i) {
			case 0:
				return SETUP;
			case 1:
				return UPDATE;
			case 2:
				return ROOM_LIST;
			case 3:
				return ROOM_UPDATE;
			case 4:
				return RESPONSE;
			default:
				return INVALID;
		}
	}
	
	/**
	 * Converts this InboundPacketType to a corresponding integer representation
	 *
	 * @return An integer representation of this InboundPacketType
	 */
	public int toInt()
	{
		return (this == INVALID) ? -1 : this.ordinal();
	}
}