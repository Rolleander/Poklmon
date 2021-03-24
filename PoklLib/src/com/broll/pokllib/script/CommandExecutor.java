package com.broll.pokllib.script;

import java.util.ArrayList;
import java.util.List;

import com.broll.pokllib.script.syntax.CompareTyp;
import com.broll.pokllib.script.syntax.FunctionCommand;
import com.broll.pokllib.script.syntax.IsCommand;
import com.broll.pokllib.script.syntax.Parameter;
import com.broll.pokllib.script.syntax.ParameterType;
import com.broll.pokllib.script.syntax.SetCommand;
import com.broll.pokllib.script.syntax.Value;
import com.broll.pokllib.script.syntax.VarCommand;
import com.broll.pokllib.script.syntax.VariableException;

public class CommandExecutor {

	private VariableStorage variableStorage;
	private ScriptInterface scriptInterface;

	public CommandExecutor() {

	}

	public void setVariableStorage(VariableStorage variableStorage) {
		this.variableStorage = variableStorage;
	}

	public boolean executeIs(IsCommand isCommand) throws VariableException, SyntaxError {
		String identifier = isCommand.getVariableName();
		Value v1=null;
		if(identifier.startsWith(ScriptIdentifiers.FUNCTION))
		{
			v1 = callFunction(identifier.substring(ScriptIdentifiers.FUNCTION.length()));
		}
		else
		{
			v1 = variableStorage.getVariable(identifier);
		}
		Parameter parameter = isCommand.getParameter();
		CompareTyp compareTyp = isCommand.getCompareTyp();
		Value v2 = getValue(parameter);
		switch (compareTyp) {
		case EQUALS:
			return v1.isEqual(v2);
		case LESS:
			return v1.isLowerThan(v2);
		case MORE:
			return v1.isHigherThan(v2);
		case LESSOREQUAL:
			return v1.isEqualOrLowerThan(v2);
		case MOREOREQUAL:
			return v1.isEqualOrHigherThan(v2);
		}
		throw new VariableException();
	}

	public void executeVar(VarCommand varCommand) throws VariableException, SyntaxError {
		Parameter parameter = varCommand.getParameter();
		String identifier = varCommand.getVariableName();
		initValue(identifier, getValue(parameter));
	}

	public void executeSet(SetCommand setCommand) throws VariableException, SyntaxError {
		String identifier = setCommand.getVariableName();
		Parameter parameter = setCommand.getParameter();
		Value value1 = variableStorage.getVariable(identifier);
		Value value2 = getValue(parameter);
		switch (setCommand.getTyp()) {
		case TO:
			value1.setValue(value2);
			break;
		case ADD:
			value1.addValue(value2);
			break;
		case SUB:
			value1.subValue(value2);
			break;
		case DIV:
			value1.divValue(value2);
			break;
		case MUL:
			value1.mulValue(value2);
			break;
		}
	}
	
	private Value callFunction(String function) throws VariableException, SyntaxError
	{
		FunctionCommand functionCommand = FunctionCommand.build(function);
		String name = functionCommand.getCommandName();
		List<Value> params = new ArrayList<Value>();
		for (Parameter p : functionCommand.getParameter()) {
			params.add(getValue(p));
		}
		return new Value(scriptInterface.function(name.toLowerCase(), params));
	}

	public Value getValue(Parameter parameter) throws VariableException, SyntaxError {
		ParameterType type = parameter.getType();
		if (type == ParameterType.VARIABLE) {
			String identifier = (String) parameter.getValue();
			// read value from storage
			return variableStorage.getVariable(identifier);
		} else if (type == ParameterType.FUNCTION) {
			String function = (String) parameter.getValue();
			return callFunction(function);
		} else {
			return new Value(parameter.getValue());
		}
	}

	public void initValue(String identifier, Value value) throws VariableException {
		variableStorage.addVariable(identifier.toLowerCase(), value);
	}

	public void setScriptInterface(ScriptInterface scriptInterface) {
		this.scriptInterface = scriptInterface;
	}

}
