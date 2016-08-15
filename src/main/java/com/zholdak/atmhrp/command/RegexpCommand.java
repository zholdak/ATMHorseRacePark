package com.zholdak.atmhrp.command;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexpCommand extends Command {

	private Pattern pattern;
	
	public RegexpCommand(String command, Execution exec) {
		super(command, exec);
		this.pattern = Pattern.compile(command);
	}
	
	@Override
	public boolean matches(String input) {
		return input != null && pattern.matcher(input).matches();
	}

	@Override
	public String[] getArgs(String input) {
		
		List<String> argsList = new ArrayList<>();
		
		Matcher m = pattern.matcher(input);
		if( m.find() ) {
			for( int i=1; i<=m.groupCount(); i++ ) {
				argsList.add( m.group( i ) );
			}
		}
		return argsList.toArray(new String[argsList.size()]);
	}
}
