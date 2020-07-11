/*
 * (C) John Schneider 2020
 */
package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author John Schneider
 */
public class ProjectPreviewPanel extends JPanel implements FocusListener
{   
    private JButton projectPreview;
    private WrappedCenteredTextPane projectName;
    //11 chars fit but allow 1 extra for hyphen
    private static final int MAX_CHARS_PER_LINE = 10;
    private static final int HEIGHT_PER_LINE = 20;
    public static final Dimension PANEL_SIZE = new Dimension(85, 175);

    public class WrappedCenteredTextPane extends JTextPane
    {    
        private static final int MAX_CHARS_PER_LINE = 11;
        private static final int HEIGHT_PER_LINE = 20;
        private String originalText;
                
        public WrappedCenteredTextPane(String text)
        {
            super();
            
            this.originalText = text;
            
            String linedText = "" + text.charAt(0);
            int len = text.length();
            
            if (len > MAX_CHARS_PER_LINE)
            {
                for (int i = 1; i < len; i++)
                {
                    if (i % MAX_CHARS_PER_LINE == 0)
                    {
                        linedText += "-\n";
                    }
                    
                    linedText += text.charAt(i);
                }
                
                int lines = (int) Math.ceil(((float) len) / MAX_CHARS_PER_LINE);
                this.setPreferredSize(new Dimension((int)
                    ProjectPreviewPanel.PANEL_SIZE.getWidth(), HEIGHT_PER_LINE * 
                    lines));
            }
            else 
            {
                linedText = text;
                this.setPreferredSize(new Dimension(
                    (int) ProjectPreviewPanel.PANEL_SIZE.getWidth(), 20));
            }
            
            setText(linedText);
        }
        
        public String getOriginalText()
        {
            return originalText;
        }
    }
    
    public ProjectPreviewPanel(String textUnderPreview)
    {
        super();

        buildThis(textUnderPreview);        
    }
    
    private void buildThis(String textUnderPreview)
    {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setPreferredSize(PANEL_SIZE);
        this.setMaximumSize(PANEL_SIZE);
        
        buildProjectPreview();
        buildProjectName(textUnderPreview);
        addComponentsToThis();
    }
    
    private void buildProjectPreview()
    {
        Color bg = new Color((float) Math.random(), 
            (float) Math.random(), (float) Math.random());
        
        projectPreview = new JButton();
        projectPreview.setPreferredSize(new Dimension((int) 
            PANEL_SIZE.getWidth(), 115));
        projectPreview.setMaximumSize(new Dimension((int) PANEL_SIZE.getWidth(), 
            115));
        
        projectPreview.setBackground(bg);
        projectPreview.setForeground(new Color(255 - bg.getRed(),
            255 - bg.getGreen(), 255 - bg.getBlue()));
        
        projectPreview.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    private void buildProjectName(String textUnderPreview)
    {
        projectName = new WrappedCenteredTextPane(textUnderPreview);
        projectName.addFocusListener(this);        
        projectName.setOpaque(false);
        projectName.setMaximumSize(new Dimension((int) PANEL_SIZE.getWidth(),
            60));
        centerProjectName();
    }
    
    private void centerProjectName()
    {
        StyledDocument doc = projectName.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        
        projectName.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    private void addComponentsToThis()
    {
        this.add(projectPreview);
        this.add(projectName);
    }
    
    @Override
    public void focusGained(FocusEvent e)
    {
        if (e.getSource() == projectName)
        {
            projectName.setOpaque(true);
            projectName.paintAll(projectName.getGraphics());
        }
    }

    @Override
    public void focusLost(FocusEvent e)
    {
        if (e.getSource() == projectName)
        {
            projectName.setOpaque(false);
            this.paintAll(this.getGraphics());
            // TODO : save new name
        }
    }
    
    public JButton getProjectPreviewObject()
    {
        return projectPreview;
    }
    
    public WrappedCenteredTextPane getProjectNameObject()
    {
        return projectName;
    }
    
    public void addPreviewActionListener(ActionListener al)
    {
        projectPreview.addActionListener(al);
    }
    
    public void setEditable(boolean editable)
    {
        projectName.setEditable(editable);
    }
}
