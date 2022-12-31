package edu.uga.miage.m1.polygons.gui.commands;

import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Add implements Command {
    private SimpleShape figure ;
    private GroupeShape groupeShape;
    private List<SimpleShape> listeShapes;
    private JPanel m_panel;
    private RemoteControl remote;

    public Add(SimpleShape figure , GroupeShape groupeShape,RemoteControl remote) {
        this.figure = figure;
        this.groupeShape = groupeShape;
        this.listeShapes = remote.getListeShapes();
        this.m_panel = remote.getM_panel();
        this.remote = remote;
    }

    @Override
    public void execute(){
        listeShapes.remove(this.figure);
        if (!this.listeShapes.contains(this.groupeShape)){
           System.out.printf("{new groupe} ");
        }
        //Attribution de la couleur
        this.groupeShape.add(this.figure);
        Command groupeDraw = new Draw(this.groupeShape,this.remote);
        groupeDraw.execute();
    }

}
