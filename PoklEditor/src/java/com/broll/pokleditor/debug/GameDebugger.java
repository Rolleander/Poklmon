package com.broll.pokleditor.debug;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.broll.pokleditor.main.PoklEditorMain;
import com.broll.pokllib.game.StartInformation;

public class GameDebugger {

    public static File debugPath;

    public static void debugGame(StartInformation info) {
        String start = StartInformation.parseToConsole(info);
        System.out.println("Debug-Command: " + start);
        try {
            String[] cmds = getCommand("path:" + PoklEditorMain.POKL_PATH.getAbsolutePath() + " " + start);
            System.out.println("Exec: " + Arrays.stream(cmds).collect(Collectors.joining(" ")));
            Process p = new ProcessBuilder().inheritIO().command(cmds).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] getCommand(String startCommands) {
        String path = debugPath.getAbsolutePath();
        return new String[]{"java","--add-exports=java.base/sun.security.action=ALL-UNNAMED", "-XstartOnFirstThread", "-jar", path, startCommands};
    }

}
