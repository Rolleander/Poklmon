package com.broll.pokleditor.gui.components;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokllib.animation.AnimationFX;
import com.broll.pokllib.animation.AnimationFXTarget;

public class AnimationFXLine extends JPanel
{

    private AnimationFX fx;
    private JComboBox<String> target;
    private JTextField data;
    private SoundBox sound;
    private FrameBox frame;
    private FrameDurationBox duration;
    private ShakestrengthBox shake;

    public AnimationFXLine(AnimationFX fx)
    {
        AnimationFXTarget[] values = AnimationFXTarget.values();
        String[] text = new String[values.length];

        for (int i = 0; i < values.length; i++)
        {
            text[i] = values[i].name();
        }
        target = new JComboBox<String>(text);

        this.fx = fx;
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));

        frame = new FrameBox("At");
        frame.setFrame(fx.getAtFrame());
        add(frame);

        JLabel name = new JLabel(fx.getType().name());
        add(name);

        switch (fx.getType())
        {
            case PLAYSOUND:
                sound = new SoundBox("Sound");
                sound.setSound(fx.getValue());
                add(sound);
                break;

            case COLOROVERLAY:
                add(new JLabel("Target"));
                add(target);
                target.setSelectedIndex(fx.getTarget().ordinal());
                add(new JLabel("Color (r,g,b,a)"));
                String c = fx.getR() + "," + fx.getG() + "," + fx.getB() + "," + fx.getA();
                data = new JTextField(12);
                data.setText(c);
                add(data);
                duration = new FrameDurationBox("Duration");
                duration.setFrame(fx.getLength());
                add(duration);

                break;

            case SHAKE:
                add(new JLabel("Target"));

                add(target);
                target.setSelectedIndex(fx.getTarget().ordinal());

                shake = new ShakestrengthBox("Shakestrength");
                shake.setFrame(fx.getValue());
                add(shake);

                duration = new FrameDurationBox("Duration");
                duration.setFrame(fx.getLength());
                add(duration);
                break;
        }

    }

    public void setActionListener(ActionListener l)
    {
        JButton kill = GraphicLoader.newIconButton("cross.png");
        kill.addActionListener(l);
        add(kill);
    }

    public AnimationFX getFx()
    {
        fx.setAtFrame(frame.getFrame());
        switch (fx.getType())
        {
            case COLOROVERLAY:
                fx.setLength(duration.getFrame());
                String colors = data.getText();
                String[] c = colors.split(",");
                fx.setR(Integer.parseInt(c[0]));
                fx.setG(Integer.parseInt(c[1]));
                fx.setB(Integer.parseInt(c[2]));
                fx.setA(Integer.parseInt(c[3]));
                fx.setLength(duration.getFrame());
                fx.setTarget(AnimationFXTarget.values()[target.getSelectedIndex()]);
                break;
            case PLAYSOUND:
                fx.setValue(sound.getSound());

                break;
            case SHAKE:
                fx.setValue(shake.getFrame());
                fx.setLength(duration.getFrame());
                fx.setTarget(AnimationFXTarget.values()[target.getSelectedIndex()]);
                break;
        }
        return fx;
    }
}
