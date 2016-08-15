package com.zholdak.atmhrp.output;

public abstract class Output {

	public Output() {
	}
	
	public abstract void open() throws Exception;
	
	public abstract void out(String message) throws Exception;

	public abstract void close() throws Exception;
}
