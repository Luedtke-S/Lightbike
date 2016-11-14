package edu.lawrence.cmsc250.lightbike.client.graphics;

import java.io.IOException;

import edu.lawrence.cmsc250.lightbike.client.game.Constants;
import edu.lawrence.cmsc250.lightbike.client.networking.Gateway;
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
	
	@FXML
	public void initialize()
	{
		playerLabel1.setTextFill(Constants.RED);
		playerLabel2.setTextFill(Constants.BLUE);
		playerLabel3.setTextFill(Constants.GREEN);
		playerLabel4.setTextFill(Constants.YELLOW);
	}
	
	public void setPlayer1()
	{
		isP1 = true;
		readyButton.setText("Start");
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
}
