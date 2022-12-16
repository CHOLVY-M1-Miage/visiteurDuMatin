package edu.uga.miage.m1.polygons.gui.visitor;

import static edu.uga.miage.m1.polygons.gui.file.XmlFile.convertStringToXml;
import static edu.uga.miage.m1.polygons.gui.file.XmlFile.convertXmlToString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.uga.miage.m1.polygons.gui.shapes.Binome;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

/**
 *  @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 *
 */
class XMLVisitorTest {

	@Test
	void test_circle_visitor() {
		var c = new Circle(0, 0);
		String representation = 
				String.format("<shape><type>%s</type><x>%d</x><y>%d</y></shape>", "circle", c.getX(), c.getY());
		String expectedRepresentation = convertXmlToString(convertStringToXml(representation));
		XmlVisitor visitor = new XmlVisitor();
		c.accept(visitor);
		representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the circle");
		}
		Document document = convertStringToXml(representation);
		assertEquals(expectedRepresentation, convertXmlToString(document));
	}	

	@Test
	void test_triangle_visitor() {
		var t = new Triangle(0, 0);
		String representation = 
				String.format("<shape><type>%s</type><x>%d</x><y>%d</y></shape>", "triangle", t.getX(), t.getY());
		String expectedRepresentation = convertXmlToString(convertStringToXml(representation));
		XmlVisitor visitor = new XmlVisitor();
		t.accept(visitor);
		representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the triangle");
		}
		Document document = convertStringToXml(representation);
		assertEquals(expectedRepresentation, convertXmlToString(document));
	}

	@Test
	void test_square_visitor() {
		var s = new Square(0, 0);
		String representation = 
				String.format("<shape><type>%s</type><x>%d</x><y>%d</y></shape>", "square", s.getX(), s.getY());
		String expectedRepresentation = convertXmlToString(convertStringToXml(representation));
		XmlVisitor visitor = new XmlVisitor();
		s.accept(visitor);
		representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the square");
		}
		Document document = convertStringToXml(representation);
		assertEquals(expectedRepresentation, convertXmlToString(document));
	}

	@Test
	void test_binome_visitor() {
		var b = new Binome(0, 0);
		String representation =
				String.format("<shape><type>%s</type><x>%d</x><y>%d</y></shape>", "binome", b.getX(), b.getY());
		String expectedRepresentation = convertXmlToString(convertStringToXml(representation));
		XmlVisitor visitor = new XmlVisitor();
		b.accept(visitor);
		representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the binome");
		}
		Document document = convertStringToXml(representation);
		assertEquals(expectedRepresentation, convertXmlToString(document));
	}
 }
