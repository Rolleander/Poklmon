package com.broll.poklmon.map.areas;

import com.broll.pokllib.map.MapFile;
import com.broll.poklmon.script.PackageImporter;
import com.broll.poklmon.script.ProcessingUtils;
import com.broll.poklmon.script.ScriptEngineFactory;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;

public class MapAreaContainer {

	private List<MapArea> areas = new ArrayList<MapArea>();

	public MapAreaContainer(MapFile mapFile) {
		if (mapFile.getAreaScripts() != null) {
			for (String script : mapFile.getAreaScripts()) {
				if (script != null && script.length() > 0) {
					MapArea area = descriptArea(script);
					areas.add(area);
				}else{
					//add empty area (to ensure right order)
					areas.add(new MapArea());
				}
			}
		}
	}

	public MapArea descriptArea(String script) {
		MapArea area = new MapArea();
		AreaScriptActions actions = new AreaScriptActions(area);
		ScriptEngine engine =  ScriptEngineFactory.createScriptEngine();

		//ScriptEngine engine = new ScriptEngineManager(null).getEngineByName("rhino");
		PackageImporter importer = new PackageImporter();
		importer.addPackage(AreaType.class.getPackage());

		engine.put("area", actions);
		ProcessingUtils.runScript(engine, importer.buildScript(script));
		return area;
	}

	public MapArea getArea(int id) {
		if (id < 0 || id >= areas.size()) {
			return null;
		}
		return areas.get(id);
	}

}
