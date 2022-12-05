package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinomeTest {
    @Mock
    Visitor v;

    @Test
    void test_getters() {
        Binome b = new Binome(0, 0);
        assertEquals(0, b.getX());
        assertEquals(0, b.getY());
    }
}
