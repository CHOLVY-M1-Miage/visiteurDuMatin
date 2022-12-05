package edu.uga.miage.m1.polygons.gui.commande;

import edu.uga.miage.m1.polygons.gui.RemoteControl;
import edu.uga.miage.m1.polygons.gui.commands.add;
import edu.uga.miage.m1.polygons.gui.commands.draw;
import edu.uga.miage.m1.polygons.gui.shapes.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class addTest {
    @Test
    void test_add_newGroupe() {
        JPanel m_panel = new JPanel();
        Graphics2D graphics2 = (Graphics2D) m_panel.getGraphics();
        java.util.List<SimpleShape> shapes = new ArrayList<>();
        List<SimpleShape> shapes_control = new ArrayList<>();
        RemoteControl control = new RemoteControl(shapes);
        GroupeShape g1 = new GroupeShape();
        GroupeShape g1_control = new GroupeShape();

        Circle c = new Circle(0, 0);
        g1_control.add(c);
        shapes_control.add(g1);

        control.addCommand(new add(graphics2,c,g1,shapes));
        control.play();

        System.out.println("LS:" + shapes);
        System.out.println("LSC:" + shapes_control);
        System.out.println("G:" + g1.getShapes());
        System.out.println("GC:" + g1_control.getShapes());

        assertTrue(shapes_control.containsAll(shapes)
                && shapes_control.size() == shapes.size()
                && g1_control.getShapes().containsAll(g1.getShapes())
                && g1_control.getShapes().size() == g1.getShapes().size()
        );
    }

    @Test
    void test_add_existedGroupe() {
        JPanel m_panel = new JPanel();
        Graphics2D graphics2 = (Graphics2D) m_panel.getGraphics();
        java.util.List<SimpleShape> shapes = new ArrayList<>();
        List<SimpleShape> shapes_control = new ArrayList<>();
        RemoteControl control = new RemoteControl(shapes);
        GroupeShape g1 = new GroupeShape();
        GroupeShape g1_control = new GroupeShape();

        Circle c = new Circle(0, 0);
        Circle c2 = new Circle(10, 10);
        g1.add(c);
        g1_control.add(c);
        g1_control.add(c2);
        shapes.add(g1);
        shapes_control.add(g1);

        control.addCommand(new add(graphics2,c2,g1,shapes));
        control.play();

        System.out.println("LS:" + shapes);
        System.out.println("LSC:" + shapes_control);
        System.out.println("G:" + g1.getShapes());
        System.out.println("GC:" + g1_control.getShapes());

        assertTrue(shapes_control.containsAll(shapes)
                && shapes_control.size() == shapes.size()
                && g1_control.getShapes().containsAll(g1.getShapes())
                && g1_control.getShapes().size() == g1.getShapes().size()
        );
    }
}
