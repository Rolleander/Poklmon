package com.broll.poklmon.battle.util.message;

import com.broll.poklmon.battle.util.BattleRandom;

import java.util.ArrayList;
import java.util.List;

public class TrainerMessages
{

    private static List<String> poklmonIntro = new ArrayList<String>();
    private static List<String> poklmonOutro = new ArrayList<String>();
    private static List<String> poklmonDefeated = new ArrayList<String>();
    private static List<String> poklmonKilled = new ArrayList<String>();

    static
    {

        //intro messages
        poklmonIntro.add("Auf gehts #!");
        poklmonIntro.add("Du bist dran #!");
        poklmonIntro.add("Ich schicke mein # in den Kampf!");
        poklmonIntro.add("Mein # wird dich platt machen!");
        poklmonIntro.add("Du hast keine Chance gegen #!");
        poklmonIntro.add("Jetzt kommt mein #!");
        poklmonIntro.add("Zeigs ihm #!");
        poklmonIntro.add("Deine Zeit ist gekommen #!");

        //outro messages
        poklmonOutro.add("Zurück mit dir #!");
        poklmonOutro.add("Gut gekämpft #!");
        poklmonOutro.add("Dich brauche ich jetzt nicht mehr #!");
        poklmonOutro.add("Du hast dir eine Pause verdient #!");
        poklmonOutro.add("Mein nächstes Poklmon wird dich vernichten!");

        //defeated messages
        poklmonDefeated.add("Oh nein, mein #!");
        poklmonDefeated.add("Was wie konnte das passieren!?");
        poklmonDefeated.add("So viel Glück ist unglaublich!");
        poklmonDefeated.add("Mist! #!");

        //kill messages
        poklmonKilled.add("Gut gemacht #!");
        poklmonKilled.add("Haha, ich habe dich gewarnt!");
        poklmonKilled.add("Das hast du verdient!");
        poklmonKilled.add("Ja gut so #!");
        poklmonKilled.add("Keiner kann mein # aufhalten!");

    }

    public static String getIntro()
    {
        return randomEntry(poklmonIntro);
    }

    public static String getOutro()
    {
        return randomEntry(poklmonOutro);
    }

    public static String getDefeated()
    {
        return randomEntry(poklmonDefeated);
    }

    public static String getKilled()
    {
        return randomEntry(poklmonKilled);
    }


    private static String randomEntry(List<String> list)
    {
        int entry = (int)(BattleRandom.random() * list.size());
        return list.get(entry);
    }
}
