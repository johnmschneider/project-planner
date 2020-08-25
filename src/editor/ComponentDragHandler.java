/*
 * (C) John Schneider 2020
 */
package editor;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;

/**
 *
 * @author John Schneider
 */
public class ComponentDragHandler extends MouseMotionAdapter implements
     MouseListener
{
    private Component component, parent;
    private int relativeStartX, relativeStartY;
    
    private boolean offsetsNeedStored;
    
    public ComponentDragHandler(Component component, Component parent)
    {
        this.component = component;
        this.parent = parent;
        offsetsNeedStored = true;
    }
    
    public void attach()
    {
        component.addMouseMotionListener(this);
        component.addMouseListener(this);
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
        
        component.setLocation(e.getXOnScreen() - parent.getX() - relativeStartX, 
            e.getYOnScreen() - parent.getY() - relativeStartY);
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
