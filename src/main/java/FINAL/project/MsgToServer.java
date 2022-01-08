package FINAL.project;

import java.io.Serializable;

public class MsgToServer implements Serializable {

	private static final long serialVersionUID = 6478488422500507486L;

	private String command;
	private Object myObject;
	private String myClass;
	private String operation;

	String getMyClass() {
		return myClass;
	}

	public void setMyClass(String myClass) {
		this.myClass = myClass;
	}

	public MsgToServer(String myClass, String msg, Object myObject, String operation) {
		this.myClass = myClass;
		this.command = msg;
		this.myObject = myObject;
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
