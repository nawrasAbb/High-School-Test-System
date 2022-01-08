package FINAL.project;

import FINAL.project.StartExam;
import FINAL.project.AnswerSheet;
import FINAL.project.Student;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "takeexams")
public class TakeExam implements Serializable {

	private static final long serialVersionUID = 2576044686348269040L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	boolean ExamType;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "AnswerSheet_id")
	private AnswerSheet answerSheet;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "startExam_id")
	private StartExam startExam;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "Student_id")
	private Student student;

	public TakeExam() {
	}

	public TakeExam(boolean ExamType, Student student) {
		this.ExamType = ExamType;

		this.student = student;

	}

	/***** getters ****/
	public boolean isType() {
		return this.ExamType;
	}

	public StartExam getStartExam() {
		return this.startExam;
	}

	public AnswerSheet getAnswerSheet() {
		return this.answerSheet;
	}

	public Student getStudent() {
		return this.student;
	}

	public int getColumn_id() {
		return id;
	}

	/***** setters ******/
	public void setType(boolean ExamType) {
		this.ExamType = ExamType;
	}

	public void setAnswerSheet(AnswerSheet answerSheet) {
		this.answerSheet = answerSheet;
		
		
	}

	public void setStartExam(StartExam startExam) {
		this.startExam = startExam;
	}

	public void setStudent(Student student) {
		this.student = student;
		student.addTakeExam(this);
	}

	public void addTime(StartExam startExam2) {
		OnlineExamInterface.mystartExam=startExam2;
		
	}


}