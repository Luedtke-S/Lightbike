package edu.lawrence.cmsc250.lightbike.client.networking;

/**
 * @author thislooksfun
 */
public class RoomListEvent implements PacketEvent
{
	public final Room[] rooms;
	
	RoomListEvent(String parseFrom)
	{
		String[] list = parseFrom.split(",");
		Room[] rs = new Room[list.length];
		for (int i = 0; i < list.length; i++) {
			String[] roomData = list[i].split(":");
			rs[i] = new Room(Integer.parseInt(roomData[0]), roomData[1]);
		}
		rooms = rs;
	}
}