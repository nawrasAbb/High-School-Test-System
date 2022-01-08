package FINAL.project;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "person")
public class Person implements Serializable {


	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int person_Id;
	private String personName;
	private String username;
	@Column(name = "Person_Authorization")
	private int authorization;
	private boolean status;
	@Column(name = "Person_password")
	private String password;

	
	
	public Person() {}
	
	public Person(int person_Id, String personName, String username, int authorization, String password) {
		this.person_Id = person_Id;
		this.personName = personName;
		this.username = username;
		this.authorization = authorization;
		this.status = false;
		this.password = password;
	}

	/***** setters ******/

	public void setPerson_Id(int person_Id) {
		this.person_Id = person_Id;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setAuthorization(int authorization) {
		this.authorization = authorization;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/***** getters ******/
	public int getColumn_id() {
		return this.id;
	}
	
	public int getPerson_Id() {
		return this.person_Id;
	}

	public String getPersonName() {
		return this.personName;
	}

	public String getUsername() {
		return this.username;
	}

	public int getAuthorization() {
		return this.authorization;
	}

	public boolean getStatus() {
		return this.status;
	}
	public String getPassword() {
		return this.password;
	}


}



