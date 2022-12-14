package edu.uga.miage.m1.polygons.gui.visitor;

import edu.uga.miage.m1.polygons.gui.shapes.*;

import java.io.FileWriter;

/**
 * You must define a method for each type of Visitable.
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 *
 */
public interface Visitor {
	public void visit(Circle circle);
	public void visit(Square square);
	public void visit(Triangle triangle);
    public void visit(GroupeShape shapes);
	public void visit(Binome binome);
	public String getRepresentation();
	public void head(FileWriter fileWriter);
	public void foot(FileWriter fileWriter);
}