package FINAL.project;
import java.io.Serializable;
import java.util.ArrayList;   
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "Teachers")
@Table(name = "teachers")
public class Teacher extends Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ManyToMany(mappedBy = "teacherOfTheCourse", cascade = { CascadeType.PERSIST,CascadeType.MERGE }, targetEntity = Course.class)
	private List<Course> courses;

	@ManyToMany(mappedBy = "teachersOfTheSubjec", cascade = { CascadeType.PERSIST,CascadeType.MERGE }, targetEntity = Subject.class)
	private List<Subject> subjects;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacher")
	private List<Exam> WrittenExamsList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacher")
	private List<StartExam> startedExamsList;

	public Teacher() { super();}
	
	public Teacher(int personId, String personName, String username, int authorization, String password) {
		super(personId, personName, username, authorization, password);
		this.startedExamsList = new ArrayList<StartExam>();
		this.courses = new ArrayList<Course>();
		this.subjects = new ArrayList<Subject>();
		this.WrittenExamsList = new ArrayList<Exam>();
	}

	/**** setters ****/
	
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
	public void setSubject(List<Subject> subject) {
		this.subjects = subject;
	}	
	
	public void setStartedExamsList(List<StartExam> startedExamsList) {
		this.startedExamsList = startedExamsList;
	}	
		
	public void setWrittenExamsList(List<Exam> WrittenExamsList) {
		this.WrittenExamsList = WrittenExamsList;
	}	
	
	public void addStartedExamsList(StartExam startedExamsList) {
		this.startedExamsList.add(startedExamsList);
	}	
		
	public void addWrittenExamsList(Exam WrittenExamsList) {
		this.WrittenExamsList.add(WrittenExamsList);
	}	
	


	/**** getters ****/

	public List<Course> getCourses() {
		return this.courses;
	}

	public List<Subject> getSubject() {
		return this.subjects;
	}

	public List<StartExam> getStartedExamsList() {
		return this.startedExamsList;
	}

	public List<Exam> getWrittenExamsList() {
		return this.WrittenExamsList;
	}
	
	/**** add  ****/

	public void addCourse(Course...AddCourses) {
		for(Course course: AddCourses) {
			courses.add(course);
			course.getTeacherOfTheCourse().add(this);
		}
	}
	
	public void addSubject(Subject...AddSubjects) {
		for(Subject subject: AddSubjects) {
			subjects.add(subject);
			subject.getTeachersOfTheSubjec().add(this);
		}
	}
}

