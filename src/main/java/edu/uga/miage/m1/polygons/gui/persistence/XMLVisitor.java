package edu.uga.miage.m1.polygons.gui.persistence;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class XMLVisitor implements Visitor {

    private String representation = null;

    public XMLVisitor() {
    }

    @Override
    public void visit(Circle circle) {
        this.representation = String.format("<shape><type>%s</type><x>%d</x><y>%d</y></shape>", "circle", circle.getX(), circle.getY());
    }

    @Override
    public void visit(Square square) {
        this.representation = String.format("<shape><type>%s</type><x>%d</x><y>%d</y></shape>", "square", square.getX(), square.getY());
    }

    @Override
    public void visit(Triangle triangle) {
        this.representation = String.format("<shape><type>%s</type><x>%d</x><y>%d</y></shape>", "triangle", triangle.getX(), triangle.getY());
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
}
