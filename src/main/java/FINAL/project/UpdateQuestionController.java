package FINAL.project;

import java.io.IOException;
import FINAL.project.Question;
import FINAL.project.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UpdateQuestionController {

	@FXML
	private AnchorPane errorF;

	@FXML
	private TextField Question_ID;

	public TextField getDescription() {
		return Description;
	}

	public void setDescription(TextField description) {
		Description = description;
	}

	static Subject subject;
    @FXML
    private Button update;
    
	@FXML
	private TextField Description;

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
	private Label Error;

	@FXML
	private Label ErrorID;

	@FXML
	private TextField newid;

	@FXML
	void Back(ActionEvent event) throws IOException {
		App.setRoot("teacherHomePage");
	}

	@FXML
	void UpdateQuestion(ActionEvent event) throws IOException, InterruptedException {
		String name;
		// int flag=0;
		String id = newid.getText();
		if (id.isEmpty())
			App.setCenter("ID is null,please enter ID!");
		else {
			if (id.length() != 5)
				App.setCenter("ID must be from 5 digit!");
			else {
				if (id.matches("[0-9]+") == false)
					App.setCenter("Please enter only digit!");
				else if( !(CorrectAnswer.getText().matches("[1-4]"))) {
					App.setCenter("CorrectAnswer must be a valid number (1-4)!");
				}
				else {
					String subject_id = id.substring(0, 2);
					MsgToServer massageMsgToServer = new MsgToServer("Subject", "Get", Integer.parseInt(subject_id),
							"updateQ");
					System.out.println("subject_id");
					try {
						SimpleChatClient.getClient().sendToServer(massageMsgToServer);
					} catch (IOException e) {
						System.out.println("msg not sent ");
						e.printStackTrace();
					}
					Thread.sleep(1000);
					if (subject == null) {
						App.setCenter("Subject is not found!");
					} else {
						
						// flag=1;
						
						massageMsgToServer = new MsgToServer("Question", "Get", Integer.parseInt(id), "updateQ");
						try {
							SimpleChatClient.getClient().sendToServer(massageMsgToServer);
						} catch (IOException e) {
							System.out.println("msg not sent ");
							e.printStackTrace();
						}
						Thread.sleep(700);
						if (myQuestion!= null) {
							App.setCenter("ID already exist!");
						} else {
						//	Subject mySubject = new Subject(Integer.parseInt(subject_id), name);
							Question newQuestion = new Question(Integer.parseInt(newid.getText()),
									Description.getText(), App.getSubject_());
							newQuestion.setAnswer(0, Answe1.getText());
							newQuestion.setAnswer(1, Answe2.getText());
							newQuestion.setAnswer(2, Answe3.getText());
							newQuestion.setAnswer(3, Answe4.getText());
							newQuestion.setCorrectAnswer(Integer.parseInt(CorrectAnswer.getText()));
							//newQuestion.setQuestion_id(Integer.parseInt(newid.getText()))

							massageMsgToServer = new MsgToServer(" ", "Save",newQuestion,"");
							//MsgToServer massageMsgToServer2 = new MsgToServer(" ", "Update",newQuestion,"");
							try {

							//	SimpleChatClient.getClient().sendToServer(massageMsgToServer2);
								SimpleChatClient.getClient().sendToServer(massageMsgToServer);
							} catch (IOException e) {
								System.out.println("msg not sent ");
								e.printStackTrace();
							}
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Question Updated");
							alert.setHeaderText(null);
							alert.setContentText("you have succssefully updated the Question! ");
							alert.showAndWait();
							try {
								App.setRoot("teacherHomePage");
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
	static Question myQuestion;
	@FXML
	void showQuestion(ActionEvent event) throws IOException, InterruptedException {
		update.setDisable(false);
		String id_string = Question_ID.getText();
		if (Question_ID.getText().isEmpty())
			{App.setCenter("ID is null,please enter ID!");
			Description.setText("");
			Answe1.setText("");
			Answe2.setText("");
			Answe3.setText("");
			Answe4.setText("");
			CorrectAnswer.setText("");
			update.setDisable(true);}
		else {
			if (id_string.length() != 5)
			{	App.setCenter("Question ID must be 5 digits!");
				Description.setText("");
				Answe1.setText("");
				Answe2.setText("");
				Answe3.setText("");
				Answe4.setText("");
				CorrectAnswer.setText("");
				update.setDisable(true);}
			else {
				if (id_string.matches("[0-9]+") == false)
				{	App.setCenter("Please enter only digit!");
					Description.setText("");
					Answe1.setText("");
					Answe2.setText("");
					Answe3.setText("");
					Answe4.setText("");
					CorrectAnswer.setText("");
					update.setDisable(true);}
				else {
					int Qid = Integer.parseInt(Question_ID.getText());
				
						MsgToServer massageMsgToServer = new MsgToServer("Question", "Get", Qid,"updateQ");
						try {
							SimpleChatClient.getClient().sendToServer(massageMsgToServer);
						} catch (

						IOException e) {
							System.out.println("msg not sent ");
							e.printStackTrace();
						}
						Thread.sleep(700);
						if (myQuestion != null) {
							Description.setText(myQuestion.getContent());
							Answe1.setText(myQuestion.getAnswers(0));
							Answe2.setText(myQuestion.getAnswers(1));
							Answe3.setText(myQuestion.getAnswers(2));
							Answe4.setText(myQuestion.getAnswers(3));
							CorrectAnswer.setText(Integer.toString(myQuestion.getCorrectAnswer()));
							return;
						} else if (myQuestion==null){
							App.setCenter("Question ID does not exist!");
							Description.setText("");
							Answe1.setText("");
							Answe2.setText("");
							Answe3.setText("");
							Answe4.setText("");
							CorrectAnswer.setText("");
							update.setDisable(true);
						}
					}
				}
			}
		}
		
	
}
