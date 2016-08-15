package com.zholdak.atmhrp.command;

import com.zholdak.atmhrp.exception.ExecutionNotInitializedException;

public abstract class Command {

	protected String command;
	private Execution exec;
	
	public Command(String command, Execution exec) {
		this.command = command;
		this.exec = exec;
	}
	
	public String getCommand() {
		return command;
	}
	
	public boolean matches(String arg) {
		return arg != null && command.equalsIgnoreCase(arg);
	}

	public abstract String[] getArgs(String input);
	
	public String execute(String input) throws Exception {
		if( exec == null ) {
			throw new ExecutionNotInitializedException(String.format("for command '%s'", command));
		}
		return exec.execute(this, input);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((command == null) ? 0 : command.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Command))
			return false;
		Command other = (Command) obj;
		if (command == null) {
			if (other.command != null)
				return false;
		} else if (!command.equals(other.command))
			return false;
		return true;
	}
}
