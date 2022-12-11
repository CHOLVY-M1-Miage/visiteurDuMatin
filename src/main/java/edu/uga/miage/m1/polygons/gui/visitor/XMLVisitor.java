package edu.uga.miage.m1.polygons.gui.visitor;

import edu.uga.miage.m1.polygons.gui.shapes.*;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

import static edu.uga.miage.m1.polygons.gui.file.FileUtils.*;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class XMLVisitor implements Visitor {

    private String representation = "";

    public XMLVisitor() {
    }

    @Override
    public void visit(Circle circle) {
        this.representation = String.format("<shape><type>%s</type><x>%d</x><y>%d</y></shape>", "circle", circle.getX()+25, circle.getY()+25);
    }
    @Override
    public void visit(Square square) {
        this.representation = String.format("<shape><type>%s</type><x>%d</x><y>%d</y></shape>", "square", square.getX()+25, square.getY()+25);
    }
    @Override
    public void visit(Triangle triangle) {
        this.representation = String.format("<shape><type>%s</type><x>%d</x><y>%d</y></shape>", "triangle", triangle.getX(), triangle.getY());
    }
    @Override
    public void visit(Binome binome){
        this.representation = String.format("<shape><type>%s</type><x>%d</x><y>%d</y></shape>", "binome", binome.getX()+25, binome.getY()+25);
    }
    @Override
    public void visit(GroupeShape shapes) {
        XMLVisitor visitor = new XMLVisitor();
        this.representation = "<groupe>\n";
        for (SimpleShape s : shapes.getShapes()) {
            s.accept(visitor);
            this.representation += visitor.getRepresentation();
            if (!s.equals(shapes.getLastShapes())) {
                this.representation += "\n";
            }
        }
        this.representation += "\n</groupe>";
    }

    /**
     * @return the representation in JSon example for a Triangle:
     *
     *         <pre>
     * {@code
     *  <shape>
     *    <type>triangle</type>
     *    <x>-25</x>
     *    <y>-25</y>
     *  </shape>
     * }
     * </pre>
     */

    public String getRepresentation() {
        return representation;
    }

    /*############################""*/
    @Override
    public void head(FileWriter fileWriter) {
        write(fileWriter,
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                        "<root>\n" +
                        "<shapes>\n"
        );
    }

    @Override
    public void foot(FileWriter fileWriter) {
        write(fileWriter,
                "</shapes>\n" +
                        "</root>");
    }
}
