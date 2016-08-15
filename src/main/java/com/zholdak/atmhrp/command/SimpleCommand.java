package com.zholdak.atmhrp.command;

public final class SimpleCommand extends Command {

	public SimpleCommand(String command) {
		super(command, null);
	}
	
	public SimpleCommand(String command, Execution exec) {
		super(command, exec);
	}

	@Override
	public String[] getArgs(String input) {
		return new String[]{input};
	}
}
