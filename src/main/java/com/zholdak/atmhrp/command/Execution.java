package com.zholdak.atmhrp.command;

public interface Execution {
	public String execute(Command command, String input) throws Exception;
}
