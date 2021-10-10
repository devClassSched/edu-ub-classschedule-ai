package edu.ub.classscheduleai.exception;

public class AlreadySchedForSemesterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AlreadySchedForSemesterException() {
		super();
	}
	
	public AlreadySchedForSemesterException(String msg) {
		super(msg);
	}

}
