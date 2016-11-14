package edu.lawrence.cmsc250.lightbike.client.networking;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * @author thislooksfun
 */
public class Room
{
	/** The ID of the room */
	final int id;
	/** The number of occupants of the room */
	final int occupants;
	/** The display name of the room */
	final String name;
	
	/**
	 * Make a new Room object
	 *
	 * @param id        The ID of the room
	 * @param occupants The number of occupants of the room
	 * @param name      The display name of the room
	 */
	Room(int id, int occupants, String name)
	{
		this.id = id;
		this.occupants = occupants;
		this.name = name;
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