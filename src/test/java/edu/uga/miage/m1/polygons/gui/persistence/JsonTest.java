package edu.uga.miage.m1.polygons.gui.persistence;

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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonTest {
    @Test
    void test_lecture_file_groupLess() throws IOException, ParseException {
        List<SimpleShape> shapes = new ArrayList<>();

        shapes.add(new Square(52,33));
        shapes.add(new Square(103,98));
        shapes.add(new Square(164,153));
        shapes.add(new Triangle(238,212));
        shapes.add(new Triangle(310,240));
        shapes.add(new Circle(338,73));
        String rep = "{\"shapes\":[{\"x\":52,\"y\":33,\"type\":\"square\"},{\"x\":103,\"y\":98,\"type\":\"square\"},{\"x\":164,\"y\":153,\"type\":\"square\"},{\"x\":238,\"y\":212,\"type\":\"triangle\"},{\"x\":310,\"y\":249,\"type\":\"triangle\"},{\"x\":319,\"y\":73,\"type\":\"circle\"}]}";


        exportJSON(shapes,"testeXmlJson/fileExport.json");
        Object ob = new JSONParser().parse(new FileReader("testeXmlJson/fileExport.json"));


        System.out.println(ob.toString());

        Assertions.assertEquals(rep, ob.toString());


    }
}
