package edu.uga.miage.m1.polygons.gui.commande;

import edu.uga.miage.m1.polygons.gui.RemoteControl;
import edu.uga.miage.m1.polygons.gui.commands.Clear;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;

public class clearTest {
    @Test
    void test_clear_command(){
        List<SimpleShape> shapes= new ArrayList<>();
        RemoteControl control = new RemoteControl(shapes);
        Circle c = new Circle(0, 0);
        Circle c2 = new Circle(10, 10);
        shapes.add(c);
        shapes.add(c2);

        control.addCommand(new Clear(shapes));
        control.play();

        assertTrue(shapes.isEmpty());

    }
}
