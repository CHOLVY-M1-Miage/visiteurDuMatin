package edu.uga.miage.m1.polygons.gui.shapes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SquareTest {

	@Test
	void test_getters() {
		Square s = new Square(0, 0);
		assertEquals(-25, s.getX());
		assertEquals(-25, s.getY());
	}

}
