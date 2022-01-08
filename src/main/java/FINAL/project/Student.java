package FINAL.project;

import java.util.ArrayList; 
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import FINAL.project.Subject;
import FINAL.project.Course;



@Entity(name = "students") 
@Table(name = "students")
public class Student extends Person  {


	 
	private static final long serialVersionUID = 1L;

	@ManyToMany(mappedBy = "studentsInTheStubject", cascade = { CascadeType.PERSIST,CascadeType.MERGE }, targetEntity = Subject.class)
	private List<Subject> subjects;

	@ManyToMany(mappedBy = "studentsInCourse", cascade = { CascadeType.PERSIST,CascadeType.MERGE }, targetEntity = Course.class)
	private List<Course> courses;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")
	private List<AnswerSheet> allAnswerSheets;


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")
	private List<TakeExam> takeExams ;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")
	private List<Manual> manualExams ;
	
	public Student() { super();}

	public Student(int personId, String personName, String username, int authorization, String password) {
		super(personId, personName, username, authorization, password);
		allAnswerSheets = new ArrayList<AnswerSheet>();
		subjects = new ArrayList<Subject>();
		courses = new ArrayList<Course>();
		takeExams = new ArrayList<TakeExam>();
		manualExams=new ArrayList<Manual>();

	}

	
	public void addManual(Manual Exam) {
		manualExams.add(Exam);
		
	}
	public List<Manual> getManualExams() {
		return this.manualExams;
	}
	public void setManualExams(List<Manual> manualExams) {
		this.manualExams = manualExams;
	}
	
	/**** getters *****/
	public List<Subject> getSubjects() {
		return this.subjects;
	}

	public List<Course> getCourses() {
		return this.courses;
	}

	public List<AnswerSheet> getAllAnswerSheets() {
		return this.allAnswerSheets;
	}
	
	public List<TakeExam> getTakenExams() {
		return this.takeExams;
	}

	/**** setters ******/

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public void setAllAnswerSheets(List<AnswerSheet> allAnswerSheets) {
		this.allAnswerSheets = allAnswerSheets;
	}
	
	
	/*****  add   ****/
	
	public void addCourse(Course...AddCourses) {
		for(Course course: AddCourses) {
			courses.add(course);
			course.getStudentsInCourse().add(this);
		}
	}
	
	public void addSubject(Subject...AddSubjects) {
		for(Subject subject: AddSubjects) {
			subjects.add(subject);
			subject.getStudentsInTheStubject().add(this);
		}
	}

	public void addTakeExam(TakeExam Exam) {
		this.takeExams.add(Exam);
		
	}

	public void addAnswerSheet(AnswerSheet answerSheet) {
		this.allAnswerSheets.add(answerSheet);
	}
	

}
