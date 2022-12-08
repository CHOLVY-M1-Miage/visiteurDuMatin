package edu.uga.miage.m1.polygons.gui.XmlFile;

import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static edu.uga.miage.m1.polygons.gui.file.XmlFile.*;
import static edu.uga.miage.m1.polygons.gui.file.export.export;
import static edu.uga.miage.m1.polygons.gui.persistence.Json.exportJSON;
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


        String path = "src/test/java/edu/uga/miage/m1/polygons/gui/XmlFile/file/exportTest.xml";
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


        String path = "src/test/java/edu/uga/miage/m1/polygons/gui/XmlFile/file/exportTest.xml";
        Visitor visitorXml = new XMLVisitor();
        export(visitorXml,"xml",path,shapes);

        Document document = openXmlFile(path);
        assertEquals(rep, convertXmlToString(document));

    }
}