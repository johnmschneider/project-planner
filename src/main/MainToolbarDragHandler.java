/*
 * (C) John Schneider 2020
 */
package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;

/**
 *
 * @author John Schneider
 */
public class MainToolbarDragHandler extends MouseMotionAdapter implements
     MouseListener
{
    private JFrame window;
    private int relativeStartX, relativeStartY;
    
    private boolean offsetsNeedStored;
    
    public MainToolbarDragHandler(JFrame window)
    {
        this.window = window;
        offsetsNeedStored = true;
    }
    
    @Override
    public void mouseDragged(MouseEvent e)
    {
        if (offsetsNeedStored)
        {
            relativeStartX = e.getX();
            relativeStartY = e.getY();
            offsetsNeedStored = false;
        }
        
        window.setLocation(e.getXOnScreen() - relativeStartX, 
            e.getYOnScreen() - relativeStartY);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        offsetsNeedStored = true;
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }
    
}
