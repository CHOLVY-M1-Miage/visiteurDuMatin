package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * This interface defines the <tt>SimpleShape</tt> extension. This extension
 * is used to draw shapes. 
 * 
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 *
 */
public interface SimpleShape
{

    /**
     * Method to draw the shape of the extension.
     * @param g2 The graphics object used for painting.
     **/
    void draw(JPanel m_panel);
    void draw(JPanel m_panel,boolean estDansGroupe);

    int getX();

    void setX(int x);
    
    int getY();

    void setY(int y);

    void accept(Visitor visitor);

    void move(int deltaX,int deltaY);

    boolean isSelect(int coordX,int coordY);

    SimpleShape shapeSelect(int x, int y);
}