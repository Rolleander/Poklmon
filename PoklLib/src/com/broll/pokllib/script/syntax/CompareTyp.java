package com.broll.pokllib.script.syntax;

import com.broll.pokllib.script.ScriptIdentifiers;

public enum CompareTyp {

	MOREOREQUAL(ScriptIdentifiers.MOREOREQUAL), LESSOREQUAL(ScriptIdentifiers.LESSOREQUAL), EQUALS(
			ScriptIdentifiers.EQUAL), MORE(ScriptIdentifiers.MORE), LESS(ScriptIdentifiers.LESS);

	private String compareName;

	private CompareTyp(String name) {
		this.compareName = name;
	}

	public String getCompareName() {
		return compareName;
	}
}
