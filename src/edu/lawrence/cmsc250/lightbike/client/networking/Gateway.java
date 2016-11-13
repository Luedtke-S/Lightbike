package edu.lawrence.cmsc250.lightbike.client.networking;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import edu.lawrence.cmsc250.lightbike.client.game.Constants;
import edu.lawrence.cmsc250.lightbike.client.game.physics.Direction;
import edu.lawrence.cmsc250.lightbike.client.game.physics.Point2D;
import javafx.application.Platform;
import static edu.lawrence.cmsc250.lightbike.client.game.Bike.*;

/**
 * @author thislooksfun
 */
public enum Gateway
{
	; //DO NOT REMOVE
	
	/** The map of packet types to their corresponding {@link PacketEventHandler}s */
	private static final Map<Class, PacketEventHandler> EVENT_HANDLER_MAP = new HashMap<>();
	
	/** The OUTPUT going to the server */
	private static final PrintWriter OUTPUT;
	/** The INPUT coming from the server */
	private static final BufferedReader INPUT;
	
	// Establish the connection to the server.
	static {
//		OUTPUT = null;
//		INPUT = null;

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
		
		PipedInputStream pis = new PipedInputStream();
		PipedOutputStream pos = null;
		try {
			pos = new PipedOutputStream(pis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		INPUT = new BufferedReader(new InputStreamReader(pis));
		OUTPUT = new PrintWriter(pos);
		ProcessInputThread.startThread(INPUT);
	}
	
	/**
	 * Register an event handler
	 * 
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
				
				for (int i = 1; i <= max; i++) {
					System.out.println("[1/3] Sent bike data " + i + "/" + (max - 1));
					String bike1 = (bike1Start.x + (i / 10.0)) + ":" + (bike1Start.y);
					String bike2 = (bike2Start.x - (i / 10.0)) + ":" + (bike2Start.y);
					OUTPUT.println(InboundPacketType.UPDATE.ordinal() + "\n" + i + "|2|" + bike1 + "|" + bike2);
					OUTPUT.flush();
					try {
						Thread.sleep(1000 / 60);
					} catch (InterruptedException ignored) {}
				}
				
				bike1Start = new Point2D(bike1Start.x + (max / 10.0), bike1Start.y);
				bike2Start = new Point2D(bike2Start.x - (max / 10.0), bike1Start.y);
				
				for (int i = 1; i <= max / 2; i++) {
					System.out.println("[2/3] Sent bike data " + i + "/" + (max - 1));
					String bike1 = (bike1Start.x) + ":" + (bike1Start.y + (i / 10.0));
					String bike2 = (bike2Start.x) + ":" + (bike2Start.y - (i / 10.0));
					OUTPUT.println(InboundPacketType.UPDATE.ordinal() + "\n" + i + "|2|" + bike1 + "|" + bike2);
					OUTPUT.flush();
					try {
						Thread.sleep(1000 / 60);
					} catch (InterruptedException ignored) {}
				}
				
				bike1Start = new Point2D(bike1Start.x, bike1Start.y + ((max / 2) / 10.0));
				bike2Start = new Point2D(bike2Start.x, bike1Start.y - ((max / 2) / 10.0));
				
				for (int i = 1; i <= max; i++) {
					System.out.println("[3/3] Sent bike data " + i + "/" + (max - 1));
					String bike1 = (bike1Start.x - (i / 10.0)) + ":" + (bike1Start.y);
					String bike2 = (bike2Start.x + (i / 10.0)) + ":" + (bike2Start.y);
					OUTPUT.println(InboundPacketType.UPDATE.ordinal() + "\n" + i + "|2|" + bike1 + "|" + bike2);
					OUTPUT.flush();
					try {
						Thread.sleep(1000 / 60);
					} catch (InterruptedException ignored) {}
				}
				
				OUTPUT.close();
			}).start();
		}
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
	 * Send a packet to the server
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
	 * Send a packet to the server
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
		/** The number of the last update received - used to make sure no update was missed */
		public static int lastUpdateNumber = 0;
		
		/**
		 * Create a new GameUpdateEvent instance
		 *
		 * @param updateNumber The update number for this update
		 */
		GameUpdateEvent(int updateNumber)
		{
			if (updateNumber != lastUpdateNumber + 1)
				throw new IllegalStateException("Update numbers must be sequential - expected '" + (lastUpdateNumber + 1) + "' got '" + updateNumber + "'");
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
	private static final class ProcessInputThread extends Thread
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
						case SETUP:
							//TODO do setup
							break;
						case UPDATE:
							// Packet format
							// {updatenumber}|{bikecount}|{bike1}|{bike2}|{bike3?}|{bike4?}
							String[] data = input.readLine().split("\\|");
							
							if (!EVENT_HANDLER_MAP.containsKey(GameUpdateEvent.class))
								return; //There is no handler for this event, don't bother
							
							int updateNumber = Integer.parseInt(data[0]);
							if (updateNumber != GameUpdateEvent.lastUpdateNumber + 1)
								throw new IllegalStateException("Update numbers must be sequential - expected '" + (GameUpdateEvent.lastUpdateNumber + 1) + "' got '" + updateNumber + "'");
							
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
							PacketEventHandler<GameUpdateEvent> handler = EVENT_HANDLER_MAP.get(GameUpdateEvent.class);
							GameUpdateEvent event = new GameUpdateEvent(updateNumber);
							Platform.runLater(() -> handler.handleEvent(event));
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
			
			System.out.println("ProcessInputThread has closed");
		}
	}
}