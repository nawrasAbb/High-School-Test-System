package FINAL.project;
import java.io.Serializable; 

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import FINAL.project.StartExam;

@Entity
@Table(name = "request")
public class Request implements Serializable {

	private static final long serialVersionUID = -7029466617153317290L;
	//Fields 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int RequestID;
	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	private boolean available;
	private String Explanation;
	private String ExtraTime;
	private String ExamCode;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "request_id")
	private StartExam startExam;

	// getters 
	
	public String getExplanation() {
		return this.Explanation;
	}

	public String getExtraTime() {
		return this.ExtraTime;
	}

	public String getCode() {
		return this.ExamCode;
	}
	public StartExam getStartExam() {
		return this.startExam;
	}
	//sitters
	public void setExplanation(String explanation) {
		this.Explanation = explanation;
	}

	public void setExtraTime(String extraTime) {
		this.ExtraTime = extraTime;
	}

	public void setCode(String code) {
		this.ExamCode =code;
	}
	public void setStartExam(StartExam exam) {
		this.startExam =exam;
	}

	// constructor 
	public Request(String explanation, String time, String code2,StartExam theExam ) {
		this.Explanation = explanation;
		this.ExtraTime = time;
		this.ExamCode = code2;
		this.startExam=theExam;
		this.available=true;
	}

	public Request() {}

}