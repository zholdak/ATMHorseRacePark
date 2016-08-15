package com.zholdak.atmhrp.output;

public final class ConsoleOutput extends Output {

	public ConsoleOutput() {
		super();
	}
	
	@Override
	public void open() throws Exception {
	}

	@Override
	public void out(String message) throws Exception {
		if( message != null ) {
			System.out.println(message);
		}
	}

	@Override
	public void close() throws Exception {
	}

}
