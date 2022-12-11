package edu.uga.miage.m1.polygons.gui.graphique;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import java.util.List;

public class shapeInteraction {
    
    public static SimpleShape whoWasClicked(List<SimpleShape> shapes, int mouseX, int mouseY){
        if (shapes.isEmpty()) return null;
        int i = 0;
        while ((i < shapes.size()) && (!shapes.get(i).isSelect(mouseX,mouseY))){
            i++;
        }
        return (i == shapes.size())? null : shapes.get(i);

    }
}
