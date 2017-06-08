package com.broll.pokleditor.gui.components;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.broll.pokleditor.data.scriptinghelp.Command;
import com.broll.pokleditor.data.scriptinghelp.Field;
import com.broll.pokleditor.data.scriptinghelp.Function;
import com.broll.pokleditor.data.scriptinghelp.ScriptDictionary;
import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokleditor.window.VerticalLayout;

public class ScriptHelpBox extends JPanel
{
   
    public ScriptHelpBox(ScriptDictionary scriptDictionary)
    {
        setLayout(new VerticalLayout(-1));
        add(new JLabel("Fields", GraphicLoader.loadIcon("link_button.png"), JLabel.RIGHT));
        for (Field field : scriptDictionary.getFields())
        {
            add(getFieldGui(field));
        }
        add(Box.createVerticalStrut(20));
        add(new JLabel("Commands", GraphicLoader.loadIcon("lightning.png"), JLabel.RIGHT));
        for (Command command : scriptDictionary.getCommands())
        {
            add(getCommandGui(command));
        }
        add(Box.createVerticalStrut(20));

        add(new JLabel("Functions", GraphicLoader.loadIcon("math_functions.png"), JLabel.RIGHT));
        for (Function function : scriptDictionary.getFunctions())
        {
            add(getFunctionGui(function));
        }
    }

    private JPanel getFieldGui(Field field)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(getFieldLabel(field));
        JLabel desc = new JLabel(field.getDescription());
        desc.setFont(desc.getFont().deriveFont(9f));
        panel.add(desc);
        return panel;
    }

    private JLabel getFieldLabel(Field field)
    {
        String icon = field.getVarType().getIcon();
        JLabel label = new JLabel(field.getName(), GraphicLoader.loadIcon(icon), JLabel.LEADING);
        label.setForeground(new Color(100,150,250));
        return label;
    }

    private JPanel getCommandGui(Command command)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JLabel info = new JLabel(command.getName(), GraphicLoader.loadIcon("lightning.png"), JLabel.LEADING);
        panel.add(info);
        panel.add(new JLabel("@"));
        for (Field parameter : command.getParameter())
        {
            panel.add(getFieldLabel(parameter));
        }
        panel.setToolTipText(command.getDescription());
        JLabel desc = new JLabel(command.getDescription());
        desc.setFont(desc.getFont().deriveFont(9f));
        panel.add(desc);
        return panel;
    }

    private JPanel getFunctionGui(Function function)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel info = new JLabel(function.getName(), GraphicLoader.loadIcon("math_functions.png"), JLabel.LEADING);

        panel.add(info);
        panel.add(new JLabel("Return", GraphicLoader.loadIcon(function.getReturnType().getIcon()), JLabel.RIGHT));
        panel.add(new JLabel("$"));
        for (Field parameter : function.getParameter())
        {
            panel.add(getFieldLabel(parameter));
        }
        panel.setToolTipText(function.getDescription());
        JLabel desc = new JLabel(function.getDescription());
        desc.setFont(desc.getFont().deriveFont(9f));
        panel.add(desc);
        return panel;
    }

}