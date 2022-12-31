package edu.uga.miage.m1.polygons.gui.shapes;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Shape {
    int mX;
    int mY;
    JLabel label;
    ImageIcon icon;


    Shape(int x, int y, String lien){
        mX = x - 25;
        mY = y - 25;
        icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(lien)));
    }

    public int getX() {
        return mX;
    }

    public void setX(int x) {
        this.mX = x;
    }

    public int getY() {
        return mY;
    }

    public void setY(int y) {
        this.mY = y;
    }


    /**
     * Implements the <tt>SimpleShape.draw()</tt> method for painting
     * the shape.
     *
     * @param g2 The graphics object used for painting.
     */
    public void draw(JPanel mPanel) {
        this.draw(mPanel, false);
    }

    public void draw(JPanel mPanel, boolean estDansGroupe) {
        this.label = new JLabel(icon);
        mPanel.add(this.label);
        Dimension size = label.getPreferredSize();
        this.label.setBounds(getX(), getY(), size.width, size.height);
        if (estDansGroupe){
            this.label.setBorder( BorderFactory.createLineBorder(Color.RED));
        }
        this.label.setLocation(getX(), getY());
    }

    public void select(Color couleur){
        this.label.setBorder( BorderFactory.createLineBorder(couleur,4));
    }

    public JLabel getLabel(){
        return this.label;
    }

    private void updateCoord(int newX,int newY){
        this.label.setLocation(newX,newY);
        this.setX(newX);
        this.setY(newY);
    }

    public void move(int x,int y) {
        int newX = this.getX() + x;
        int newY = this.getY() + y;
        updateCoord(newX,newY);
    }

    public void deplace(int deltaX, int deltaY){
        int x = this.mX - 25 + deltaX;
        int y = this.mY - 25 + deltaY;
        updateCoord(x,y);
    }
}
