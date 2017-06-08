package com.broll.poklmon.gui.selection;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.ColorUtil;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.gui.GUIUpdate;
import com.broll.poklmon.resource.GUIDesign;
import com.broll.poklmon.resource.GUIFonts;

public class RectSelectionBox extends SelectionBox
{
    private float selectionAnimation;

    public RectSelectionBox(DataContainer data, String[] items, int x, int y, int w, int h)
    {
        super(data,items, x, y, w,false);
        render.setSize(x, y, w, h);
        selection = new RectSelectionModel(items);
        selectionWidth /= 2;
        selectionWidth -= 5;
        selectionHeight += 10;
        ypos += 5;
        selectionXP = selectionWidth + 15;
        xpos -= 12;
        selectionYP += 20;
    }

    @Override
    protected void drawSelection(Graphics g, float x, float y, float w, float h)
    {
        float r = 1.5f;
        g.setColor(ColorUtil.newColor(250, 0, 0));
        float a = (float)(Math.sin(selectionAnimation) * r) + r;
        x -= a;
        y -= a;
        w += a * 2;
        h += a * 2;
        g.drawRect(x, y, w, h);
        g.drawRect(x - 1, y - 1, w + 2, h + 2);
    }


    @Override
    public void render(Graphics g)
    {
        render.render(g);
        //draw selection
        int selected = selection.getSelectedItem();
        int x = xpos;
        int y = ypos;
        for (int i = 0; i < selection.getSize(); i++)
        {
            int w = selectionWidth;
            int h = selectionHeight;
            if (selection.isBlocked(i))
            {
                g.setColor(ColorUtil.newColor(150, 150, 150));
            }
            else
            {
                g.setColor(ColorUtil.newColor(50, 50, 50));
            }
            g.setFont(GUIFonts.dialogText);
            g.drawString(selection.getItem(i), x + 5, y - 3);
            if (selected == i)
            {
                drawSelection(g, x, y, w, h);
            }
            x += selectionXP;
            if (i == 1)
            {
                y += selectionYP;
                x = xpos;
            }

        }
    }

    @Override
    protected void updateSelection()
    {
        super.updateSelection();
        if (GUIUpdate.isMoveLeft())
        {
        	data.getSounds().playSound(GUIDesign.MOVE_SOUND);
            ((RectSelectionModel)selection).left();
        }
        if (GUIUpdate.isMoveRight())
        {
        	data.getSounds().playSound(GUIDesign.MOVE_SOUND);
            ((RectSelectionModel)selection).right();
        }
        selectionAnimation += 0.1f;
    }
}
