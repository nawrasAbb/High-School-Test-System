 package FINAL.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import FINAL.project.Course;
import FINAL.project.Exam;
import FINAL.project.Question;
import FINAL.project.Subject;
import FINAL.project.Teacher;

import javafx.util.Pair;

public class CreateExam {
	private static List<Question> questions;
	private static List<Integer> points;
	static String exam_id;
	private static Exam exam;
	private static Subject subject;
	private static Course course;
	private static Teacher teacher;
	private  static String teacherNotes ;
	private static  String studentNotes;
	private static List<Question> choosenQuestions1= new ArrayList<Question>();
	private static List<Integer> points1;
	private static String Time;
	
	
	public static  void makeExam(String Time,String TeacherName, List<Question> choosenQuestions, List<Integer> points2,
									String teachernotes,String studentsnotes) throws InterruptedException {
		MsgToServer massageMsgToServer = new MsgToServer("Course", "Get", exam_id.substring(2, 4),"CreateExam");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
		Thread.sleep(1000);
		System.out.println("in sending subject");
		//System.out.println(course.getCourse_id());
		//System.out.println("The uid print    "+ exam_id.substring(0, 2));
		massageMsgToServer = new MsgToServer("Subject", "Get",exam_id.substring(0, 2),"CreateExam");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
		Thread.sleep(1000);
		//System.out.println(subject.getSubject_id());
		Exam newExam=new Exam(Integer.parseInt(exam_id),Double.parseDouble(Time),points2,(Teacher)App.getUser(),
				studentsnotes,teachernotes,subject,course,choosenQuestions);
		massageMsgToServer = new MsgToServer("Exam", "Save", newExam,"CreateExam");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.err.println("Save hane been failed");
		}
		
