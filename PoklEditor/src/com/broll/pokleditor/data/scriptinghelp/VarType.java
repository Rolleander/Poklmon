package com.broll.pokleditor.data.scriptinghelp;

public enum VarType
{

    NUMBER("num","token_match_int.png"),TEXT("txt","token_literal_text.png"),BOOLEAN("bol","bool.png");
    
    private String txt;
    private String icon;
    
    private VarType(String txt,String icon)
    {
        this.txt=txt+":";
        this.icon=icon;
    }
    
    public String getIcon()
    {
        return icon;
    }
    
    public String getTxt()
    {
        return txt;
    }
}
