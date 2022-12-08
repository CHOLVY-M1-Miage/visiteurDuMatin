package edu.uga.miage.m1.polygons.gui.xmlFile;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.*;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static edu.uga.miage.m1.polygons.gui.file.XmlFile.*;
import static edu.uga.miage.m1.polygons.gui.file.export.export;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlFileTest {
    @Test
    void test_export_shapes_groupLess() throws IOException, ParseException, ParserConfigurationException, SAXException {
        List<SimpleShape> shapes = new ArrayList<>();

        shapes.add(new Square(52,33));
        shapes.add(new Square(103,98));
        shapes.add(new Square(164,153));
        shapes.add(new Triangle(238,212));
        shapes.add(new Triangle(310,249));
        shapes.add(new Circle(319,73));
        String rep = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><root>\n<shapes>\n<shape><type>square</type><x>52</x><y>33</y></shape>\n<shape><type>square</type><x>103</x><y>98</y></shape>\n<shape><type>square</type><x>164</x><y>153</y></shape>\n<shape><type>triangle</type><x>238</x><y>212</y></shape>\n<shape><type>triangle</type><x>310</x><y>249</y></shape>\n<shape><type>circle</type><x>319</x><y>73</y></shape>\n</shapes>\n</root>";


        String path = "src/test/java/edu/uga/miage/m1/polygons/gui/xmlFile/file/exportTest.xml";
        Visitor visitorXml = new XMLVisitor();
        export(visitorXml,"xml",path,shapes);

        Document document = openXmlFile(path);
        assertEquals(rep, convertXmlToString(document));

    }

    @Test
    void test_export_shapes_group() throws IOException, ParseException, ParserConfigurationException, SAXException {
        List<SimpleShape> shapes = new ArrayList<>();
        GroupeShape groupeShape = new GroupeShape();

        shapes.add(new Square(52,33));
        shapes.add(groupeShape);
        groupeShape.add(new Square(103,98));
        groupeShape.add(new Square(164,153));
        groupeShape.add(new Triangle(238,212));
        groupeShape.add(new Triangle(310,249));
        shapes.add(new Circle(319,73));
        String rep = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><root>\n<shapes>\n<shape><type>square</type><x>52</x><y>33</y></shape>\n<groupe>\n<shape><type>square</type><x>103</x><y>98</y></shape>\n<shape><type>square</type><x>164</x><y>153</y></shape>\n<shape><type>triangle</type><x>238</x><y>212</y></shape>\n<shape><type>triangle</type><x>310</x><y>249</y></shape>\n</groupe>\n<shape><type>circle</type><x>319</x><y>73</y></shape>\n</shapes>\n</root>";


        String path = "src/test/java/edu/uga/miage/m1/polygons/gui/xmlFile/file/exportGroupeTest.xml";
        Visitor visitorXml = new XMLVisitor();
        export(visitorXml,"xml",path,shapes);

        Document document = openXmlFile(path);
        assertEquals(rep, convertXmlToString(document));

    }

    @Test
    void test_import_shapes_groupless() throws ParserConfigurationException, IOException, SAXException {
        String path = "src/test/java/edu/uga/miage/m1/polygons/gui/xmlFile/file/importTest.xml";
        Document document = openXmlFile(path);

        List<SimpleShape> shapes_Controle = new ArrayList<>();
        shapes_Controle .add(new Square(52,33));
        shapes_Controle .add(new Square(103,98));
        shapes_Controle .add(new Square(164,153));
        shapes_Controle .add(new Triangle(238,212));
        shapes_Controle .add(new Triangle(310,249));
        shapes_Controle .add(new Circle(319,73));

        List<SimpleShape> shapes = new ArrayList<>();
        shapes = importXml(document);

        int taille = shapes_Controle.size();
        assertTrue(shapes.size() == taille);
        for (int i = 0; i < taille;i++ ){
            Assert.assertEquals(shapes_Controle.get(i).getX(),shapes.get(i).getX());
            Assert.assertEquals(shapes_Controle.get(i).getY(),shapes.get(i).getY());
        }
    }

    @Test
    void test_import_file_group() throws IOException, ParseException, ParserConfigurationException, SAXException {
        String path = "src/test/java/edu/uga/miage/m1/polygons/gui/xmlFile/file/importGroupeTest.xml";
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

        Document document = openXmlFile(path);
        shapes = importXml(document);

        assertTrue(shapes.size() == taille);
        for (int i = 0; i < taille;i++){
            if (shapes.get(i).getClass().getName().equals("edu.uga.miage.m1.polygons.gui.shapes.GroupeShape")){
                GroupeShape groupeShape = (GroupeShape) shapes.get(i);
                for (int j = 0; j < groupeShape.getShapes().size();j++){
                    Assert.assertEquals(groupe_Controle.getShapes().get(j).getX(),groupeShape.getShapes().get(j).getX());
                    Assert.assertEquals(groupe_Controle.getShapes().get(j).getY(),groupeShape.getShapes().get(j).getY());
                }
            }
            else {
                Assert.assertEquals(shapes_Controle.get(i).getX(),shapes.get(i).getX());
                Assert.assertEquals(shapes_Controle.get(i).getY(),shapes.get(i).getY());
            }

        }
    }
}