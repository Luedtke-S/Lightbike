package edu.lawrence.cmsc250.lightbike.client.networking;

/**
 * @author thislooksfun
 */
public class RoomUpdateEvent implements PacketEvent
{
	public final int pos;
	/** Whether or not the room was closed */
	public final boolean roomClosed;
	/** The room object associated with this update, if it's still open */
	public final Room room;
	
	RoomUpdateEvent(String parseFrom)
	{
		// parseFrom format:
		// {open}:{pos}:{ID}:{occupants}:{readyList}:{name}
		String[] roomData = parseFrom.split(":", 6);
		this.roomClosed = Integer.parseInt(roomData[0]) != 1;
		this.pos = (roomClosed ? -1 : Integer.parseInt(roomData[1]));
		this.room = (roomClosed ? null : new Room(Integer.parseInt(roomData[2]), Integer.parseInt(roomData[3]), Integer.parseInt(roomData[4]), roomData[5]));
	}
}