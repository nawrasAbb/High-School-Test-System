package FINAL.project;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import FINAL.project.CheckExam;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class CheckAndConfirmGrades {
	String code;
	@FXML // fx:id="Back"
	private Button Back; // Value injected by FXMLLoader

	@FXML // fx:id="StartAutoChecking"
	private Button StartAutoChecking; // Value injected by FXMLLoader

	@FXML // fx:id="ConfirmGrades"
	private Button ConfirmGrades; // Value injected by FXMLLoader

	@FXML // fx:id="TextField"
	private TextField TextField; // Value injected by FXMLLoader

	@FXML
	private static   ProgressIndicator loader; // Value injected by FXMLLoader

	@FXML // fx:id="WrongInput"
	private static Label WrongInput;

	@FXML
	void BackHome(ActionEvent event) {
		try {
			App.setRoot("TeacherHomePage");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void autoCheck(ActionEvent event) throws InterruptedException, IOException {
		code = TextField.getText();
		if (code.isEmpty() == true) {
			App.setCenter("Code field is empty.");
		} else if (code.length() != 4) {
			App.setCenter("Code must contain 4 digits or letters.");
			
		} else if (code.matches("[a-zA-Z0-9]+") == false) {
			App.setCenter("Invalid code! it must contain only digits or letters.");
			 
		}
		else {
			CheckExam.ExamCheck(code,1);
		}
		
		
		
	
	}
	
	
	static void autocheck2() throws InterruptedException, IOException {
		System.out.println("returnded code "+CheckExam.getStartExam().getCode());
		System.out.println("    Im in autocheck2");
		int state=InputCheck2(1);
		if (state == 0) {
			
			System.out.println("Before examcheck2");
		CheckExam.ExamCheck2();
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		//	loader.setProgress(100.0);
			System.out.println("after examcheck2");
			
		App.setMsgAtCenter("The exam have been checked ");
		}
	}
	
	
	
	@FXML
	void Confirm(ActionEvent event) throws InterruptedException, IOException {
		code = TextField.getText();
		if (code.isEmpty() == true) {
			App.setCenter("Code field is empty.");
		} else if (code.length() != 4) {
			App.setCenter("Code must contain 4 digits or letters.");
			
		} else if (code.matches("[a-zA-Z0-9]+") == false) {
			App.setCenter("Invalid code! it must contain only digits or letters.");
			 
		}
		else {
			CheckExam.ExamCheck(code,2);
		}
		
	Thread.sleep(1000);
		if (InputCheck2(0) == 0) {
		
			System.out.println("Im in confirm2");
			try {
				Pair<Parent, Object> confirmPair = App.getFxmlAndController("ConfirmGradesInterface");
				ConfirmGradesInterface confirm = (ConfirmGradesInterface) confirmPair.getValue();
				if (ConfirmGradesInterface.setInitParam(confirm) == 0) {
					App.setRoot2(confirmPair.getKey());
				}
				else if(ConfirmGradesInterface.setInitParam(confirm) == -1) {
					App.setCenter("Exam have not been checked");
				}

				System.out.println("Im in confirm2 after ifs");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	

	
	public static  int InputCheck2(int op) throws InterruptedException {
		int result=CheckExam.checkIfLegal(op);	
		if (result == -1) {
			try {
				App.setCenter("There is no exam with this code");
				
				loader.setProgress(0);
				//return -1;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return -1;
		}
		if (result== 2) {
			try {
				App.setCenter("this Exam hasn't been checked yet , please press autoCheck");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 2;
		
		}
		if (result== -2) {

			try {
				App.setCenter("Exam has already been checked!");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return -2;
		}
		System.out.println(result);
		return result;
	}
}