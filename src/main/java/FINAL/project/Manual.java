package FINAL.project;
import java.io.File;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ManualExams")
public class Manual  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "startExam_id")
	private StartExam startExam;

	private File ExamFile;
	private File submittedExam;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private Student student;
	
	
	public Manual(StartExam exam,Student s) {
		super();
		this.startExam = exam;
	this.student=s;
	}
	public Manual() {}


	public File getWordFile() {
		return ExamFile;
	}

	public File getSubmittedExam() {
		return submittedExam;
	}
	public void setSubmittedExam(File submittedExam) {
		this.submittedExam = submittedExam;
	}
	public void setWordFile(File wordFile) {
		this.ExamFile = wordFile;
	}
	public StartExam getStartExam() {
		return this.startExam;
	}
	public void setStartExam(StartExam startExam) {
		this.startExam = startExam;
	}
	
	
	
}

