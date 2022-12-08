package edu.uga.miage.m1.polygons.gui.commands;

import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import java.awt.*;
import java.util.List;

public class Move implements Command {

    private Graphics2D g2;
    private SimpleShape newFigure ;
    private GroupeShape groupeShape;
    private List<SimpleShape> listeShapes;

    public Move(Graphics2D g2, SimpleShape newFigure , GroupeShape groupeShape, List<SimpleShape> listeShapes) {
        this.g2 = g2;
        this.newFigure = newFigure;
        this.groupeShape = groupeShape;
        this.listeShapes = listeShapes;
    }

    @Override
    public void execute(){
    }

}
