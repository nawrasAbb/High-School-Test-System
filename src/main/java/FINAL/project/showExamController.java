package FINAL.project;

import FINAL.project.Exam;
import FINAL.project.StartExam;
import FINAL.project.Teacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;

public class showExamController {
	private static int thereIsExam = 0;
	private static Scene newsceneScene;

	public int isThereIsExam() {
		return thereIsExam;
	}

	public static void setThereIsExam(int thereIsExam2) {
		thereIsExam = thereIsExam2;
	}

	public static Scene getNewsceneScene() {
		return newsceneScene;
	}

	public static void setNewsceneScene(Scene newsceneScene2) {
		newsceneScene = newsceneScene2;
	}

	@FXML
	private TextField examId;

	@FXML
	private TextArea allExamId;

	public TextArea getAllExamId() {
		return allExamId;
	}

	public void setAllExamId(TextArea allExamId) {
		this.allExamId = allExamId;
	}

	@FXML
	void backtohomepage(ActionEvent event) throws IOException {
		App.setRoot("teacherHomePage");
	}

	static Stage showStage;

	@FXML
	void printselectedexam(ActionEvent event) throws IOException, InterruptedException {
		thereIsExam = 0;
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
					MsgToServer massageMsgToServer = new MsgToServer("Exam", "Get", Integer.parseInt(id_exam),
							"ShowExam for teacher");
					try {
						SimpleChatClient.getClient().sendToServer(massageMsgToServer);
					} catch (IOException e) {
						System.out.println("msg not sent ");
						e.printStackTrace();
					}
					Thread.sleep(700);
					
					if (thereIsExam == 1) {

						showStage = new Stage();
						showStage.setScene(getNewsceneScene());
						showStage.show();
					}
					else if (thereIsExam == -1) {App.setCenter("Exam Id not yours");}
				}
			}
		}
	}

	public static void showExamforTeacher(Exam exam) throws InterruptedException, IOException {

		{
			Pair<Parent, Object> displayPair = App.getFxmlAndController("DisplayContent");
			ControlsubWindow.DisplayExam(exam, (DisplayContentController) displayPair.getValue());
			setNewsceneScene(new Scene(displayPair.getKey()));

		}
	}

	public static void setexams(showExamController show) throws InterruptedException {
		String myString = null;
		List<Exam> exams = new ArrayList<Exam>();
		List<StartExam> startexams = new ArrayList<StartExam>();

		Teacher teacher = (Teacher) App.getUser();
		exams = teacher.getWrittenExamsList();
		startexams = teacher.getStartedExamsList();
		myString = "Exams:\n";
		for (Exam myExam : exams) {
			myString += "Exam Course :" + myExam.getCourseOfExam().getCourseName() + "  Exam ID :" + myExam.getExam_id()
					+ "\n";
		}
		myString += "Start Exams:\n";
		for (StartExam myStartExam : startexams) {
			myString += "Exam Course :" + myStartExam.getExam().getCourseOfExam().getCourseName() + "  Exam ID :"
					+ myStartExam.getExam().getExam_id() + "\n";
		}
		show.getAllExamId().setText(myString);
		return;
	}
}
