package edu.uga.miage.m1.polygons.gui.commande;

import edu.uga.miage.m1.polygons.gui.commands.RemoteControl;
import edu.uga.miage.m1.polygons.gui.commands.Clear;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;

public class ClearTest {
    @Test
    void test_clear_command(){
        JPanel m_panel = new JPanel();
        GroupeShape groupe = new GroupeShape();
        List<SimpleShape> shapes = new ArrayList<>();
        RemoteControl control = new RemoteControl(shapes,groupe,m_panel);
        Circle c = new Circle(0, 0);
        Circle c2 = new Circle(10, 10);
        shapes.add(c);
        shapes.add(c2);

        //control.addCommand(new Clear(control));
        control.play();

        assertTrue(shapes.isEmpty());

    }
}
