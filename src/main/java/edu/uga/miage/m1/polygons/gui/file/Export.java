package edu.uga.miage.m1.polygons.gui.file;

import edu.uga.miage.m1.polygons.gui.visitor.JsonVisitor;
import edu.uga.miage.m1.polygons.gui.visitor.Visitor;
import edu.uga.miage.m1.polygons.gui.visitor.XmlVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static edu.uga.miage.m1.polygons.gui.file.FileUtils.chooseFile;

public class Export {
    private static String virgule(String type,List<SimpleShape> listeShapes, SimpleShape s){
        return ((type.equals("xml")) || (s.equals(listeShapes.get(listeShapes.size() - 1)))) ? "" : ",";
    }

    public static void export(Visitor visitor,String type, String path, List<SimpleShape> listeShapes) {
        FileWriter fileWriter = FileUtils.fileWriter(path,type);
        //Ajout du json head
        visitor.head(fileWriter);

        // Ajout des figures (boucle for)
        for (SimpleShape s : listeShapes) {
            s.accept(visitor);
            try {
                fileWriter.write(visitor.getRepresentation() + virgule(type,listeShapes,s) + '\n');
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        //Ajout du json foot
        visitor.foot(fileWriter);
        //Fermeture du fichier
        FileUtils.closer(fileWriter);
    }

    public static void exportXml(List<SimpleShape> shapes) {
        String path = chooseFile(true, true, false);
        Visitor visitorXml = new XmlVisitor();
        export(visitorXml, "xml", path, shapes);
        System.out.println("Xml export make in file: " + path);
    }

    public static void exportJson(List<SimpleShape> shapes) {
        String path = chooseFile(true, false, true);
        Visitor visitorJson = new JsonVisitor();
        export(visitorJson, "json", path, shapes);
        System.out.println("json export make in file: " + path);
    }
}
