package edu.uga.miage.m1.polygons.gui.persistence;

import edu.uga.miage.m1.polygons.gui.file.FileUtils;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Json {

    public static void createSimpleShape(String type, int x, int y, List<SimpleShape> listeShapes, Graphics2D g2){
        SimpleShape figure;
        switch (type.toUpperCase()) {
            case "CIRCLE":
                figure = new Circle(x, y);
                figure.draw(g2);
                break;
            case "TRIANGLE":
                figure = new Triangle(x, y);
                figure.draw(g2);
                break;
            case "SQUARE":
                figure = new Square(x, y);
                figure.draw(g2);
                break;
            default:
                //System.out.println("No shape named " + m_selected);
                figure = null;

        }
        listeShapes.add(figure);
    }

    /*
     *G�n�re le fichier JSON du dessin dans le dossier JSON
     */
    public static String jsonVirgule(List<SimpleShape> listeShapes,SimpleShape s){
        return (s.equals(listeShapes.get(listeShapes.size() - 1))) ? "" : ",";
    }

    public static void exportJSON(List<SimpleShape> listeShapes) {
        JSonVisitor jsonVisitor = new JSonVisitor();
        FileWriter fileWriter = FileUtils.fileWriter("json");
        //Ajout du json head
        jsonVisitor.head(fileWriter);

        // Ajout des figures (boucle for)
        for (SimpleShape s : listeShapes) {
            s.accept(jsonVisitor);
            try {
                fileWriter.write(jsonVisitor.getRepresentation() + jsonVirgule(listeShapes,s) + '\n');
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        //Ajout du json foot
        jsonVisitor.foot(fileWriter);
        //Fermeture du fichier
        FileUtils.closer(fileWriter);
    }

    public static void importJSon(List<SimpleShape> listeShapes, Graphics2D g2) {
        JSonVisitor jsonVisitor = new JSonVisitor();
        String line;
        try {
            //R�cup�re le texte du fichier Json
            BufferedReader lines = FileUtils.readFile("json");
            //V�rifier que l'ent�te du fichier est correcte.
            if (jsonVisitor.estHead(lines)){
                System.out.println("Fichier json valide");
                line = FileUtils.readLine(lines);
                while(!line.equals("]")){
                    //Convertion des figures (format xml en obj SimpleShape)
                    System.out.println("figure: " + line);
                    //R�cup�ration des figures (format xml)
                    String[] shapeData = line.split(",");
                    String type = shapeData[0].split("\"")[3];
                    int cordX = Integer.parseInt(shapeData[1].split(": ")[1]);
                    int cordY = Integer.parseInt(shapeData[2].split(": ")[1].split("}")[0]);
                    System.out.println(String.format("SimpleShape: Type: %s X: %s Y: %s",type,cordX,cordY));
                    createSimpleShape(type,cordX,cordY,listeShapes,g2);
                    //Passe � la figure suivante
                    line = FileUtils.readLine(lines);
                }
                //V�rifie que le fichier xml se termine correctement
                if (jsonVisitor.estFoot(lines)){
                    System.out.println("Importation r�alis�e avec succ�s");
                }
                else {
                    System.out.println("Fichier json Invalide");
                    listeShapes.clear();
                }
            }
            else {
                System.out.println("Attention le fichier n'est pas un json valide.");
                listeShapes.clear();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
