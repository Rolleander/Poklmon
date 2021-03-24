package com.broll.pokllib.script;

public enum LineType {

	
	COMMENT(ScriptIdentifiers.COMMENT_LINE),VAR(ScriptIdentifiers.VAR),
	SET(ScriptIdentifiers.SET),DO(ScriptIdentifiers.COMMANDO),IS(ScriptIdentifiers.IF),
	ELSE(ScriptIdentifiers.ELSE),END(ScriptIdentifiers.END),STOP(ScriptIdentifiers.STOP),
	LABEL(ScriptIdentifiers.LABEL),GOTO(ScriptIdentifiers.GOTO);
	
	private String lineName;
	
	private LineType(String name)
	{
	    this.lineName=name;
	}
	
	public String getLineName()
    {
        return lineName;
    }
}
