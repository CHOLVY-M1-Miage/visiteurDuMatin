package edu.uga.miage.m1.polygons.gui.commands;

import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import java.awt.*;
import java.util.List;

public class Remove implements Command {

    private SimpleShape figure ;
    private GroupeShape groupeShape;
    private List<SimpleShape> listeShapes;

    public Remove(SimpleShape figure , GroupeShape groupeShape, List<SimpleShape> listeShapes) {
        this.figure = figure;
        this.groupeShape = groupeShape;
        this.listeShapes = listeShapes;
    }

    @Override
    public void execute(){
        System.out.println(this.listeShapes);
        this.groupeShape.remove(this.figure);
        System.out.println("->" +this.groupeShape.getShapes().isEmpty());
        if (this.groupeShape.getShapes().isEmpty()){
            this.listeShapes.remove(this.groupeShape);
        }
        listeShapes.add(this.figure);
    }

}
