/*
 * (C) John Schneider 2020
 */
package main;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author John Schneider
 */
public class MainToolbar extends JPanel implements ActionListener
{
    private JPanel toolbarTop, toolbarBottom;
    private JPanel searchHolder;
    private JPanel windowButtonsHolder;
    private JButton minimize, restoreMaximize, close;
    private JFrame window;
    private FlowLayout layout;
    
    private boolean isMaximizeButton;
    private Dimension restoreSize;
    private Point restoreLocation;
    private GraphicsConfiguration config;
    private Toolkit defaultToolkit;
    
    public MainToolbar(JFrame window)
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
        layout = new FlowLayout();
        layout.setVgap(0);
        this.setLayout(layout);
        
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
        
    }
    
    private void buildWindowButtonsHolder()
    {
        windowButtonsHolder = new JPanel();
        windowButtonsHolder.setLayout(layout);
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
        
        newButton.setForeground(Color.WHITE);
        newButton.setOpaque(true);
        newButton.setBackground(toolbarTop.getBackground());
        newButton.setBorderPainted(false);
        newButton.addActionListener(this);
        newButton.setFocusable(false);
        newButton.setPreferredSize(new Dimension(45, 
            toolbarTop.getPreferredSize().height));
        newButton.addMouseListener(new HoverHandler(newButton, hoverColor));
                
        return newButton;
    }
    
    private void buildBottom()
    {
        toolbarBottom = new JPanel();
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
}
