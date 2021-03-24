package com.broll.poklmon.battle.util.message;

public class BattleMessages
{
    /*
    public static String wildPoklmonIntro="Ein wildes # erscheint!";
    public static String playerPoklmonIntro="Los #!";
    public static String playerPoklmonOutro="Zur�ck in den Poklball #!";
    public static String playerLost="# hat kein kampff�higes Poklmon mehr. # f�llt in Ohnmacht...";
    public static String networkLost="Du hast kein kampff�higes Poklmon mehr. # gewinnt den Kampf!";
    public static String networkWon="# hat kein kampff�higes Poklmon mehr. Du gewinnst den Kampf!";

    public static String playerLastPoklmon="Dein letztes Poklmon macht sich bereit!";
    
    public static String itemHeal="# regeneriert % KP!";
    public static String itemAp="# AP wurden aufgef�llt!";
    
    
    
    public static String trainerSwitch="% ruft # zur�ck!";   
    public static String trainerDefeated="# wurde besiegt!";
    public static String trainerMoney="# gewinnt % PoklDollar!";   
    public static String trainerIntro="# m�chte k�mpfen!";
    public static String trainerNextPoklmonQuestion="% wird als n�chstes # einsetzen! M�chtest du dein Poklmon wechseln?";
    public static String trainerPoklmonIntro="% schickt # in den Kampf!";
           
    public static String commandInfo="Was soll #";
    public static String commandInfo2="tun?";
    public static String poklmonDefeated="# wurde besiegt!";    
    public static String attackText="# setzt % ein!";
    public static String attackNoHit="Der Angriff von # ging daneben!";
    public static String attackNoEffect="Es hat keine Wirkung!";
    public static String volltreffer="Volltreffer!";
    public static String multihit="# mal getroffen!";
    public static String rueckstoss="# wird vom R�cksto� verletzt!";
    public static String kpopfer="# verbraucht Energie!";   
    public static String selfheal="# regeneriert sich!";       
    public static String changeAttribute="% von # ";
    public static String changeAttributePositive="steigt";
    public static String changeAttributeNegative="sinkt";
    public static String changeAttributePositiveFailed="ist Maximum!";
    public static String changeAttributeNegativeFailed="kann nicht weiter sinken.";
    public static String gainEXP="# erh�lt % Erfahrungspunkte!";
    public static String[] actions={"Kampf","Beutel","Poklmon","Flucht"};
    public static String[] attributeChangeStrength={"!"," stark!"," enorm!"};
    public static String chooseNextPoklmon="Welches Poklmon schickst du in den Kampf?";
 */
    public static String putName(String text,String poklmonName)
    {
        return text.replace("#", poklmonName);
    }
    
    public static String putName(String text,String poklmonName, String attackName)
    {
        return text.replace("#", poklmonName).replace("%", attackName);
    }
}
