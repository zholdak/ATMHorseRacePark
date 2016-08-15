package com.zholdak.atmhrp.input;

public abstract class Input {

	public Input() {
	}
	
	public abstract void open() throws Exception;
	
	public abstract String get() throws Exception;

	public abstract void close() throws Exception;
}
