package edu.uga.miage.m1.polygons.gui.factory;

import edu.uga.miage.m1.polygons.gui.graphique.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.shapes.*;

public class Factory {
    public static SimpleShape createShape(String type, int x, int y) {
        SimpleShape figure;
        switch (type.toUpperCase()) {
            case "CIRCLE":
                figure = new Circle(x, y);
                break;
            case "TRIANGLE":
                figure = new Triangle(x, y);
                break;
            case "SQUARE":
                figure = new Square(x, y);
                break;
            case "BINOME":
                figure = new Binome(x, y);
                break;
            default:
                System.out.println("No shape named " + type);
                figure = null;

        }
        System.out.println("New figure: " + type + "(" + x + "," + y + ")");
        return figure;
    }

    public static SimpleShape createShape(JDrawingFrame.Shapes type, int x, int y) {
        SimpleShape figure;
        switch (type) {
            case CIRCLE:
                figure = new Circle(x, y);
                break;
            case TRIANGLE:
                figure = new Triangle(x, y);
                break;
            case SQUARE:
                figure = new Square(x, y);
                break;
            case BINOME:
                figure = new Binome(x, y);
                break;
            default:
                System.out.println("No shape named " + type);
                figure = null;

        }
        System.out.println("New figure: " + type + "(" + x + "," + y + ")");
        return figure;
    }
}
