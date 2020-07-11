/*
 * (C) John Schneider 2020
 */
package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author John Schneider
 */
public class Main implements Runnable
{
    private JFrame mainWindow;
    private JScrollPane projectsPane;
    private JPanel projectsPanePanel;
    private MainToolbar toolbar;
    
    public static void main(String[] args)
    {
        Main main = new Main();
        SwingUtilities.invokeLater(main);
    }

    @Override
    public void run()
    {
        initializeResourceFile();
        buildFrame();
        addComponentsToFrame();

        mainWindow.setVisible(true);
    }

    private void initializeResourceFile()
    {
        //TODO : replace this with install directory
        ResourceFile.setResourceDirectoryPath("D:/non college related/java/"
                + "workspace/project planner/res");
    }

    private void buildFrame()
    {
        mainWindow = new JFrame("Project Planner");
        mainWindow.setSize(1600, 900);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLayout(new BorderLayout());
        mainWindow.setUndecorated(true);
        
        toolbar = new MainToolbar(mainWindow);
        
        buildGroups();
        buildProjectsPane();
    }

    
    private void buildGroups()
    {

    }

    private void buildProjectsPane()
    {
        projectsPane = new JScrollPane();
        
        buildProjectsPanePanel();

        //scroll to top
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                projectsPane.getVerticalScrollBar().setValue(0);
            }
        });
    }

    private void buildProjectsPanePanel()
    {
        projectsPanePanel = new JPanel();
        
        //GridLayout gl = new GridLayout(0, 10, 10, 5);
        WrapLayout wl = new WrapLayout();
        wl.setVgap(20);
        wl.setHgap(60);
        projectsPanePanel.setLayout(wl);
    }

    private void addComponentsToFrame()
    {
        addComponentsToProjectsPane();
        
        mainWindow.add(toolbar, BorderLayout.NORTH);
        mainWindow.add(projectsPane, BorderLayout.CENTER);
    }

    private void addComponentsToProjectsPane()
    {
        addComponentsToProjectsPanePanel();

        projectsPane.setViewportView(projectsPanePanel);
    }

    private void addComponentsToProjectsPanePanel()
    {
        projectsPanePanel.add(new NewProjectPanel());
        
        // TODO : temp test
        for (int i = 0; i < 25; i++)
        {
            projectsPanePanel.add(new ProjectPreviewPanel(Math.random() > 0.5 ? (Math.random() > 0.5 ? "a super duper long name test" : "1234567890123") : "test"));
        }
    }
    
}
