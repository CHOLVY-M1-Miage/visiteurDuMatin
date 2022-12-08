package edu.uga.miage.m1.polygons.gui.file;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

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
            default:
                //System.out.println("No shape named " + m_selected);
                shape = null;

        }
        return shape;
    }
}
