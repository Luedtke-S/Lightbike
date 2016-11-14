package edu.lawrence.cmsc250.lightbike.client.graphics;

import java.io.IOException;

import edu.lawrence.cmsc250.lightbike.client.game.Constants;
import edu.lawrence.cmsc250.lightbike.client.networking.Gateway;
import edu.lawrence.cmsc250.lightbike.client.networking.RoomUpdateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

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
	
	private boolean isP1 = false;
	
	int playersReady2 = 1;
	int playersReady3 = 1;
	int playersReady4 = 1;
	
	@FXML
	public void initialize()
	{
		playerLabel1.setTextFill(Constants.RED);
		playerLabel2.setTextFill(Constants.BLUE);
		playerLabel3.setTextFill(Constants.GREEN);
		playerLabel4.setTextFill(Constants.YELLOW);
		
		Gateway.addEventHandler(event -> gameUpdated(event), RoomUpdateEvent.class);
	}
	
	public void gameUpdated(RoomUpdateEvent e)
	{
		
		if (e.roomClosed) {
			try {
				Stage parent = (Stage)readyLabel1.getScene().getWindow();
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("chooseRoomDialog.fxml"));
				Parent root = (Parent)loader.load();
				Scene scene = new Scene(root);
				parent.setScene(scene);
				parent.setTitle("Choose Room");
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
		
		if (e.room.occupants == 2) {
			if (e.room.player2Ready) {
				readyLabel2.setText("READY");
				playersReady2 += 1;
			}
		}
		
		if (e.room.occupants == 3) {
			if (e.room.player2Ready) {
				readyLabel2.setText("READY");
				playersReady3 += 1;
			}
			
			if (e.room.player2Ready) {
				readyLabel2.setText("READY");
				playersReady3 += 1;
			}
		}
		
		if (e.room.occupants == 4) {
			if (e.room.player2Ready) {
				readyLabel2.setText("READY");
				playersReady4 += 1;
			}
			
			if (e.room.player2Ready) {
				readyLabel2.setText("READY");
				playersReady4 += 1;
			}
			
			if (e.room.player4Ready) {
				readyLabel4.setText("READY");
				playersReady4 += 1;
			}
		}
		
		if (playersReady2 == 2 || playersReady3 == 3 || playersReady4 == 4) {
			readyButton.setDisable(false);
		}
	}
	
	public void setPlayer1()
	{
		isP1 = true;
		readyButton.setText("Start");
		readyButton.setDisable(true);
	}
	
	@FXML
	public void pressReady() throws IOException
	{
		
		if (isP1) {
			
		} else {
			Gateway.updateReadyState(readyButton.isSelected());
		}
		
		Stage parent = (Stage)readyLabel1.getScene().getWindow();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("arena.fxml"));
		Parent root = (Parent)loader.load();
		Scene scene = new Scene(root);
		parent.setScene(scene);
		parent.setTitle("It's on!");
	}
	
	@FXML
	public void pressLeave() throws IOException
	{
		Gateway.sendLeaveRoom();
		Stage parent = (Stage)readyLabel1.getScene().getWindow();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("chooseRoomDialog.fxml"));
		Parent root = (Parent)loader.load();
		Scene scene = new Scene(root);
		parent.setScene(scene);
		parent.setTitle("Choose Room");
	}
}
