package edu.uga.miage.m1.polygons.gui.commands;

import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import java.awt.*;
import java.util.List;

public class Reseat implements Command {

    private Graphics2D g2;
    private SimpleShape newFigure ;

    private List<SimpleShape> listeShapes;

    public Reseat(List<SimpleShape> listeShapes) {
        this.listeShapes = listeShapes;
    }

    @Override
    public void execute() {
        this.listeShapes.clear();
    }
}
