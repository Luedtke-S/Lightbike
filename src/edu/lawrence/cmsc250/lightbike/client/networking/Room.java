package edu.lawrence.cmsc250.lightbike.client.networking;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * @author thislooksfun
 */
public class Room
{
	private static final int player2ReadyMask = 1 << 2;
	private static final int player3ReadyMask = 1 << 1;
	private static final int player4ReadyMask = 1;
	
	/** The ID of the room */
	public final int id;
	/** The number of occupants of the room */
	public final int occupants;
	/** The display name of the room */
	public final String name;
	
	/** Whether or not player2 is ready */
	public final boolean player2Ready;
	/** Whether or not player3 is ready */
	public final boolean player3Ready;
	/** Whether or not player4 is ready */
	public final boolean player4Ready;
	
	/**
	 * Make a new Room object
	 *
	 * @param id        The ID of the room
	 * @param readyMask The mast of which players are ready
	 * @param occupants The number of occupants of the room
	 * @param name      The display name of the room
	 */
	Room(int id, int occupants, int readyMask, String name)
	{
		this.id = id;
		this.occupants = occupants;
		this.name = name;
		
		player2Ready = (readyMask & player2ReadyMask) > 0;
		player3Ready = (readyMask & player3ReadyMask) > 0;
		player4Ready = (readyMask & player4ReadyMask) > 0;
	}
	
	/** The class used to construct the list of cells in a {@link ListView} */
	public static class CellFactory implements Callback<ListView<Room>, ListCell<Room>>
	{
		@Override
		public ListCell<Room> call(ListView<Room> param)
		{
			return new RoomListCell();
		}
		
		/** The class used to construct individual cells in the {@link ListView} */
		private class RoomListCell extends ListCell<Room>
		{
			@Override
			protected void updateItem(Room r, boolean bln)
			{
				super.updateItem(r, bln);
				if (r == null)
					setText(null);
				else {
					setText(r.name + "(" + r.occupants + "/4)");
				}
			}
		}
	}
}