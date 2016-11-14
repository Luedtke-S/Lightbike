package edu.lawrence.cmsc250.lightbike.client.networking;

/**
 * @author thislooksfun
 */
public class RoomListEvent implements PacketEvent
{
	public final Room[] rooms;
	
	RoomListEvent(String parseFrom)
	{
		// parseFrom format:
		// {room1ID}:{room1Occupants}:{room1ReadyList}:{room1Name},{room2ID}:{room2Occupants}:{room2ReadyList}:{room2Name},...,{roomNID}:{roomNOccupants}:{roomNReadyList}:{roomNName}
		String[] list = parseFrom.split(",");
		Room[] rs = new Room[list.length];
		for (int i = 0; i < list.length; i++) {
			String[] roomData = list[i].split(":", 4);
			rs[i] = new Room(Integer.parseInt(roomData[0]), Integer.parseInt(roomData[1]), Integer.parseInt(roomData[2]), roomData[3]);
		}
		rooms = rs;
	}
}