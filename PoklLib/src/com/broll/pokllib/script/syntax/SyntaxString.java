package com.broll.pokllib.script.syntax;

import com.broll.pokllib.script.SyntaxError;

public class SyntaxString {

	private String code;

	public SyntaxString(String code)
	{
		this.code=code;
	}
	
	public String getCode() {
		return code;
	}
	
	//skips to part
	public void skip(String sign) throws SyntaxError
	{
		int index=code.toLowerCase().indexOf(sign.toLowerCase());
		if(index==-1)
		{
			throw new SyntaxError();
		}
		code=code.substring(index+sign.length(), code.length());
	}
	
	public boolean hasMark(String mark)
	{
		int index=code.toLowerCase().indexOf(mark.toLowerCase());
		return index!=-1;
	}
	
	public String cutAttribute(String to) throws SyntaxError
	{
		int index=code.toLowerCase().indexOf(to.toLowerCase());
		if(index==-1)
		{
			throw new SyntaxError();
		}
		String cut=code.substring(0, index);
		code=code.substring(index,code.length());
		return cut.trim();
	}
	
	public String cutAttribute() throws SyntaxError
	{
		int index=code.length();
		if(index==-1)
		{
			throw new SyntaxError();
		}
		String cut=code.substring(0, index);
		code=code.substring(index,code.length());
		return cut.trim();
	}
	
	@Override
	public String toString() {
		return code;
	}

	public String cutLastAttribute(String to) throws SyntaxError {
		int index=code.toLowerCase().lastIndexOf(to.toLowerCase());
		if(index==-1)
		{
			throw new SyntaxError();
		}
		String cut=code.substring(0, index);
		code=code.substring(index,code.length());
		return cut.trim();
	}
}
