package com.zholdak.atmhrp;

import com.zholdak.atmhrp.atm.hrp.HorseRacePark;
import com.zholdak.atmhrp.input.ConsoleInput;
import com.zholdak.atmhrp.output.ConsoleOutput;

public class Application {

	public static void main(String[] args) {
		
		new Application().go();
	}

	private void go() {
		
		ConsoleInput input = new ConsoleInput();
		ConsoleOutput output = new ConsoleOutput();
		
		try {
			new HorseRacePark(input, output).start();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
}
