package com.broll.poklmon.resource;

import com.broll.poklmon.data.DataException;
import com.broll.poklmon.data.DataLoader;
import com.broll.poklmon.data.basics.Image;

public class GUIDesign
{
    public static Image selectionBoxCorner;
    public static Image selectionBoxBorder;
    public static Image selectionBoxBorder2;
    public static Image textbox,textbox2;
    public static Image selector;
    public static Image caret,caret2;
    
    public static String MOVE_SOUND="select";
    public static String CLICK_SOUND="click";
    public static String CANCEL_SOUND="close";
    
    public static void loadDesign() throws DataException
    {
       
        Image select=DataLoader.loadImage(ResourceUtils.DATA_PATH +"resource/graphics/selectionboxParts.png");
        selectionBoxCorner=select.getSubImage(0, 0,32,32);
        selectionBoxBorder=select.getSubImage(32, 0,32,32);  
        selectionBoxBorder2=select.getSubImage(64, 0,32,32);  
       
        textbox=DataLoader.loadImage(ResourceUtils.DATA_PATH +"resource/graphics/textbox.png");
        textbox2=DataLoader.loadImage(ResourceUtils.DATA_PATH +"resource/graphics/textbox2.png");  
        selector=DataLoader.loadImage(ResourceUtils.DATA_PATH +"resource/graphics/cursor.png");
        caret=DataLoader.loadImage(ResourceUtils.DATA_PATH +"resource/graphics/caret.png");
        caret2=DataLoader.loadImage(ResourceUtils.DATA_PATH +"resource/graphics/caret2.png");
    }
}
