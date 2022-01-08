
package FINAL.project;

import java.io.IOException; 
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class InsertCodeInterface {

	@FXML // fx:id="EnterExamCode"
	private  Button EnterExamCode; // Value injected by FXMLLoader

	@FXML // fx:id="textField"
	private TextField CodeField;// Value injected by FXMLLoader


	@FXML // fx:id="backHome"
	private  Button backHome; // Value injected by FXMLLoader
	private static int counter = 0;
	private static String CodeFieldContent;

	
	
	private  Timer timerWait;
	private  TimerTask My_task_2 = new TimerTask() {
		@Override
		public void run() {
			try {
				EnterExamCode.setDisable(true);
				backHome.setDisable(true);

				App.setCenter("Please try again after 1 minute!");
				Thread.sleep(5000);
				System.out.println("Time is up");
				EnterExamCode.setDisable(false);
				backHome.setDisable(false);
				counter = 0;

			} catch (Exception e) {
				System.out.println("Timer is failed");
			}
		}
	};

	
	
	@FXML
	 void backHome(ActionEvent event)  {
		try {
			App.setRoot("studentHomePage");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	@FXML
	void EnterCode(ActionEvent event) throws IOException, InterruptedException {
		CodeFieldContent=CodeField.getText();
		if (CodeField.getText().isEmpty()) {
			App.setCenter("Please insert a non-Empy code!");
			counter++;
		}
		else if(CodeField.getText().length()!=4) {
			counter++;
			App.setCenter("Code must contain 4 digits or letters");
		}
		else if (CodeField.getText().matches("[a-zA-Z0-9]+") == false) {
			App.setCenter("Invalid code! it must contain only digits or letters.");
			counter++;
		}
		 else {
			TakeExamControl.CheckIfLegal(CodeField.getText());
		}
		if (counter > 4) {
			timerWait=new Timer();
		   My_task_2 = new TimerTask() {
				@Override
				public void run() {
					try {
						EnterExamCode.setDisable(true);
						backHome.setDisable(true);

						App.setCenter("Please try again after 1 minute!");
						Thread.sleep(5000);
						System.out.println("Time is up");
						EnterExamCode.setDisable(false);
						backHome.setDisable(false);
						counter = 0;

					} catch (Exception e) {
						System.out.println("Timer is failed");
					}
				}
			};

			timerWait.schedule(My_task_2, 1);
		} 
	}
			// invalid code
		static void ExamNotFound(int value) throws IOException, InterruptedException {
			System.out.println("value"+value);
			if (value == 0) {
				
				// correct code & online exam
				TakeExamControl.getStartExam22().setLock(true);
				MsgToServer massageMsgToServer = new MsgToServer("", "Update", TakeExamControl.getStartExam22(), "");
				try {
					SimpleChatClient.getClient().sendToServer(massageMsgToServer);
				} catch (IOException e) {
					e.printStackTrace();
				}
				App.setRoot("OnlineExamInterface");

			} else if (value == 1) {
				// correct code & manual exam
				App.setExamCode(CodeFieldContent);
				MsgToServer massageMsgToServer = new MsgToServer("", "Update", App.getStartExam(), "");
				try {
					SimpleChatClient.getClient().sendToServer(massageMsgToServer);
				} catch (IOException e) {
					e.printStackTrace();
				}
				App.setRoot("ManualExam");

			} else if (value == -4) {
				App.setCenter("This Exam is over!");
				counter++;
			}
			else if(value==3) {
				App.setCenter("You have already take this exam");
				counter++;
			}
			else if(value==-1) {
				App.setCenter("Invalid code , please try again!");
					counter++;
			}
	
	
		}

	}

