package FINAL.project;
import java.io.Serializable;
import java.util.ArrayList;  
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import FINAL.project.Student;
import FINAL.project.Teacher;

@Entity
@Table(name = "courses")
public class Course implements Serializable {

	private static final long serialVersionUID = 8943592945235181821L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int course_id;
	private String courseName;
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = Student.class)
	@JoinTable(name = "courses_students", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
	private List<Student> studentsInCourse;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = Teacher.class)
	@JoinTable(name = "courses_teachers", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"))
	private List<Teacher> teacherOfTheCourse;

	public Course() {}
	
	public Course(int course_id, String Name) {
		super();
		this.course_id = course_id;
		this.courseName = Name;
		studentsInCourse = new ArrayList<Student>();
		teacherOfTheCourse = new ArrayList<Teacher>();
	}

	/***** getters *****/
	public List<Student> getStudentsInCourse() {
		return this.studentsInCourse;
	}

	public List<Teacher> getTeacherOfTheCourse() {
		return this.teacherOfTheCourse;
	}

	public String getCourseName() {
		return this.courseName;
	}
	public int getColumn_id() {
		return this.id;
	}
	
	public int getCourse_id() {
		return this.course_id;
	}

	/***** setters ****/

	public void setStudentsInCourse(List<Student> studentsInCourse) {
		this.studentsInCourse = studentsInCourse;
	}

	public void setTeacherOfTheCourse(List<Teacher> teacherOfTheCourse) {
		this.teacherOfTheCourse = teacherOfTheCourse;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}


	public void addStudentsInCourse(Student student) {
		this.studentsInCourse.add(student);
	}

	public void addTeachersInCourse(Teacher teacher) {
		this.teacherOfTheCourse.add(teacher);
	}

	
}
