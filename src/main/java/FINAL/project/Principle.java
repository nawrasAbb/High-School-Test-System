package FINAL.project;
import java.io.Serializable;

import javax.persistence.Entity; 
import javax.persistence.Table;

@Entity(name="Principle")
@Table(name = "principle")

public class Principle extends Person implements Serializable {


	private static final long serialVersionUID = 1L;

	public Principle() { super();}
	
	public Principle(int personId, String personName, String username, int authorization, String password) {
		super(personId, personName, username, authorization, password);
	}
}



