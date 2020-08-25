/*
 * (C) John Schneider 2020
 */
package main;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author John Schneider
 */
public class ResourceLoader
{
    
    public static Image getScaledImage(ResourceFile path, Dimension size)
    {
        BufferedImage bufferedImage;
        Image scaledImage = null;
        
        try 
        {
            bufferedImage = ImageIO.read(path);
            
            scaledImage = bufferedImage.getScaledInstance((int) size.getWidth(), 
                (int) size.getHeight(), Image.SCALE_SMOOTH);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        
        return scaledImage;
    }
    
    public static ImageIcon getScaledImageIcon(ResourceFile path, 
        Dimension size)
    {
        return new ImageIcon(getScaledImage(path, size));
    }
}
