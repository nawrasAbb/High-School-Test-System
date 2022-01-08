package FINAL.project;

//check
import FINAL.project.Student;
import FINAL.project.Person;
import FINAL.project.Teacher;
import FINAL.project.AnswerSheet;
import FINAL.project.TakeExam;
import FINAL.project.StartExam;
import FINAL.project.Exam;
import FINAL.project.Request;
import FINAL.project.Question;
import FINAL.project.CheckExam;
import FINAL.project.Course;
import FINAL.project.Documentation;
import FINAL.project.Principle;
import FINAL.project.Subject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ocsf.ConnectionToClient;
import ocsf.AbstractServer;

public class SimpleServer extends AbstractServer {
//
	private static Session session;

	public static <T> List<T> getAll(Class<T> object) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
		Root<T> rootEntry = criteriaQuery.from(object);
		CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);
		TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);
		return allQuery.getResultList();
	}

	public SimpleServer(int port) {
		super(port);
		try {
			Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
		} catch (Exception e) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}

		SimpleServer.Initialization();
		System.out.println("SERVER UP!");
	}

	private static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Person.class);
		configuration.addAnnotatedClass(Student.class);
		configuration.addAnnotatedClass(AnswerSheet.class);
		configuration.addAnnotatedClass(Exam.class);
		configuration.addAnnotatedClass(Teacher.class);
		configuration.addAnnotatedClass(Request.class);
		configuration.addAnnotatedClass(Question.class);
		configuration.addAnnotatedClass(StartExam.class);
		configuration.addAnnotatedClass(TakeExam.class);
		configuration.addAnnotatedClass(CheckExam.class);
		configuration.addAnnotatedClass(Subject.class);
		configuration.addAnnotatedClass(Course.class);
		configuration.addAnnotatedClass(Question.class);
		configuration.addAnnotatedClass(Documentation.class);
		configuration.addAnnotatedClass(Principle.class);
		configuration.addAnnotatedClass(Manual.class);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	private String classname;
	
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {

		MsgToServer newMassageMsgToServer = (MsgToServer) msg;
classname= newMassageMsgToServer.getMyClass();
		String massage = newMassageMsgToServer.getCommand();
String operation=newMassageMsgToServer.getOperation();
		String className = newMassageMsgToServer.getMyClass();
		Object myobject = newMassageMsgToServer.getMyObject();

		System.out.println("Received Message: " + msg.toString());

		System.out.println("Received command :" + massage);
		System.out.println("Received name:" + myobject.toString());
		if (massage.startsWith("Save"))
			mysave(myobject);
		else if (massage.startsWith("Update"))
			try {
				myUpdate(myobject);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		else if (massage.startsWith("Delete"))
			mydelete(myobject);
		else if (massage.startsWith("Close"))
			myclose();
		else if (massage.startsWith("Get"))
			try {
				System.out.println("calling myGet");
				myGet(className, myobject, operation,client);
			} catch (IOException e) {
				e.printStackTrace();
			}

	}

	private void myclose() {

		session.getSessionFactory().close();
		session.close();
	}

	public static <T> void mysave(Object myobject) {
		//session.beginTransaction();
		System.err.println("In Save ");
		session.save(myobject);
		session.flush();
		session.getTransaction().commit();
	}

	public static <T> void myUpdate(Object myobject) throws InterruptedException {
		//session.beginTransaction();
		System.err.println("In update ");
		Object mergedOne = session.merge(myobject);
		session.update(mergedOne);
		session.flush();
		session.getTransaction().commit();

	}
	
	public static <T> void mydelete(Object myobject) {
		session.delete(myobject);
		session.flush();
		// session.getTransaction().commit();
	}

	public static void myGet(String className, Object myobject, String operation, ConnectionToClient client)
			throws IOException {
		System.out.println("Im in get");
		// Person get ALL
		if (className.contentEquals("Person")) {
			System.out.println("Im in person");
			System.out.println("6");
			if (myobject.toString().contentEquals("All") || (myobject.toString().contentEquals("ALL"))) {
				System.out.println("Im in all");
				System.out.println("im in the first if");
				msgToClient newMassageMsgToClient = new msgToClient("PersonList", "", getAll(Person.class), operation);
				client.sendToClient(newMassageMsgToClient);
				return;
			}
			// search by id
			else if (myobject.getClass().equals(Integer.class)) {
				System.out.println("Im in inteager");
				List<Person> persons = getAll(Person.class);
				for (Person temp : persons) {
					if (Integer.toString(temp.getPerson_Id()).equals(myobject)) {
						msgToClient newMassageMsgToClient = new msgToClient("Person", "", temp, operation);
						client.sendToClient(newMassageMsgToClient);
						return;
					}

				}
				msgToClient newMassageMsgToClient = new msgToClient("Person", "", null, operation);
				client.sendToClient(newMassageMsgToClient);
				return;
			}
			// search by username
			else if (myobject.getClass().equals(String.class)) {
				System.out.println("Im string");
				List<Person> persons = getAll(Person.class);

				System.out.println("list size "+persons.size());
				for (Person temp : persons) {
					if ((temp.getUsername()).contentEquals(myobject.toString())) {
						System.out.println("7");
						msgToClient newMassageMsgToClient = new msgToClient("Person", "", temp, operation);
						System.out.println("Exist");
						client.sendToClient(newMassageMsgToClient);
						return;
					}
				} 
				msgToClient newMassageMsgToClient = new msgToClient("Person", "", null, operation);
				System.out.println("Not exist");
				client.sendToClient(newMassageMsgToClient);
				return;

			}
		}

		if (className.contentEquals("Student")) {
			if (myobject.toString().contentEquals("All") || (myobject.toString().contentEquals("ALL"))) {
				msgToClient newMassageMsgToClient = new msgToClient("StudentList", "", getAll(Student.class),
						operation);
				client.sendToClient(newMassageMsgToClient);
				return;
			}
			List<Student> Students = getAll(Student.class);
			for (Student temp : Students) {
				if (Integer.toString(temp.getPerson_Id()).equals(myobject.toString())) {
					msgToClient newMassageMsgToClient = new msgToClient("Student", "", temp, operation);
					client.sendToClient(newMassageMsgToClient);
					return;
				}
			}
		}

		if (className.contentEquals("Teacher")) {
			if (myobject.toString().contentEquals("All") || (myobject.toString().contentEquals("ALL"))) {
				msgToClient newMassageMsgToClient = new msgToClient("TeacherList", "", getAll(Teacher.class),
						operation);
				client.sendToClient(newMassageMsgToClient);
				return;
			}
			List<Teacher> Teachers = getAll(Teacher.class);
			for (Teacher temp : Teachers) {
				if (Integer.toString(temp.getPerson_Id()).equals(myobject.toString())) {
					msgToClient newMassageMsgToClient = new msgToClient("Teacher", "", temp, operation);
					client.sendToClient(newMassageMsgToClient);
					return;
				}
			}
		}

		if (className.contentEquals("Principle")) {
			if (myobject.toString().contentEquals("All") || (myobject.toString().contentEquals("ALL"))) {
				msgToClient newMassageMsgToClient = new msgToClient("PrincipleList", "", getAll(Principle.class),
						operation);
				client.sendToClient(newMassageMsgToClient);
				return;
			}
			List<Principle> Principles = getAll(Principle.class);
			for (Principle temp : Principles) {
				if (Integer.toString(temp.getPerson_Id()).equals(myobject.toString())) {
					msgToClient newMassageMsgToClient = new msgToClient("Principle", "", temp, operation);
					client.sendToClient(newMassageMsgToClient);
					return;
				}
			}
		}

		if (className.contentEquals("Subject")) {
			if (myobject.toString().contentEquals("All") || (myobject.toString().contentEquals("ALL"))) {
				msgToClient newMassageMsgToClient = new msgToClient("SubjectList", "", getAll(Subject.class),
						operation);
				client.sendToClient(newMassageMsgToClient);
				return;
			}
			List<Subject> Subjects = getAll(Subject.class);
			for (Subject temp : Subjects) {
				if (Integer.toString(temp.getSubject_id()).contentEquals(myobject.toString())) {
					msgToClient newMassageMsgToClient = new msgToClient("Subject", "", temp, operation);
					client.sendToClient(newMassageMsgToClient);
					return;
				}
			}
			msgToClient newMassageMsgToClient = new msgToClient("Subject", "", null, operation);
			client.sendToClient(newMassageMsgToClient);
		}

		if (className.contentEquals("Course")) {
			if (myobject.toString().contentEquals("All") || (myobject.toString().contentEquals("ALL"))) {
				msgToClient newMassageMsgToClient = new msgToClient("CourseList", "", getAll(Course.class), operation);
				client.sendToClient(newMassageMsgToClient);
				return;
			}
			List<Course> Courses = getAll(Course.class);
			for (Course temp : Courses) {
				if (Integer.toString(temp.getCourse_id()).contentEquals(myobject.toString())) {
					msgToClient newMassageMsgToClient = new msgToClient("Course", "", temp, operation);
					client.sendToClient(newMassageMsgToClient);
					return;
				}
			}
			msgToClient newMassageMsgToClient = new msgToClient("Course", "", null, operation);
			client.sendToClient(newMassageMsgToClient);

		}

		if (className.contentEquals("Exam")) {
			System.out.println("Im in exam");
			// Get All Exams
			if (myobject.toString().contentEquals("All") || (myobject.toString().contentEquals("ALL"))) {
				System.out.println("Im in all");
				msgToClient newMassageMsgToClient = new msgToClient("ExamList", "", getAll(Exam.class), operation);
				client.sendToClient(newMassageMsgToClient);
				return;
			}
			// Get Exam by ID
			else {
				System.out.println("Im in not all");
				List<Exam> Exams = getAll(Exam.class);
				System.out.println((Integer) myobject);
				for (Exam temp : Exams) {
					if (Integer.toString(temp.getExam_id()).contentEquals((Integer.toString((Integer) myobject)))) {
						msgToClient newMassageMsgToClient = new msgToClient("Exam", "", temp, operation);
						client.sendToClient(newMassageMsgToClient);
						return;
					}
				}
				msgToClient newMassageMsgToClient = new msgToClient("Exam", "", null, operation);
				client.sendToClient(newMassageMsgToClient);
				return;
			}
		}

		if (className.contentEquals("Question")) {
			if (myobject.toString().contentEquals("All") || (myobject.toString().contentEquals("ALL"))) {
				msgToClient newMassageMsgToClient = new msgToClient("QuestionList", "", getAll(Question.class),
						operation);
				client.sendToClient(newMassageMsgToClient);
				return;
			}
			List<Question> Questions = getAll(Question.class);
			for (Question temp : Questions) {
				if (Integer.toString(temp.getQuestion_id()).equals(myobject.toString())) {
					msgToClient newMassageMsgToClient = new msgToClient("Question", "", temp, operation);
					client.sendToClient(newMassageMsgToClient);
					return;
				}
			}
			msgToClient newMassageMsgToClient = new msgToClient("Question", "", null, operation);
			client.sendToClient(newMassageMsgToClient);
		}

		if (className.contentEquals("StartExam")) {
			if (myobject.toString().contentEquals("All") || (myobject.toString().contentEquals("ALL"))) {
				msgToClient newMassageMsgToClient = new msgToClient("StartExamList", "", getAll(StartExam.class),
						operation);
				client.sendToClient(newMassageMsgToClient);
				return;
			} else {
				System.err.println("In start exam in simple server");
				List<StartExam> StartExams = new ArrayList<StartExam>();
				StartExams = getAll(StartExam.class);
				for (StartExam temp : StartExams) {
					System.out.println("inside start exam , code " + temp.getCode());
					if (temp.getCode().contentEquals((String) myobject)) {
						System.out.println("my start exam " + temp.getTeacher().getPersonName());
						msgToClient newMassageMsgToClient = new msgToClient("StartExam", "", temp, operation);
						client.sendToClient(newMassageMsgToClient);
						return;
					}
				}
				msgToClient newMassageMsgToClient = new msgToClient("StartExam", "", null, operation);
				client.sendToClient(newMassageMsgToClient);
				return;
			}
		}

		if (className.contentEquals("TakeExam")) {
			if (myobject.toString().contentEquals("All") || (myobject.toString().contentEquals("ALL"))) {
				System.out.println("aaaaa");
				msgToClient newMassageMsgToClient = new msgToClient("TakeExamList", "", getAll(TakeExam.class),
						operation);
				client.sendToClient(newMassageMsgToClient);
				return;
			}
			List<TakeExam> TakeExams = new ArrayList<TakeExam>();
			TakeExams = getAll(TakeExam.class);
			for (TakeExam temp : TakeExams) {

				if (temp.getStartExam().getCode().equals(myobject)) {
					msgToClient newMassageMsgToClient = new msgToClient("TakeExam", "", temp, operation);
					client.sendToClient(newMassageMsgToClient);
					return;
				}
			}
			msgToClient newMassageMsgToClient = new msgToClient("TakeExam", "", null, operation);
			client.sendToClient(newMassageMsgToClient);
		}
		if (className.contentEquals("Request")) {
			if (myobject.toString().contentEquals("All") || (myobject.toString().contentEquals("ALL"))) {
				msgToClient newMassageMsgToClient = new msgToClient("RequestList", "", getAll(Request.class),
						operation);
				client.sendToClient(newMassageMsgToClient);
				return;
			}
			List<Request> Requests = getAll(Request.class);
			for (Request temp : Requests) {
				if (temp.getCode().equals(myobject.toString())) {
					msgToClient newMassageMsgToClient = new msgToClient("Request", "", temp, operation);
					client.sendToClient(newMassageMsgToClient);
					return;
				}
			}
			msgToClient newMassageMsgToClient = new msgToClient("Request", "", null, operation);
			client.sendToClient(newMassageMsgToClient);
			return;

		}

		if (className.contentEquals("AnswerSheet")) {
			System.out.println("inside answersheet");
			if (myobject.toString().contentEquals("All") || (myobject.toString().contentEquals("ALL"))) {
				System.out.println("inside ALL");
				msgToClient newMassageMsgToClient = new msgToClient("AnswerSheetList", "", getAll(AnswerSheet.class),
						operation);
				client.sendToClient(newMassageMsgToClient);
				return;
			}
			List<AnswerSheet> listAnswers = getAll(AnswerSheet.class);
			if (listAnswers.isEmpty())
				System.out.println("listAnswers empty");
			System.err.println(listAnswers.size());
			for (AnswerSheet temp : listAnswers) {
				System.err.println("here!");
				System.out.println(temp.getStudent().getPerson_Id());
				System.out.println(myobject.toString());
				if (Integer.toString(temp.getStudent().getPerson_Id()).equals(myobject.toString())) {
					System.out.println("im in if answeshwwt");
					msgToClient newMassageMsgToClient = new msgToClient("AnswerSheet", "", temp, operation);
					client.sendToClient(newMassageMsgToClient);
					return;
				}
			}
			msgToClient newMassageMsgToClient = new msgToClient("AnswerSheet", "", null, operation);
			client.sendToClient(newMassageMsgToClient);
			return;
		}
	}

	@Override
	protected synchronized void clientDisconnected(ConnectionToClient client) {
		System.out.println("Client Disconnected.");
		super.clientDisconnected(client);
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		super.clientConnected(client);
		System.out.println("Client connected: " + client.getInetAddress());
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Required argument: <port>");
		} else {
			SimpleServer server = new SimpleServer(Integer.parseInt(args[0]));
			server.listen();
		}

	}

	public static void Initialization() {
		Course course = new Course(11, "Computer Science");

		Subject sub1 = new Subject(12, "Math");
		Subject sub2 = new Subject(23, "History");
		session.save(course);
		session.save(sub1);
		session.save(sub2);
		session.flush();

		Student anna = new Student(209053438, "Anna", "anna", 0, "123");
		Student aseel = new Student(207062241, "Aseel", "aseel", 0, "234");
		Student nawras = new Student(315085043, "Nawras", "nawras", 0, "345");
		Principle hoda = new Principle(11111111, "Hoda", "hoda", 2, "111");
		Teacher soha = new Teacher(31568496, "soha", "soha", 1, "456");

		session.save(nawras);
		session.save(soha);
		session.save(anna);
		session.save(aseel);
		session.save(hoda);
		session.flush();

		course.addStudentsInCourse(anna);
		anna.addCourse(course);
		course.addStudentsInCourse(nawras);
		nawras.addCourse(course);

		Question q1 = new Question(12345, "2+2=?", sub1);
		q1.AddAnswer("1");
		q1.AddAnswer("2");
		q1.AddAnswer("4");
		q1.AddAnswer("0");
		q1.setCorrectAnswer(3);

		Question q2 = new Question(23456, "2*3=?", sub1);
		q2.AddAnswer("14");
		q2.AddAnswer("6");
		q2.AddAnswer("9");
		q2.AddAnswer("4");
		q2.setCorrectAnswer(2);

		Question q3 = new Question(34567, "what year did the WW1 end", sub2);
		q3.AddAnswer("1914");
		q3.AddAnswer("1915");
		q3.AddAnswer("1917");
		q3.AddAnswer("1918");
		q3.setCorrectAnswer(4);

		Question q4 = new Question(12333, "5+5=?", sub2);
		q4.AddAnswer("1");
		q4.AddAnswer("2");
		q4.AddAnswer("4");
		q4.AddAnswer("0");
		q4.setCorrectAnswer(3);

		List<Integer> points = new ArrayList<Integer>();
		List<Question> ques = new ArrayList<Question>();
		List<Question> ques2 = new ArrayList<Question>();
		List<Integer> ans = new ArrayList<Integer>();
		List<Integer> ans2 = new ArrayList<Integer>();
		List<Integer> ans3 = new ArrayList<Integer>();

		points.add(25);
		points.add(30);
		points.add(45);

		ques.add(q1);
		ques.add(q2);
		ques2.add(q3);
		ques2.add(q4);

		ans.add(2);
		ans.add(3);
		ans2.add(1);
		ans2.add(2);
		ans3.add(3);
		ans3.add(2);

		session.save(q1);
		session.save(q2);
		session.save(q3);
		session.save(q4);

		Exam firstExam = new Exam(121155, 1, points, soha, "easy exam for you", "draft", sub1, course, ques);
		session.save(firstExam);
		session.flush();

		Exam secondExam = new Exam(231166, 90, points, soha, "another exam", "draft2", sub2, course, ques2);

		session.save(secondExam);
		session.flush();

		StartExam startExam = new StartExam("KL25", false, soha);
		session.save(startExam);

		startExam.setExam(firstExam);
		session.flush();

		StartExam startExam2 = new StartExam("AS12", true, soha);
		session.save(startExam2);
		

		startExam2.setExam(secondExam);
		session.flush();

		TakeExam takeExam4 = new TakeExam(false, aseel);
		session.save(takeExam4);
		session.flush();

		TakeExam takeExam2 = new TakeExam(false, anna);
		session.save(takeExam2);
		session.flush();

		TakeExam takeExam3 = new TakeExam(false, nawras);
		session.save(takeExam3);
		session.flush();

		takeExam4.setStudent(aseel);
		takeExam2.setStudent(anna);
		takeExam3.setStudent(nawras);

		startExam2.addTakeExam(takeExam2);
		startExam2.addTakeExam(takeExam3);
		startExam.addTakeExam(takeExam4);
		session.flush();

		AnswerSheet Answers2 = new AnswerSheet(startExam, anna, takeExam2, firstExam, ans2);
		AnswerSheet Answers3 = new AnswerSheet(startExam2, nawras, takeExam3, secondExam, ans3);
		AnswerSheet Answers4 = new AnswerSheet(startExam, aseel, takeExam4, firstExam, ans3);

		Answers2.setConfirmed(true);
		Answers2.setGrade(100);
		Answers3.setGrade(50);
		Answers3.setGrade(70);

		Answers2.setExam(firstExam);
		Answers3.setExam(secondExam);
		Answers4.setExam(firstExam);

		aseel.addAnswerSheet(Answers4);
		anna.addAnswerSheet(Answers2);
		nawras.addAnswerSheet(Answers3);

		session.save(Answers2);
		session.save(Answers3);
		session.save(Answers4);

		takeExam2.setAnswerSheet(Answers2);
		takeExam3.setAnswerSheet(Answers3);
		takeExam4.setAnswerSheet(Answers4);
		session.flush();

		List<StartExam> mylist = new ArrayList<StartExam>();
		mylist.add(startExam);
		mylist.add(startExam2);
		soha.setStartedExamsList(mylist);

		List<Exam> mylist2 = new ArrayList<Exam>();
		mylist2.add(firstExam);
		mylist2.add(secondExam);
		soha.setWrittenExamsList(mylist2);

		Request request1 = new Request("nawras", "15", "KL25", startExam);
		Request request2 = new Request("Anna", "20", "AS12", startExam2);
		request1.setAvailable(true);
		request2.setAvailable(true);
		session.save(request1);
		session.save(request2);
		session.flush();

		session.getTransaction().commit();

	}
}