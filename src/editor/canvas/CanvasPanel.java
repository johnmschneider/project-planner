/*
 * (C) John Schneider 2020
 */
package editor.canvas;

import editor.ToolbarHeight;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author John Schneider
 */
public class CanvasPanel extends JPanel implements ComponentListener
{
    private BufferedImage canvas;
    private JFrame window;
    private ToolbarHeight height;
    private boolean isPainting;
    
    public CanvasPanel(JFrame window, ToolbarHeight height)
    {
        super();
        
        this.window = window;
        this.height = height;
        
        isPainting = false;
        canvas = new BufferedImage(window.getWidth(), window.getHeight(), 
            BufferedImage.TYPE_INT_ARGB);
        
        setLayout(null);
        setOpaque(false);
        window.addComponentListener(this);
    }
    
    
    @Override
    public void paintComponent(Graphics g)
    {
        if (!isPainting)
        {
            isPainting = true;
            Graphics cg = getCanvasGraphics();
            
            clearCanvas(cg);
            
            super.paintComponent(cg);
            paintAllSubcomponents(cg);
            
            g.drawImage(canvas, 0, 0, null);
            
            isPainting = false;
        }
    }
    
    private void paintAllSubcomponents(Graphics g)
    {
        paintAllSubcomponents((Container) this);
    }
    
    /**
     * For some reason, component.paintAll was painting components above
     *  the hierarchy (i.e. the PARENTS of this component as well as the 
     *  children). This method will ONLY paint the children.
     */
    private void paintAllSubcomponents(Container parent)
    {
        for (Component child : parent.getComponents())
        {
            if (child instanceof Container)
            {
                Container childContainer = (Container) child;
                
                child.repaint();
                paintAllSubcomponents(childContainer);
            }
            else
            {
                child.repaint();
            }
        }
    }
    
    public void clearCanvas(Graphics g)
    {
        Color oldColor = g.getColor();
        g.setColor(getBackground());
        g.fillRect(0, 0, canvas.getWidth(), 
            canvas.getHeight());
        g.setColor(oldColor);
    }
    
    @Override
    public void componentResized(ComponentEvent e)
    {
        canvas = new BufferedImage(window.getWidth(), window.getHeight(), 
            BufferedImage.TYPE_INT_ARGB);
        paintComponent(getGraphics());
    }

    @Override
    public void componentMoved(ComponentEvent e)
    {
        if (e.getSource() != window)
        {
            paintComponent(getGraphics());
        }
    }

    @Override
    public void componentShown(ComponentEvent e)
    {
        paintComponent(getGraphics());
    }

    @Override
    public void componentHidden(ComponentEvent e)
    {
    }
    
    public Graphics getCanvasGraphics()
    {
        return canvas.getGraphics();
    }
    
    public boolean isPaintingSubcomponents()
    {
        return isPainting;
    }
    
}
