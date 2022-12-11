package edu.uga.miage.m1.polygons.gui.visitor;

import edu.uga.miage.m1.polygons.gui.shapes.*;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

import static edu.uga.miage.m1.polygons.gui.file.FileUtils.*;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JSonVisitor implements Visitor {

    private String representation = "";

    public JSonVisitor() {
    }

    @Override
    public void visit(Circle circle) {
        this.representation = String.format("{\"type\": \"%s\", \"x\": %d,\"y\": %d}", "circle", circle.getX()+25, circle.getY()+25);
    }
    @Override
    public void visit(Square square) {
        this.representation = String.format("{\"type\": \"%s\", \"x\": %d,\"y\": %d}", "square", square.getX()+25, square.getY()+25);
    }
    @Override
    public void visit(Triangle triangle) {
        this.representation = String.format("{\"type\": \"%s\", \"x\": %d,\"y\": %d}", "triangle", triangle.getX(), triangle.getY());
    }
    @Override
    public void visit(Binome binome) {
        this.representation = String.format("{\"type\": \"%s\", \"x\": %d,\"y\": %d}", "binome", binome.getX()+25, binome.getY()+25);
    }
    @Override
    public void visit(GroupeShape shapes) {
        JSonVisitor visitor = new JSonVisitor();
        this.representation = "{\"groupe\": [\n";
        for (SimpleShape s: shapes.getShapes()){
            s.accept(visitor);
            this.representation += visitor.getRepresentation();
            if (!s.equals(shapes.getLastShapes())){
                this.representation += ",\n";
            }
        }
        this.representation += "\n]}";
    }

    /**
     * @return the representation in JSon example for a Circle
     *
     *         <pre>
     * {@code
     *  {
     *     "shape": {
     *     	  "type": "circle",
     *        "x": -25,
     *        "y": -25
     *     }
     *  }
     * }
     *         </pre>
     */

    public String getRepresentation() {
        return this.representation;
    }


    @Override
    public void head(FileWriter fileWriter) {
        write(fileWriter,"{\n\"shapes\":[\n");
    }
    @Override
    public void foot(FileWriter fileWriter) {
        write(fileWriter,"]\n}");
    }

}
