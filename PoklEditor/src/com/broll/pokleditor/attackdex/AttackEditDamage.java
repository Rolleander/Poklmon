package com.broll.pokleditor.attackdex;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.broll.pokleditor.gui.components.AttackPriorityBox;
import com.broll.pokleditor.gui.components.IntBox;
import com.broll.pokllib.attack.Attack;
import com.broll.pokllib.attack.AttackDamage;

public class AttackEditDamage extends JPanel
{

    private Attack attack;
    private IntBox damage = new IntBox("Damage", 3);
    private AttackPriorityBox priority = new AttackPriorityBox("Priority");
    private IntBox ap = new IntBox("AP", 2);
    private IntBox hitchance = new IntBox("Hitchance", 3);

    public AttackEditDamage()
    {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(damage);
        add(hitchance);
        add(ap);
        add(new JLabel("%"));
        add(priority);

    }

    public void setAttack(Attack attack)
    {
        this.attack = attack;
        AttackDamage d = attack.getDamage();
        damage.setValue(d.getDamage());
        ap.setValue(d.getAp());
        priority.setPriority(d.getPriority());
        hitchance.setValue(fromHitchance(d.getHitchance()));
    }

    public void save()
    {
        AttackDamage d = new AttackDamage();
        d.setAp(ap.getValue());
        d.setDamage(damage.getValue());
        d.setHitchance(toHitchance(hitchance.getValue()));
        d.setPriority(priority.getPriority());
        attack.setDamage(d);
    }

    private static int fromHitchance(float hitchance)
    {
        return (int)(hitchance * 100);
    }

    private static float toHitchance(int hitchance)
    {
        return (float)hitchance / 100f;
    }
}
