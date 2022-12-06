package edu.uga.miage.m1.polygons.gui.json;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static edu.uga.miage.m1.polygons.gui.persistence.Json.exportJSON;
import static edu.uga.miage.m1.polygons.gui.persistence.Json.importJson;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonTest {
    @Test
    void test_ecriture_file_groupLess() throws IOException, ParseException {
        List<SimpleShape> shapes = new ArrayList<>();

        shapes.add(new Square(52,33));
        shapes.add(new Square(103,98));
        shapes.add(new Square(164,153));
        shapes.add(new Triangle(238,212));
        shapes.add(new Triangle(310,249));
        shapes.add(new Circle(319,73));
        String rep = "{\"shapes\":[{\"x\":52,\"y\":33,\"type\":\"square\"},{\"x\":103,\"y\":98,\"type\":\"square\"},{\"x\":164,\"y\":153,\"type\":\"square\"},{\"x\":238,\"y\":212,\"type\":\"triangle\"},{\"x\":310,\"y\":249,\"type\":\"triangle\"},{\"x\":319,\"y\":73,\"type\":\"circle\"}]}";

        String path = "src/test/java/edu/uga/miage/m1/polygons/gui/json/file/exportTest.json";
        exportJSON(path,shapes);
        Object ob = new JSONParser().parse(new FileReader(path));

        System.out.println(ob.toString());

        Assertions.assertEquals(rep, ob.toString());
    }

    @Test
    void test_lecture_file_groupLess() throws IOException, ParseException {
        String path = "src/test/java/edu/uga/miage/m1/polygons/gui/json/file/exportTest.json";
        List<SimpleShape> shapes;
        List<SimpleShape> shapes_Controle = new ArrayList<>();
        shapes_Controle.add(new Square(52,33));
        shapes_Controle.add(new Square(103,98));
        shapes_Controle.add(new Square(164,153));
        shapes_Controle.add(new Triangle(238,212));
        shapes_Controle.add(new Triangle(310,249));
        shapes_Controle.add(new Circle(319,73));
        int taille = shapes_Controle.size();

        shapes = importJson(path);

        assertTrue(shapes.size() == taille);
        for (int i = 0; i < taille;i++ ){
            assertEquals(shapes_Controle.get(i).getX(),shapes.get(i).getX());
            assertEquals(shapes_Controle.get(i).getY(),shapes.get(i).getY());
        }
    }
}
