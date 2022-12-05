package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Binome implements SimpleShape{
    int m_x;

    int m_y;

    public Binome(int x, int y) {
        m_x = x - 25;
        m_y = y - 25;
    }

    /**
     * Implements the <tt>SimpleShape.draw()</tt> method for painting
     * the shape.
     * @param g2 The graphics object used for painting.
     */
    public void draw(Graphics2D g2){
        this.draw(g2,false);
    }

    public void draw(Graphics2D g2,boolean estDansGroupe) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint(m_x, m_y, Color.pink, m_x + 50, m_y, Color.WHITE);
        g2.setPaint(gradient);
        g2.fill(new Rectangle2D.Double(m_x, m_y, 50, 50));
        BasicStroke wideStroke = new BasicStroke(4.0f);
        Color bordure = (estDansGroupe) ? Color.red : Color.black;
        g2.setColor(bordure);
        g2.setStroke(wideStroke);
        g2.draw(new Rectangle2D.Double(m_x, m_y, 50, 50));
        //TODO
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getX() {
        return m_x + 25;
    }

    @Override
    public void setX(int x){
        this.m_x = x;
    }

    @Override
    public int getY() {
        return m_y + 25;
    }

    @Override
    public void setY(int Y){
        this.m_y = Y;
    }

    @Override
    public void move(int deltaX,int deltaY){
        this.setX(this.m_x + deltaX);
        this.setY(this.m_y + deltaY);
    }

    @Override
    public boolean isSelect(int coordX, int coordY) {
        return (
                (coordX >= this.getX() - 25) &&
                        (coordX <= this.getX() + 25) &&
                        (coordY >= this.getY() - 25) &&
                        (coordY <= this.getY() + 25)
        );
    }

    @Override
    public SimpleShape shapeSelect(int x, int y){
        return this;
    }
}
