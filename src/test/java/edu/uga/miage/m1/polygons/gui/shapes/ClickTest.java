package edu.uga.miage.m1.polygons.gui.shapes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static edu.uga.miage.m1.polygons.gui.shapeInteraction.whoWasClicked;
import static org.junit.Assert.assertTrue;

public class ClickTest {
    private List<SimpleShape> shapes;

    void init(){
        this.shapes = new ArrayList<>();
        this.shapes.add(new Square(0,0));
        this.shapes.add(new Square(50,50));
        this.shapes.add(new Circle(100,0));
        this.shapes.add(new Circle(100,50));
        this.shapes.add(new Triangle(150,0));
        this.shapes.add(new Triangle(150,50));
        this.shapes.add(new Binome(200,0));
        this.shapes.add(new Binome(200,50));
        GroupeShape groupe = new GroupeShape();
        groupe.add(new Circle(250,0));
        groupe.add(new Square(250,50));
        this.shapes.add(groupe);
    }

    @Test
    void test_click_square(){
        init();
        //System.out.println("->" + this.shapes);
        SimpleShape clickedShape = whoWasClicked(this.shapes,10,10);
        assertTrue(clickedShape.getX() == -25 && clickedShape.getY() == -25);
    }

    @Test
    void test_click_circle(){
        init();
        //System.out.println("->" + this.shapes);
        SimpleShape clickedShape = whoWasClicked(this.shapes,103,53);
        assertTrue(clickedShape.getX() == 75 && clickedShape.getY() == 25);
    }

    @Test
    void test_click_triangle(){
        init();
        //System.out.println("->" + this.shapes);
        SimpleShape clickedShape = whoWasClicked(this.shapes,148,-8);
        assertTrue(clickedShape.getX() == 150 && clickedShape.getY() == 0);
    }

    @Test
    void test_click_binome(){
        init();
        //System.out.println("->" + this.shapes);
        SimpleShape clickedShape = whoWasClicked(this.shapes,204,45);
        assertTrue(clickedShape.getX() == 175 && clickedShape.getY() == 25);
    }

    @Test
    void test_click_empty(){
        init();
        //System.out.println("->" + this.shapes);
        SimpleShape clickedShape = whoWasClicked(this.shapes,180,80);
        //System.out.println("->:" + clickedShape);
        assertTrue(clickedShape == null);
    }
}
