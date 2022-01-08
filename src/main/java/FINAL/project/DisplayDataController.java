package FINAL.project;
import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.util.Pair;

public class DisplayDataController {
	
	    @FXML
	    void PrintQuestions(ActionEvent event) throws IOException, InterruptedException {
	    	MsgToServer massageMsgToServer = new MsgToServer("Subject", "Get", "ALL","init Subjects Principle");
			try {
				SimpleChatClient.getClient().sendToServer(massageMsgToServer);
			} catch (IOException e) {
				System.out.println("msg not sent ");
				e.printStackTrace();
			}}
			
	    	
			
		static void ShowQuestions(List<Subject> myobject) throws IOException, InterruptedException{
	    	Pair<Parent,Object> pair=App.getFxmlAndController("DisplayQuestions");
	    	App.setRoot2(pair.getKey());
	    	DisplayQuestionsController displayQuestions=(DisplayQuestionsController)pair.getValue();
	    	ControlsubWindow.InitSubject(myobject,displayQuestions);
	    	App.setRoot2(pair.getKey());
	    }
	    
	    @FXML
	    void printExams(ActionEvent event) throws IOException, InterruptedException {
	    	
	    	MsgToServer massageMsgToServer1 = new MsgToServer("Exam", "Get", "ALL","init Exams Principle");
			try {
				SimpleChatClient.getClient().sendToServer(massageMsgToServer1);
			} catch (IOException e) {
				System.out.println("msg not sent ");
				e.printStackTrace();
			}
			}
	    
	    static void ShowExams(List<Exam> myobject) throws IOException, InterruptedException{
	    	
	    	Pair<Parent,Object> pair=App.getFxmlAndController("DisplayExams");
	    	App.setRoot2(pair.getKey());
	    	DisplayExamsController displayExams=(DisplayExamsController)pair.getValue();
	    	ControlsubWindow.InitExam(myobject, displayExams);
	    	App.setRoot2(pair.getKey());	
	    	
	    }

	    @FXML
	    void printResult(ActionEvent event) throws IOException, InterruptedException {
	    	
	    	MsgToServer massageMsgToServer = new MsgToServer("Exam", "Get", "ALL","init Results Principle");
			try {
				SimpleChatClient.getClient().sendToServer(massageMsgToServer);
			} catch (IOException e) {
				System.out.println("msg not sent ");
				e.printStackTrace();
			}

	    }
	    static void ShowResults(List<Exam> myobject) throws IOException, InterruptedException{
	    	Pair<Parent,Object> pair=App.getFxmlAndController("DisplayResults");
	    	App.setRoot2(pair.getKey());
	    	DisplayResultsController displayRes=(DisplayResultsController)pair.getValue();
	    	ControlsubWindow.InitResults(myobject,displayRes);
	    	App.setRoot2(pair.getKey());	
	    }

	    @FXML
	    void logout(ActionEvent event) throws IOException {
	    	App.setRoot("LogIn");

	    }
	    @FXML
	    void backtohome(ActionEvent event) throws IOException {
	    	App.setRoot("principleHomePage");
	    }
}