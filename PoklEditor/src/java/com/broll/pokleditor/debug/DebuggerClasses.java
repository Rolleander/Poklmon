package com.broll.pokleditor.debug;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DebuggerClasses {

    private static Map<String, Class> debuggerClasses = new HashMap<>();
    public static URLClassLoader debugerClassLoader;

    public static void loadDebuggerClasses() throws Exception {
        String pathToJar = GameDebugger.debugPath.getAbsolutePath();
        JarFile jarFile = new JarFile(pathToJar);
        Enumeration<JarEntry> e = jarFile.entries();

        URL[] urls = {new URL("jar:file:" + pathToJar + "!/")};
        debugerClassLoader = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                continue;
            }
            // -6 because of .class
            String className = je.getName().substring(0, je.getName().length() - 6);
            if (className.startsWith("com/broll/poklmon")) {
                className = className.replace('/', '.');
                debuggerClasses.put(className, debugerClassLoader.loadClass(className));
            }
        }
    }

    public static Class getClass(String clazzName) {
        return debuggerClasses.get(clazzName);
    }

}
