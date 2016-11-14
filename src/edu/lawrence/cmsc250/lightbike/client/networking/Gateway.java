package edu.lawrence.cmsc250.lightbike.client.networking;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import edu.lawrence.cmsc250.lightbike.client.game.Constants;
import edu.lawrence.cmsc250.lightbike.client.game.physics.Direction;
import edu.lawrence.cmsc250.lightbike.client.game.physics.Point2D;
import static edu.lawrence.cmsc250.lightbike.client.game.Bike.bike1;
import static edu.lawrence.cmsc250.lightbike.client.game.Bike.bike2;

/**
 * @author thislooksfun
 */
public enum Gateway
{
	; //DO NOT REMOVE
	
	/** The map of packet types to their corresponding {@link PacketEventHandler}s */
	private static final Map<Class, PacketEventHandler> EVENT_HANDLER_MAP = new HashMap<>();
	
	/** The output going to the server */
	private static final PrintWriter OUTPUT;
	/** The input coming from the server */
	private static final BufferedReader INPUT;
	
	// Establish the connection to the server.
	static {
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			// Create a socket to connect to the server
			Socket socket = new Socket("143.44.68.178", 1337); //TODO: Move host port either into constants, or preferably into startup input box
			outputStream = socket.getOutputStream();
			inputStream = socket.getInputStream();
		} catch (IOException ex) {
			System.err.println("[SYSTEM] Exception creating gateway socket:");
			ex.printStackTrace();
		}
		
		if (outputStream == null)
			throw new IllegalStateException("OutputStream is null!");
		if (inputStream == null)
			throw new IllegalStateException("InputStream is null!");
		
		// Create an output stream to send data to the server
		OUTPUT = new PrintWriter(outputStream);
		// Create an INPUT stream to read data from the server
		INPUT = new BufferedReader(new InputStreamReader(inputStream));
		
