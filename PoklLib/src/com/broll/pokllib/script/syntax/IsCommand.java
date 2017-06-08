package com.broll.pokllib.script.syntax;

import com.broll.pokllib.script.ScriptIdentifiers;
import com.broll.pokllib.script.SyntaxError;

public class IsCommand {

	private String variableName;
	private CompareTyp compareTyp;
	private Parameter parameter;

	public IsCommand(String name) {
		this.variableName = name;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setCompareTyp(CompareTyp compareTyp) {
		this.compareTyp = compareTyp;
	}

	public Parameter getParameter() {
		return parameter;
	}

	public CompareTyp getCompareTyp() {
		return compareTyp;
	}

	public static IsCommand build(String code) throws SyntaxError {
		// Command syntax:
		// is variablenname equal parameter
		SyntaxString str = new SyntaxString(code);
		str.skip(ScriptIdentifiers.IF);// skip is
		IsCommand cmd = null;

		for (CompareTyp compareTyp : CompareTyp.values()) {
			String comp = compareTyp.getCompareName();
			if (str.hasMark(comp)) {
				String var = str.cutAttribute(comp);
				if (var == null || var.length() == 0) {
					throw new SyntaxError();
				}

				cmd = new IsCommand(var);
				cmd.setCompareTyp(compareTyp);
				str.skip(comp);
			}
		}
		if (cmd == null) {
			String var = str.cutAttribute();
			cmd = new IsCommand(var);
			cmd.setCompareTyp(CompareTyp.EQUALS);
			cmd.setParameter(new Parameter("true"));
			return cmd;
		}

		cmd.setParameter(new Parameter(str.cutAttribute()));
		return cmd;

	}
}
