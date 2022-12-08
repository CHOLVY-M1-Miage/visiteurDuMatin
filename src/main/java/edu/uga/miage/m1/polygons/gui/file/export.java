package edu.uga.miage.m1.polygons.gui.file;

import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class export {
    public static String virgule(String type,List<SimpleShape> listeShapes, SimpleShape s){
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
}
