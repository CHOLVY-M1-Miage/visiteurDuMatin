package edu.uga.miage.m1.polygons.gui.persistence;

import edu.uga.miage.m1.polygons.gui.shapes.*;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
    this.representation = String.format("{\"type\": \"%s\", \"x\": %d,\"y\": %d}", "circle", circle.getX(), circle.getY());

    }

    @Override
    public void visit(Square square) {
        this.representation = String.format("{\"type\": \"%s\", \"x\": %d,\"y\": %d}", "square", square.getX(), square.getY());
    }

    @Override
    public void visit(Triangle triangle) {
        this.representation = String.format("{\"type\": \"%s\", \"x\": %d,\"y\": %d}", "triangle", triangle.getX(), triangle.getY());
    }

    @Override
    public void visit(GroupeShape shapes) {
        JSonVisitor visitor = new JSonVisitor();
        List<SimpleShape> listeShapes = shapes.getShapes();
        SimpleShape lastShape = listeShapes.get(listeShapes.size() - 1);
        for (SimpleShape s: shapes.getShapes()){
            s.accept(visitor);
            this.representation += visitor.getRepresentation();
            if (!s.equals(lastShape)){
                this.representation += ",\n";
            }
        }
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
        return representation;
    }

    @Override
    public void head(FileWriter fileWriter) {
        write(fileWriter,"{\n\"shapes\":[\n");
    }

    @Override
    public void foot(FileWriter fileWriter) {
        write(fileWriter,"]\n}");
    }

    @Override
    public boolean estHead(BufferedReader lines){
        try {
            String head = lines.readLine() + lines.readLine();
            System.out.println(head);
            return head.equals("{\"shapes\":[");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean estFoot(BufferedReader lines) {
        try {
            String foot = lines.readLine() + lines.readLine();
            System.out.println(foot);
            return foot.equals("}null");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
