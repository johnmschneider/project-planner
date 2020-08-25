/*
 * (C) John Schneider 2020
 */
package main;

import editor.TaskChart;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import main.Main.PanelTypeArgs;

/**
 *
 * @author John Schneider
 */
public class MainPanel implements PanelBuilder, ActionListener
{
    private Main main;
    private JFrame mainWindow;
    
    private JScrollPane projectsPane;
    private JPanel projectsPanePanel;
    private MainToolbar toolbar;
    private NewProjectPanel newButton;
    
    
    public MainPanel(Main main)
    {
        this.main = main;
        mainWindow = main.getMainWindow();
    }

    @Override
    public void build()
    {
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
        scrollProjectsPaneToTop();
    }
    
    private void buildProjectsPanePanel()
    {
        projectsPanePanel = new JPanel();
        
        WrapLayout wl = new WrapLayout();
        wl.setVgap(20);
        wl.setHgap(60);
        projectsPanePanel.setLayout(wl);
    }
    
    private void scrollProjectsPaneToTop()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                projectsPane.getVerticalScrollBar().setValue(0);
            }
        });
    }
    
    @Override
    public void addComponentsToFrame()
    {
        addComponentsToProjectsPane();
        
        mainWindow.add(toolbar, BorderLayout.NORTH);
        mainWindow.add(projectsPane, BorderLayout.CENTER);
    }

    private void addComponentsToProjectsPane()
    {
        ArrayList<ProjectPreviewPanel> panels = getProjectsPanePanels();
        
        addComponentsToProjectsPanePanel(panels);

        projectsPane.setViewportView(projectsPanePanel);
    }
    
    private ArrayList<ProjectPreviewPanel> getProjectsPanePanels()
    {
        newButton = new NewProjectPanel();
        newButton.addPreviewActionListener(this);
        
        return getTestPanels();
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == newButton.getProjectPreviewObject())
        {
            main.setCurrentPanelType(Main.PanelType.EDITOR, 
                new PanelTypeArgs());
        }
    }
    
    // TODO : temp test
    private ArrayList<ProjectPreviewPanel> getTestPanels()
    {
        ArrayList<ProjectPreviewPanel> testPanels = new ArrayList<>();
        
        for (int i = 0; i < 25; i++)
        {
            testPanels.add(new ProjectPreviewPanel(Math.random() > 0.5 ? (Math.random() > 0.5 ? "a super duper long name test" : "1234567890123") : "test"));
        }
        
        return testPanels;
    }
    
    private void addComponentsToProjectsPanePanel(ArrayList<ProjectPreviewPanel>
        panels)
    {
        projectsPanePanel.add(newButton);
        
        for (int i = 0; i < panels.size(); i++)
        {
            projectsPanePanel.add(panels.get(i));
        }
    }
    
    @Override
    public void removeComponentsFromFrame()
    {
        mainWindow.remove(toolbar);
        mainWindow.remove(projectsPane);
    }
}
