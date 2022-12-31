package edu.uga.miage.m1.polygons.gui.commands;

import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import java.awt.*;
import java.util.List;

public class Move implements Command {

    private SimpleShape newFigure ;
    private int newX;
    private int newY;

    public Move(SimpleShape figure , int newX, int newY) {
        this.newFigure = figure;
        this.newX = newX;
        this.newY = newY;
    }

    @Override
    public void execute(){
        this.newFigure.move(newX,newY);
    }

}
