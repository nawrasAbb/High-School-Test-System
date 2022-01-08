package FINAL.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.Session;

@Entity
@Table(name = "checkExams")
public class CheckExam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	static int studentID;
	@Column(name = "checkExamCod")
	static String code;
	static double CalculateGrade = 0;
	static Session session;

	static AnswerSheet studentAnswer = new AnswerSheet();
	static List<Question> questions = new ArrayList<Question>();
	static List<TakeExam> take_Exam = new ArrayList<TakeExam>();
	static Exam WantedExam = new Exam();
	static StartExam startExam = new StartExam();
	static int ExamID = 0;

	public static void ExamCheck(String code, int num) throws InterruptedException

	{
		String string = "getstarat exam for exam check";
		if (num == 2) {
			string = "getstarat exam for exam confirm";
		}
		// find take exam with this code , search the one with this studentID then get
		// the take exam answersheet for the right student
		MsgToServer massageMsgToServer = new MsgToServer("StartExam", "Get", code, string);
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}

	}

	public static void ExamCheck2() {

		take_Exam = startExam.getTakeExam();
		ExamID = startExam.getExam().getExam_id();
		System.out.println("EXAM ID _" + ExamID);
		startExam.SetIsChecked(true);
		// *****save start exam change
		/*
		 * MsgToServer massageMsgToServer = new MsgToServer("", "Save", startExam, "");
		 * try { SimpleChatClient.getClient().sendToServer(massageMsgToServer); } catch
		 * (IOException e1) { e1.printStackTrace(); }
		 */
		WantedExam = startExam.getExam();
		if (WantedExam != null) {
			questions = WantedExam.getQuestions();
			for (TakeExam takenExam : take_Exam) {
				studentID = takenExam.getStudent().getPerson_Id();
				studentAnswer = takenExam.getAnswerSheet();
				if ((studentAnswer.getIsChecked()) == false) {
					for (int i = 0; i < (questions.size()); i++) {
						if ((questions.get(i).getCorrectAnswer()) == (studentAnswer.getAnswersList().get(i))) {
							CalculateGrade = CalculateGrade + WantedExam.getPoints().get(i);

						}
					}
					studentAnswer.setGrade(CalculateGrade);
					CalculateGrade = 0;
					studentAnswer.setChecked(true);
					MsgToServer massageMsgToServer = new MsgToServer("", "Update", studentAnswer, "");
					try {
						SimpleChatClient.getClient().sendToServer(massageMsgToServer);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					getStartExam().SetIsChecked(true);
					 massageMsgToServer = new MsgToServer("", "Update", getStartExam(), "");
					try {
						SimpleChatClient.getClient().sendToServer(massageMsgToServer);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}

	}

	public static List<AnswerSheet> getAnswerSheets() {
		List<AnswerSheet> Returnedlist = new ArrayList<AnswerSheet>();
		List<TakeExam> take_Exam = new ArrayList<TakeExam>();

		take_Exam = startExam.getTakeExam();
		for (TakeExam tempExam : take_Exam) {
			if (tempExam.getAnswerSheet().getIsConfirmed() == false)
				if (tempExam.getAnswerSheet().getIsChecked() == true) {
					Returnedlist.add(tempExam.getAnswerSheet());
				}

		}

		return Returnedlist;
	}

	public static void ExamCheckconfirm(String code) throws InterruptedException

	{
		// find take exam with this code , search the one with this studentID then get
		// the take exam answersheet for the right student
		MsgToServer massageMsgToServer = new MsgToServer("StartExam", "Get", code, "getstarat exam for exam confirm");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}

	}

	public static void confirmGrade(String notes, AnswerSheet AS1) throws InterruptedException {

		AS1.setConfirmed(true);
		AS1.setNotes(notes);

		// *****save Students change
		// *****save AnswerSheets change
		MsgToServer massageMsgToServer = new MsgToServer("", "Update", AS1, "");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		massageMsgToServer = new MsgToServer("", "Save",AS1.getStudent(), "");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void changeGrade(double grade, String explanation, AnswerSheet AS1) throws InterruptedException {
		AS1.setGrade(grade);

		AS1.setChangeGradeExplination(explanation);
		MsgToServer massageMsgToServer = new MsgToServer("", "Update", AS1, "");
		try {
		SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e1) {
		e1.printStackTrace();
		}

		}



	public static int checkIfLegal(int op) throws InterruptedException {
		/*
		 * if (startExam == null) { return -1; }
		 */
		System.out.println("EXAm type = " + startExam.ExamType() + " exam code" + startExam.getCode());
		if (getStartExam().ExamType() == true) {
			return -1;
		}
		if (getStartExam().IsChecked() == true && op == 1) {
			return -2;
		}
		if (getStartExam().IsChecked() == false && op == 0) {
			return 2;
		}
		return 0;

	}

	static void getAnswerSheet(int studentID, int op) throws InterruptedException {
		String string = "get AS for Student change";
		if (op == 1) {
			string = "get AS for Student confirm";
		}
		MsgToServer massageMsgToServer = new MsgToServer("AnswerSheet", "Get", studentID, string);
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
		Thread.sleep(1000);
	}

	public static StartExam getStartExam() {
		return startExam;
	}

	public static void setStartExam(StartExam startExam2) {
		startExam = startExam2;
	}

}