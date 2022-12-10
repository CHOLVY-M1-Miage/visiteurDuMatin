package edu.uga.miage.m1.polygons.gui.shapes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TriangleTest {

	@Test
	void test_getters() {
		Triangle t = new Triangle(0, 0);
		assertEquals(25, t.getX());
		assertEquals(25, t.getY());
	}

}
