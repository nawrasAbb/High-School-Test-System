package FINAL.project;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import FINAL.project.App;
import FINAL.project.OneLineOfExamController;
import FINAL.project.StartExamForTeacherController;
import FINAL.project.Exam;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Pair;


@Entity
@Table(name = "startExams")
public class StartExam implements Serializable {

	
	private static final long serialVersionUID = 1303220275139547131L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private double remainingTime;
	private double intialTime=0;
	private double actualTime=0;
	private int ExamineeNumber=0, SelfSubmittedNumber=0, DidntCompleteNumber=0;
	
	
	public void addSelfSubmittedNumber() {
		this.SelfSubmittedNumber++;
	}
	public void addExamineeNumber() {
		this.ExamineeNumber++;
	}
	public void addDidntCompleteNumber() {
		this.DidntCompleteNumber++;
	}


	public void setActualTime(double actualTime) {
		this.actualTime = actualTime;
	}

	public static void setListOfLines(List<OneLineOfExamController> listOfLines) {
		StartExam.listOfLines = listOfLines;
	}

	public static void setTheMainPage(StartExamForTeacherController theMainPage) {
		StartExam.theMainPage = theMainPage;
	}

	public boolean isLock() {
		return isLocked;
	}

	public void setLock(boolean lock) {
		this.isLocked = lock;
	}



	private boolean isLocked;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exam_id")
	private Exam MyExam;
	
	
	@Column(name = "Exam_Code")
	private String code;
	private boolean ExamType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;

	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "startExam")
	private List<TakeExam> takeExam;
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "startExam")
	private List<Manual> ManualList;

	private boolean Checked;
	public StartExam() {
	}

	public StartExam(String code, boolean ExamType,Teacher theTeacher) {
		this.teacher = theTeacher;
		
		this.code = code;
		this.ExamType = ExamType;
		this.takeExam = new ArrayList<TakeExam>();
		 this.isLocked=false;
		 this.ManualList =new ArrayList<Manual>();
		this.Checked=false;

	}

	/***** getters ******/
	public int getColumn_id() {
		return this.id;
	}

	public double getIntialTime() {
		return intialTime;
	}
	public void setIntialTime(double intialTime) {
		this.intialTime = intialTime;
	}
	public double getActualTime() {
		return actualTime;
	}
	public Exam getExam() {
		return this.MyExam;
		
	}

	public String getCode() {
		return this.code;
	}

	public boolean ExamType() {
		return this.ExamType;
	}

	public List<TakeExam> getTakeExam() {
		return this.takeExam;
	}

	public Teacher getTeacher() {
		return this.teacher;
	}
	
	public double getRemainingTime() {
		return this.remainingTime;
	}
	
	public boolean IsChecked() {
		return Checked;
	}
	/***** setters ******/

	public  void SetIsChecked(boolean b) {
		this.Checked=b;
	}
	public void setExam(Exam exam) {
		this.MyExam = exam;
		 this.remainingTime = exam.getTime();
		 this.intialTime=exam.getTime();
		 this.actualTime=this.intialTime;

	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setType(boolean ExamType) {
		this.ExamType = ExamType;
	}
	
	public void setRemainig(double time) {
		this.remainingTime = time;
	}

	public void setTakeExam(List<TakeExam> takeExam) {
		this.takeExam = takeExam;
	}
	
	public void addTakeExam(TakeExam exam ) {
		this.takeExam.add(exam);
		exam.setStartExam(this);
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
		teacher.getStartedExamsList().add(this);
	}

	
	
	/***********ANNA**********/
	
	public void AddTime(double extra) {
	actualTime=this.intialTime+extra;
	this.remainingTime+=extra;
		MsgToServer massageMsgToServer = new MsgToServer("", "Update", this,"");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
	//event buc send to all client that time is added
	}
	
	public void countDown() {
		remainingTime--;
	
	}
	
	private int count =0;
	private static List<OneLineOfExamController> listOfLines= new ArrayList<OneLineOfExamController>();
	private static StartExamForTeacherController theMainPage=new StartExamForTeacherController();
	
	public static void initialization(StartExamForTeacherController controller) throws IOException, InterruptedException {
		theMainPage=controller;
		
		MsgToServer massageMsgToServer = new MsgToServer("Exam", "Get", "All","init StartEXam Exams");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
		Thread.sleep(1000);
		
	}
	public static void initialization2(List<Exam> allExams2) throws IOException	
		{
		List<Exam> allExams=new ArrayList<Exam>();
		allExams=allExams2;
		VBox box=new VBox();
		for(Exam exam:allExams) {
			Pair<Parent,Object> oneLineOfExamPair=App.getFxmlAndController("OneLineOfExam");
			OneLineOfExamController LineController=(OneLineOfExamController) oneLineOfExamPair.getValue();
			LineController.getExamNameAndID().setText(exam.getSubjectOfExam().getSubjectName()+" - "+ exam.getCourseOfExam().getCourseName()+" - "+
														exam.getExam_id());
			box.getChildren().add(oneLineOfExamPair.getKey());
			listOfLines.add(LineController);
		}
		theMainPage.getDisplayAllExams().setContent(box);
	}
	
	public static void WhichButtonHasPressesd() throws IOException {
		for(OneLineOfExamController page:listOfLines) {
			if(page.getChooseButton().isArmed()) {
				theMainPage.getExamID().setText(page.getExamNameAndID().getText());
				break;
			}
		}
	}	

	public void setTimeOut() throws IOException {
		this.remainingTime=0;
		count ++;
		///start Documentation
		if(count==1) {
		Documentation doc = new Documentation( this.intialTime, this.actualTime, ExamineeNumber, SelfSubmittedNumber, DidntCompleteNumber, this);
		MsgToServer massageMsgToServer = new MsgToServer("", "Save", doc,"");
		SimpleChatClient.getClient().sendToServer(massageMsgToServer);}
	}
	
	public void addManual(Manual manualExam) {
		ManualList.add(manualExam);
	}
	
	



}








