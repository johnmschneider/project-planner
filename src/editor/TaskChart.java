/*
 * (C) John Schneider 2020
 */
package editor;

import editor.canvas.Task;
import editor.canvas.CanvasPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import main.Main;
import main.PanelBuilder;


/**
 *
 * @author John Schneider
 */
public class TaskChart implements PanelBuilder
{
    private int ticksSinceLastSecond;
    private float zoom;
    
    private JFrame window;
    private EditorToolbar toolbar;
    private CanvasPanel canvas;
    
    private ArrayList<Task> tasksToAdd;
    private boolean needsToLoadDefaultChart;
    
    private long timeOfLastRefresh;
    private int minimumMillisecondsBetweenRefreshCalls;
    
//    private Thread secondTimer;
            
    public TaskChart(JFrame window)
    {
        needsToLoadDefaultChart = true;
        this.window = window;
        tasksToAdd = new ArrayList<>();
        timeOfLastRefresh = System.currentTimeMillis();
        minimumMillisecondsBetweenRefreshCalls = 10;
        
//        secondTimer = new Thread(this);
//        secondTimer.start();
    }
    
    public TaskChart(String filepath, JFrame window)
    {
        this(window);
        
        needsToLoadDefaultChart = false;
        loadChart(filepath);
    }

    
    private void loadDefaultChart()
    {
        Task root = new Task(this, "Learn Project Planner");
        Task newTask = new Task(this, "Create a new task");
        
        root.setLocation(500, 25);
        root.addChildTask(newTask);
        
        tasksToAdd.add(root);
    }
    
    private void loadChart(String filepath)
    {
        throw new UnsupportedOperationException("File opening not yet supported in editor.TaskChart class.");
    }
    
    @Override
    public void build()
    {
        window.getContentPane().setLayout(new BoxLayout(window.getContentPane(), BoxLayout.PAGE_AXIS));
        toolbar = new EditorToolbar(window);
        canvas = new CanvasPanel(window, toolbar);
        
        Dimension winSize = window.getSize();
        canvas.setBackground(new Color(130, 130, 125));
        canvas.setPreferredSize(new Dimension(
            (int) winSize.getWidth(), (int) winSize.getHeight() - 
            toolbar.getTotalHeight()));
        
        if (needsToLoadDefaultChart)
        {
            loadDefaultChart();
        }
    }

    @Override
    public void addComponentsToFrame()
    {
        window.add(toolbar);
        
        addTasksToCanvas();
        window.add(canvas);
    }
    
    private void addTasksToCanvas()
    {
        for (Task t : tasksToAdd)
        {
            canvas.add(t);
        }
    }
    
    @Override
    public void removeComponentsFromFrame()
    {
        window.remove(toolbar);
        window.remove(canvas);
    }
    
    public Graphics getGraphics()
    {
        return canvas.getCanvasGraphics();
    }
    
    public CanvasPanel getCanvas()
    {
        return canvas;
    }
    
    public void clearCanvas()
    {
        //don't allow a clearing of the frame without the ability to 
        //  repaint the canvas
        if (canvasCanRefresh())
        {
            canvas.clearCanvas(canvas.getGraphics());
        }
    }
    
    public void refreshCanvas()
    {
        if (canvasCanRefresh())
        {
            canvas.paintComponent(canvas.getGraphics());
            timeOfLastRefresh = System.currentTimeMillis();
        }
        // else drop the frame
    }
    
    public boolean canvasCanRefresh()
    {
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - timeOfLastRefresh;
        
        // give the Event Dispatch Thread time to repaint (to avoid flickering)
        return (timeElapsed >=
            minimumMillisecondsBetweenRefreshCalls);
    }
    
}
