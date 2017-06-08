package com.broll.poklmon.battle.poklmon.states;

public enum EffectStatus
{

    CONFUSION("# ist verwirrt!","# verletzt sich vor Verwirrung selbst!","# ist nicht mehr verwirrt!",-1),
    PAUSE("# muss sich ausruhen!","# ruht sich aus...",null,-1),
    AFRAID("# ist starr vor Angst!","# hat Angst anzugreifen!","# ist nicht mehr verängstigt!",-1),
    ABSORBEFFECT("# wurde bepflanzt!","# wird Energie absorbiert!",null,-1),
    HEALPLANTS("# pflanzt heilende Kräuter!","# frisst heilende Kräuter!",null,-1),
    CRINGE(null,"# schreckt zurück!",null,-1),
    CURSE("# wurde verflucht!","# wird durch Fluch verletzt!",null,-1),
    DOOM("# ist seinem Ende nahe!","# kann seinen Untergang nicht aufhalten!",null,-1),
    SLEEPY("# wird müde!",null,null,-1),
    FUCHTLER(null,null,null,-1),
    FLUTWELLE("# wartet auf die große Welle!",null,null,-1),
    DRAGONSTORM(null,null,null,-1),
    KLINGENSTRUDEL("# wird im Klingenstrudel gefangen!","# wird durch Klingenstrudel verletzt!","# hat sich aus Klingenstrudel befreit!",-1),
    WICKEL("# wird von Wickel gefangen!","# wird durch Wickel verletzt!","# hat sich aus Wickel befreit!",-1),
    ENERGYFOCUS("# hat seinen Gegner fokussiert!",null,"# hat seinen Fokus verloren!",-1),
    SOLARBEAM("# absorbiert Sonnenlicht!",null,null,-1);
    

    private String entryName;
    private String durName;
    private String exitName;
    private int animationID;
    
    private EffectStatus( String entry,String dur, String exit, int ani)
    {
  
        durName=dur;
        entryName=entry;
        exitName=exit;
        animationID=ani;
    }
    
    public int getAnimationID()
    {
        return animationID;
    }
    
    public String getDurName() {
		return durName;
	}
    
    public String getEntryName() {
        return entryName;
    }
    public String getExitName() {
        return exitName;
    }

    
    
}
