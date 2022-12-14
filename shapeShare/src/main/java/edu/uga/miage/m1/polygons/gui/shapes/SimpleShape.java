package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.visitor.Visitor;

import javax.swing.*;

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

    JLabel getLabel();
    void accept(Visitor visitor);

    void select();

    void move(int deltaX,int deltaY);

    void deplace(int x,int y);

    boolean isSelect(int coordX,int coordY);

    SimpleShape shapeSelect(int x, int y);
}