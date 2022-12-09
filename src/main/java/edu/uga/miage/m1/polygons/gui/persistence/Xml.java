package edu.uga.miage.m1.polygons.gui.persistence;

import edu.uga.miage.m1.polygons.gui.file.FileUtils;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;
import org.w3c.dom.Document;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Xml {

    /**
     * Crée et dessine la figure
     */
    public static void createSimpleShape(String type, int x, int y, List<SimpleShape> listeShapes, Graphics2D g2){
        SimpleShape figure;
        switch (type.toUpperCase()) {
            case "CIRCLE":
                figure = new Circle(x, y);
                //figure.draw(g2);
                break;
            case "TRIANGLE":
                figure = new Triangle(x, y);
                //figure.draw(g2);
                break;
            case "SQUARE":
                figure = new Square(x, y);
                //figure.draw(g2);
                break;
            default:
                //System.out.println("No shape named " + m_selected);
                figure = null;

        }
        listeShapes.add(figure);
    }

    public static void exportXML(Visitor visitor,String path,List<SimpleShape> listeShapes) {
        FileWriter fileWriter = FileUtils.fileWriter(path,"xml");
        //Ajout du xml head
        visitor.head(fileWriter);

        // Ajout des figures (boucle for)
        for (SimpleShape s : listeShapes) {
            s.accept(visitor);
            try {
                fileWriter.write(visitor.getRepresentation() + '\n');
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        //Ajout du xml foot
        visitor.foot(fileWriter);
        //Fermeture du fichier
        FileUtils.closer(fileWriter);
    }

    public static List<SimpleShape> importXML(Document xml){
        System.out.println("--> "+ xml);
        return new ArrayList<>();
    }

    public static void importXML(List<SimpleShape> listeShapes,Graphics2D g2) {
        String line;
        XMLVisitor xmlVisitor = new XMLVisitor();
        try {
            //Récupère le texte du fichier xml
            BufferedReader lines = FileUtils.readFile("fileimport","xml");

            //Vérifier que l'entête du fichier est correcte.
            if (xmlVisitor.estHead(lines)){
                System.out.println("Fichier xml valide");
                line = FileUtils.readLine(lines);
                while(!line.equals("</shapes>")){
                    //Convertion des figures (format xml en obj SimpleShape)
                    System.out.println("figure: " + line);
                    //Récupération des figures (format xml)
                    String[] shapeData = String.join(">",line.split("<")).split(">");
                    System.out.println(String.format("SimpleShape: Type: %s X: %s Y: %s",shapeData[4],shapeData[8],shapeData[12]));
                    createSimpleShape(shapeData[4],Integer.parseInt(shapeData[8]),Integer.parseInt(shapeData[12]),listeShapes,g2);
                    //Passe à la figure suivante
                    line = FileUtils.readLine(lines);

                }
                //Vérifie que le fichier xml se termine correctement
                if (xmlVisitor.estFoot(lines)){
                    System.out.println("Importation réalisée avec succès");
                }
                else {
                    System.out.println("Fichier xml Invalide");
                    listeShapes.clear();
                }
            }
            else {
                System.out.println("Attention le fichier n'est pas un xml valide.");
                listeShapes.clear();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
