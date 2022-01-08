package FINAL.project;

import java.io.IOException;
import java.util.List;

import FINAL.project.AnswerSheet;
import FINAL.project.Student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;

public class printsMyExamsController {

	private static boolean thereIsExam=false;
	
	
	
	
	public boolean isThereIsExam() {
		return thereIsExam;
	}

	public static void setThereIsExam(boolean thereIsExam2) {
		thereIsExam = thereIsExam2;
	}

	@FXML
	private TextField examId;

	public TextArea getAllExamId() {
		return allExamId;
	}

	public void setAllExamId(TextArea allExamId) {
		this.allExamId = allExamId;
	}

	@FXML
	private TextArea allExamId;

	@FXML
	void backtohomepage(ActionEvent event) throws IOException {
		App.setRoot("studentHomePage");
	}
	private static Scene newsceneScene;
	
	//check if the exam id the student Entered is valid then opens the exam in a new window
	@FXML
	void printselectedexam(ActionEvent event) throws IOException, InterruptedException {
		thereIsExam=false;
		if (examId.getText().isEmpty()) {
			App.setCenter("Please enter an ID!");
		} else {
			if (examId.getText().matches("[0-9]+") == false) {
				App.setCenter("Please enter only digit!");
			} else {
				String id_exam = examId.getText();
				if (id_exam.length() != 6) {
					App.setCenter("ID must be 6 digit!");
				} else {
					MsgToServer massageMsgToServer = new MsgToServer("Exam","Get", Integer.parseInt(id_exam),"Open exam for student");
					try {
						SimpleChatClient.getClient().sendToServer(massageMsgToServer);
					} catch (IOException e) {
						System.out.println("msg not sent ");
						e.printStackTrace();
					}
					Thread.sleep(700);
					if(thereIsExam==true) {
						Stage showStage=new Stage();
						showStage.setScene(getNewsceneScene());
						showStage.show();
					}
				}
			}
		}
	}
	


public static Scene getNewsceneScene() {
		return newsceneScene;
	}

	public static void setNewsceneScene(Scene newsceneScene2) {
		newsceneScene = newsceneScene2;
	}

public static void ShowExam(Exam myExam) throws InterruptedException, IOException{

	Pair<Parent, Object> displayPair = App.getFxmlAndController("DisplayContent");
	ControlsubWindow.printExam(myExam,(DisplayContentController) displayPair.getValue());
	setNewsceneScene(new Scene(displayPair.getKey()));
	
	

}

	public static void setexams(printsMyExamsController show) throws InterruptedException {
		String mystring = "";
		Student student = (Student) App.getUser();
		if (student != null) {

			List<AnswerSheet> allAnswerSheets = student.getAllAnswerSheets();
			for (AnswerSheet aSheet : allAnswerSheets) {
				
				if(aSheet.getIsConfirmed()) {
				mystring += "Exam Course : " + aSheet.getExam().getCourseOfExam().getCourseName() + "      Exam ID : "
						+ Integer.toString(aSheet.getExam().getExam_id()) + ".\n";
			}}
			show.getAllExamId().setText(mystring);
			return;

		}
	}

}




