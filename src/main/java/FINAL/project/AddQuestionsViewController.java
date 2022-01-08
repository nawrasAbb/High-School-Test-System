package FINAL.project;
import FINAL.project.Question;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
public class AddQuestionsViewController {

	private Question question=new Question();   
	@FXML
    private CheckBox Choose;
	@FXML
    private Label QuestionContent;
    @FXML
    private Text IsChoosed;
    @FXML
    private Text QuestionNumber;

    
    
    
    public CheckBox getChoose() {
		return Choose;
	}
	
    public void setChoose(CheckBox choose) {
		Choose = choose;
	}

	public Label getQuestionContent() {
		return this.QuestionContent;
	}
	
	public void setQuestionContent(Label questionContent) {
		QuestionContent=questionContent;
	}


	public Text getIsChoosed() {
		return this.IsChoosed;
	}
	public void setIsChoosed(String state) {
		IsChoosed.setText(state);
	}

	public Text getQuestionNumber() {
		return this.QuestionNumber;
	}
	
	public void setQuestionNumber(Text questionNumber) {
		QuestionNumber=questionNumber;
	}


    public Question getQuestion() {
		return question;
	}
	
    public void setQuestion(Question question) {
		this.question = question;
	}


}