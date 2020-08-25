/*
 * (C) John Schneider 2020
 */
package editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import main.HoverHandler;
import main.MainToolbarDragHandler;
import main.ResourceFile;
import main.ResourceLoader;
import main.WrapLayout;

/**
 *
 * @author John Schneider
 */
public class EditorToolbar extends JPanel implements ActionListener, 
    ToolbarHeight
{
    private JPanel toolbarTop, toolbarBottom;
    private JPanel searchHolder;
    private JPanel windowButtonsHolder;
    private JButton minimize, restoreMaximize, close;
    private JFrame window;
    private FlowLayout noVgapFlowlayout;
    private BorderLayout noVgapBorderLayout;
    
    private boolean isMaximizeButton;
    private Dimension restoreSize;
    private Point restoreLocation;
    private GraphicsConfiguration config;
    private Toolkit defaultToolkit;
    
    private JButton newTask;
    
    public EditorToolbar(JFrame window)
    {
        super();
        
        initializeThis(window);
        buildThis();
        addComponentsToThis();
    }
    
    private void initializeThis(JFrame window)
    {
        this.window = window;
        isMaximizeButton = true;
        
        defaultToolkit = Toolkit.getDefaultToolkit();
        config = window.getGraphicsConfiguration();
    }
    
    private void buildThis()
    {
        noVgapFlowlayout = new FlowLayout();
        noVgapFlowlayout.setVgap(0);
        
        noVgapBorderLayout = new BorderLayout();
        noVgapBorderLayout.setVgap(0);
        
        this.setLayout(noVgapBorderLayout);
        
        buildToolbar();
    }
    
    private void buildToolbar()
    {
        buildTop();
        buildBottom();
    }

    private void buildTop()
    {
        toolbarTop = new JPanel();
        toolbarTop.setBackground(new Color(66, 57, 239));
        toolbarTop.setPreferredSize(new Dimension(
            window.getWidth(), 30));
        
        BorderLayout bl = new BorderLayout();
        bl.setVgap(0);
        toolbarTop.setLayout(bl);
        
        MainToolbarDragHandler dragHandler = new MainToolbarDragHandler(window);
        toolbarTop.addMouseListener(dragHandler);
        toolbarTop.addMouseMotionListener(dragHandler);
        
        buildSearchBar();
        buildWindowButtonsHolder();
    }
    
    private void buildSearchBar()
    {
        // throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private void buildWindowButtonsHolder()
    {
        windowButtonsHolder = new JPanel();
        windowButtonsHolder.setLayout(noVgapFlowlayout);
        windowButtonsHolder.setOpaque(false);
        
        FlowLayout fl = new FlowLayout();
        fl.setHgap(0);
        fl.setVgap(0);
        windowButtonsHolder.setLayout(fl);
        
        buildWindowControlButtons();
    }
    
    private void buildWindowControlButtons()
    {
        Color defaultHoverColor = new Color(20, 13, 163);
        
        minimize = buildWindowControlButton("--", defaultHoverColor);
        restoreMaximize = buildWindowControlButton("[ ]", defaultHoverColor);
        close = buildWindowControlButton("X", Color.RED);
    }
    
    private JButton buildWindowControlButton(String text, Color hoverColor)
    {
        JButton newButton = new JButton(text);
        
        newButton.setOpaque(true);
        newButton.setForeground(Color.WHITE);
        newButton.setBackground(toolbarTop.getBackground());
        newButton.setBorderPainted(false);
        newButton.setPreferredSize(new Dimension(45, 
            toolbarTop.getPreferredSize().height));
        
        newButton.setFocusable(false);
        newButton.addActionListener(this);
        newButton.addMouseListener(new HoverHandler(newButton, hoverColor));
                
        return newButton;
    }
    
    private void buildBottom()
    {
        toolbarBottom = new JPanel();
        
        toolbarBottom.setBackground(new Color(66, 57, 239));
        toolbarBottom.setPreferredSize(new Dimension(
            window.getWidth(), 50));
        
        toolbarBottom.setBackground(new Color(183, 205, 255));
        
        toolbarBottom.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        Dimension size = new Dimension(35, 35);
        newTask = new JButton();
        newTask.setOpaque(false);
        newTask.setContentAreaFilled(false);
        
        newTask.setIcon(ResourceLoader.getScaledImageIcon(
            new ResourceFile("newTask.png"), size));
        newTask.setPreferredSize(new Dimension(45, 45));
        
        toolbarBottom.add(newTask);
    }
    
    private void addComponentsToThis()
    {
        addComponentsToButtonsHolder();
        addComponentsToTop();
                
        add(toolbarTop, BorderLayout.NORTH);
        add(toolbarBottom, BorderLayout.SOUTH);
    }
    
    private void addComponentsToButtonsHolder()
    {
        windowButtonsHolder.add(minimize, BorderLayout.WEST);
        windowButtonsHolder.add(restoreMaximize, BorderLayout.CENTER);
        windowButtonsHolder.add(close, BorderLayout.EAST);
    }
    
    private void addComponentsToTop()
    {
        toolbarTop.add(windowButtonsHolder, BorderLayout.EAST);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
                
        if (source == minimize)
        {
            window.setState(JFrame.ICONIFIED);
        }
        else if (source == restoreMaximize)
        {
            if (isMaximizeButton)
            {
                restoreSize = window.getSize();
                restoreLocation = window.getLocation();
                
                maximizeWindow();
                resizeThis();
                
                isMaximizeButton = false;
            }
            else
            {
                window.setSize(restoreSize);
                window.setLocation(restoreLocation);
                
                resizeThis();
                
                isMaximizeButton = true;
            }
        }
        else if (source == close)
        {
            window.dispatchEvent(new WindowEvent(window, 
                WindowEvent.WINDOW_CLOSING));
        }
    }
    
    private void maximizeWindow()
    {
        Insets insets = defaultToolkit.getScreenInsets(config);
        Dimension screenSize = defaultToolkit.getScreenSize();
        
        int width = screenSize.width - insets.left - insets.right;
        int height = screenSize.height - insets.top - insets.bottom;
        
        window.setSize(width, height);
        window.setLocationRelativeTo(null);
    }
    
    private void resizeThis()
    {
        int width = window.getWidth();
        this.setPreferredSize(new Dimension(width, this.getHeight()));
        
        toolbarTop.setPreferredSize(new Dimension(width, 
            toolbarTop.getHeight()));
        
        toolbarBottom.setPreferredSize(new Dimension(width,
            toolbarBottom.getHeight()));
    }
    
    @Override
    public int getTotalHeight()
    {
        return toolbarTop.getHeight() + toolbarBottom.getHeight();
    }
}
