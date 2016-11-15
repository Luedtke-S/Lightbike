package edu.lawrence.cmsc250.lightbike.client.graphics;

import java.io.IOException;

import edu.lawrence.cmsc250.lightbike.client.Main;
import edu.lawrence.cmsc250.lightbike.client.game.Constants;
import edu.lawrence.cmsc250.lightbike.client.networking.Gateway;
import edu.lawrence.cmsc250.lightbike.client.networking.RoomUpdateEvent;
import edu.lawrence.cmsc250.lightbike.client.networking.SetupEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

/**
 * @author luedtkes
 */
public class PlayerStartMenuDialogController
{
	@FXML
	Label playerLabel1;
	@FXML
	Label playerLabel2;
	@FXML
	Label playerLabel3;
	@FXML
	Label playerLabel4;
	@FXML
	Label readyLabel1;
	@FXML
	Label readyLabel2;
	@FXML
	Label readyLabel3;
	@FXML
	Label readyLabel4;
	@FXML
	ToggleButton readyButton;
	
	private int player;
	
	public static void show(int player)
	{
		try {
			FXMLLoader loader = new FXMLLoader(PlayerStartMenuDialogController.class.getResource("playerStartMenuDialog.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Main.root.setScene(scene);
			Main.root.setTitle("Ready Up");
			
			PlayerStartMenuDialogController controller = loader.getController();
			controller.setPlayer(player);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize()
	{
		playerLabel1.setTextFill(Constants.RED);
		playerLabel2.setTextFill(Constants.BLUE);
		playerLabel3.setTextFill(Constants.GREEN);
		playerLabel4.setTextFill(Constants.YELLOW);
		readyLabel1.setTextFill(Constants.READY);
		Gateway.setEventHandler(this::gameUpdated, RoomUpdateEvent.class);
		Gateway.setEventHandler(event -> Controller.show(event.playerCount), SetupEvent.class);
	}
	
	public void gameUpdated(RoomUpdateEvent e)
	{
		if (e.roomClosed)
			ChooseRoomDialogController.show();
		else {
			int needReady = e.room.occupants - 1;
			
			if (player != 2) {
				if (e.room.occupants >= 2) {
					if (e.room.player2Ready) {
						readyLabel2.setText("READY");
						readyLabel2.setTextFill(Constants.READY);
						needReady--;
					} else {
						readyLabel2.setText("NOT READY");
						readyLabel2.setTextFill(Constants.NOT_READY);
					}
				} else {
					readyLabel2.setText("empty");
					readyLabel2.setTextFill(Constants.EMPTY_SLOT);
				}
			}
			
			if (player != 3) {
				if (e.room.occupants >= 3 && player != 2) {
					if (e.room.player3Ready) {
						readyLabel3.setText("READY");
						readyLabel3.setTextFill(Constants.READY);
						needReady--;
					} else {
						readyLabel3.setText("NOT READY");
						readyLabel3.setTextFill(Constants.NOT_READY);
					}
				} else {
					readyLabel3.setText("empty");
					readyLabel3.setTextFill(Constants.EMPTY_SLOT);
				}
			}
			
			if (player != 4) {
				if (e.room.occupants == 4 && player != 2) {
					if (e.room.player4Ready) {
						readyLabel4.setText("READY");
						readyLabel4.setTextFill(Constants.READY);
						needReady--;
					} else {
						readyLabel4.setTextFill(Constants.NOT_READY);
						readyLabel4.setText("NOT READY");
					}
				} else {
					readyLabel4.setText("empty");
					readyLabel4.setTextFill(Constants.EMPTY_SLOT);
				}
			}
			
			if (player == 1)
				readyButton.setDisable(needReady != 0);
		}
	}
	
	public void setPlayer(int player)
	{
		this.player = player;
		switch (player) {
			case 1:
				readyButton.setText("Start");
				readyButton.setDisable(true);
				readyLabel1.setText("You");
				readyLabel1.setTextFill(Constants.YOU_COLOR);
				break;
			case 2:
				readyLabel2.setText("You");
				readyLabel2.setTextFill(Constants.YOU_COLOR);
				break;
			case 3:
				readyLabel3.setText("You");
				readyLabel3.setTextFill(Constants.YOU_COLOR);
				break;
			case 4:
				readyLabel4.setText("You");
				readyLabel4.setTextFill(Constants.YOU_COLOR);
				break;
		}
	}
	
	@FXML
	public void pressReady() throws IOException
	{
		if (player == 1) {
			Gateway.startGame();
		} else {
			Gateway.updateReadyState(readyButton.isSelected());
		}
	}
	
	@FXML
	public void pressLeave() throws IOException
	{
		Gateway.sendLeaveRoom();
		ChooseRoomDialogController.show();
	}
}
