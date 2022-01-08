package FINAL.project;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LogInController {

	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Button loginButton;

	static int TriesNumber = 0;

	private Timer timer;
	 
	
	@FXML
	void PerformLogin(ActionEvent event) throws IOException, InterruptedException {
		TriesNumber++;
		LogIn.CheckUserNameAndPassword(username.getText(), password.getText());
		if (getTriesNumber() > 3) {
			timer = new Timer();
			TimerTask My_task_1 = new TimerTask() {
				@Override
				public void run() {
					try {
						password.setText("");
						username.setText("");
						password.setDisable(true);
						username.setDisable(true);
						loginButton.setDisable(true);
						App.setCenter("Please try again after 10 seconds!");
						Thread.sleep(10000);
						System.out.println("Time is up");
						password.setDisable(false);
						username.setDisable(false);
						loginButton.setDisable(false);
						TriesNumber = 0;
				
					
						
						
					} catch (Exception e) {
						System.out.println("Timer is failed");
					}
				}
			};
			timer.schedule(My_task_1, 1);
		
		}
		
	}

	static public int getTriesNumber() {
		return TriesNumber;
	}

	static public void setTriesNumber(int triesNumber1) {
		TriesNumber = triesNumber1;
	}
}








