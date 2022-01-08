package FINAL.project;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Pair;

public class StartExamForTeacherController {

	@FXML
	private TextField Code;

	@FXML
	private ChoiceBox<String> ChooseType;

	@FXML
	private TextField ExamID;

	@FXML
	private ScrollPane DisplayAllExams;

	@FXML
	private /*static*/ Text CorrectCode;

	@FXML
	private Text CossedExam;

	@FXML
	private Text ChoosedType;

	@FXML
	private /*static*/ Text ErrorWindow;

	@FXML
	private Circle circle;

	@FXML
	private Text error;

	static Exam exam;
	
	
	
	

	public static Exam getExam() {
		return exam;
	}

	public static void setExam(Exam exam) {
		StartExamForTeacherController.exam = exam;
	}

	private static String codeExist="";

	public static String getCodeExist() {
	return codeExist;
}

public static void setError(String error2) {
	codeExist = error2;
}
	
	// getting the tupe from the choose button
	@FXML
	void setType(MouseEvent event) {
		ObservableList<String> option = FXCollections.observableArrayList("Manual", "Online");
		getChooseType().setItems(option);
	}

	// go back to home page
	@FXML
	void Back(ActionEvent event) throws IOException {
		App.setRoot("teacherHomePage");
	}

	// performing an exam
	@FXML
	void StartExamButton(ActionEvent event) throws IOException, InterruptedException {
		setError("");
		CorrectCode.setText("");
		ChoosedType.setText("");
		CossedExam.setText("");
		circle.setVisible(false);
		error.setVisible(false);
		ErrorWindow.setText("");

		// Check data
		if (StartExamControl.CheckData(Code.getText(), ChooseType.getValue(), ExamID.getText()) != null) {
			Pair<String, String> state = StartExamControl.CheckData(Code.getText(), ChooseType.getValue(),
					ExamID.getText());
			Thread.sleep(200);
			String errorString = state.getValue();
			String theError=state.getKey();
			if(!(getCodeExist().isEmpty())) {
				theError=getCodeExist()+theError;
				errorString=errorString+"Code";
			}
			if (theError.isEmpty()) {
				// creating a new start exam (type to false bc online is defult)
				//exam.setExamIsOut(true);
				StartExam sExam = new StartExam(Code.getText(), false,(Teacher) App.getUser());
				if (ChooseType.getValue().contains("Manual")) {// if the chose manual we change the exam type to true
					sExam.setType(true);
				}
				sExam.setExam(exam);// set the exam for the start exam
				MsgToServer massageMsgToServer = new MsgToServer("StartExam", "Save", sExam,"");
				try {
					SimpleChatClient.getClient().sendToServer(massageMsgToServer);
				} catch (IOException e) {
					System.err.println("Save hane been failed");
				}
				((Teacher) App.getUser()).addStartedExamsList(sExam);// adding the exam to her list of started exam
				 massageMsgToServer= new MsgToServer("", "Update", ((Teacher) App.getUser()), "");// saving the teacher
				SimpleChatClient.getClient().sendToServer(massageMsgToServer);
				App.setMsgAtCenter("The exam start");
				App.setRoot("teacherHomePage");

			}

			else {
				// else report the errors
				if (errorString.contains("Code"))
					CorrectCode.setText("X");
				if (errorString.contains("Type"))
					ChoosedType.setText("X");
				if (errorString.contains("ID"))
					CossedExam.setText("X");
				circle.setVisible(true);
				error.setVisible(true);
				ErrorWindow.setText(theError);
			}
		}
	}

	public  /*static*/ void ExamNotUnique(String string, String string2) {
		CorrectCode.setText("X");
		ErrorWindow.setText(string);
	}

	/***** getters *****/

	public TextField getCode() {
		return this.Code;
	}

	public ChoiceBox<String> getChooseType() {
		return this.ChooseType;
	}

	public TextField getExamID() {
		return this.ExamID;
	}

	public ScrollPane getDisplayAllExams() {
		return this.DisplayAllExams;
	}

	public Text getCorrectCode() {
		return CorrectCode;
	}

	public Text getCossedExam() {
		return this.CossedExam;
	}

	public Text getChoosedType() {
		return this.ChoosedType;
	}

	public Text getErrorWindow() {
		return ErrorWindow;
	}

	public Circle getCircle() {
		return circle;
	}

	public Text getError() {
		return error;
	}

	/***** setters *****/

	public void setCode(TextField code) {
		this.Code = code;
	}

	public void setExamID(TextField examID) {
		this.ExamID = examID;
	}

	public void setDisplayAllExams(ScrollPane displayAllExams) {
		this.DisplayAllExams = displayAllExams;
	}

	public void setCorrectCode(Text correctCode) {
		CorrectCode = correctCode;
	}

	public void setCossedExam(Text cossedExam) {
		this.CossedExam = cossedExam;
	}

	public void setChoosedType(Text choosedType) {
		this.ChoosedType = choosedType;
	}

	public void setChooseType(ChoiceBox<String> chooseType) {
		this.ChooseType = chooseType;
	}

	public void setErrorWindow(Text errorWindow) {
		ErrorWindow = errorWindow;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	public void setError(Text error) {
		this.error = error;
	}

	
}



