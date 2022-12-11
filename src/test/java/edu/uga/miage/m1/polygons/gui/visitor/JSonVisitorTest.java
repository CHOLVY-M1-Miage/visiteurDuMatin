package edu.uga.miage.m1.polygons.gui.visitor;

import static edu.uga.miage.m1.polygons.gui.file.JsonFile.jsonFromString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javax.json.JsonObject;

import edu.uga.miage.m1.polygons.gui.shapes.*;
import org.junit.jupiter.api.Test;

/**
 *  @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 *
 */
class JSonVisitorTest {

	@Test
	void test_circle_visitor() {
		var c = new Circle(0, 0);
		String representation = 
				String.format("{\"type\": \"%s\", \"x\": %d,\"y\": %d}", "circle", c.getX(), c.getY());
		String expectedRepresentation = jsonFromString(representation).toString();
		
		JSonVisitor visitor = new JSonVisitor();
		c.accept(visitor);
		representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the circle");
		}
		JsonObject jObject = jsonFromString(representation);
		assertEquals(expectedRepresentation, jObject.toString());
	}

	@Test
	void test_triangle_visitor() {
		var t = new Triangle(0, 0);
		String representation = 
				String.format("{\"type\": \"%s\",\"x\": %d,\"y\": %d}", "triangle", t.getX(), t.getY());
		String expectedRepresentation = jsonFromString(representation).toString();
		
		JSonVisitor visitor = new JSonVisitor();
		t.accept(visitor);
		representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the triangle");
		}
		JsonObject jObject = jsonFromString(representation);
		assertEquals(expectedRepresentation, jObject.toString());
	}

	@Test
	void test_square_visitor() {
		var s = new Square(0, 0);
		String representation = 
				String.format("{\"type\": \"%s\", \"x\": %d,\"y\": %d}", "square", s.getX(), s.getY());
		String expectedRepresentation = jsonFromString(representation).toString();
		
		JSonVisitor visitor = new JSonVisitor();
		s.accept(visitor);
		representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the square");
		}
		JsonObject jObject = jsonFromString(representation);
		assertEquals(expectedRepresentation, jObject.toString());
	}

	@Test
	void test_binome_visitor() {
		var b = new Binome(0, 0);
		String representation =
				String.format("{\"type\": \"%s\", \"x\": %d,\"y\": %d}", "binome", b.getX(), b.getY());
		String expectedRepresentation = jsonFromString(representation).toString();

		JSonVisitor visitor = new JSonVisitor();
		b.accept(visitor);
		representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the binome");
		}
		JsonObject jObject = jsonFromString(representation);
		assertEquals(expectedRepresentation, jObject.toString());
	}

	@Test
	void test_groupe_empty_visitor() {
		GroupeShape groupeShapes = new GroupeShape();
		String representation =
				String.format("{\"groupe\": []}");
		String expectedRepresentation = jsonFromString(representation).toString();

		JSonVisitor visitor = new JSonVisitor();
		groupeShapes.accept(visitor);
		representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the binome");
		}
		JsonObject jObject = jsonFromString(representation);
		assertEquals(expectedRepresentation, jObject.toString());
	}

	@Test
	void test_groupe_oneElement_visitor() {
		GroupeShape groupeShapes = new GroupeShape();
		groupeShapes.add(new Circle(0, 0));
		String representation =
				String.format("{\"groupe\": [{\"type\": \"%s\", \"x\": 0,\"y\": 0}]}", "circle");
		String expectedRepresentation = jsonFromString(representation).toString();

		JSonVisitor visitor = new JSonVisitor();
		groupeShapes.accept(visitor);
		representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the binome");
		}
		JsonObject jObject = jsonFromString(representation);
		assertEquals(expectedRepresentation, jObject.toString());
	}

	@Test
	void test_groupe_manyElement_visitor() {
		GroupeShape groupeShapes = new GroupeShape();
		groupeShapes.add(new Circle(0, 0));
		groupeShapes.add(new Triangle(10, 10));
		groupeShapes.add(new Square(20, 20));
		String representation =
				String.format("{\"groupe\": [{\"type\": \"circle\", \"x\": 0,\"y\": 0},{\"type\": \"triangle\", \"x\": 10,\"y\": 10},{\"type\": \"square\", \"x\": 20,\"y\": 20}]}");
		String expectedRepresentation = jsonFromString(representation).toString();

		JSonVisitor visitor = new JSonVisitor();
		groupeShapes.accept(visitor);
		representation = visitor.getRepresentation();
		if (representation == null) {
			fail("The visitor sequence must be implemented for the binome");
		}
		JsonObject jObject = jsonFromString(representation);
		assertEquals(expectedRepresentation, jObject.toString());
	}
}
