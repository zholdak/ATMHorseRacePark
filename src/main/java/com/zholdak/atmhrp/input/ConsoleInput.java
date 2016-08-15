package com.zholdak.atmhrp.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class ConsoleInput extends Input {

	private BufferedReader br = null;

	public ConsoleInput() {
		super();
	}

	@Override
	public void open() throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
	}
	
	@Override
	public String get() throws IOException {
		return br.readLine();
	}

	@Override
	public void close() throws Exception {
		if( br != null ) {
			br.close();
			br = null;
		}
	}
}