		((Teacher)App.getUser()).addWrittenExamsList(newExam);
		MsgToServer massageMsgToServer1 = new MsgToServer("", "Update", (Teacher)App.getUser(), "");

		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer1);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
		System.out.println("After save function /////////////////////////////////////////////////////////");
	}
	
	
	
	public static String CheckIfLegal(String Time,String TeacherName, List<Question> choosenQuestions, List<Integer> points2) {
		int ErrorInTime=0;
		int ErrorInName=0;
		int ErrorInQuesList=0;
		int ErrorInPointsList=0;
		String ErrorMsg="";
		String spaString=TeacherName.replaceAll("\\s", "");
		char[] chars=spaString.toCharArray();
		if(Time.isEmpty()==true) ErrorInTime=1;
		else if(Time.matches("[0-9]+")==false) ErrorInTime=1;
		else if(Integer.parseInt(Time)<=0) ErrorInTime=1;
		if(TeacherName.isEmpty()==true) ErrorInName=1;
		else{
			for (char c : chars) {
				if(!Character.isLetter(c) && Character.toString(c)!="") {
					ErrorInName=1;
				}
			}
		}
	    if(choosenQuestions.isEmpty()==true) ErrorInQuesList=1;
	    if(points2.isEmpty()==true) ErrorInPointsList=1;
	    
	    if(ErrorInTime==1) 
	    	ErrorMsg=ErrorMsg+"Time";
	    if(ErrorInName==1 ) 
	    	ErrorMsg=ErrorMsg+" + Name";
	    if(ErrorInPointsList==1) 
	    	ErrorMsg=ErrorMsg+" + Points";
	    if(ErrorInQuesList==1) 
	    	ErrorMsg=ErrorMsg+" + Questions";
	    return ErrorMsg;

	}
	
	
	
	


	public static void checkExamID_c(String exam_id1) throws InterruptedException, IOException {
		setExamID(exam_id1);
		if (exam_id.isEmpty() == true)
			App.setErrorAtCenter("An empty code");
		else if (exam_id.matches("[0-9]+") == false)
			App.setErrorAtCenter("The Id must contain 6 digits");
		else if (exam_id.length() != 6)
			App.setErrorAtCenter("The Id must contain 6 digits");
		else 	
			{
			System.out.println("Exam id "+exam_id);
			int course_id = Integer.parseInt(exam_id.substring(2, 4));
			System.out.println("course_id id "+course_id);
			MsgToServer massageMsgToServer = new MsgToServer("Course", "Get", course_id, "create exam check course");
			try {
				SimpleChatClient.getClient().sendToServer(massageMsgToServer);
			} catch (IOException e) {
				System.out.println("msg not sent ");
				e.printStackTrace();
			}

		}
	}

	static public void checkExamID_S() throws InterruptedException, IOException {
		int subject_id = Integer.parseInt(exam_id.substring(0, 2));
		MsgToServer massageMsgToServer = new MsgToServer("Subject", "Get", subject_id, "create exam check subject");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
	}

	public static void checkExamID_E() throws InterruptedException, IOException {

		MsgToServer massageMsgToServer = new MsgToServer("Exam", "Get",Integer.parseInt(exam_id), "create exam check unique");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
		System.out.println("Yes ID");
		massageMsgToServer = new MsgToServer("Question", "Get", "ALL", "GETALL Q for create Exam");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}

	}

	public static String getExamID() {
		return (exam_id);
	}

	public static void setExamID(String exam_id1) {
		exam_id = (exam_id1);
	}

	public Exam getExam() {
		return exam;
	}

	public static void setExam(Exam exam1) {
		exam = exam1;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions2) {
		questions = questions2;
	}

	public List<Integer> getPoints() {
		return points;
	}

	public void setPoints(List<Integer> points2) {
		points = points2;
	}

	public static Pair<String, List<Question>> addQuestionslegality(List<AddQuestionsViewController> AddQuestionspages,
			List<Question> choosedQuestion) {
		int numberOfChoosedQues = 0;
		for (AddQuestionsViewController page : AddQuestionspages) {
			if (page.getChoose().isSelected() == true) {
				numberOfChoosedQues++;
				if (page.getIsChoosed().getText() != "V") {
					choosedQuestion.add(page.getQuestion());
					page.getIsChoosed().setText("V");
				}
			}
			page.getChoose().setSelected(false);
		}
		if (numberOfChoosedQues == 0)
			return new Pair<>("There are no Choosed Question", choosedQuestion);
		return new Pair<>("Successfully added", choosedQuestion);
	}

	public static Pair<String, List<Question>> RemoveQuestionslegality(
			List<AddQuestionsViewController> AddQuestionspages, List<Question> choosedQuestion) {
		int numberOfChoosedQues = 0;
		for (AddQuestionsViewController page : AddQuestionspages) {
			if (page.getChoose().isSelected() == true) {
				numberOfChoosedQues++;
				if (page.getIsChoosed().getText() == "V") {
					choosedQuestion.remove(page.getQuestion());
					page.getIsChoosed().setText("");
				}
			}
			page.getChoose().setSelected(false);
		}
		if (numberOfChoosedQues == 0)
			return new Pair<>("There are no Choosed Question", choosedQuestion);
		return new Pair<>("Successfully Removed", choosedQuestion);

	}

	public static String CheckGradingfields(List<GradingViewController> gradesList) {
		int totalGrade = 0;
		String wrongString = "";
		for (GradingViewController page : gradesList) {
			page.getInCorrect().setText("");
			if (page.getPoints().getText() == "") {
				page.getInCorrect().setText("X");
				wrongString = "Incorrect data";
			} else if (page.getPoints().getText().matches("[0-9]+") == false) {
				page.getInCorrect().setText("X");
				wrongString = "Incorrect data";
			} else if (Integer.parseInt(page.getPoints().getText()) <= 0) {
				page.getInCorrect().setText("X");
				wrongString = "Incorrect data";
			} else if (Integer.parseInt(page.getPoints().getText()) > 100) {
				page.getInCorrect().setText("X");
				wrongString = "Incorrect data";
			} else if ((Integer.parseInt(page.getPoints().getText()) > 0)
					&& Integer.parseInt(page.getPoints().getText()) <= 100) {
				totalGrade += Integer.parseInt(page.getPoints().getText());
			}
		}
		if (wrongString != "Incorrect data" && totalGrade != 100) {
			if (totalGrade > 100)
				wrongString = "Up to 100";
			if (totalGrade < 100)
				wrongString = "Down to 100";
		}
		return wrongString;
	}

	public static void DisplayExam(int time, String TeacherName, List<Question> choosenQuestions, List<Integer> points2,
			String TeacherNotes, String StudentNotes, DisplayExamController object) throws InterruptedException {
		int i=1;
		String initString = "Exam Structure '_'\n " + "Time: " + Time + "\n\n" + "Teacher Name: " + TeacherName + "\n\n"
				+ "Notes For Teacher: " + TeacherNotes + "\n\n" + "Notes For Student: " + StudentNotes + "\n\n";
		int index = 0;
		String queString = "";
		for (Question question : choosenQuestions) {
			queString +="Question number "+i+":\n"+ question.getContent() + "\n" + "1. " + question.getAnswers(0) + ".\n" + "2. "
					+ question.getAnswers(1) + ".\n" + "3. " + question.getAnswers(2) + ".\n" + "4. "
					+ question.getAnswers(3) + "." + "\n" + "Points: " + points2.get(index) + "\n\n";
			index++;
			i++;
		}
		String finishString = initString + queString;
		object.getViewField().setText(finishString);

	
	
	}
		
	static void createActualExam()	
		{
		
		/*System.out.println("in creating");
		Exam exam = new Exam(Integer.parseInt(exam_id),Double.parseDouble(Time), points1, teacher, studentNotes, teacherNotes,
				subject, course, choosenQuestions1);
		setExam(exam);
		((Teacher)App.getUser()).addWrittenExamsList(exam);
		System.out.println("saving new Exam");
		MsgToServer massageMsgToServer1 = new MsgToServer("", "Save", exam,"");
		MsgToServer massageMsgToServer2 = new MsgToServer("", "Update", teacher,"");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer1);
			SimpleChatClient.getClient().sendToServer(massageMsgToServer2);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}*/

	}

	

	public static Subject getSubject() {
		return subject;
	}

	public static void setSubject(Subject subject1) {
		subject = subject1;
	}

	public static Course getCourse() {
		return course;
	}

	public static void setCourse(Course course1) {
		System.err.println("im in setcourse   "+ course1.getCourse_id());
		course = course1;
		System.err.println("im in getcourse   "+ getCourse().getCourse_id());
	}
	
	
	

}
