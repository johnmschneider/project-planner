/*
 * (C) John Schneider 2020
 */
package editor.canvas;

import editor.ComponentDragHandler;
import editor.TaskChart;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JButton;

/**
 *
 * @author John Schneider
 */
public class Task extends JButton implements ActionListener
{
    private ArrayList<Task> parentTasks;
    private ArrayList<Task> childTasks;
    private String taskText;
    private static final int MINIMUM_HEIGHT = 50;
    private TaskChart chart;
    private CanvasPanel canvas;
    
    private ComponentDragHandler dragHandler;
    private boolean isPainting;
    
    public Task(TaskChart chart)
    {
        super();
        
        parentTasks = new ArrayList<>();
        childTasks = new ArrayList<>();
        
        this.chart = chart;
        this.canvas = chart.getCanvas();
        dragHandler = new ComponentDragHandler(this, chart.getCanvas());
        isPainting = false;
        
        setBackground(new Color(225, 225, 250));
        setForeground(Color.black);
        
        dragHandler.attach();
    }
    
    public Task(TaskChart chart, String text)
    {
        this(chart);
        
        setTaskText(text);
    }
    
    
    public void setTaskText(String text)
    {
        taskText = text;
        this.setPreferredSize(new Dimension(200, calculateHeight()));
        
        this.setBounds(getX(), getY(), 200, calculateHeight());
    }
    
    private int calculateHeight()
    {
        int simpleHeight = 12 * getNumberOfLines();
        
        return (simpleHeight >= MINIMUM_HEIGHT ? simpleHeight : MINIMUM_HEIGHT);
    };
    
    private int getNumberOfLines()
    {
        return getTaskText().split("\n").length;
    }
    
    public String getTaskText()
    {
        return taskText;
    }
    
    public void addParentTask(Task parent)
    {
        parentTasks.add(parent);
    }
    
    public boolean taskHasParent()
    {
        return !parentTasks.isEmpty();
    }
    
    public void addChildTask(Task child)
    {
        childTasks.add(child);
        
        child.setLocation(getX() + 225, getY() + 50 + 
            (MINIMUM_HEIGHT * childTasks.size()));
        child.addActionListener(this);
        chart.getCanvas().add(child);
    }
    
    @Override
    public void setLocation(int x, int y)
    {
        super.setLocation(x, y);
        
        Graphics g = getGraphics();
        
        if (g != null)
        {
            updateConnections(g);
        }
    }
    
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        if (!isPainting && chart.canvasCanRefresh())
        {
            boolean canvasIsPainting = chart.getCanvas().
                isPaintingSubcomponents();
            Graphics cg = chart.getGraphics();
            
            if (!canvasIsPainting)
            {
                chart.clearCanvas();
            }

            updateConnections(cg);
            
            if (!canvasIsPainting)
            {
                chart.refreshCanvas();
            }
            
            isPainting = false;
        }
    }
    
    public void updateConnections(Graphics g)
    {
        setParentTaskPositions();
        setChildTaskPositions();
        updateConnectionToParentTasks(g);
        drawConnectionToChildTasks();
        drawTaskText(g);
    }
    
    private void setParentTaskPositions()
    {
        for (int i = 0; i < parentTasks.size(); i++)
        {
            Task parent = parentTasks.get(i);
            
            parent.setLocation(getX() - 225, getY() - (50 + 
                (MINIMUM_HEIGHT * parentTasks.size())));
        }
    }
    
    private void setChildTaskPositions()
    {
        for (int i = 0; i < childTasks.size(); i++)
        {
            Task child = childTasks.get(i);
            
            child.setLocation(getX() + 225, getY() + 50 + 
                (MINIMUM_HEIGHT * childTasks.size()));
        }
    }
    
    private void updateConnectionToParentTasks(Graphics g)
    {
        for (int i = 0; i < parentTasks.size(); i++)
        {
            Task parent = parentTasks.get(i);
            parent.paintComponent(chart.getCanvas().getCanvasGraphics());
        }
    }
    
    public void drawConnectionToChildTasks()
    {
        Graphics g = chart.getGraphics();
        Color oldColor = g.getColor();
        
        g.setColor(Color.cyan);
        
        for (Task child : childTasks)
        {
            CanvasPanel canvas = chart.getCanvas();
            g.drawLine(canvas.getX() + getX(), canvas.getY() + getY() - 160, 
                canvas.getX() + child.getX(), canvas.getY() + child.getY() - 
                160);
        }
        
        g.setColor(oldColor);
    }
    
    public void drawTaskText(Graphics g)
    {
        Color oldColor = g.getColor();
        
        Graphics2D g2d = (Graphics2D) g;
        Rectangle2D bounds = g.getFont().getStringBounds(getTaskText(), 
            g2d.getFontRenderContext());
        Dimension size = getPreferredSize();
        
        g.drawString(getTaskText(), 
            (int) (  size.getWidth()  - bounds.getWidth())  / 2,
            (int) (((size.getHeight() - bounds.getHeight()) / 2) +
                bounds.getHeight()/1.25f));
        g.setColor(oldColor);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("my id == " + this);
    }
    
}
