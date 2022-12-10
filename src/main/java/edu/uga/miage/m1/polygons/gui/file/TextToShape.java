package edu.uga.miage.m1.polygons.gui.file;

import edu.uga.miage.m1.polygons.gui.shapes.*;

public class TextToShape {
    public static SimpleShape createSimpleShape(String type, int x, int y){
        SimpleShape shape;
        //System.out.println("New obj: " + type + "," + x + "," + y);
        switch (type.toUpperCase()) {
            case "CIRCLE":
                shape = new Circle(x, y);
                break;
            case "TRIANGLE":
                shape = new Triangle(x, y);
                break;
            case "SQUARE":
                shape = new Square(x, y);
                break;
            case "BINOME":
                shape = new Binome(x, y);
                break;
            default:
                //System.out.println("No shape named " + m_selected);
                shape = null;

        }
        return shape;
    }
}
