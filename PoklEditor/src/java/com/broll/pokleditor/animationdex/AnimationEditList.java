package com.broll.pokleditor.animationdex;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.broll.pokleditor.gui.GraphicLoader;
import com.broll.pokleditor.window.EditorWindow;
import com.broll.pokleditor.window.VerticalLayout;
import com.broll.pokllib.animation.Animation;
import com.broll.pokllib.animation.AnimationSprite;
import com.broll.pokllib.animation.AnimationStep;

public class AnimationEditList extends JPanel
{

    private JList<String> liste = new JList<String>();
    private Animation animation;
    public static int selectedSheet;
    private JScrollPane scroll;
    public static float transparency=1f;
    public static float size=2f;
    public static float angle=0;

    public AnimationEditList(final ActionListener save)
    {
        setLayout(new BorderLayout());
        scroll = new JScrollPane(liste, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                                 ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);

        JButton add = new JButton("Add", GraphicLoader.loadIcon("plus.png"));
        JButton copy = new JButton("Copy", GraphicLoader.loadIcon("shape_group.png"));
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0)
            {
                addFrame();
            }
        });
        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                copyFrame();
            }
        });
        JPanel bot = new JPanel();
        bot.add(add);
        bot.add(copy);
        add(bot, BorderLayout.SOUTH);


        JPanel north = new JPanel();
        north.setLayout(new VerticalLayout());

        JTextArea help=new JTextArea("R = Reset \nSCROLL = Change Size \nT + SCROLL = Tilechange\nA + SCROLL = Rotate \nD + SCROLL = Transparency");
        help.setFocusable(false);
        help.setEditable(false);
        north.add(help);    
        add(north, BorderLayout.NORTH);
    }

    public void setAnimation(Animation animation)
    {
        this.animation = animation;

        List<AnimationStep> steps = animation.getAnimation();
        if (steps == null || steps.size() == 0)
        {// create new sheet for empty animations
            steps = new ArrayList<AnimationStep>();
            steps.add(createNewStep());
            animation.setAnimation(steps);
        }

        selectedSheet = 0;
        updateList();
    }

    private AnimationStep createNewStep()
    {
        AnimationStep step = new AnimationStep();
        List<AnimationSprite> sprites = new ArrayList<AnimationSprite>();
        AnimationSprite sprite = new AnimationSprite();
        sprite.setSize(2);
        sprite.setSpriteID(0);
        sprite.setTransparency(1);
        sprite.setX(160);
        sprite.setY(200);
        sprites.add(sprite);
        sprite = new AnimationSprite();
        sprite.setSize(2);
        sprite.setSpriteID(1);
        sprite.setTransparency(1);
        sprite.setX(640);
        sprite.setY(200);
        sprites.add(sprite);
        step.setSprites(sprites);
        return step;
    }

    private void addFrame()
    {
        animation.getAnimation().add(createNewStep());
        selectedSheet = animation.getAnimation().size() - 1;
        updateList();
    }

    private void copyFrame()
    {
        AnimationStep copyStep = animation.getAnimation().get(selectedSheet);
        AnimationStep pasteStep = new AnimationStep();
        pasteStep.setSprites(new ArrayList<AnimationSprite>());
        for (AnimationSprite copySprite : copyStep.getSprites())
        {
            AnimationSprite pasteSprite = new AnimationSprite();
            pasteSprite.setAngle(copySprite.getAngle());
            pasteSprite.setSize(copySprite.getSize());
            pasteSprite.setSpriteID(copySprite.getSpriteID());
            pasteSprite.setTransparency(copySprite.getTransparency());
            pasteSprite.setX(copySprite.getX());
            pasteSprite.setY(copySprite.getY());
            pasteStep.getSprites().add(pasteSprite);
        }
        animation.getAnimation().add(pasteStep);
        selectedSheet = animation.getAnimation().size() - 1;
        updateList();
    }

    private void updateList()
    {
        int count = animation.getAnimation().size();
        String[] names = new String[count];
        for (int i = 0; i < count; i++)
        {
            names[i] = "Frame " + i;
            AnimationStep step = animation.getAnimation().get(i);
            if (step.getSprites() == null)
            {
                step.setSprites(new ArrayList<AnimationSprite>());

            }
        }
        liste = new JList<String>(names);
        liste.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting())
                {
                    selectedSheet = liste.getSelectedIndex();
                    EditorWindow.repaintAnimations();
                }
            }
        });
        liste.setSelectedIndex(selectedSheet);
        scroll.getViewport().setView(liste);
        revalidate();
        EditorWindow.repaintAnimations();
    }

}
