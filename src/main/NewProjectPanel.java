/*
 * (C) John Schneider 2020
 */
package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author John Schneider
 */
public class NewProjectPanel extends ProjectPreviewPanel
{
    
    public NewProjectPanel()
    {
        super("New Project");
        
        setEditable(false);
        
        JButton preview = this.getProjectPreviewObject();
        preview.setText("+");
        preview.setForeground(new Color(150, 150, 150));
        preview.setBackground(new Color(220, 220, 220));
        preview.setFont(new Font("Arial", Font.PLAIN, 50));
        preview.paintAll(preview.getGraphics());
    }
    
    @Override
    public void focusGained(FocusEvent e)
    {

    }
    
    @Override
    public void focusLost(FocusEvent e)
    {

    }
}
