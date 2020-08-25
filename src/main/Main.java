/*
 * (C) John Schneider 2020
 */
package main;

import editor.TaskChart;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import main.Main.PanelTypeArgs.ArgType;

/**
 *
 * @author John Schneider
 */
public class Main implements Runnable
{
    public static enum PanelType
    {
        MAIN,
        EDITOR;
    }
    
    public static class PanelTypeArgs
    {
        public static enum ArgType
        {
            FILEPATH;
        }
        
        private HashMap<ArgType, String> arguments;
        
        
        public PanelTypeArgs()
        {
            arguments = new HashMap<>();
        }
        
        public void set(ArgType type, String argument)
        {
            arguments.put(type, argument);
        }
        
        public String get(ArgType type)
        {
            return arguments.get(type);
        }
        
        public boolean isEmpty()
        {
            return arguments.isEmpty();
        }
    }
    
    private PanelType currentPanelType;
    
    private JFrame mainWindow;
    private MainPanel mainPanel;
    private TaskChart editor;
    private static boolean programExiting;
    
    public static void main(String[] args)
    {
        programExiting = false;
        
        Main main = new Main();
        SwingUtilities.invokeLater(main);
    }

    @Override
    public void run()
    {
        initializeVariables();
        initializeResourceFile();
        
        setCurrentPanelType(PanelType.MAIN, new PanelTypeArgs());
        
        mainWindow.setVisible(true);
    }
    
    private void initializeVariables()
    {
        currentPanelType = null;
        
        initializeFrame();
    }
    
    private void initializeFrame()
    {
        mainWindow = new JFrame("Project Planner");
        mainWindow.setSize(1600, 900);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLayout(new BorderLayout());
        mainWindow.setUndecorated(true);
    }
    
    private void initializeResourceFile()
    {
        //TODO : replace this with install directory
        ResourceFile.setResourceDirectoryPath("D:/non college related/java/"
                + "workspace/project planner/res");
    }
    
    public void setCurrentPanelType(PanelType type, PanelTypeArgs args)
    {
        destructPanel(args);
        
        currentPanelType = type;
        initializePanel(args);
        
        mainWindow.paintAll(mainWindow.getGraphics());
    }
    
    private void destructPanel(PanelTypeArgs args)
    {
        if (previousPanelTypeExists())
        {
            switch (currentPanelType)
            {
                case MAIN:
                    mainPanel.removeComponentsFromFrame();

                    break;
                case EDITOR:
                    editor.removeComponentsFromFrame();

                    break;
            }
        }
    }
    
    private boolean previousPanelTypeExists()
    {
        return currentPanelType != null;
    }
    
    private void initializePanel(PanelTypeArgs args)
    {
        switch (currentPanelType)
        {
            case MAIN:
                initializeMain(args);
                
                break;
            case EDITOR:
                initializeEditor(args);
                
                break;
        }
    }
    
    private void initializeMain(PanelTypeArgs args)
    {
        mainPanel = new MainPanel(this);
        mainPanel.build();
        mainPanel.addComponentsToFrame();
    }
    
    private void initializeEditor(PanelTypeArgs args)
    {
        if (args.isEmpty())
        {
            editor = new TaskChart(mainWindow);
        }
        else
        {
            editor = new TaskChart(args.get(ArgType.FILEPATH), mainWindow);
        }
        
        editor.build();
        editor.addComponentsToFrame();
    }
    
    public JFrame getMainWindow()
    {
        return mainWindow;
    }
    
    public static synchronized void flagProgramAsExiting()
    {
        programExiting = true;
    }
    
    public static boolean isProgramExiting()
    {
        return programExiting;
    }
}
