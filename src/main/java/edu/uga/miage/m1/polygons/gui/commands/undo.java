package edu.uga.miage.m1.polygons.gui.commands;

import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import java.util.List;

public class undo implements Command {

    private GroupeShape groupeShape;
    private List<SimpleShape> listeShapes;

    public undo(GroupeShape groupeShape, List<SimpleShape> listeShapes) {
        this.groupeShape = groupeShape;
        this.listeShapes = listeShapes;
    }

    @Override
    public void execute(){
    }

}
