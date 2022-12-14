package edu.uga.miage.m1.polygons.gui.commands;

import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Remove implements Command {

    private SimpleShape figure ;
    private GroupeShape groupeShape;
    private List<SimpleShape> listeShapes;
    private JPanel m_panel;
    private RemoteControl remote;

    public Remove(SimpleShape figure , GroupeShape groupeShape,RemoteControl remote) {
        this.figure = figure;
        this.groupeShape = groupeShape;
        this.listeShapes = remote.getListeShapes();
        this.m_panel = remote.getM_panel();
        this.remote = remote;
    }

    @Override
    public void execute(){
        this.groupeShape.remove(this.figure);
        //System.out.println("->" +this.groupeShape.getShapes().isEmpty());
        if (this.groupeShape.getShapes().isEmpty()){
            this.listeShapes.remove(this.groupeShape);
        }
        listeShapes.add(this.figure);
        this.figure.getLabel().setBorder( BorderFactory.createLineBorder(Color.black,0));
    }

}
