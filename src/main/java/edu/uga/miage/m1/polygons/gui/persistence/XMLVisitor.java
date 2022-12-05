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
public class XMLVisitor implements Visitor {

    private String representation = "";

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

    @Override
    public void visit(Binome binome){
        this.representation = String.format("<shape><type>%s</type><x>%d</x><y>%d</y></shape>", "binome", binome.getX(), binome.getY());
    }

    @Override
    public void visit(GroupeShape shapes) {
        XMLVisitor visitor =new XMLVisitor();
        List<SimpleShape> listeShapes = shapes.getShapes();
        SimpleShape lastShape = listeShapes.get(listeShapes.size() - 1);
        for (SimpleShape s: shapes.getShapes()){
            s.accept(visitor);
            this.representation += visitor.getRepresentation();
            if (!s.equals(lastShape)){
                this.representation += "\n";
            }
        }
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

    @Override
    public boolean estHead(BufferedReader lines){
        try {
            String head = lines.readLine() + lines.readLine() + lines.readLine();
            System.out.println(head);
            return head.equals("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                    "<root>" +
                    "<shapes>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean estFoot(BufferedReader lines) {
        try {
            String foot = lines.readLine() + lines.readLine();
            System.out.println(foot);
            return foot.equals("</root>null");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
