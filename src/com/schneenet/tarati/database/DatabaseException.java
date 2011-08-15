package com.schneenet.tarati.database;

public class DatabaseException extends Throwable {
	
	/**
	 * Because Eclipse wanted it
	 */
	private static final long serialVersionUID = -5186966705543949418L;

	public DatabaseException(Throwable cause) {
		super(cause);
	}
	
	public DatabaseException(String msg) {
		super(msg);
	}
	
}
