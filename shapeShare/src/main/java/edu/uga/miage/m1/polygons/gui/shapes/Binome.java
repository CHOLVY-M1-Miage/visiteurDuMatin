package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.visitor.Visitor;

public class Binome extends Shape implements SimpleShape {
    public Binome(int x, int y) {
        super(x,y,"../images/icones/binome.png");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public SimpleShape shapeSelect(int x, int y) {
        return this;
    }
}
