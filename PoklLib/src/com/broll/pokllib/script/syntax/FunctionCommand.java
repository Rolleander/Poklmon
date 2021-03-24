package com.broll.pokllib.script.syntax;

import java.util.ArrayList;
import java.util.List;

import com.broll.pokllib.script.ScriptIdentifiers;
import com.broll.pokllib.script.SyntaxError;

public class FunctionCommand {

	private String commandName;
	private List<Parameter> parameter;

	public FunctionCommand(String name) {
		this.commandName = name;
		parameter = new ArrayList<Parameter>();
	}

	public void addParameter(Parameter object) {
		parameter.add(object);
	}

	public String getCommandName() {
		return commandName;
	}

	public List<Parameter> getParameter() {
		return parameter;
	}

	public static FunctionCommand build(String code) throws SyntaxError {
		// Command syntax:
		// do methodename
		// do methodename @ ...parameters..
		SyntaxString str = new SyntaxString(code);
		FunctionCommand command = null;
		// skip (
		String name = str.cutAttribute(ScriptIdentifiers.FUNCTION_START);
		str.skip(ScriptIdentifiers.FUNCTION_START);
		String content = str.cutLastAttribute(ScriptIdentifiers.FUNCTION_END);
		String[] params = content.split(",");
		command = new FunctionCommand(name);
		if(params[0]!=null&&params[0].length()>0)
		{
		for (String param : params) {
			command.addParameter(new Parameter(param));
		}
		}
		return command;

	}
}
