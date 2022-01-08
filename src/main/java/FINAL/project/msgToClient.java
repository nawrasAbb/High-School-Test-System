package FINAL.project;

import java.io.Serializable;

public class msgToClient implements Serializable {

	private static final long serialVersionUID = 1L;

	private String command;
	private Object myObject;
	private String myClass;
	private String operation;

	public String getMyClass() {
		return myClass;
	}

	public void setMyClass(String myClass) {
		this.myClass = myClass;
	}

	public msgToClient(String myClass, String msg, Object myObject, String operation) {

		this.command = msg;
		this.myObject = myObject;
		this.myClass = myClass;
		this.operation = operation;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Object getMyObject() {
		return myObject;
	}

	public void setMyObject(Object myObject) {
		this.myObject = myObject;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}
