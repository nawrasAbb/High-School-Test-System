package FINAL.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TakeExamControl {
	private static StartExam startExam22=new StartExam();
	
	
	
	
	
	public static StartExam getStartExam22() {
		return startExam22;
	}

	public static void setStartExam22(StartExam startExam) {
		startExam22 = startExam;
	}

	public static void TakeExamAction(StartExam mystartExam) {

		mystartExam.addSelfSubmittedNumber();
		Student student = (Student) App.getUser();
		TakeExam takeExam = new TakeExam(false, student);
		mystartExam.addTakeExam(takeExam);
		takeExam.setStudent(student);
		
		OnlineExamInterface.setStudent(student);
		OnlineExamInterface.setTakeExam(takeExam);
		OnlineExamInterface.setMystartExam(mystartExam);
	}

	public static void CheckIfLegal(String text) throws InterruptedException {
		// check if code exist && if time is not over else return -1
		// check type - online return 0 , manual return 1
		MsgToServer massageMsgToServer = new MsgToServer("TakeExam", "Get", "All", "Check Code takeExam");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
		 massageMsgToServer = new MsgToServer("StartExam", "Get", text, "Check Code takeExam");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println(" msg not sent ");
			e.printStackTrace();
		}
		

	}

	public static int ChecklegalId(String text, String given_idS) throws InterruptedException {
		Student student = ((Student) App.getUser());
		int given_course_id = Integer.parseInt(given_idS);
		if (text.isEmpty()) {
			return -1;

		}
		int found = 0;
		if (text.matches("[0-9]+") == false) {
			return -1;
		}

		// Checkif ID exist and is in the course's List
		int id = Integer.parseInt(text);
		if (App.getUser().getPerson_Id() != id) {
			return -1;
		}
		List<Course> wantedCourse = new ArrayList<Course>();
		wantedCourse = student.getCourses();
		for (Course tempCourse : wantedCourse) {
			if (tempCourse.getCourse_id() == given_course_id) {
				found = 1;
			}

		}
		if (found == 0) {
			return -3;
		}

		// Check if he already took the exam
		List<TakeExam> takenExams = new ArrayList<TakeExam>();
		takenExams = App.getTakeExams_List_();

		for (TakeExam temExam : takenExams) {
			if ((student.getTakenExams() == temExam)) {
				found = 2;
			}
		}
		if (found == 2) {
			return -2;
		}

		return 0;
	}

	public static void submit(List<Integer> answerList, StartExam mystartExam,TakeExam takeExam,Student student) throws InterruptedException {
		mystartExam.addSelfSubmittedNumber();
		MsgToServer massageMsgToServer1 = new MsgToServer("", "Update", mystartExam, "");
		MsgToServer massageMsgToServer2 = new MsgToServer("", "Save", takeExam, "");

		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer1);

			SimpleChatClient.getClient().sendToServer(massageMsgToServer2);
			System.out.println("update start");
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
		
		
		AnswerSheet answerSheet = new AnswerSheet(mystartExam, student,takeExam, mystartExam.getExam(), answerList);
		takeExam.setAnswerSheet(answerSheet);
  	student.addAnswerSheet(answerSheet);
	
		MsgToServer massageMsgToServer3 = new MsgToServer(null, "Update",student,null );
		try {	SimpleChatClient.getClient().sendToServer(massageMsgToServer3);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
	}
}
