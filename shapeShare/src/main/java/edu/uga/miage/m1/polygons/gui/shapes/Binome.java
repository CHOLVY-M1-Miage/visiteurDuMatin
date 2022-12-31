package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.visitor.Visitor;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Binome extends Shape implements SimpleShape {
    public Binome(int x, int y) {
        super(x,y,"../images/icones/binome.png");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isSelect(int coordX, int coordY) {
        int x = this.getX() + 25;
        int y = this.getY() + 25;
        double dist = Math.sqrt(Math.pow((coordX - x),2) + Math.pow((coordY - y),2));
        return 25.00 > dist;
    }

    @Override
    public SimpleShape shapeSelect(int x, int y) {
        return this;
    }
}
