package com.broll.pokllib.game;


import java.io.File;

public class StartInformation {

    private boolean debugGame;
    private boolean debugMap;
    private boolean debugAttack;
    private boolean debugAnimation;
    private boolean touchControling = false;

    private String debugScene;
    private int mapId, mapX, mapY;
    private int attackId;
    private int animationId;
    private File path;

    public StartInformation() {
        debugGame = false;
        debugMap = false;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public void setTouchControling(boolean touchControling) {
        this.touchControling = touchControling;
    }

    public static String parseToConsole(StartInformation info) {
        if (info.isDebugAnimation()) {
            return "debug:animation:" + info.getAnimationId();
        } else if (info.isDebugAttack()) {
            return "debug:attack:" + info.getAttackId();
        } else if (info.isDebugMap()) {
            return "debug:map:" + info.getMapId() + "," + info.getMapX() + "," + info.getMapY();
        } else if (info.isDebugGame()) {
            return "debug:game";
        }
        return "game";
    }

    public static StartInformation parseFromConsole(String in) {
        StartInformation info = new StartInformation();
        for (String cmd : in.toLowerCase().split("\\s+")) {
            if (cmd.startsWith("path:")) {
                info.setPath(new File(cmd.substring("path:".length())));
            } else if (cmd.equals("debug:game")) {
                info.debugScene("TitleMenuState");
            } else if (cmd.startsWith("debug:map:")) {
                String[] params = cmd.substring("debug:map:".length()).split(",");
                info.debugMap(Integer.parseInt(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]));
            } else if (cmd.startsWith("debug:attack:")) {
                String param = cmd.substring("debug:attack:".length());
                info.debugAttack(Integer.parseInt(param));
            } else if (cmd.startsWith("debug:animation:")) {
                String param = cmd.substring("debug:animation:".length());
                info.debugAnimation(Integer.parseInt(param));
            }
        }
        return info;
    }

    public void debugScene(String scene) {
        this.debugScene = scene;
        debugGame = true;
    }

    public void debugAttack(int attack) {
        debugGame = true;
        debugAttack = true;
        attackId = attack;
    }

    public void debugAnimation(int animation) {
        animationId = animation;
        debugAnimation = true;
        debugGame = true;
    }

    public void debugMap(int map, int x, int y) {
        debugGame = true;
        debugMap = true;
        this.mapId = map;
        this.mapX = x;
        this.mapY = y;
    }

    public String getDebugScene() {
        return debugScene;
    }

    public int getMapId() {
        return mapId;
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public int getAttackId() {
        return attackId;
    }

    public int getAnimationId() {
        return animationId;
    }

    public boolean isDebugMap() {
        return debugMap;
    }

    public boolean isDebugGame() {
        return debugGame;
    }

    public boolean isDebugAnimation() {
        return debugAnimation;
    }

    public boolean isDebugAttack() {
        return debugAttack;
    }

    public File getDataPath() {
        return path;
    }

    public boolean isTouchControling() {
        return touchControling;
    }
}
