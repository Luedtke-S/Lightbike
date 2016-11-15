package edu.lawrence.cmsc250.lightbike.client.graphics;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import edu.lawrence.cmsc250.lightbike.client.Main;
import edu.lawrence.cmsc250.lightbike.client.networking.Gateway;
import edu.lawrence.cmsc250.lightbike.client.networking.JoinRoomSuccessEvent;
import edu.lawrence.cmsc250.lightbike.client.networking.Room;
import edu.lawrence.cmsc250.lightbike.client.networking.RoomListEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

/**
 * @author luedtkes
 */
public class ChooseRoomDialogController
{
	@FXML
	private Button joinButton;
	@FXML
	private ListView<Room> roomView;
	
	private ObservableList<Room> rooms = FXCollections.observableArrayList();
	
	public static void show()
	{
		try {
			Parent root = FXMLLoader.load(ChooseRoomDialogController.class.getResource("chooseRoomDialog.fxml"));
			Scene scene = new Scene(root);
			Main.root.setScene(scene);
			Main.root.setTitle("Choose Room");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize()
	{
		roomView.setCellFactory(new Room.CellFactory());
		roomView.getSelectionModel().selectedItemProperty().addListener((ov, obj, newItem) -> roomSelected(newItem));
		
		Gateway.requestRooms();
		Gateway.setEventHandler(event -> {
			rooms.clear();
			Collections.addAll(rooms, event.rooms);
		}, RoomListEvent.class);
		Gateway.setEventHandler(event -> {
			PlayerStartMenuDialogController.show(event.playerNum);
		}, JoinRoomSuccessEvent.class);
		
		roomView.setItems(rooms);
	}
	
	@FXML
	public void pressJoin()
	{
		Gateway.sendJoinRoom(roomView.getSelectionModel().getSelectedItem().id);
	}
	
	@FXML
	public void pressRefresh()
	{
		Gateway.requestRooms();
	}
	
	@FXML
	public void pressCreate() throws IOException
	{
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Enter Room Name");
		
		Optional<String> name = dialog.showAndWait();
		
		if (name.isPresent()) {
			Gateway.sendCreateRoom(name.get());
			PlayerStartMenuDialogController.show(1);
		}
	}
	
	private void roomSelected(Room r)
	{
		joinButton.setDisable(r == null);
	}
}