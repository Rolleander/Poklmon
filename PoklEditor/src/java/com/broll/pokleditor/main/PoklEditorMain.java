package com.broll.pokleditor.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.broll.pokleditor.data.PoklData;
import com.broll.pokleditor.debug.DebuggerClasses;
import com.broll.pokleditor.debug.GameDebugger;
import com.broll.pokleditor.gui.script.JavascriptFormatter;
import com.broll.pokleditor.gui.script.ScriptEnvironments;
import com.broll.pokleditor.map.MapTileEditor;
import com.broll.pokleditor.panel.EditorPanel;
import com.broll.pokleditor.resource.ImageLoader;
import com.broll.pokleditor.resource.SoundLoader;
import com.broll.pokleditor.window.EditorWindow;
import com.broll.pokleditor.window.LoadingWindow;
import com.broll.pokllib.main.KryoDataControl;
import com.broll.pokllib.main.PoklLib;


public class PoklEditorMain {

    private static KryoDataControl dataControl;
    public static File POKL_PATH;
    private static File dataPath, debugDataPath;

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                //in ide
                String path = PoklEditorMain.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                path = path.substring(0, path.length() - "PoklEditor/build/classes/java/main/".length());
                GameDebugger.debugPath = new File(path + "/Poklmon/desktop/build/libs/Poklmon.jar");
                path = path + "Poklmon/assets/";
                POKL_PATH = new File(path + "data/");
            } else {
                String path = PoklEditorMain.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                System.out.println("EditorClass: " + path);
                String decodedPath = URLDecoder.decode(path, "UTF-8");
                decodedPath = decodedPath.substring(0, decodedPath.length() - "PoklEditor.jar".length());
                POKL_PATH = new File(decodedPath + "data/");
                GameDebugger.debugPath = new File(decodedPath + "/Poklmon.jar");
            }
            System.out.println("POKL_PATH: " + POKL_PATH.getAbsolutePath());
            System.out.println("DEBUG_PATH: " + GameDebugger.debugPath.getAbsolutePath());
            ImageLoader.initPath(POKL_PATH);
            SoundLoader.initPath(POKL_PATH);
            LoadingWindow.open();
            System.out.println("Load Tiles...");
            MapTileEditor.tiles = ImageLoader.loadTileset();
            System.out.println("Load DbControl...");
            dataPath = new File(POKL_PATH.getPath() + "/poklmon.data");
            debugDataPath = new File(POKL_PATH.getPath() + "/poklmon-debug.data");
            dataControl = new KryoDataControl();
            System.out.println("Load Data...");
            dataControl.read(new FileInputStream(dataPath));
            System.out.println("Init PoklLib..");
            PoklLib.init(dataControl);
            // PoklLib.init(new XmlDataControl());
            System.out.println("Init Window...");
            EditorWindow window = new EditorWindow();
            System.out.println("Load Indexes...");
            PoklData.loadIndexes();
            System.out.println("Create caches...");
            ImageLoader.createCache();
            JavascriptFormatter.init();
            System.out.println("Load debugger classes...");
            DebuggerClasses.loadDebuggerClasses();
            System.out.println("Init scripting environment...");
            ScriptEnvironments.init();
            System.out.println("Show Window...");
            EditorPanel panel = new EditorPanel();
            window.open(panel);
        } catch (Exception e) {
            e.printStackTrace();
            BugSplashDialog.showError("Failed to start editor: " + e.getMessage());
            System.exit(0);
        }
    }

    public static void writeSaveData() {
        System.out.println("Saving...");
        writeData(dataPath);
    }

    public static void writeDebugData(){
        System.out.println("Save debug data...");
        writeData(debugDataPath);
    }

    private static void writeData(File dataPath){
        try {
            // dataControl.saveData("data/poklmon.data");
            dataControl.commit(new FileOutputStream(dataPath));
        } catch (Exception e) {
            e.printStackTrace();
            BugSplashDialog.showError("Failed to save data: " + e.getMessage());
        }
    }


    public static void cleanupDebug() {
        PoklLib.init(dataControl);
    }

    /*
     * public static void forceDebugSave() { try {
     * System.out.println("Saving Test...");
     * dataControl.saveData("data/poklmon_test.data"); } catch (Exception e) {
     * e.printStackTrace(); BugSplashDialog.showError("Failed to save data: " +
     * e.getMessage()); } }
     */
}
