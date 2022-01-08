package FINAL.project;

import FINAL.project.Student;
import FINAL.project.AnswerSheet;
import FINAL.project.CheckExam;
import FINAL.project.Question;
import FINAL.project.Person;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ConfirmGradesInterface {

	@FXML // fx:id="ChangeGrade"
	private Button ChangeGrade; // Value injected by FXMLLoader

	@FXML // fx:id="Confirm"
	private Button Confirm; // Value injected by FXMLLoader

	@FXML // fx:id="ChangeExplination"
	private TextField ChangeExplination; // Value injected by FXMLLoader

	@FXML // fx:id="NewGrade"
	private   TextField NewGrade; // Value injected by FXMLLoader

	@FXML // fx:id="Notes"
	private  TextField Notes; // Value injected by FXMLLoader

	@FXML // fx:id="ShowAnswers"
	private TextArea ShowAnswers; // Value injected by FXMLLoader

	@FXML // fx:id="IdLabel"
	private Label IdLabel; // Value injected by FXMLLoader

	@FXML // fx:id="GradeLabel"
	private  Label GradeLabel; // Value injected by FXMLLoader
	@FXML // fx:id="Home"
	private Button Home; // Value injected by FXMLLoader

	@FXML // fx:id="NextStudent"
	private Button NextStudent; // Value injected by FXMLLoader

	@FXML // fx:id="BackCheckAndConfirm"
	private Button BackCheckAndConfirm; // Value injected by FXMLLoader

	@FXML // fx:id="wrongGrade"
	private  Label wrongGrade; // Value injected by FXMLLoader

	@FXML // fx:id="wrongExp"
	private  Label wrongExp; // Value injected by FXMLLoader

	@FXML
	private  ProgressIndicator confirmDone;


	static String Explination;
	static double Grade = -1;
	static List<AnswerSheet> result = new ArrayList<AnswerSheet>();
	static List<Question> Ques = new ArrayList<Question>();
	static List<Integer> PointsList = new ArrayList<Integer>();
	static Student student = new Student();
	static int SheetIndex = 0;
	int StudentID = 0;
	int SheetSize = 0;

	@FXML
	void SetParamStudent() {

		int AnswerIndex = 0;

		student = result.get(SheetIndex).getTakeExam().getStudent();
		PointsList = result.get(SheetIndex).getExam().getPoints();
		Grade = result.get(SheetIndex).getGrade();
		StudentID = student.getPerson_Id();
		String txt = "Student ID : " + StudentID;

		IdLabel.setText(txt);
		txt = "Grade : " + Double.toString(Grade);
		GradeLabel.setText(txt);

		ShowAnswers.setText("Student Answers: \n");
		for (int i = 0; i < (Ques.size()); i++) {
			if (!(ShowAnswers == null))
				ShowAnswers.appendText("\n Question:\n" + Ques.get(i).getContent() + "\n Points : " + PointsList.get(i)
						+ "\n \ns Answers : \n" + "1) " + Ques.get(i).getAnswers(0) + "\n" + "2) "
						+ Ques.get(i).getAnswers(1) + "\n" + "3) " + Ques.get(i).getAnswers(2) + "\n" + "4) "
						+ Ques.get(i).getAnswers(3) + "\n \n" + "The Correct Answer :" + Ques.get(i).getCorrectAnswer()
						+ "\n \n " + "Submitted Answer: " + result.get(SheetIndex).getAnswersList().get(AnswerIndex)
						+ "\n");
			AnswerIndex++;
		}
		SheetIndex++;

	}

	public List<Integer> getPointsList() {
		return PointsList;
	}

	public void setPointsList(List<Integer> pointsList) {
		PointsList = pointsList;
	}

	@FXML
	void BackToCheck(ActionEvent event) {
		try {
			App.setRoot("CheckAndConfirmGrades");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void BackHome(ActionEvent event) {
		try {
			App.setRoot("TeacherHomePage");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static public AnswerSheet AS;

	@FXML
	void ChangeGrade(ActionEvent event) throws InterruptedException {

		Explination = ChangeExplination.getText();

		if (NewGrade.getText().isEmpty()) {
			try {

				App.setCenter("Please Enter new Grade & Explination!");
				wrongGrade.setText("X");
				wrongExp.setText("X");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (NewGrade.getText().matches("[0-9]+.[0-9]+") == false
				&& NewGrade.getText().matches("[0-9]+") == false) {
			try {

				App.setCenter("Please Enter a valid Grade!");
				wrongGrade.setText("X");
				wrongExp.setText(" ");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Grade = Double.parseDouble(NewGrade.getText());

			if ((Grade < 0) || (Grade > 100)) {
				try {

					App.setCenter("Please Enter a valid Grade!");
					wrongGrade.setText("X");
					wrongExp.setText(" ");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (Explination.isEmpty()) {
				try {

					App.setCenter("In order to change the grade , you need to add an explination!");
					wrongExp.setText("X");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			else {
	            System.out.println(StudentID);
				CheckExam.getAnswerSheet(StudentID,0);
				if(AS==null)
					System.out.println("AS is null");

				
			}
		}
		
		
		wrongGrade.setText(" ");
		wrongExp.setText(" ");
		Grade = Double.parseDouble(NewGrade.getText());
		if(AS==null)
			System.out.println("AS is null");
		CheckExam.changeGrade(Grade, Explination, AS);
		result.get(SheetIndex - 1).setConfirmed(false);
		confirmDone.setProgress(0);
		GradeLabel.setText("Grade : " + Grade);
	}

	@FXML
	void ConfirmGrade(ActionEvent event) throws InterruptedException {

		confirmDone.setProgress(100.0);
		result.get(SheetIndex - 1).setConfirmed(true);
		CheckExam.getAnswerSheet(StudentID,1);
		Thread.sleep(1000);
		if(AS==null)
			System.out.println("AS is null");
		CheckExam.confirmGrade(Notes.getText(), AS);
		wrongGrade.setText(" ");
		wrongExp.setText(" ");
	}

	@FXML
	void Next(ActionEvent event) {

		{
			if (result.get(SheetIndex - 1).getIsConfirmed() == false) {
				try {
					App.setCenter("Please confirm the grade .before moving on to the next student!");
					wrongGrade.setText(" ");
					wrongExp.setText(" ");
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {
				if (SheetIndex < SheetSize) {
					NewGrade.setText(" ");
					ChangeExplination.setText(" ");
					Notes.setText(" ");
					confirmDone.setProgress(0);
					SetParamStudent();
				} else {
					result.get(0).getStartExam().SetIsChecked(true);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("ALL DONE - EXAM CODE : " + CheckExam.getStartExam().getCode());
					alert.setHeaderText(null);
					alert.setContentText("you have finished confirming all the grades for this exam! ");
					alert.showAndWait();
					try {
						App.setRoot("CheckAndConfirmGrades");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	static int setInitParam(ConfirmGradesInterface confirm2) throws InterruptedException {
		if (CheckExam.checkIfLegal(0) == 2) {
			return -1;
		}
		
		System.out.println("\n code " +  CheckExam.getStartExam().getCode());
		confirm2.setResult(CheckExam.getAnswerSheets());
		confirm2.setSheetSize(confirm2.getResult().size());
		System.out.println("\n size of reults " + confirm2.getSheetSize());
		if (confirm2.getSheetSize() == 0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ALL DONE - EXAM CODE : " + CheckExam.getStartExam().getCode());
			alert.setHeaderText(null);
			alert.setContentText("you have finished confirming all the grades for this exam! ");
			alert.showAndWait();
			try {
				App.setRoot("CheckAndConfirmGrades");
			} catch (IOException e) {
				e.printStackTrace();
			}

			return -1;
		}
		System.out.println("after first time 1 " );
		confirm2.setQues(confirm2.getResult().get(confirm2.getSheetIndex()).getExam().getQuestions());
		confirm2.setStudent(confirm2.getResult().get(confirm2.getSheetIndex()).getTakeExam().getStudent());
		confirm2.setPointsList(confirm2.getResult().get(confirm2.getSheetIndex()).getExam().getPoints());
		confirm2.setGrade(confirm2.getResult().get(confirm2.getSheetIndex()).getGrade());
		confirm2.setStudentID(confirm2.getStudent().getPerson_Id());
		confirm2.setSheetSize(confirm2.getResult().size());
		System.out.println("before , Sheet Index=" + confirm2.getSheetIndex());
		System.out.println("\n beefore sheet size=  " + confirm2.getSheetSize());

		int AnswerIndex = 0;
		confirm2.setStudent(confirm2.getResult().get(confirm2.getSheetIndex()).getTakeExam().getStudent());
		confirm2.setGrade(confirm2.getResult().get(confirm2.getSheetIndex()).getGrade());
		String txt = "Student ID : " + confirm2.getStudentID();
	
		if (!(confirm2.getIdLabel() == null))
			{confirm2.getIdLabel().setText(txt);}
		txt = "Grade : " + Double.toString(confirm2.getGrade());
		confirm2.setGradeLabel(txt);

		confirm2.getShowAnswers().setText("Student Answers: \n");

		System.out.println("after first time 2 1/2 " );
		for (int i = 0; i < (confirm2.getQues().size()); i++) {
			if (!(confirm2.getShowAnswers() == null)) {
				confirm2.getShowAnswers().appendText("  \n Question:\n" + confirm2.getQues().get(i).getContent()
						+ " \n Points: " + confirm2.getPointsList().get(i) + "\n  \nAnswers : \n" + "1) "
						+ confirm2.getQues().get(i).getAnswers(0) + "\n" + "2) "
						+ confirm2.getQues().get(i).getAnswers(1) + "\n" + "3) "
						+ confirm2.getQues().get(i).getAnswers(2) + "\n" + "4) "
						+ confirm2.getQues().get(i).getAnswers(3) + "\n \n " + "The Correct Answer :"
						+ confirm2.getQues().get(i).getCorrectAnswer() + "\n \n" + "Submitted Answer: "
						+ confirm2.getResult().get(confirm2.getSheetIndex()).getAnswersList().get(AnswerIndex) + "\n");
			AnswerIndex++;

			System.out.println("after first time " + confirm2.getSheetIndex());
		}}
		System.out.println("after first time 3 " );
		confirm2.setSheetIndex(confirm2.getSheetIndex() + 1);
		return 0;
	}

	private int getSheetSize() {
		return this.SheetSize;
	}

	private List<Question> getQues() {
		return Ques;
	}

	private double getGrade() {
		return Grade;
	}

	private int getStudentID() {
		return this.StudentID;
	}

	private void setGrade(double grade2) {
		Grade = grade2;
	}

	private void setStudent(Student student2) {
		student = student2;
	}

	private void setSheetIndex(int i) {
	SheetIndex = i;
	}

	private int getSheetIndex() {
		return SheetIndex;
	}

	private void setSheetSize(int size) {
		this.SheetSize = size;
	}

	private void setStudentID(int personId) {
		this.StudentID = personId;
	}

	private Person getStudent() {
		return student;
	}

	private void setQues(List<Question> questions) {
	Ques = questions;
	}

	private List<AnswerSheet> getResult() {
		return result;
	}

	private void setResult(List<AnswerSheet> answers) {
		result = answers;
	}



	private TextArea getShowAnswers() {
		return this.ShowAnswers;
	}

	public Label getIdLabel() {
		return this.IdLabel;
	}

	public void setIdLabel(Label idLabel) {
		this.IdLabel = idLabel;
	}

	public String getGradeLabel() {
		return GradeLabel.getText();
	}

	public void setGradeLabel(String gradeLabel) {
	GradeLabel.setText(gradeLabel);
	}

}