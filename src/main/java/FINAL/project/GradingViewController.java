package FINAL.project;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class GradingViewController {

	private Question question;
	
    @FXML
    private Label QuestionNumber;

	@FXML
    private TextField Points;

    @FXML 
    private Text viewQuestion;

    @FXML
    private Label InCorrect;
    
    private int grade;
    
    
    
    
    public int getGrade() {
		return this.grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Label getQuestionNumber() {
		return this.QuestionNumber;
	}

	public void setQuestionNumber(Label questionNumber) {
		this.QuestionNumber = questionNumber;
	}

	public TextField getPoints() {
		return this.Points;
	}

	public void setPoints(TextField points) {
		this.Points = points;
	}

	public Text getViewQuestion() {
		return this.viewQuestion;
	}

	public void setViewQuestion(Text viewQuestion) {
		this.viewQuestion = viewQuestion;
	}

	public Label getInCorrect() {
		return this.InCorrect;
	}

	public void setInCorrect(Label inCorrect) {
		this.InCorrect = inCorrect;
	}

	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	
	

}

