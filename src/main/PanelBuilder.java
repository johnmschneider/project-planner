/*
 * (C) John Schneider 2020
 */
package main;

/**
 *
 * @author John Schneider
 */
public interface PanelBuilder
{
    void build();
    void addComponentsToFrame();
    void removeComponentsFromFrame();
}
