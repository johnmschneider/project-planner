/*
 * (C) John Schneider 2020
 *
 *  Turns the relative filepath specified into an absolute filepath
 *      starting at the path of the resource folder for the program.
 *
 *  The resourceDirectoryPath must be set before using this class.
 */
package main;

import java.io.File;

/**
 *
 * @author John Schneider
 */
public class ResourceFile extends File
{
    private static String resourceDirectoryPath;
    
    public ResourceFile(String relativePath)
    {
        super(resourceDirectoryPath + "/" + relativePath);
    }
    
    public static void setResourceDirectoryPath(String resourceDirectoryPath)
    {
        ResourceFile.resourceDirectoryPath = resourceDirectoryPath;
    }
}
