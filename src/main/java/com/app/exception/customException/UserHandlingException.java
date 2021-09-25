package com.app.exception.customException;

@SuppressWarnings("serial")
public class UserHandlingException extends RuntimeException {
	public UserHandlingException(String mesg) {
		super(mesg);
	}
}