		// Start the communication thread
		ProcessInputThread.startThread(INPUT);

//		PipedInputStream pis = new PipedInputStream();
//		PipedOutputStream pos = null;
//		try {
//			pos = new PipedOutputStream(pis);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		INPUT = new BufferedReader(new InputStreamReader(pis));
//		OUTPUT = new PrintWriter(pos);
//		ProcessInputThread.startThread(INPUT);
	}
	
	/**
	 * Register an event handler
	 * <p>
	 * NOTE: The event handle will ALWAYS be run on the main graphics thread
	 *
	 * @param handler The event handler to register
	 * @param clazz   The event class for which this handler should be registered
	 * @param <T>     The type of the event class
	 */
	public static <T extends PacketEvent> void addEventHandler(PacketEventHandler<T> handler, Class<T> clazz)
	{
		EVENT_HANDLER_MAP.put(clazz, handler);
		
		if (clazz.equals(GameUpdateEvent.class)) {
			new Thread(() -> {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException ignored) {}
				
				System.out.println("Starting arbitrary bike data");
				
				Point2D bike1Start = bike1.getLocation();
				Point2D bike2Start = bike2.getLocation();
				
				int gap = (Constants.GRID_SIZE / 2) - Constants.START_OFFSET;
				int max = Constants.GRID_SIZE - gap * 2;
				max *= 10;
				
				int packet = 0;
				
				for (int i = 1; i <= max; i++) {
					System.out.println("[1/3] Sent bike data " + i + "/" + (max - 1));
					String bike1 = (bike1Start.x + (i / 10.0)) + ":" + (bike1Start.y);
					String bike2 = (bike2Start.x - (i / 10.0)) + ":" + (bike2Start.y);
					OUTPUT.println(InboundPacketType.UPDATE.ordinal() + "\n" + (++packet) + "|2|" + bike1 + "|" + bike2);
					OUTPUT.flush();
					try {
						Thread.sleep(1000 / 60);
					} catch (InterruptedException ignored) {}
				}
				
				bike1Start = new Point2D(bike1Start.x + (max / 10.0), bike1Start.y);
				bike2Start = new Point2D(bike2Start.x - (max / 10.0), bike2Start.y);
				
				{ //Turn 1
					System.out.println("[1.5/3] Sent bike turn data 1/2");
					String bike1 = (bike1Start.x) + ":" + (bike1Start.y) + ">" + Direction.DOWN.toInt() + ">" + (bike1Start.x) + ":" + (bike1Start.y);
					String bike2 = (bike2Start.x) + ":" + (bike2Start.y) + ">" + Direction.UP.toInt() + ">" + (bike2Start.x) + ":" + (bike2Start.y);
					OUTPUT.println(InboundPacketType.UPDATE.ordinal() + "\n" + (++packet) + "|2|" + bike1 + "|" + bike2);
					OUTPUT.flush();
					try {
						Thread.sleep(1000 / 60);
					} catch (InterruptedException ignored) {}
				}
				
				for (int i = 1; i <= max / 2; i++) {
					System.out.println("[2/3] Sent bike data " + i + "/" + (max - 1));
					String bike1 = (bike1Start.x) + ":" + (bike1Start.y + (i / 10.0));
					String bike2 = (bike2Start.x) + ":" + (bike2Start.y - (i / 10.0));
					OUTPUT.println(InboundPacketType.UPDATE.ordinal() + "\n" + (++packet) + "|2|" + bike1 + "|" + bike2);
					OUTPUT.flush();
					try {
						Thread.sleep(1000 / 60);
					} catch (InterruptedException ignored) {}
				}
				
				bike1Start = new Point2D(bike1Start.x, bike1Start.y + ((max / 2) / 10.0));
				bike2Start = new Point2D(bike2Start.x, bike2Start.y - ((max / 2) / 10.0));
				
				{ //Turn 2
					System.out.println("[2.5/3] Sent bike turn data 2/2");
					String bike1 = (bike1Start.x) + ":" + (bike1Start.y) + ">" + Direction.LEFT.toInt() + ">" + (bike1Start.x) + ":" + (bike1Start.y);
					String bike2 = (bike2Start.x) + ":" + (bike2Start.y) + ">" + Direction.RIGHT.toInt() + ">" + (bike2Start.x) + ":" + (bike2Start.y);
					OUTPUT.println(InboundPacketType.UPDATE.ordinal() + "\n" + (++packet) + "|2|" + bike1 + "|" + bike2);
					OUTPUT.flush();
					try {
						Thread.sleep(1000 / 60);
					} catch (InterruptedException ignored) {}
				}
				
				for (int i = 1; i <= max; i++) {
					System.out.println("[3/3] Sent bike data " + i + "/" + (max - 1));
					String bike1 = (bike1Start.x - (i / 10.0)) + ":" + (bike1Start.y);
					String bike2 = (bike2Start.x + (i / 10.0)) + ":" + (bike2Start.y);
					OUTPUT.println(InboundPacketType.UPDATE.ordinal() + "\n" + (++packet) + "|2|" + bike1 + "|" + bike2);
					OUTPUT.flush();
					try {
						Thread.sleep(1000 / 60);
					} catch (InterruptedException ignored) {}
				}
				
				OUTPUT.close();
			}).start();
		}
	}
	
	/** Request the list of rooms from the server */
	public static void requestRooms()
	{
		sendPacket(OutboundPacketType.GET_ROOMS);
	}
	
	/**
	 * Create a new room
	 *
	 * @param name The name of the room to create
	 */
	public static void sendCreateRoom(String name)
	{
		sendPacket(OutboundPacketType.CREATE_ROOM, name);
	}
	
	/**
	 * Join a room
	 *
	 * @param id The id of the room to join
	 */
	public static void sendJoinRoom(int id)
	{
		sendPacket(OutboundPacketType.JOIN_ROOM, id);
	}
	
	/** Leave the current room */
	public static void sendLeaveRoom()
	{
		sendPacket(OutboundPacketType.LEAVE_ROOM);
	}
	
	/**
	 * Update the ready state
	 *
	 * @param state The new ready state
	 */
	public static void updateReadyState(boolean state)
	{
		sendPacket(OutboundPacketType.READY_STATE, state ? 1 : 0);
	}
	
	/** Starts the game */
	public static void startGame()
	{
		sendPacket(OutboundPacketType.START_GAME);
	}
	
	/**
	 * Send a direction press to the server -- MUST BE {@link Direction#LEFT} or {@link Direction#RIGHT}
	 *
	 * @param d The direction to turn
	 */
	public static void sendControlPressed(Direction d)
	{
		if (d != Direction.LEFT && d != Direction.RIGHT)
			throw new IllegalStateException("Control must be either LEFT or RIGHT");
		sendPacket(OutboundPacketType.CONTROL, d.toInt());
	}
	
	// <editor-fold desc="Send packet methods" defaultstate="collapsed">
	
	/**
	 * Send a packet to the server with no body
	 *
	 * @param packetType The type of packet to send
	 */
	private static void sendPacket(OutboundPacketType packetType)
	{
		System.out.println("Sending packet of type " + packetType.name());
//		OUTPUT.println(packetType.toInt());
//		OUTPUT.flush();
	}
	
	/**
	 * Send a packet to the server with an int body
	 *
	 * @param packetType The type of packet to send
	 * @param packet     The packet data to send
	 */
	private static void sendPacket(OutboundPacketType packetType, int packet)
	{
		System.out.println("Sending packet of type " + packetType.name() + " with data " + packet);
//		OUTPUT.println(packetType.toInt());
//		OUTPUT.println(packet);
//		OUTPUT.flush();
	}
	
	/**
	 * Send a packet to the server with a string body
	 *
	 * @param packetType The type of packet to send
	 * @param packet     The packet data to send
	 */
	private static void sendPacket(OutboundPacketType packetType, String packet)
	{
		System.out.println("Sending packet of type " + packetType.name() + " with data " + packet);
//		OUTPUT.println(packetType.toInt());
//		OUTPUT.println(packet);
//		OUTPUT.flush();
	}
	// </editor-fold>
	
	/**
	 * Gets the {@link PacketEventHandler} for the given class
	 *
	 * @param clazz The class for which to get the {@link PacketEventHandler}
	 *
	 * @return A {@link PacketEventHandler}, or {@code null} if none is registered for the given class
	 */
	@SuppressWarnings("unchecked")
	static <T extends PacketEvent> PacketEventHandler<T> getHandlerForClass(Class<T> clazz)
	{
		return EVENT_HANDLER_MAP.get(clazz);
	}
}