/*
 * (C) John Schneider 2020
 */
package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author John Schneider
 */
public class HoverHandler extends MouseAdapter
{
    private Component parent;
    private Color hoverColor;
    private Color oldColor;
    
    public HoverHandler(Component parent, Color hoverColor)
    {
        this.parent = parent;
        this.hoverColor = hoverColor;
    }
    
    @Override
    public void mouseEntered(MouseEvent e)
    {
        oldColor = parent.getBackground();
        parent.setBackground(hoverColor);
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        parent.setBackground(oldColor);
    }
    
}
