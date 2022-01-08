 package FINAL.project;
import java.io.IOException;

import javafx.util.Pair;

public class CreateQuestion {
	
	static Subject subject=new Subject();
	static String incorrectFields=new String();
	static String theError=new String();
	
	
	
	
	
	public static Subject getSubject() {
		return subject;
	}


	public static void setSubject(Subject subject) {
		CreateQuestion.subject = subject;
	}


	public static void CheckIfLegal(String ID,String Description,String Answe1,String Answe2,String Answe3,String Answe4,
			String CorrectAnswer) throws InterruptedException {
		theError="";	
		incorrectFields="";
		if(ID.isEmpty()) {
			theError=theError+"The code field id an empty\n";
			incorrectFields=incorrectFields+"ID";
		
		}
		else if(ID.matches("[0-9]+")==false || ID.length()!=5) {
			theError=theError+"The code field must contain 5 digits\n";
			incorrectFields=incorrectFields+"ID";
		}
		else{
			
			MsgToServer massageMsgToServer = new MsgToServer("Subject", "Get", ID.substring(0, 2),"Check subject for create question");
			try {
				SimpleChatClient.getClient().sendToServer(massageMsgToServer);
			} catch (IOException e) {
				System.out.println("msg not sent ");
				e.printStackTrace();
			}
			
			//System.out.println("The subject is    "+ subject.getSubject_id());
		   
			 massageMsgToServer = new MsgToServer("Question", "Get", ID,"Check unique for create question");
			try {
				SimpleChatClient.getClient().sendToServer(massageMsgToServer);
			} catch (IOException e) {
				System.out.println("msg not sent ");
				e.printStackTrace();
			}
			Thread.sleep(700);
		}
		
			
	//	}
		
//	public static void Check2(){
		
		
		
		if(Description.isEmpty()) {
			theError=theError+"Discription field is an empty\n";
			incorrectFields=incorrectFields+"Description";
		}
		if(Answe1.isEmpty()) {
			theError=theError+"Answer 1 is empty\n";
			incorrectFields=incorrectFields+"Answer1";
		}
		if(Answe2.isEmpty()) {
			theError=theError+"Answer 2 is empty\n";
			incorrectFields=incorrectFields+"Answer2";
		}
		if(Answe3.isEmpty()) {
			theError=theError+"Answer 3 is empty\n";
			incorrectFields=incorrectFields+"Answer3";
		}
		if(Answe4.isEmpty()) {
			theError=theError+"Answer 4 is empty\n";
			incorrectFields=incorrectFields+"Answer4";
		}
		if(CorrectAnswer.isEmpty()) {
			theError=theError+"Correct answer is an empty\n";
			incorrectFields=incorrectFields+"CorrectAnswer";
		}
		else if(CorrectAnswer.matches("[0-9]+")==false) {
			theError=theError+"Invalid correct answer\n";
			incorrectFields=incorrectFields+"CorrectAnswer";
		}
		else if(Integer.parseInt(CorrectAnswer)<1 || Integer.parseInt(CorrectAnswer)>4) {
			theError=theError+"The correct answer must be a number between 1 and 4\n";
			incorrectFields=incorrectFields+"CorrectAnswer";
		}
		
			
	
		 Pair<String, String> state= new Pair<>(theError, incorrectFields);
		CreateQuestionInterfaceController.setPair(state);
	}
	
	
	public static void createQuestion(String id,String description,String answer1,String answer2,String answer3,String answer4,String correctAnswer){
		
		// make a question & save it
		
		Question question =new Question((Integer.parseInt(id)),description, subject);
		question.AddAnswer(answer1);
		question.AddAnswer(answer2);
		question.AddAnswer(answer3);
		question.AddAnswer(answer4);
		question.setSubject(subject);
		question.setCorrectAnswer(Integer.parseInt(correctAnswer));
		
		MsgToServer massageMsgToServer2 = new MsgToServer("", "Save",question ,"");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer2);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
	
	}
}
