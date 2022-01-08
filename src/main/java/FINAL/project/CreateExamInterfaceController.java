package FINAL.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import FINAL.project.Question;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;




public class CreateExamInterfaceController {
	private List<AddQuestionsViewController> addQuestionsPages=new ArrayList<AddQuestionsViewController>();
	private  List<GradingViewController> gradesPageControllers=new ArrayList<GradingViewController>();
	private List<Question> choosenQuestions=new ArrayList<Question>();
	private List<Integer> points = new ArrayList<Integer>();

	 @FXML
	    private Button button;

    @FXML
    private TextField Time;

    @FXML
    private ScrollPane DisplayQuesField;

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
    private Button createbutton;


    @FXML
    void finish(ActionEvent event) throws IOException {
App.setRoot("teacherHomePage");
    }


	@FXML
    void performAddQuestion(ActionEvent event) throws IOException {
		
		getCorrectQuestions().setText("");
    	Pair<String, List<Question>> pagesState;
    	pagesState=CreateExam.addQuestionslegality(addQuestionsPages,choosenQuestions);
    	String stateString=pagesState.getKey();
    	setChoosenQuestions(pagesState.getValue());
    	if(stateString=="Successfully added") {
			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully added");
				alert.show();
			});
			updateQuestionsInGradingField();
    	}
    	if(stateString=="There are no Choosed Question") {
    		App.setErrorAtCenter("Error! There are no selected questions");
    	}

	}
    
	

    @FXML
    void PerformShowExam(ActionEvent event) throws IOException, NumberFormatException, InterruptedException {
    	Stage showStage=new Stage();
		Pair<Parent, Object> displayPair=App.getFxmlAndController("DisplayExam");
		CreateExam.DisplayExam(Integer.parseInt(Time.getText()),TeacherName.getText(),choosenQuestions,points,TeacherNotes.getText(),StudentNotes.getText(),
								(DisplayExamController)displayPair.getValue());
		Scene newsceneScene=new Scene(displayPair.getKey());
    	showStage.setScene(newsceneScene);
    	showStage.show();
    }
	
	

    
    
    @FXML
	void performCreateExam(ActionEvent event) throws IOException, InterruptedException {
		boolean IndexTime;
		boolean IndexName;
		boolean IndexPoints;
		boolean Indexquestions;
		String time = Time.getText();
		String teacherName = TeacherName.getText();
		String theError;
		CorrectTime.setText("");
		CorrectTeacherName.setText("");
		CorrectGrades.setText("");
		CorrectQuestions.setText(""); 
		theError = CreateExam.CheckIfLegal(time, teacherName, choosenQuestions, points);
		if (theError.isEmpty()) {
			//CreateExam exam=new CreateExam();
			CreateExam.makeExam(time, teacherName, choosenQuestions, points,TeacherNotes.getText()
								,StudentNotes.getText());
			button.setDisable(false);
			createbutton.setDisable(true);
			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully created");
				alert.show();
			});

			return;
		}
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
		if (IndexTime || IndexName || IndexPoints || Indexquestions)
			App.setErrorAtCenter("Error! Incorrect data.");
	}
    
    
    
    @FXML
    void performRemoveQuestion(ActionEvent event) throws IOException {
    	Pair<String, List<Question>> pagesState;
    	pagesState=CreateExam.RemoveQuestionslegality(addQuestionsPages,choosenQuestions);
    	String stateString=pagesState.getKey();
    	setChoosenQuestions(pagesState.getValue());
    	if(stateString=="Successfully Removed") {
			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully Removed");
				alert.show();
			});
			updateQuestionsInGradingField();
    	}
    	if(stateString=="There are no Choosed Question") {
    		App.setErrorAtCenter("Error! There are no selected questions");
    	}
    }
    

    
    void updateQuestionsInGradingField() throws IOException {
    	VBox box=new VBox();
    	int i=1;
    	gradesPageControllers.clear();
		for(Question ques: choosenQuestions) {
			Pair<Parent, Object> fxmlPair=App.getFxmlAndController("GradingView");
    		GradingViewController controller=(GradingViewController) fxmlPair.getValue();
    		controller.getQuestionNumber().setText("Question Number "+Integer.toString(i));
    		controller.getViewQuestion().setText(ques.getContent()+":\n"+"1. "+ques.getAnswers(0)+".\n"+"2. "+
    					ques.getAnswers(1)+".\n"+"3. "+ques.getAnswers(2)+".\n"+"4. "+ques.getAnswers(3)+".");
    		i++;
    		box.getChildren().add(fxmlPair.getKey());
    		gradesPageControllers.add((GradingViewController)fxmlPair.getValue());
		}
		getGradingField().setContent(box);
		setGradesPageControllers(gradesPageControllers);
    }
    
    
    
	@FXML
    void performSetGrades(ActionEvent event) throws IOException {
		getCorrectGrades().setText("");
		String wrongGrades=CreateExam.CheckGradingfields(gradesPageControllers);
		if(wrongGrades=="") {
			for(GradingViewController page: gradesPageControllers) {
				points.add(Integer.parseInt(page.getPoints().getText()));
			}
			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully Set Grades");
				alert.show();
			});
		}
		else if(wrongGrades=="Incorrect data") {
			getCorrectGrades().setText("X");
			App.setErrorAtCenter("Error! There is illegal grades");
		}
		else if(wrongGrades=="Up to 100"){
			getCorrectGrades().setText("X");
			App.setErrorAtCenter("Error! The amount of grades exceeds 100");
		}
		else {
			getCorrectGrades().setText("X");
			App.setErrorAtCenter("Error! The amount of grades less than 100");
		}
		
		
		
    }    
    





	public List<GradingViewController> getGradesPageControllers() {
		return this.gradesPageControllers;
	}


	public void setGradesPageControllers(List<GradingViewController> gradesPageControllers) {
		this.gradesPageControllers = gradesPageControllers;
	}





    public ScrollPane getGradingField() {
		return this.GradingField;
	}


	public void setGradingField(ScrollPane gradingField) {
		this.GradingField = gradingField;
	}


	public List<Question> getChoosenQuestions() {
		return this.choosenQuestions;
	}

	public void setChoosenQuestions(List<Question> choosenQuestions) {
		this.choosenQuestions = choosenQuestions;
	}


	public List<Integer> getPoints() {
		return this.points;
	}


	public void setPoints(List<Integer> points) {
		this.points = points;
	}


	public TextField getTime() {
		return this.Time;
	}


	public void setTime(TextField time) {
		this.Time = time;
	}


	public TextArea getTeacherNotes() {
		return this.TeacherNotes;
	}


	public void setTeacherNotes(TextArea teacherNotes) {
		this.TeacherNotes = teacherNotes;
	}


	public TextField getTeacherName() {
		return this.TeacherName;
	}


	public void setTeacherName(TextField teacherName) {
		this.TeacherName = teacherName;
	}


	public TextArea getStudentNotes() {
		return this.StudentNotes;
	}


	public void setStudentNotes(TextArea studentNotes) {
		this.StudentNotes = studentNotes;
	}


	public Text getCorrectTime() {
		return this.CorrectTime;
	}


	public void setCorrectTime(Text correctTime) {
		this.CorrectTime = correctTime;
	}


	public Text getCorrectQuestions() {
		return this.CorrectQuestions;
	}


	public void setCorrectQuestions(Text correctQuestions) {
		this.CorrectQuestions = correctQuestions;
	}


	public Text getCorrectGrades() {
		return this.CorrectGrades;
	}


	public void setCorrectGrades(Text correctGrades) {
		this.CorrectGrades = correctGrades;
	}


	public Text getCorrectTeacherName() {
		return this.CorrectTeacherName;
	}


	public void setCorrectTeacherName(Text correctTeacherName) {
		this.CorrectTeacherName = correctTeacherName;
	}




	public ScrollPane getDisplayQuesField() {
		return this.DisplayQuesField;
	}

	public void setDisplayQuesField(ScrollPane displayQuesField) {
		this.DisplayQuesField = displayQuesField;
	}



	public List<AddQuestionsViewController> getAddQuestionsPages() {
		return this.addQuestionsPages;
	}


	public void setAddQuestionsPages(List<AddQuestionsViewController> addQuestionsPages) {
		this.addQuestionsPages = addQuestionsPages;
	}

}






