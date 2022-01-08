package FINAL.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

public class UpdateExamController {
	

	private static List<Integer> points = new ArrayList<Integer>();
	private static List<Question> choosenQuestions = new ArrayList<Question>(); 
	@FXML
	private TextField Time;

	@FXML
	private   ScrollPane DisplayQuesField;

	@FXML
	private ScrollPane GradingField;

	@FXML
	private TextArea TeacherNotes;

	@FXML
	private TextField TeacherName;

	@FXML
	private TextArea StudentNotes;

	@FXML
	private Text CorrectTime;

	@FXML
	private Text CorrectQuestions;

	@FXML
	private Text CorrectGrades;

	@FXML
	private Text CorrectTeacherName;

	@FXML
	private Text CorrectCode;

	@FXML
	private Text SubjectField;

	@FXML
	private Text CourseField;

	@FXML
	private TextField ExamId;
	
	

	

	@FXML
	void performAddQuestion(ActionEvent event) throws IOException {

		getCorrectQuestions().setText("");
		Pair<VBox, String> pagesState;
		pagesState = UpdateExam.addQuestions();
		String stateString = pagesState.getValue();
		if (stateString == "Successfully added") {
			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully updated");
				alert.show();
			});
			getGradingField().setContent(pagesState.getKey());
		}

		if (stateString == "There are no Choosed Question") {
			App.setErrorAtCenter("Error! There are no selected questions");
		}

	}

	@FXML
	void performCreateExam(ActionEvent event) throws IOException, InterruptedException {
		
		boolean IndexTime;
		boolean IndexName;
		boolean IndexPoints;
		boolean Indexquestions;
		boolean IndexCode;
		String time = Time.getText();
		String teacherName = TeacherName.getText();
		Pair<String, List<GradingViewController>> pair =UpdateExam.CheckIfLegal(time, teacherName, getChoosenQuestions(), getPoints(), ExamId.getText());
		String theError=pair.getKey();
		List<GradingViewController> gradesPages=pair.getValue();
		CorrectTime.setText("");
		CorrectTeacherName.setText("");
		CorrectGrades.setText("");
		CorrectQuestions.setText("");
		CorrectCode.setText("");
		System.out.println("the error "+theError);
		if (theError == "") {
			UpdateExam exam=new UpdateExam();
			exam.makeExam(SubjectField.getText()+CourseField.getText()+ExamId.getText(),Time.getText(), TeacherName.getText(), 
								choosenQuestions, points,TeacherNotes.getText(),StudentNotes.getText());
			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully created");
				alert.show();
			});
			App.setRoot("teacherHomePage");
			return;
		}
		IndexCode = theError.contains("Code");
		IndexTime = theError.contains("Time");
		IndexName = theError.contains("Name");
		IndexPoints = theError.contains("Points");
		Indexquestions = theError.contains("Questions");
		if (IndexTime == true)
			CorrectTime.setText("X");
		if (IndexName == true)
			CorrectTeacherName.setText("X");
		if (IndexPoints == true)
			CorrectGrades.setText("X");
		if (Indexquestions == true)
			CorrectQuestions.setText("X");
		if (IndexCode == true)
			CorrectCode.setText("X");
		if (IndexTime || IndexName || IndexPoints || Indexquestions || IndexCode || CorrectGrades.getText().isEmpty()==false)
			App.setErrorAtCenter("Error! Incorrect data.");

	}

	@FXML
	void performRemoveQuestion(ActionEvent event) throws IOException {
		CorrectGrades.setText("X");
		Pair<VBox, String> pagesState;
		pagesState = UpdateExam.RemoveQuestionslegality();
		String stateString = pagesState.getValue();
		if (stateString == "Successfully Removed") {
			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully Removed");
				alert.show();
			});
			getGradingField().setContent(pagesState.getKey());
		}
		if (stateString == "There are no Choosed Question") {
			App.setErrorAtCenter("Error! There are no selected questions");
		}
	}

	@FXML
	void performSetGrades(ActionEvent event) throws IOException, InterruptedException {

		getCorrectGrades().setText("");
		Pair<String, List<GradingViewController>> pair = UpdateExam.CheckGradingfields();
		List<GradingViewController> gradesPageControllers = pair.getValue();
		String wrongGrades = pair.getKey();
		getPoints().clear();
		System.out.println(wrongGrades);
		if (wrongGrades.isEmpty()) {
			for (GradingViewController page : gradesPageControllers) {
				getPoints().add(Integer.parseInt(page.getPoints().getText()));
			}
			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully Set Grades");
				alert.show();
			});
		} else if (wrongGrades == "Incorrect data") {
			getCorrectGrades().setText("X");
			App.setErrorAtCenter("Error! There is illegal grades");
		} else if (wrongGrades == "Up to 100") {
			getCorrectGrades().setText("X");
			App.setErrorAtCenter("Error! The amount of grades exceeds 100");
		} else {
			getCorrectGrades().setText("X");
			App.setErrorAtCenter("Error! The amount of grades less than 100");
		}

	}

    
	
  @FXML
  void Back(ActionEvent event) throws IOException {
        App.setRoot("teacherHomePage");
	
  }  
	
	

	/*********** getters *************/

	public TextField getTeacherName() {
		return this.TeacherName;
	}

	public TextArea getTeacherNotes() {
		return this.TeacherNotes;
	}

	public  ScrollPane getGradingField() {
		return GradingField;
	}

	public ScrollPane getDisplayQuesField() {
		return DisplayQuesField;
	}

	public TextField getTime() {
		return this.Time;
	}

	public Text getCorrectCode() {
		return this.CorrectCode;
	}

	public TextArea getStudentNotes() {
		return this.StudentNotes;
	}

	public Text getCorrectTime() {
		return this.CorrectTime;
	}

	public Text getCorrectQuestions() {
		return this.CorrectQuestions;
	}

	public Text getCorrectGrades() {
		return this.CorrectGrades;
	}

	public Text getCorrectTeacherName() {
		return this.CorrectTeacherName;
	}

	
	public Text getSubjectField() {
		return this.SubjectField;
	}

	public Text getCourseField() {
		return this.CourseField;
	}

	public TextField getExamId() {
		return this.ExamId;
	}

	/*********** setters *************/

	public void setSubjectField(Text subjectField) {
		this.SubjectField = subjectField;
	}

	public void setCourseField(Text courseField) {
		this.CourseField = courseField;
	}

	public void setCorrectCode(Text correctCode) {
		this.CorrectCode = correctCode;
	}

	public void setExamId(TextField examId) {
		this.ExamId = examId;
	}

	public void setCorrectTime(Text correctTime) {
		this.CorrectTime = correctTime;
	}

	public void setCorrectQuestions(Text correctQuestions) {
		this.CorrectQuestions = correctQuestions;
	}

	public void setCorrectGrades(Text correctGrades) {
		this.CorrectGrades = correctGrades;
	}

	public void setCorrectTeacherName(Text correctTeacherName) {
		this.CorrectTeacherName = correctTeacherName;
	}

	public void setTime(TextField time) {
		this.Time = time;
	}

	public  void setDisplayQuesField(ScrollPane displayQuesField) {
		this.DisplayQuesField = displayQuesField;
	}

	public  void setGradingField(ScrollPane gradingField) {
		this.GradingField = gradingField;
	}

	public void setTeacherNotes(TextArea teacherNotes) {
		this.TeacherNotes = teacherNotes;
	}

	public void setTeacherName(TextField teacherName) {
		this.TeacherName = teacherName;
	}

	public void setStudentNotes(TextArea studentNotes) {
		this.StudentNotes = studentNotes;
	}

	public static List<Integer> getPoints() {
		return points;
	}

	public static void setPoints(List<Integer> points) {
		UpdateExamController.points = points;
	}

	public List<Question> getChoosenQuestions() {
		return choosenQuestions;
	}

	public static void setChoosenQuestions(List<Question> choosenQuestions1) {
		choosenQuestions = choosenQuestions1;
	}

	

}