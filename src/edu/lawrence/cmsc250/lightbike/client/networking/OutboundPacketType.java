package edu.lawrence.cmsc250.lightbike.client.networking;

/**
 * The types of outbound packets
 *
 * @author thislooksfun
 */
enum OutboundPacketType
{
	/** -- 0 -- A request to get the list of rooms */
	GET_ROOMS,
	/** -- 1 -- A request to create a room */
	CREATE_ROOM,
	/** -- 2 -- A request to join a room */
	JOIN_ROOM,
	/** -- 3 -- A request to leave the current room */
	LEAVE_ROOM,
	/** -- 4 -- A request to change the current ready state */
	READY_STATE,
	/** -- 5 -- A request to start the game */
	START_GAME,
	/** -- 6 -- A control update - the user pressed a button */
	CONTROL;
	
	/**
	 * Converts this OutboundPacketType to a corresponding integer representation
	 *
	 * @return An integer representation of this OutboundPacketType
	 */
	public int toInt()
	{
		return this.ordinal();
	}
}