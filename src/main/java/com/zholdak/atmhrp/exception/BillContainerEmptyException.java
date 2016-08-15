package com.zholdak.atmhrp.exception;

public class BillContainerEmptyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BillContainerEmptyException(String message) {
		super(message);
	}
}
