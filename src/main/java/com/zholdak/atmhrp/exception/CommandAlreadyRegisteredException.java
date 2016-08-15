package com.zholdak.atmhrp.exception;

public class CommandAlreadyRegisteredException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CommandAlreadyRegisteredException(String message) {
		super(message);
	}
}
