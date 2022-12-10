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
    private int origalX;
    private int origalY;

    public Draw(JPanel m_panel, SimpleShape newFigure, List<SimpleShape> listeShapes) {
        this.m_panel = m_panel;
        this.newFigure = newFigure;
        this.listeShapes = listeShapes;
        this.origalX = newFigure.getX();
        this.origalY = newFigure.getY();
    }

    @Override
    public void execute(){
        this.newFigure.setX(this.origalX);
        this.newFigure.setY(this.origalY);
        if (this.m_panel != null) this.newFigure.draw(this.m_panel);
        this.listeShapes.add(this.newFigure);
    }

}
