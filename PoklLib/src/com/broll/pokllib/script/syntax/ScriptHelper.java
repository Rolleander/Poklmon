package com.broll.pokllib.script.syntax;

import com.broll.pokllib.script.SyntaxError;

public class ScriptHelper {

	public static String getString(DoCommand cmd, int nr) throws SyntaxError {
		Parameter param = getParameter(cmd, nr);
		if (param.getType() == ParameterType.STRING) {
			return (String) param.getValue();
		}
		throw new SyntaxError();
	}

	public static double getNumber(DoCommand cmd, int nr) throws SyntaxError {
		Parameter param = getParameter(cmd, nr);
		if (param.getType() == ParameterType.NUMBER) {
			return (Double) param.getValue();
		}
		throw new SyntaxError();
	}

	public static boolean getBoolean(DoCommand cmd, int nr) throws SyntaxError {
		Parameter param = getParameter(cmd, nr);
		if (param.getType() == ParameterType.BOOLEAN) {
			return (Boolean) param.getValue();
		}
		throw new SyntaxError();
	}

	private static Parameter getParameter(DoCommand cmd, int nr) throws SyntaxError {
		if (nr > -1 && nr < cmd.getParameter().size()) {
			return cmd.getParameter().get(nr);
		}
		throw new SyntaxError();
	}

}
