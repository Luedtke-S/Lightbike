package edu.lawrence.cmsc250.lightbike.client.networking;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import edu.lawrence.cmsc250.lightbike.client.game.physics.Direction;
import static edu.lawrence.cmsc250.lightbike.client.game.Bike.*;

/**
 * @author thislooksfun
 */
public enum Gateway
{
	; //DO NOT REMOVE
	
	private static final Map<Class, PacketEventHandler> EVENT_HANDLER_MAP = new HashMap<>();
	
	/** The OUTPUT going to the server */
	private static final PrintWriter OUTPUT;
	/** The INPUT coming from the server */
	private static final BufferedReader INPUT;
	
	// Establish the connection to the server.
	static {
		OUTPUT = null;
		INPUT = null;

//		OutputStream outputStream = null;
//		InputStream inputStream = null;
//		try {
//			// Create a socket to connect to the server
//			Socket socket = new Socket("localhost", 8000);
//			outputStream = socket.getOutputStream();
//			inputStream = socket.getInputStream();
//		} catch (IOException ex) {
//			System.err.println("[SYSTEM] Exception creating gateway socket:");
//			ex.printStackTrace();
//		}
//		
//		if (outputStream == null)
//			throw new IllegalStateException("OutputStream is null!");
//		if (inputStream == null)
//			throw new IllegalStateException("InputStream is null!");
//		
//		// Create an OUTPUT stream to send data to the server
//		OUTPUT = new PrintWriter(outputStream);
//		// Create an INPUT stream to read data from the server
//		INPUT = new BufferedReader(new InputStreamReader(inputStream));
//		
//		// Start the communication thread
//		ProcessInputThread.startThread(INPUT);
		
		Reader r = new StringReader(InboundPacketType.UPDATE.ordinal() + "\n1|2|30:10|40:10");
		ProcessInputThread.startThread(new BufferedReader(r));
	}
	
	public static <T extends PacketEvent> void addEventHandler(PacketEventHandler<T> handler, Class<T> clazz)
	{
		EVENT_HANDLER_MAP.put(clazz, handler);
	}
	
	public static void sendControlPressed(Direction d)
	{
		sendPacket(OutboundPacketType.CONTROL, d.toInt());
	}
	
	// <editor-fold desc="Send packet methods" defaultstate="collapsed">
	private static void sendPacket(OutboundPacketType packetType, int packet)
	{
		System.out.println("Sending packet of type " + packetType.name() + " with data " + packet);
//		OUTPUT.println(packetType.toInt());
//		OUTPUT.println(packet);
//		OUTPUT.flush();
	}
	
	private static void sendPacket(OutboundPacketType packetType, String packet)
	{
		System.out.println("Sending packet of type " + packetType.name() + " with data " + packet);
//		OUTPUT.println(packetType.toInt());
//		OUTPUT.println(packet);
//		OUTPUT.flush();
	}
	// </editor-fold>
	
	
	/**
	 * A handler to be used when a certain type of packet is received
	 *
	 * @param <T> The type of the event to handle
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
	
	
	/** A base interface for packet-based events */
	public interface PacketEvent {}
	
	
	/** An event fired when a game update occurs */
	public static class GameUpdateEvent implements PacketEvent
	{
		public static int lastUpdateNumber = -1;
		
		public GameUpdateEvent(int updateNumber)
		{
			lastUpdateNumber = updateNumber;
		}
	}
	
	
	/** The types of outbound packets */
	private enum OutboundPacketType
	{
		/** A control update - the user pressed a button */
		CONTROL,
		/** None of the other types match, thus it's invalid */
		INVALID;
		
		/**
		 * Converts this OutboundPacketType to a corresponding integer representation
		 *
		 * @return An integer representation of this OutboundPacketType
		 */
		public int toInt()
		{
			return (this == INVALID) ? -1 : this.ordinal();
		}
	}
	
	
	/** The types of inbound packets */
	private enum InboundPacketType
	{
		/** Setup the inital game state */
		SETUP,
		/** Update the game state */
		UPDATE,
		/** Respond to client request */
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
	
	
	/** The thread responsible for communicating with the server */
	private static class ProcessInputThread extends Thread
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
		public static void startThread(BufferedReader input)
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
					InboundPacketType pt = InboundPacketType.fromInt(Integer.parseInt(s));
					switch (pt) {
						case SETUP:
							//TODO do setup
							break;
						case UPDATE:
							// Packet format:
							// {updatenumber}|{bikecount}|{bike1}|{bike2}|{bike3?}|{bike4?}
							String[] data = input.readLine().split("\\|");
							
							if (!EVENT_HANDLER_MAP.containsKey(GameUpdateEvent.class))
								return; //There is no handler for this event, don't bother
							
							int updateNumber = Integer.parseInt(data[0]);
							if (GameUpdateEvent.lastUpdateNumber > -1 && updateNumber != GameUpdateEvent.lastUpdateNumber + 1)
								throw new IllegalStateException("Update numbers don't match!");
							
							int bikeCount = Integer.parseInt(data[1]);
							switch (bikeCount) {
								case 4:
									bike4.updateFrom(data[5]);
								case 3:
									bike3.updateFrom(data[4]);
								case 2:
									bike2.updateFrom(data[3]);
									bike1.updateFrom(data[2]);
							}
							//noinspection unchecked
							EVENT_HANDLER_MAP.get(GameUpdateEvent.class).handleEvent(new GameUpdateEvent(updateNumber));
							break;
						case RESPONSE:
							//TODO process response
							break;
						case INVALID:
							System.err.println("[SYSTEM] Invalid response - flushing INPUT...");
							while (input.ready())
								System.err.println(" >>  " + input.readLine());
							break;
					}
				}
			} catch (IOException | NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}
}