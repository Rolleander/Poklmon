package com.broll.poklmon.game.scene.script;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;

import com.broll.pokllib.jscript.PackageImporter;
import com.broll.poklmon.game.GameManager;
import com.broll.poklmon.game.scene.ScriptSceneProcess;
import com.broll.poklmon.map.object.MapObject;

public abstract class ScriptingEnvironment {

	protected List<Object> controller = new ArrayList<Object>();
	protected List<String> objectNames = new ArrayList<String>();
	protected List<Package> importedPackages = new ArrayList<Package>();
	protected PackageImporter importer = new PackageImporter();

	public ScriptingEnvironment() {
		buildImports();
	}

	public void addController(GameManager game, MapObject caller) {
		controller.clear();
		objectNames.clear();
		addControllers(game, caller);
	}

	protected abstract void addControllers(GameManager game, MapObject caller);

	protected void addPackage(Class subclass) {
		importedPackages.add(subclass.getPackage());
	}

	protected abstract void addImports();

	public void buildImports() {
		addImports();
		for (Package pack : importedPackages) {
			importer.addPackage(pack);
		}
	}

	public void importObjects(ScriptEngine engine) {
		for (int i = 0; i < controller.size(); i++) {
			engine.put(objectNames.get(i), controller.get(i));
		}
	}

	protected void addController(Object controller, String objectName) {
		this.controller.add(controller);
		this.objectNames.add(objectName);
	}

	public void initController(MapObject object, final ScriptSceneProcess sceneProcess) {
		for (Object control : controller) {
			if (control instanceof CommandControl) {
				((CommandControl) control).init(object, new ScriptProcessInteraction() {
					@Override
					public void waitForResume() {
						sceneProcess.waitForResume();
					}
					@Override
					public void resume() {
						sceneProcess.resume();
					}
				});
			}
		}
	}

	public PackageImporter getImporter() {
		return importer;
	}

	public List<Object> getController() {
		return controller;
	}

	public List<String> getObjectNames() {
		return objectNames;
	}

}
