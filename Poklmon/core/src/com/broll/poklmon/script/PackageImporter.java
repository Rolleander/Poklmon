package com.broll.poklmon.script;

import java.util.ArrayList;
import java.util.List;

public class PackageImporter {

	private List<String> imports=new ArrayList<String>();

	
	public void addPackage(Package pack)
	{
	 imports.add(pack.getName());
	}
	
	public String buildScript(String script)
	{
		String importString="";
		for(int i=0; i<imports.size(); i++)
		{
			importString+=imports.get(i);
			if(i<imports.size()-1)
			{
				importString+=",";
			}
		}	
		String effect="with(new JavaImporter("+importString+")){";
		effect+=script;
		effect+="}";
		return effect;
	}
	
}
