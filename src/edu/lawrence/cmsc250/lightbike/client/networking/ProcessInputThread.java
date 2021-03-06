package edu.lawrence.cmsc250.lightbike.client.networking;

import java.io.BufferedReader;
import java.io.IOException;

import edu.lawrence.cmsc250.lightbike.client.game.Bike;

/**
 * The thread responsible for communicating with the server
 *
 * @author thislooksfun
 */
final class ProcessInputThread extends Thread
{
	/** Whether or not the thread has been started */
	private static boolean started = false;
	/** The input coming from the server */
	private final BufferedReader input;
	
	/**
	 * Don't allow anyone else to start a new thread of this type, there should only be one
	 *
	 * @param input The input coming from the server
	 */
	private ProcessInputThread(BufferedReader input)
	{
		this.input = input;
	}
	
	/**
	 * Start the ProcessInputThread if it hasn't been already
	 *
	 * @param input The input coming from the server
	 */
	static void startThread(BufferedReader input)
	{
		if (started)
			return; //Don't start twice
		new ProcessInputThread(input).start();
		started = true;
	}
	
	/** Run the thread itself */
	public void run()
	{
		try {
			while (true) {
				String s = input.readLine();
				if (s == null)
					break;
				
				switch (InboundPacketType.fromInt(Integer.parseInt(s))) {
					case ROOM_LIST: {
						// Packet format
						// {room1ID}:{room1Occupants}:{room1ReadyList}:{room1Name},{room2ID}:{room2Occupants}:{room2ReadyList}:{room2Name},...,{roomNID}:{roomNOccupants}:{roomNReadyList}:{roomNName}
						String data = input.readLine();
						
						//noinspection unchecked
						PacketEventHandler<RoomListEvent> handler = Gateway.getHandlerForClass(RoomListEvent.class);
						if (handler == null)
							return; //There is no handler for this event, don't bother
						
						handler.postEvent(new RoomListEvent(data));
						break;
					}
					case ROOM_UPDATE: {
						// Packet format
						// {open}:{ID}:{occupants}:{readyList}:{name}
						String data = input.readLine();
						
						//noinspection unchecked
						PacketEventHandler<RoomUpdateEvent> handler = Gateway.getHandlerForClass(RoomUpdateEvent.class);
						if (handler == null)
							return; //There is no handler for this event, don't bother
						
						handler.postEvent(new RoomUpdateEvent(data));
						break;
					}
					case RESPONSE: {
						// Packet format
						// {open}:{ID}:{occupants}:{readyList}:{name}
						String[] data = input.readLine().split(":", 2);
						
						switch (data[0]) {
							case "join_room": {
								int playerNum = Integer.parseInt(data[1]);
								if (playerNum > -1) {
									//noinspection unchecked
									PacketEventHandler<JoinRoomSuccessEvent> handler = Gateway.getHandlerForClass(JoinRoomSuccessEvent.class);
									if (handler == null)
										return; //There is no handler for this event, don't bother
									handler.postEvent(new JoinRoomSuccessEvent(playerNum));
								} else
									Gateway.requestRooms();
								break;
							}
						}
						break;
					}
					case SETUP: {
						// Packet format
						// {playerCount}
						String data = input.readLine();
						
						//noinspection unchecked
						PacketEventHandler<SetupEvent> handler = Gateway.getHandlerForClass(SetupEvent.class);
						if (handler == null)
							return; //There is no handler for this event, don't bother
						
						handler.postEvent(new SetupEvent(Integer.parseInt(data)));
						break;
					}
					case UPDATE: {
						// Packet format
						// {updatenumber}|{bikecount}|{bike1}|{bike2}|{bike3?}|{bike4?}
						String[] data = input.readLine().split("\\|");
						
						//noinspection unchecked
						PacketEventHandler<GameUpdateEvent> handler = Gateway.getHandlerForClass(GameUpdateEvent.class);
						if (handler == null)
							return; //There is no handler for this event, don't bother
						
						int bikeCount = Integer.parseInt(data[1]);
						Bike[] bikes = Bike.getBikes();
						switch (bikeCount) {
							case 4:
								bikes[3].updateFrom(data[5]);
							case 3:
								bikes[2].updateFrom(data[4]);
							case 2:
								bikes[1].updateFrom(data[3]);
								bikes[0].updateFrom(data[2]);
						}
						
						handler.postEvent(new GameUpdateEvent(Integer.parseInt(data[0])));
						break;
					}
					case CRASH: {
						//noinspection unchecked
						PacketEventHandler<YouCrashedEvent> handler = Gateway.getHandlerForClass(YouCrashedEvent.class);
						if (handler == null)
							return; //There is no handler for this event, don't bother
						
						handler.postEvent(new YouCrashedEvent());
						break;
					}
					case FINISH: {
						// Packet format
						// {winner}
						String data = input.readLine();
						
						GameUpdateEvent.reset();
						
						//noinspection unchecked
						PacketEventHandler<GameFinishedEvent> handler = Gateway.getHandlerForClass(GameFinishedEvent.class);
						if (handler == null)
							return; //There is no handler for this event, don't bother
						
						handler.postEvent(new GameFinishedEvent(Integer.parseInt(data)));
						break;
					}
					case INVALID: {
						System.err.println("[SYSTEM] Invalid packet type '" + s + "' - flushing input to console...");
						while (input.ready())
							System.err.println(" >>  " + input.readLine());
						break;
					}
				}
			}
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
		}
		
		System.out.println("ProcessInputThread has closed");
	}
}