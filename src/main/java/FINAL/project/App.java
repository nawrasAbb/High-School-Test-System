
package FINAL.project;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

public class App extends Application {

	private static SimpleChatClient client;
	private static Scene scene;
	private static Session session;

	private static String UserName;
	private static String currentCode;
	private static Exam currentExam;
	private static StartExam startExam = new StartExam();
	private static Person person_ = new Person();
	private static Exam Exam_ = new Exam();
	private static Question question_ = new Question();
	private static Request request_ = new Request();
	private static List<Subject> Subjects_List_ = new ArrayList<Subject>();
	private static List<Question> Questions_List_ = new ArrayList<Question>();
	private static List<TakeExam> TakeExams_List_ = new ArrayList<TakeExam>();
	private static List<AnswerSheet> AnswerSheets_List_ = new ArrayList<AnswerSheet>();
	private static Subject subject_ = new Subject();
	private static List<Exam> Exams_List_ = new ArrayList<Exam>();
	private static List<Student> Students_List = new ArrayList<Student>();
	private static List<Request> Requests_List = new ArrayList<Request>();
	private static Course course_ = new Course();
	private static Student student_ = new Student();
	private static Teacher teacher_ = new Teacher();
	private static Principle principle_ = new Principle();
	private static AnswerSheet answerSheet_ = new AnswerSheet();
	private static Person User;

	

	public static SimpleChatClient getClient1() {
		return client;
	}

	public static void setExamCode(String myCode) throws InterruptedException {
		/*
		 * currentCode = myCode;
		 * 
		 * MsgToServer massageMsgToServer = new MsgToServer("StartExam", "Get", myCode);
		 * try { SimpleChatClient.getClient().sendToServer(massageMsgToServer); } catch
		 * (IOException e) { System.out.println("msg not sent "); e.printStackTrace(); }
		 * Thread.sleep(1000); currentExam = startExam_.getExam();
		 */
	}

	/*
	 * public static void setperson(int id) { person_Id = id; }
	 */

	@Override
	public void start(Stage stage) throws IOException {
		scene = new Scene(loadFXML("HomePage"));
		stage.setScene(scene);
		stage.show();
	}

	public static void connect() {

		try {
			SimpleChatClient.getClient().openConnection();
			client = SimpleChatClient.getClient();
		} catch (IOException e) {

			client = null;
		}
	}

	static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	static void setRoot2(Parent fxml) throws IOException {
		scene.setRoot(fxml);
	}

