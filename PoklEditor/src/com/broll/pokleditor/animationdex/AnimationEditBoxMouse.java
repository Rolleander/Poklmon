package com.broll.pokleditor.animationdex;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

import com.broll.pokleditor.window.EditorWindow;
import com.broll.pokllib.animation.Animation;
import com.broll.pokllib.animation.AnimationSprite;
import com.broll.pokllib.animation.AnimationStep;

public class AnimationEditBoxMouse implements MouseListener, MouseMotionListener, MouseWheelListener
{

    private Animation animation;

    private int mousex, mousey;
    private boolean showMouse = false;
    private int keyPressed = -1;

    public AnimationEditBoxMouse()
    {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent ke)
            {
                synchronized (AnimationEditBoxMouse.class)
                {
                    switch (ke.getID())
                    {
                        case KeyEvent.KEY_PRESSED:
                            keyPressed = ke.getKeyCode();
                            if (keyPressed == KeyEvent.VK_R)
                            {
                                //reset
                                AnimationEditList.angle = 0;
                                AnimationEditList.size = 2;
                                AnimationEditList.transparency = 1;
                                EditorWindow.repaintAnimations();
                            }
                            break;

                        case KeyEvent.KEY_RELEASED:
                            keyPressed = -1;
                            break;
                    }
                    return false;
                }
            }
        });
    }

    public void setAnimation(Animation animation)
    {
        this.animation = animation;
    }

    private boolean drag = false;

    @Override
    public void mouseDragged(MouseEvent e)
    {
        processClick(e.getX(), e.getY(), true);
        EditorWindow.repaintAnimations();
        showMouse = false;
        mousex = e.getX();
        mousey = e.getY();
        drag = true;
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mousex = e.getX();
        mousey = e.getY();
        showMouse = true;
        EditorWindow.repaintAnimations();
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        showMouse = true;
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        showMouse = false;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

        if (drag)
        {
            drag = false;
            return;
        }
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            // add
            int x = e.getX();
            int y = e.getY();
            addSprite(x, y);
        }
        else
        {
            // remove
            processClick(e.getX(), e.getY(), false);
        }

        EditorWindow.repaintAnimations();
    }

    private void addSprite(int x, int y)
    {
        // System.out.println("add sprite at "+x+" "+y+" with id "+AnimationSpriteset.selection+" to frame "+AnimationEditList.selectedSheet);
        AnimationSprite sprite = new AnimationSprite();
        sprite.setX(x);
        sprite.setY(y);
        sprite.setSpriteID(AnimationSpriteset.selection);
        sprite.setSize(AnimationEditList.size);
        sprite.setAngle(AnimationEditList.angle);
        sprite.setTransparency(AnimationEditList.transparency);
        List<AnimationSprite> sprites = animation.getAnimation().get(AnimationEditList.selectedSheet).getSprites();

        sprites.add(sprite);
    }

    private void processClick(int x, int y, boolean drag)
    {
        AnimationStep step = animation.getAnimation().get(AnimationEditList.selectedSheet);
        List<AnimationSprite> sprites = step.getSprites();
        for (int i = sprites.size()-1; i >-1; i--)
        {
            AnimationSprite sprite = sprites.get(i);
            int xp = sprite.getX();
            int yp = sprite.getY();
            if (x >= xp - AnimationEditBox.selectionSize && x <= xp + AnimationEditBox.selectionSize)
            {
                if (y >= yp - AnimationEditBox.selectionSize && y <= yp + AnimationEditBox.selectionSize)
                {
                    // found
                    if (drag)
                    {

                        sprite.setX(x);
                        sprite.setY(y);
                    }
                    else
                    {
                        sprites.remove(i);
                    }
                    return;
                }
            }
        }
    }

    public int getMousex()
    {
        return mousex;
    }

    public int getMousey()
    {
        return mousey;
    }

    public boolean isShowMouse()
    {
        return showMouse;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        int scroll = e.getWheelRotation();
        if (keyPressed == KeyEvent.VK_A)
        {
            //angle
            float c = (float)Math.toRadians(2);
            if (scroll < 0)
            {
                AnimationEditList.angle += c;
            }
            else
            {
                AnimationEditList.angle -= c;
            }
        }
        else if (keyPressed == KeyEvent.VK_D)
        {
            //transp
            float c = 0.1f;
            if (scroll < 0)
            {
                AnimationEditList.transparency += c;
                if (AnimationEditList.transparency > 1)
                {
                    AnimationEditList.transparency = 1;
                }
            }
            else
            {
                AnimationEditList.transparency -= c;
                if (AnimationEditList.transparency < 0.05f)
                {
                    AnimationEditList.transparency = 0.05f;
                }
            }
        }
        else if (keyPressed == KeyEvent.VK_T)
        {
            //tile

        }
        else
        {
            //size
            float c = 0.1f;
            if (scroll < 0)
            {
                AnimationEditList.size += c;
            }
            else
            {
                AnimationEditList.size -= c;
            }

        }

        EditorWindow.repaintAnimations();
    }


}
