package edu.uga.miage.m1.polygons.gui.commands;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import javax.swing.*;
import java.util.List;

public class Draw implements Command {
    private RemoteControl remote;
    private JPanel mPanel;
    private SimpleShape newFigure ;
    private List<SimpleShape> listeShapes;
    private int origalX;
    private int origalY;

    public Draw(SimpleShape newFigure,RemoteControl remote) {
        this.newFigure = newFigure;
        this.remote = remote;
        this.mPanel = remote.getmPanel();
        this.listeShapes = remote.getListeShapes();
        this.origalX = newFigure.getX();
        this.origalY = newFigure.getY();
    }

    @Override
    public void execute(){
        this.newFigure.setX(this.origalX);
        this.newFigure.setY(this.origalY);
        this.newFigure.draw(this.mPanel);
        if (!this.listeShapes.contains(this.newFigure))this.listeShapes.add(this.newFigure);
    }

}
