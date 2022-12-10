package edu.uga.miage.m1.polygons.gui.commands;

import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import java.awt.*;
import java.util.List;

public class Add implements Command {
    private SimpleShape figure ;
    private GroupeShape groupeShape;
    private List<SimpleShape> listeShapes;

    public Add(SimpleShape figure , GroupeShape groupeShape, List<SimpleShape> listeShapes) {
        this.figure = figure;
        this.groupeShape = groupeShape;
        this.listeShapes = listeShapes;
    }

    @Override
    public void execute(){
        listeShapes.remove(this.figure);
        if (!this.listeShapes.contains(this.groupeShape)){
           this.listeShapes.add(this.groupeShape);
        }
        this.groupeShape.add(this.figure);
    }

}
