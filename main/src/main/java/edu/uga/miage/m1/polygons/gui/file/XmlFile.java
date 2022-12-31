package edu.uga.miage.m1.polygons.gui.file;

import edu.uga.miage.m1.polygons.gui.commands.Command;
import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static edu.uga.miage.m1.polygons.gui.factory.Factory.createShape;

public class XmlFile {
    public static String convertXmlToString(Document doc) {
        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tf.newTransformer();
            transformer.transform(domSource, result);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }

    public static Document convertStringToXml(String xmlString) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;

        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public static Document openXmlFile (String path) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(path);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(file);

        return document;
    }

    private static SimpleShape nodeToShape(Node node){
        Element eElement = (Element) node;
        String type = eElement.getElementsByTagName("type").item(0).getTextContent();
        int x = Integer.parseInt(eElement.getElementsByTagName("x").item(0).getTextContent());
        int y = Integer.parseInt(eElement.getElementsByTagName("y").item(0).getTextContent());

        return createShape(type, x, y);
    }

    public static List<SimpleShape> importXml (Document document){
        List<Command> commandes = new ArrayList<>();
        List<SimpleShape> shapes = new ArrayList<>();
        //System.out.println("Root Element :" + document.getDocumentElement().getNodeName());
        Node shapesNode = document.getElementsByTagName("shapes").item(0);
        NodeList nList = shapesNode.getChildNodes();
        //System.out.println("----------------------------");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            String nodeType = nNode.getNodeName();
            if (nodeType.equals("groupe")){
                GroupeShape groupe = new GroupeShape();
                shapes.add(groupe);
                NodeList nListGroupe = nNode.getChildNodes();
                for (int tempG = 0; tempG < nListGroupe.getLength(); tempG++){
                    Node nNodeGroupe = nListGroupe.item(tempG);
                    System.out.println("\nCurrent Element in groupe:" + nNodeGroupe.getNodeName());
                    if (nNodeGroupe.getNodeType() == Node.ELEMENT_NODE) {
                        groupe.add(nodeToShape(nNodeGroupe));
                    }
                }
            }
            else {
                System.out.println("\nCurrent Element :" + nodeType);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    shapes.add(nodeToShape(nNode));
                }
            }
        }
        return shapes;
    }
}
