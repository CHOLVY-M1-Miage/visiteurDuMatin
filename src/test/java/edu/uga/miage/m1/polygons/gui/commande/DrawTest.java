package edu.uga.miage.m1.polygons.gui.commande;

import edu.uga.miage.m1.polygons.gui.commands.RemoteControl;
import edu.uga.miage.m1.polygons.gui.commands.Draw;
import edu.uga.miage.m1.polygons.gui.shapes.*;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DrawTest {
    @Test
    void test_draw_circle(){
        JPanel m_panel = new JPanel();
        List<SimpleShape> shapes = new ArrayList<>();
        List<SimpleShape> shapes_control = new ArrayList<>();
        RemoteControl control = new RemoteControl(shapes);
        Circle c = new Circle(0, 0);
        shapes_control.add(c);

        control.addCommand(new Draw(m_panel,c,shapes));
        control.play();

        assertTrue(shapes_control.size() == shapes.size() && shapes_control.containsAll(shapes));
    }

    @Test
    void test_draw_square(){
        JPanel m_panel = new JPanel();
        List<SimpleShape> shapes = new ArrayList<>();
        List<SimpleShape> shapes_control = new ArrayList<>();
        RemoteControl control = new RemoteControl(shapes);
        Square s = new Square(0, 0);
        shapes_control.add(s);

        control.addCommand(new Draw(m_panel,s,shapes));
        control.play();

        assertTrue(shapes_control.size() == shapes.size() && shapes_control.containsAll(shapes));
    }

    @Test
    void test_draw_triangle(){
        JPanel m_panel = new JPanel();
        List<SimpleShape> shapes = new ArrayList<>();
        List<SimpleShape> shapes_control = new ArrayList<>();
        RemoteControl control = new RemoteControl(shapes);
        Triangle t = new Triangle(0, 0);
        shapes_control.add(t);

        control.addCommand(new Draw(m_panel,t,shapes));
        control.play();

        assertTrue(shapes_control.size() == shapes.size() && shapes_control.containsAll(shapes));
    }

    @Test
    void test_draw_plusieursFigures(){
        JPanel m_panel = new JPanel();
        List<SimpleShape> shapes = new ArrayList<>();
        List<SimpleShape> shapes_control = new ArrayList<>();
        RemoteControl control = new RemoteControl(shapes);
        Circle c = new Circle(0, 0);
        Square s = new Square(10, 10);
        Triangle t = new Triangle(20, 20);

        shapes_control.add(c);
        shapes_control.add(s);
        shapes_control.add(t);

        control.addCommand(new Draw(m_panel,c,shapes));
        control.addCommand(new Draw(m_panel,s,shapes));
        control.addCommand(new Draw(m_panel,t,shapes));
        control.play();

        assertTrue(shapes_control.size() == shapes.size() && shapes_control.containsAll(shapes));
    }

    @Test
    void test_draw_unGroupe(){
        JPanel m_panel = new JPanel();
        List<SimpleShape> shapes = new ArrayList<>();
        List<SimpleShape> shapes_control = new ArrayList<>();
        GroupeShape g1 = new GroupeShape();
        RemoteControl control = new RemoteControl(shapes);
        Circle c = new Circle(0, 0);
        Square s = new Square(10, 10);
        Triangle t = new Triangle(20, 20);

        g1.add(c);
        g1.add(s);
        g1.add(t);
        shapes_control.add(g1);

        control.addCommand(new Draw(m_panel,g1,shapes));
        control.play();

        assertTrue(shapes_control.size() == shapes.size() && shapes_control.containsAll(shapes));
    }

    @Test
    void test_draw_plusieursGroupes(){
        JPanel m_panel = new JPanel();
        List<SimpleShape> shapes = new ArrayList<>();
        List<SimpleShape> shapes_control = new ArrayList<>();
        GroupeShape g1 = new GroupeShape();
        GroupeShape g2 = new GroupeShape();

        RemoteControl control = new RemoteControl(shapes);
        Circle c = new Circle(0, 0);
        Square s = new Square(10, 10);
        Triangle t = new Triangle(20, 20);

        g1.add(c);
        g2.add(s);
        g2.add(t);
        shapes_control.add(g1);
        shapes_control.add(g2);

        control.addCommand(new Draw(m_panel,g1,shapes));
        control.addCommand(new Draw(m_panel,g2,shapes));
        control.play();

        assertTrue(shapes_control.size() == shapes.size() && shapes_control.containsAll(shapes));
    }

    @Test
    void test_draw_groupeAndAloneShape(){
        JPanel m_panel = new JPanel();
        List<SimpleShape> shapes = new ArrayList<>();
        List<SimpleShape> shapes_control = new ArrayList<>();
        GroupeShape g1 = new GroupeShape();
        GroupeShape g2 = new GroupeShape();

        RemoteControl control = new RemoteControl(shapes);
        Circle c = new Circle(0, 0);
        Square s = new Square(10, 10);
        Triangle t = new Triangle(20, 20);

        g1.add(c);
        g2.add(s);
        shapes_control.add(g1);
        shapes_control.add(t);
        shapes_control.add(g2);

        control.addCommand(new Draw(m_panel,g1,shapes));
        control.addCommand(new Draw(m_panel,t,shapes));
        control.addCommand(new Draw(m_panel,g2,shapes));
        control.play();

        assertTrue(shapes_control.size() == shapes.size() && shapes_control.containsAll(shapes));
    }
}