/*package FINAL.project;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import FINAL.project.App;
import FINAL.project.OneLineOfExamController;
import FINAL.project.StartExamForTeacherController;
import FINAL.project.Exam;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Pair;


@Entity
@Table(name = "startExams")
public class StartExam implements Serializable {

	
	private static final long serialVersionUID = 1303220275139547131L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private double remainingTime;
	private double intialTime=0;
	private double actualTime=0;
	private int ExamineeNumber=0, SelfSubmittedNumber=0, DidntCompleteNumber=0;
	
	
	public void addSelfSubmittedNumber() {
		this.SelfSubmittedNumber++;
	}
	public void addExamineeNumber() {
		this.ExamineeNumber++;
	}
	public void addDidntCompleteNumber() {
		this.DidntCompleteNumber++;
	}


	public void setActualTime(double actualTime) {
		this.actualTime = actualTime;
	}

	public static void setListOfLines(List<OneLineOfExamController> listOfLines) {
		StartExam.listOfLines = listOfLines;
	}

	public static void setTheMainPage(StartExamForTeacherController theMainPage) {
		StartExam.theMainPage = theMainPage;
	}

	public boolean isLock() {
		return isLocked;
	}

	public void setLock(boolean lock) {
		this.isLocked = lock;
	}



	private boolean isLocked;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exam_id")
	private Exam MyExam;
	
	
	@Column(name = "Exam_Code")
	private String code;
	//@Column(name = "TypeOfExam")
	private boolean ExamType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;

	///@one to one ---Documentation
	///one to many -----Resquest
	//@OneToOne(fetch = FetchType.LAZY)
	 //@JoinColumn(name = "timing") private TimeCalculate timer;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "startExam")
	private List<TakeExam> takeExam;
	

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "startExam")
	private List<Manual> ManualList;

	private boolean Checked;
	public StartExam() {
	}

	public StartExam(String code, boolean ExamType,Teacher theTeacher) {
		this.teacher = theTeacher;
		
		this.code = code;
		this.ExamType = ExamType;
		//this.exam = new Exam();
		this.takeExam = new ArrayList<TakeExam>();
		 this.isLocked=false;
		 this.ManualList =new ArrayList<Manual>();
		this.Checked=false;

	}

	//getters 
	public int getColumn_id() {
		return this.id;
	}

	public double getIntialTime() {
		return intialTime;
	}
	public void setIntialTime(double intialTime) {
		this.intialTime = intialTime;
	}
	public double getActualTime() {
		return actualTime;
	}
	public Exam getExam() {
		return this.MyExam;
		
	}

	public String getCode() {
		return this.code;
	}

	public boolean ExamType() {
		return this.ExamType;
	}

	public List<TakeExam> getTakeExam() {
		return this.takeExam;
	}

	public Teacher getTeacher() {
		return this.teacher;
	}
	
	public double getRemainingTime() {
		return this.remainingTime;
	}
	
	public boolean IsChecked() {
		return Checked;
	}
	//setters 

	public  void SetIsChecked(boolean b) {
		this.Checked=b;
	}
	public void setExam(Exam exam) {
		this.MyExam = exam;
		 this.remainingTime = exam.getTime();
		 this.intialTime=exam.getTime();
		 this.actualTime=this.intialTime;
		//timer.setExamTime(exam.getTime());
		// Exam_time=getExam().getTime();
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setType(boolean ExamType) {
		this.ExamType = ExamType;
	}
	
	public void setRemainig(double time) {
		this.remainingTime = time;
	}

	public void setTakeExam(List<TakeExam> takeExam) {
		this.takeExam = takeExam;
	}
	
	public void addTakeExam(TakeExam exam ) {
		this.takeExam.add(exam);
		exam.setStartExam(this);
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
		teacher.getStartedExamsList().add(this);
	}

	
	
	//ANNA
	
	public void AddTime(double extra) {
	actualTime=this.intialTime+extra;
	this.remainingTime+=extra;
		MsgToServer massageMsgToServer = new MsgToServer("", "Update", this);
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
	//event buc send to all client that time is added
	}
	
	public void countDown() {
		remainingTime--;
		//return this.remainingTime;
	}
	
	private int count =0;
	private static List<OneLineOfExamController> listOfLines= new ArrayList<OneLineOfExamController>();
	private static StartExamForTeacherController theMainPage=new StartExamForTeacherController();
	
	public static void initialization(StartExamForTeacherController controller) throws IOException, InterruptedException {
		theMainPage=controller;
		List<Exam> allExams=new ArrayList<Exam>();
		
		MsgToServer massageMsgToServer = new MsgToServer("Exam", "Get", "All");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
		Thread.sleep(1000);
		
		allExams=App.getExams_List_();
		VBox box=new VBox();
		for(Exam exam:allExams) {
			Pair<Parent,Object> oneLineOfExamPair=App.getFxmlAndController("OneLineOfExam");
			OneLineOfExamController LineController=(OneLineOfExamController) oneLineOfExamPair.getValue();
			LineController.getExamNameAndID().setText(exam.getSubjectOfExam().getSubjectName()+" - "+ exam.getCourseOfExam().getCourseName()+" - "+
														exam.getExam_id());
			box.getChildren().add(oneLineOfExamPair.getKey());
			listOfLines.add(LineController);
		}
		controller.getDisplayAllExams().setContent(box);
	}
	public static void WhichButtonHasPressesd() throws IOException {
		for(OneLineOfExamController page:listOfLines) {
			if(page.getChooseButton().isArmed()) {
				theMainPage.getExamID().setText(page.getExamNameAndID().getText());
				break;
			}
		}
	}	
	public static  Pair<String, String> CheckData(String code,String type,String ID) throws InterruptedException {
		return null;

		/// add this to start exam

		String errorFields = "";
		String theErrorMsg = "";
		if (code.isEmpty() == true) {
			theErrorMsg = theErrorMsg + "- Code field is empty.\n";
			errorFields = errorFields + "Code";
		}
		else if (code.length() != 4) {
			theErrorMsg = theErrorMsg + "- Code must contain 4 digits or letters.\n";
			errorFields = errorFields + "Code";
		}
		else if (code.matches("[a-zA-Z0-9]+") == false) {
			theErrorMsg = theErrorMsg + "- Invalid code! it must contain only digits or letters.\n";
			errorFields = errorFields + "Code";
		}
		if(theErrorMsg.isEmpty()) {
			
			MsgToServer massageMsgToServer = new MsgToServer("StartExam", "Get", code);
			try {
				SimpleChatClient.getClient().sendToServer(massageMsgToServer);
			} catch (IOException e) {
				System.out.println("msg not sent ");
				e.printStackTrace();
			}
			Thread.sleep(1000);
			if(App.getStartExam_()!=null) {
					theErrorMsg=theErrorMsg+"- This code not uniform";
					errorFields = errorFields + "Code";
				}
					
			}
		
		if(type==null) {
			theErrorMsg = theErrorMsg + "- Must choose the type of the exam.\n";
			errorFields = errorFields + "Type";
		}
		if(ID.isEmpty()) {
			theErrorMsg = theErrorMsg + "- Must choose the exam.\n";
			errorFields = errorFields + "ID";
		}
		return new Pair<>(theErrorMsg, errorFields);
		
	}

	public void setTimeOut() throws IOException {
		this.remainingTime=0;
		count ++;
		///start Documentation
		if(count==1) {
		Documentation doc = new Documentation( this.intialTime, this.actualTime, ExamineeNumber, SelfSubmittedNumber, DidntCompleteNumber, this);
		MsgToServer massageMsgToServer = new MsgToServer("", "Save", doc);
		SimpleChatClient.getClient().sendToServer(massageMsgToServer);}
	}
	
	public void addManual(Manual manualExam) {
		ManualList.add(manualExam);
	}
	
	



}*/
