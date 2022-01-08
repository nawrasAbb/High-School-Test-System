 package FINAL.project;
import java.io.IOException;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Pair;
public class CreateQuestionInterfaceController {

    @FXML
    private TextField Question_ID;

    @FXML
    private TextField Answe1;

    @FXML
    private TextField Answe2;

    @FXML
    private TextField Answe3;

    @FXML
    private TextField Answe4;

    @FXML
    private TextField CorrectAnswer;


    @FXML
    private TextArea Description;

 
    @FXML
    private  Text ErrorID;
   


	@FXML
    private Text ErrorAns2;

    @FXML
    private Text ErrorAns1;

    @FXML
    private Text ErrorDescription;

    @FXML
    private Text ErrorAns3;

    @FXML
    private Text ErrorAns4;

    @FXML
    private Text ErrorCorrectAns;

    @FXML
    private Text Errors;
    
    @FXML
    private Circle circle;

    @FXML
    private Text ErrorInCircle;

    String errorID2="";
    static Pair<String,String> pair=new Pair<String, String>(new String(),new String());
    
  static  public Pair<String, String> getPair() {
		return pair;
	}

  static public void setPair(Pair<String, String> pair1) {
		pair = pair1;
	}

	@FXML
    void Back(ActionEvent event) throws IOException {
    	App.setRoot("teacherHomePage");
    }

    @FXML
    void PerformCreateQuestion(ActionEvent event) throws IOException, InterruptedException {
    	circle.setVisible(false);
		ErrorInCircle.setVisible(false);
    	ErrorAns1.setText("");
    	ErrorAns2.setText("");
    	ErrorAns3.setText("");
    	ErrorAns4.setText("");
    	ErrorCorrectAns.setText("");
    	ErrorDescription.setText("");
    	ErrorID.setText("");
    	 CreateQuestion.CheckIfLegal(Question_ID.getText(),Description.getText(),Answe1.getText(),Answe2.getText(),Answe3.getText(),Answe4.getText(),
    												CorrectAnswer.getText());
    	 
    	 if(pair.getKey().isEmpty()) {
     		App.setMsgAtCenter("Added Successfully");
     		App.setRoot("teacherHomePage");
     		CreateQuestion.createQuestion(Question_ID.getText(),Description.getText(),Answe1.getText(),Answe2.getText(),Answe3.getText(),Answe4.getText(),
					CorrectAnswer.getText());
 	
     	}
     	else {
     		
     		 ErrorAns1.setText("");
 	     	ErrorAns2.setText("");
 	     	ErrorAns3.setText("");
 	     	ErrorAns4.setText("");
 	     	ErrorCorrectAns.setText("");
 	     	ErrorDescription.setText("");
  		String theerrorString=pair.getValue();
System.out.println(theerrorString+"   vvvvvvvvvvvvvvvvvvvvvvvvv");

     		if(theerrorString.contains("ID")) {  ErrorID.setText("X");}
     		if(theerrorString.contains("Description")) ErrorDescription.setText("X");
     		if(theerrorString.contains("Answer1")) ErrorAns1.setText("X");
     		if(theerrorString.contains("Answer2")) ErrorAns2.setText("X");
     		if(theerrorString.contains("Answer3")) ErrorAns3.setText("X");
     		if(theerrorString.contains("Answer4")) ErrorAns4.setText("X");
     		if(theerrorString.contains("CorrectAnswer")) ErrorCorrectAns.setText("X");
 			App.setErrorAtCenter("Error! Incorrect data.");
 			circle.setVisible(true);
 			ErrorInCircle.setVisible(true);
 			Errors.setText(pair.getKey());
 			theerrorString="";
 			
 		}
    	
     	
    }
 

	
}