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
import FINAL.project.Teacher;
import FINAL.project.Student;

@Entity
@Table(name = "subjects")
public class Subject implements Serializable {

	private static final long serialVersionUID = 8848726882760450032L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int subject_id;
	private String subjectName;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = Teacher.class)
	@JoinTable(name = "subjects_teachers", joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"))
	private List<Teacher> teachersOfTheSubjec;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = Student.class)
	@JoinTable(name = "subjects_students", joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
	private List<Student> studentsInTheStubject;


public Subject() {}
public Subject(int subject_id, String Name) {
	super();
	this.subject_id = subject_id;
	this.subjectName = Name;
	teachersOfTheSubjec = new ArrayList<Teacher>();
	studentsInTheStubject = new ArrayList<Student>();
}

/**** setters *****/
public void setTeachersOfTheSubjec(List<Teacher> teachersOfTheSubjec) {
	this.teachersOfTheSubjec = teachersOfTheSubjec;
}

public void setStudentsInTheStubject(List<Student> studentsInTheStubject) {
	this.studentsInTheStubject = studentsInTheStubject;
}

public void setSubjectName(String subjectName) {
	this.subjectName = subjectName;
}

public void setSubject_id(int subject_id) {
	this.subject_id = subject_id;
}

/***** getters *****/
public List<Teacher> getTeachersOfTheSubjec() {
	return this.teachersOfTheSubjec;
}

public List<Student> getStudentsInTheStubject() {
	return this.studentsInTheStubject;
}

public String getSubjectName() {
	return this.subjectName;
}

public int getColumn_id() {
	return this.id;
}

public int getSubject_id() {
	return this.subject_id;
}

}


