package com.broll.pokleditor.pokldex;

import com.broll.pokleditor.data.PoklData;
import com.broll.pokllib.main.PoklLib;
import com.broll.pokllib.map.MapDex;
import com.broll.pokllib.map.MapFile;
import com.broll.pokllib.map.MapID;
import com.broll.pokllib.poklmon.Poklmon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

public class PoklAreaFinder {

    public static void locate(Poklmon poklmon) {
        Set<String> locatedMaps = new HashSet<>();
        try {
            for (MapID mapId : PoklLib.data().readMapDex().getMaps()) {
                MapFile map = PoklLib.data().readMap(mapId.getId());
                if (isPoklmonCatchable(map, poklmon)) {
                    locatedMaps.add(mapId.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (locatedMaps.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No wild encounters");
        } else {
            JOptionPane.showMessageDialog(null, "Wild encounters in: \n" + locatedMaps.stream().collect(Collectors.joining("\n")));
        }
    }

    private static boolean isPoklmonCatchable(MapFile map, Poklmon poklmon) {
        for (String areaScript : map.getAreaScripts()) {
            if (isPoklmonFirstArgument(areaScript, "area\\.addWildPoklmon", poklmon) || isPoklmonFirstArgument(areaScript, "area\\.addFishingPoklmon", poklmon)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isPoklmonFirstArgument(String script, String functionCall, Poklmon poklmon) {
        String[] parts = script.split(functionCall + "\\(");
        if (parts.length > 1) {
            for (int i = 1; i < parts.length; i++) {
                String callPart = parts[i];
                String firstArgument = callPart.split(",")[0];
                int poklId = Integer.parseInt(firstArgument.trim());
                if (poklmon.getId() == poklId) {
                    return true;
                }
            }
        }
        return false;
    }

}
