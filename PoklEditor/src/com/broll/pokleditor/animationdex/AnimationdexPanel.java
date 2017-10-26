package com.broll.pokleditor.animationdex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JDialog;

import com.broll.pokleditor.data.PoklData;
import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokleditor.window.EditorWindow;
import com.broll.pokllib.animation.Animation;
import com.broll.pokllib.animation.AnimationFX;
import com.broll.pokllib.animation.AnimationStep;

public class AnimationdexPanel extends JDialog
{

    private AnimationList animationList;
    private AnimationEditPanel animationEditPanel = new AnimationEditPanel();
    private int lastID;

    public AnimationdexPanel()
    {

        setModal(true);
        setTitle("Animation Database");
        setIconImage(GraphicLoader.loadImage("rainbow.png"));
        setSize(new Dimension(1200, 1000));
        setMinimumSize(new Dimension(1000, 600));
        setLocationRelativeTo(EditorWindow.frame);

        animationEditPanel.setVisible(false);
        animationList = new AnimationList(this);


        setLayout(new BorderLayout());
        animationList.setPreferredSize(new Dimension(150, 0));

        add(animationList, BorderLayout.WEST);
        add(animationEditPanel, BorderLayout.CENTER);

        addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e)
            {
            }

            @Override
            public void windowIconified(WindowEvent e)
            {
            }

            @Override
            public void windowDeiconified(WindowEvent e)
            {
            }

            @Override
            public void windowDeactivated(WindowEvent e)
            {
            }

            @Override
            public void windowClosing(WindowEvent e)
            {
                saveLastChanges();
            }

            @Override
            public void windowClosed(WindowEvent e)
            {
            }

            @Override
            public void windowActivated(WindowEvent e)
            {
            }
        });
    }

    public void open()
    {
        this.setVisible(true);
    }

    public void view(int id)
    {
        saveLastChanges();
        lastID = id;
        animationEditPanel.setVisible(true);
        animationEditPanel.setAnimation(PoklData.loadAnimation(id));

    }

    public void saveLastChanges()
    {
        Animation last = animationEditPanel.getAnimation();
        if (last != null)
        {
            animationEditPanel.save();
            animationList.updateListEntry(last.getName(), lastID);
            PoklData.saveAnimation(last, lastID);
        }
    }

    public void addNewAnimation(int id)
    {
        saveLastChanges();
        Animation animation = new Animation();
        animation.setName("New Animation");
        animation.setAnimation(new ArrayList<AnimationStep>());
        animation.setFx(new ArrayList<AnimationFX>());

        lastID = id;
        animationEditPanel.setVisible(true);
        animationEditPanel.setAnimation(animation);
        PoklData.saveAnimation(animation, id);
    }
}
