package edu.uga.miage.m1.polygons.gui.commands;

import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Draw implements Command {

    private JPanel m_panel;
    private SimpleShape newFigure ;
    private GroupeShape groupeShape;
    private List<SimpleShape> listeShapes;

    public Draw(JPanel m_panel, SimpleShape newFigure, List<SimpleShape> listeShapes) {
        this.m_panel = m_panel;
        this.newFigure = newFigure;
        this.listeShapes = listeShapes;
    }

    @Override
    public void execute(){
        if (this.m_panel != null) this.newFigure.draw(this.m_panel);
        this.listeShapes.add(this.newFigure);
    }

}