	static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		/*
		  MsgToServer msgToServer1 =new MsgToServer("Person", "Get",UserName);
		  client.sendToServer(msgToServer1); Thread.sleep(1000);
		  App.getPerson_().setStatus(false); MsgToServer msgToServer2 =new
		  MsgToServer("", "Update",App.getPerson_());
		  System.out.println("Stage is closing"); Thread.sleep(1000); MsgToServer
		  msgToServer =new MsgToServer("", "Close", "");
		  
		  client.sendToServer(msgToServer2); client.sendToServer(msgToServer);*/
		 
	}

	static void setCenter(String error) throws IOException {
		Platform.runLater(() -> {
			Alert alert = new Alert(Alert.AlertType.ERROR, error);
			alert.show();
		});
	}

	public static void main(String[] args) {
		launch();
	}

	public static Pair<Parent, Object> getFxmlAndController(String fxml) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(App.class.getResource(fxml + ".fxml"));
		loader.setControllerFactory(c -> {
			if (c == DisplayQuestionsController.class)
				return new DisplayQuestionsController();
			if (c == DisplayExamsController.class)
				return new DisplayExamsController();
			if (c == DisplayResultsController.class)
				return new DisplayResultsController();

			Object controller;
			try {
				controller = c.getConstructor().newInstance();
				return controller;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

			return null;
		});
		Parent ret = loader.load();
		Object controller = loader.getController();
		return new Pair<>(ret, controller);
	}

	static void setMsgAtCenter(String theError) throws IOException {
		Platform.runLater(() -> {
			Alert alert = new Alert(Alert.AlertType.INFORMATION, theError);
			alert.show();
		});

	}

	static void setErrorAtCenter(String theError) throws IOException {
		Platform.runLater(() -> {
			Alert alert = new Alert(Alert.AlertType.ERROR, theError);
			alert.show();
		});
	}

	public static void addToSession(Object o) {
		session.save(o);
		session.flush();
		session.getTransaction().commit();
	}

	public static Session getSessionApp() {
		return session;
	}

	///// getters & setters

	public static AnswerSheet getAnswerSheet_() {
		return answerSheet_;
	}

	public static List<TakeExam> getTakeExams_List_() {
		return TakeExams_List_;
	}

	public static void setTakeExams_List_(List<TakeExam> takeExams_List_) {
		TakeExams_List_ = takeExams_List_;
	}

	public static List<Request> getRequests_List() {
		return Requests_List;
	}

	public static void setRequests_List(List<Request> requests_List) {
		Requests_List = requests_List;
	}

	public static Student getStudent_() {
		return student_;
	}

	public static void setStudent_(Student student_) {
		App.student_ = student_;
	}

	public static List<Question> getQuestions_List_() {
		return Questions_List_;
	}

	public static void setQuestions_List_(List<Question> questions_List_) {
		Questions_List_ = questions_List_;
	}

	public static List<AnswerSheet> getAnswerSheets_List_() {
		return AnswerSheets_List_;
	}

	public static void setAnswerSheets_List_(List<AnswerSheet> answerSheets_List_) {
		AnswerSheets_List_ = answerSheets_List_;
	}

	public static List<Student> getStudents_List() {
		return Students_List;
	}

	public static void setStudents_List(List<Student> students_List) {
		Students_List = students_List;
	}

	public static List<Subject> getSubjects_List_() {
		return Subjects_List_;
	}

	public static void setSubjects_List_(List<Subject> subjects_List_) {
		Subjects_List_ = subjects_List_;
	}

	public static Subject getSubject_() {
		return subject_;
	}

	public static void setSubject_(Subject subject_) {
		App.subject_ = subject_;
	}

	public static List<Exam> getExams_List_() {
		return Exams_List_;
	}

	public static void setExams_List_(List<Exam> exams_List_) {
		Exams_List_ = exams_List_;
	}

	public static Course getCourse_() {
		return course_;
	}

	public static void setCourse_(Course course_) {
		App.course_ = course_;
	}

	public static void setAnswerSheet_(AnswerSheet answerSheet_) {
		App.answerSheet_ = answerSheet_;
	}

	public static Exam getExam_() {
		return Exam_;
	}

	public static void setExam_(Exam exam_) {
		Exam_ = exam_;
	}

	public static Question getQuestion_() {
		return question_;
	}

	public static void setQuestion_(Question question_) {
		App.question_ = question_;
	}

	public static Request getRequest_() {
		return request_;
	}

	public static void setRequest_(Request request_) {
		App.request_ = request_;
	}

	public static StartExam getStartExam() {
		return startExam;
	}

	public static void setStartExam(StartExam startExam) {
		App.startExam = startExam;
	}

	public static Exam getcurrentExam() {
		return currentExam;
	}

	public static void setCode(String code) {
		currentCode = code;
	}

	public static String getCode() {
		return currentCode;
	}

	public static String getUserName() {
		return UserName;
	}

	public static Person getPerson_() {
		return person_;
	}

	public static void setPerson_(Person person1) {
		App.person_ = person1;
	}

	public static Teacher getTeacher_() {
		return teacher_;
	}

	public static void setTeacher_(Teacher teacher_) {
		App.teacher_ = teacher_;
	}

	public static Principle getPrinciple_() {
		return principle_;
	}

	public static void setPrinciple_(Principle principle_) {
		App.principle_ = principle_;
	}

	public static void setUSER(Person thisPerson) {
		User = thisPerson;
	}

	public static Person getUser() {
		return User;
	}

}
