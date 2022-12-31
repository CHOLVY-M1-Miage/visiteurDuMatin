package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.visitor.Visitor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinomeTest {
    @Mock
    Visitor v;

    @Test
    void test_getters() {
        Binome b = new Binome(0, 0);
        assertEquals(-25, b.getX());
        assertEquals(-25, b.getY());
    }
}
