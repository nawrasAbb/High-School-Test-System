package FINAL.project;
import FINAL.project.StartExam;
import FINAL.project.TakeExam;

import java.io.Serializable;
import java.util.ArrayList; 
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Table(name = "answerSheets")
public class AnswerSheet implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private boolean isConfirmed;
	private boolean isChecked;
	private String Notes;
	private double grade;
	private String ChangeGradeExplination;
	@Column
	@ElementCollection(targetClass = Integer.class)
	private List<Integer> answersList;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "startExam_id")
	private StartExam startExam;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private Student student;


	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "takeExam_id")
	private TakeExam takeExam;

	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "exam_id")
	private Exam exam1;

	public AnswerSheet() {
	}



	public AnswerSheet(StartExam startExam, Student student, TakeExam takeExam, Exam exam, List<Integer> answers) {
		
		this.isConfirmed = false;
		this.isChecked = false;
		this.Notes = " ";
		this.startExam = startExam;
		this.grade = -1;
		this.student = student;
		this.answersList = new ArrayList<Integer>();
		this.answersList = answers;
		this.takeExam=takeExam;
		
		this.exam1 = exam;
	}



	/***** getters *****/
	
	
	public int getColumn_id() {
		return this.id;
	}
	
	public String getChangeGradeExplination() {
		return ChangeGradeExplination;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public int getId() {
		return id;
	}



	public Exam getExam1() {
		return exam1;
	}



	public boolean getIsConfirmed() {
		return this.isConfirmed;
	}

	public boolean getIsChecked() {
		return this.isChecked;
	}

	public String getNotes() {
		return this.Notes;
	}

	public StartExam getStartExam() {
		return this.startExam;
	}

	public Student getStudent() {
		return this.student;
	}

	public List<Integer> getAnswersList() {
		return this.answersList;
	}

	public double getGrade() {
		return this.grade;
	}

	public TakeExam getTakeExam() {
		return this.takeExam;
	}

	public Exam getExam() {
		return this.exam1;
	}

	/**** setters ******/


	public void setChangeGradeExplination(String changeGradeExplination) {
		ChangeGradeExplination = changeGradeExplination;
	}

	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public void setNotes(String notes) {
		this.Notes = notes;
	}

	public void setStartExam(StartExam startExam) {
		this.startExam = startExam;
	}

	public void setStudent(Student student) {
		this.student = student;
		student.getAllAnswerSheets().add(this);
	}

	public void setAnswersList(List<Integer> answersList) {
		this.answersList = answersList;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public void setTakeExam(TakeExam takeExam) {
		this.takeExam = takeExam;
	}

	public void setExam(Exam exam) {
		this.exam1 = exam;
	}
 
}

