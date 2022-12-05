package edu.uga.miage.m1.polygons.gui.commands;

import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import java.awt.*;
import java.util.List;

public class remove implements Command {

    private Graphics2D g2;
    private SimpleShape figure ;
    private GroupeShape groupeShape;
    private List<SimpleShape> listeShapes;

    public remove(Graphics2D g2,SimpleShape figure ,GroupeShape groupeShape, List<SimpleShape> listeShapes) {
        this.g2 = g2;
        this.figure = figure;
        this.groupeShape = groupeShape;
        this.listeShapes = listeShapes;
    }

    @Override
    public void execute(){
        this.groupeShape.remove(this.figure);
        System.out.println("->" +this.groupeShape.getShapes().isEmpty());
        if (this.groupeShape.getShapes().isEmpty()){
            this.listeShapes.remove(this.groupeShape);
        }
        listeShapes.add(this.figure);
    }

}
