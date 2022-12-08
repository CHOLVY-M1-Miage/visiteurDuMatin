package edu.uga.miage.m1.polygons.gui.jsonFile;

import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import edu.uga.miage.m1.polygons.gui.shapes.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static edu.uga.miage.m1.polygons.gui.file.JsonFile.importJson;
import static edu.uga.miage.m1.polygons.gui.file.Export.export;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonFileTest {
    @Test
    void test_export_shapes_groupLess() throws IOException, ParseException {
        List<SimpleShape> shapes = new ArrayList<>();

        shapes.add(new Square(52,33));
        shapes.add(new Square(103,98));
        shapes.add(new Square(164,153));
        shapes.add(new Triangle(238,212));
        shapes.add(new Triangle(310,249));
        shapes.add(new Circle(319,73));
        String rep = "{\"shapes\":[{\"x\":52,\"y\":33,\"type\":\"square\"},{\"x\":103,\"y\":98,\"type\":\"square\"},{\"x\":164,\"y\":153,\"type\":\"square\"},{\"x\":238,\"y\":212,\"type\":\"triangle\"},{\"x\":310,\"y\":249,\"type\":\"triangle\"},{\"x\":319,\"y\":73,\"type\":\"circle\"}]}";

        String path = "src/test/java/edu/uga/miage/m1/polygons/gui/jsonFile/file/exportTest.json";
        //exportJSON(path,shapes);
        Visitor visitorJson = new JSonVisitor();
        export(visitorJson,"json",path,shapes);
        Object ob = new JSONParser().parse(new FileReader(path));

        System.out.println(ob.toString());

        Assertions.assertEquals(rep, ob.toString());
    }

    @Test
    void test_export_shapes_group() throws IOException, ParseException {
        List<SimpleShape> shapes = new ArrayList<>();
        GroupeShape groupe = new GroupeShape();

        shapes.add(new Square(52,33));
        groupe.add(new Square(103,98));
        groupe.add(new Square(164,153));
        groupe.add(new Triangle(238,212));
        groupe.add(new Triangle(310,249));
        shapes.add(groupe);
        shapes.add(new Circle(319,73));
        String rep = "{\"shapes\":[{\"x\":52,\"y\":33,\"type\":\"square\"},{\"groupe\":[{\"x\":103,\"y\":98,\"type\":\"square\"},{\"x\":164,\"y\":153,\"type\":\"square\"},{\"x\":238,\"y\":212,\"type\":\"triangle\"},{\"x\":310,\"y\":249,\"type\":\"triangle\"}]},{\"x\":319,\"y\":73,\"type\":\"circle\"}]}";

        String path = "src/test/java/edu/uga/miage/m1/polygons/gui/jsonFile/file/exportGroupeTest.json";
        //exportJSON(path,shapes);
        Visitor visitorJson = new JSonVisitor();
        export(visitorJson,"json",path,shapes);
        Object ob = new JSONParser().parse(new FileReader(path));

        System.out.println(ob.toString());

        Assertions.assertEquals(rep, ob.toString());
    }

    @Test
    void test_import_file_groupLess() throws IOException, ParseException {
        String path = "src/test/java/edu/uga/miage/m1/polygons/gui/jsonFile/file/importTest.json";
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

    @Test
    void test_import_file_group() throws IOException, ParseException {
        String path = "src/test/java/edu/uga/miage/m1/polygons/gui/jsonFile/file/importGroupeTest.json";
        List<SimpleShape> shapes;
        List<SimpleShape> shapes_Controle = new ArrayList<>();
        GroupeShape groupe_Controle = new GroupeShape();
        shapes_Controle.add(new Square(52,33));
        shapes_Controle.add(groupe_Controle);
        groupe_Controle.add(new Square(103,98));
        groupe_Controle.add(new Square(164,153));
        groupe_Controle.add(new Triangle(238,212));
        groupe_Controle.add(new Triangle(310,249));
        shapes_Controle.add(new Circle(319,73));
        int taille = shapes_Controle.size();

        shapes = importJson(path);

        assertTrue(shapes.size() == taille);
        for (int i = 0; i < taille;i++){
            if (shapes.get(i).getClass().getName().equals("edu.uga.miage.m1.polygons.gui.shapes.GroupeShape")){
                GroupeShape groupeShape = (GroupeShape) shapes.get(i);
                for (int j = 0; j < groupeShape.getShapes().size();j++){
                    assertEquals(groupe_Controle.getShapes().get(j).getX(),groupeShape.getShapes().get(j).getX());
                    assertEquals(groupe_Controle.getShapes().get(j).getY(),groupeShape.getShapes().get(j).getY());
                }
            }
            else {
                assertEquals(shapes_Controle.get(i).getX(),shapes.get(i).getX());
                assertEquals(shapes_Controle.get(i).getY(),shapes.get(i).getY());
            }

        }
    }
}
