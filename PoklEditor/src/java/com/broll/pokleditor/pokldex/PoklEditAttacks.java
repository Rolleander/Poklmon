package com.broll.pokleditor.pokldex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.broll.pokleditor.data.PoklDataUtil;
import com.broll.pokleditor.gui.components.AttackBox;
import com.broll.pokleditor.gui.components.AttackLearnBox;
import com.broll.pokleditor.gui.components.ObjectList;
import com.broll.pokleditor.gui.components.ObjectRemoveListener;
import com.broll.pokleditor.gui.GraphicLoader;
import com.broll.pokleditor.window.VerticalLayout;
import com.broll.pokllib.poklmon.AttackLearnEntry;
import com.broll.pokllib.poklmon.AttackList;
import com.broll.pokllib.poklmon.Poklmon;

public class PoklEditAttacks extends JPanel
{

    private Poklmon poklmon;
    private JPanel liste = new JPanel();
    private ArrayList<AttackLearnBox> attacks = new ArrayList<AttackLearnBox>();
    private ObjectList teachAttacks = new ObjectList();
    private AttackBox atk;
    
    public PoklEditAttacks()
    {
        setLayout(new BorderLayout());


        liste.setLayout(new VerticalLayout());
        teachAttacks.setNumbered(false);
        JScrollPane pane = new JScrollPane(liste);
        JPanel levelAttacks = new JPanel(new BorderLayout());
        JPanel tmAttacks = new JPanel(new BorderLayout());

        teachAttacks.setListSize(new Dimension(500, 400));
        levelAttacks.add(pane, BorderLayout.CENTER);
        tmAttacks.add(teachAttacks, BorderLayout.CENTER);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Level-Up Attacks", levelAttacks);
        tabs.addTab("Can learn Attacks", tmAttacks);
        add(tabs, BorderLayout.CENTER);

        JButton add = new JButton("Learn Attack", GraphicLoader.loadIcon("plus.png"));
        add.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                addBox(0, 0);
                revalidate();
                repaint();
            }
        });
        JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT));
        b.add(add);
        levelAttacks.add(b, BorderLayout.SOUTH);

        teachAttacks.addRemoveListener(new ObjectRemoveListener() {
            public void remove(int id)
            {
                poklmon.getAttackList().getTeachableAttacks().remove(id);
            }
        });

        atk = new AttackBox("Attack");
        JPanel bot = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton learn = new JButton("Learn Attack", GraphicLoader.loadIcon("plus.png"));
        learn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                int attack = atk.getAttack();
                String name = PoklDataUtil.getAllAttackNames().get(attack);
                teachAttacks.addEntry(name);
                poklmon.getAttackList().getTeachableAttacks().add(attack);

            }
        });

        bot.add(atk);
        bot.add(learn);

        tmAttacks.add(bot, BorderLayout.SOUTH);

    }

    public void setPoklmon(Poklmon poklmon)
    {
        this.poklmon = poklmon;
        atk.setAttack(0);//update attacks
        attacks.clear();
        liste.removeAll();
        AttackList atks = poklmon.getAttackList();
        List<AttackLearnEntry> entries = atks.getAttacks();
        if (entries != null)
        {
            for (AttackLearnEntry learn : entries)
            {
                addBox(learn.getLearnLevel(), learn.getAttackNumber());
            }
        }
        ArrayList<String> attackNames = PoklDataUtil.getAllAttackNames();
        ArrayList<String> attacks = new ArrayList<String>();
        if (poklmon.getAttackList().getTeachableAttacks() != null)
        {
            for (int index : poklmon.getAttackList().getTeachableAttacks())
            {
                String name = attackNames.get(index);
                attacks.add(name);
            }
        }
        else
        {
            poklmon.getAttackList().setTeachableAttacks(new ArrayList<Integer>());
        }
        teachAttacks.initList(attacks);
        revalidate();
        repaint();
    }

    private void addBox(int level, int attack)
    {

        final AttackLearnBox box = new AttackLearnBox();
        box.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                liste.remove(box);
                attacks.remove(box);
                revalidate();
                repaint();
            }
        });
        box.setLevel(level);
        box.setAttack(attack);
        attacks.add(box);
        liste.add(box);
    }

    public void save()
    {

        List<AttackLearnEntry> entries = new ArrayList<AttackLearnEntry>();
        for (AttackLearnBox box : attacks)
        {
            AttackLearnEntry entry = new AttackLearnEntry();
            entry.setAttackNumber(box.getAttack());
            entry.setLearnLevel(box.getLevel());
            entries.add(entry);
        }
        Collections.sort(entries, new Comparator<AttackLearnEntry>() {
			@Override
			public int compare(AttackLearnEntry e1, AttackLearnEntry e2) {
				int l1=e1.getLearnLevel();
				int l2=e2.getLearnLevel();
				if(l1>l2)				
				{
					return 1;
				}
				else if(l2>l1)
				{
					return -1;
				}
				return 0;
			}        	
		});
        poklmon.getAttackList().setAttacks(entries);
    }
}
