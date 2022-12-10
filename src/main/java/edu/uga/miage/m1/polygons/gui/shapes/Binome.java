package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

public class Binome implements SimpleShape{
    int m_x;
    int m_y;
    JLabel label;

    ImageIcon icon = new ImageIcon("src/main/resources/edu/uga/miage/m1/polygons/gui/images/icones/luffyHat.png");

    public Binome(int x, int y) {
        m_x = x - 25;
        m_y = y - 25;
    }

    /**
     * Implements the <tt>SimpleShape.draw()</tt> method for painting
     * the shape.
     * @param g2 The graphics object used for painting.
     */
    public void draw(JPanel m_panel){
        this.draw(m_panel,false);
    }
    public void draw(JPanel m_panel,boolean estDansGroupe) {
        this.label = new JLabel(icon);
        m_panel.add(this.label);
        Dimension size = label.getPreferredSize();
        this.label.setBounds(getX(), getY(), size.width, size.height);
        this.label.setLocation(getX(),getY());
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getX() {
        return m_x;
    }

    @Override
    public void setX(int x){
        this.m_x = x;
    }

    @Override
    public int getY() {
        return m_y;
    }

    @Override
    public void setY(int Y){
        this.m_y = Y;
    }

    @Override
    public void move(int x,int y){
        this.label.setLocation(x,y);
        setX(x);
        setY(y);
    }

    @Override
    public boolean isSelect(int coordX, int coordY) {
        return (
                (coordX >= this.getX()) &&
                        (coordX <= this.getX() + 50) &&
                        (coordY >= this.getY()) &&
                        (coordY <= this.getY() + 50)
        );
    }

    @Override
    public SimpleShape shapeSelect(int x, int y){
        return this;
    }
}
