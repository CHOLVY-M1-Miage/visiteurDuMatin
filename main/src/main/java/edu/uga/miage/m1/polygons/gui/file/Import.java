package edu.uga.miage.m1.polygons.gui.file;

import edu.uga.miage.m1.polygons.gui.commands.RemoteControl;
import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static edu.uga.miage.m1.polygons.gui.file.FileUtils.chooseFile;
import static edu.uga.miage.m1.polygons.gui.file.FileUtils.getFileExtention;
import static edu.uga.miage.m1.polygons.gui.file.JsonFile.importJson;
import static edu.uga.miage.m1.polygons.gui.file.XmlFile.importXml;
import static edu.uga.miage.m1.polygons.gui.file.XmlFile.openXmlFile;

public class Import {
    public static RemoteControl importFile(List<SimpleShape> shapes, GroupeShape groupe,JPanel m_panel) {
        //Selection du fichier
        String path = chooseFile(false, true, true);
        shapes.clear();
        RemoteControl remote = new RemoteControl(shapes,groupe, m_panel);

        //Json
        if (getFileExtention(path).equals("json")) {
            try {
                remote.load(importJson(path));
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }

        //XML
        else {
            try {
                Document document = openXmlFile(path);
                remote.load(importXml(document));
            } catch (IOException | ParserConfigurationException | SAXException e) {
                throw new RuntimeException(e);
            }
        }

        return remote;
    }
}
