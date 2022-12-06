package edu.uga.miage.m1.polygons.gui.persistence;

import edu.uga.miage.m1.polygons.gui.file.FileUtils;
import edu.uga.miage.m1.polygons.gui.shapes.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
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
     *Génére le fichier JSON du dessin dans le dossier JSON
     */
    public static String jsonVirgule(List<SimpleShape> listeShapes,SimpleShape s){
        return (s.equals(listeShapes.get(listeShapes.size() - 1))) ? "" : ",";
    }

    public static void exportJSON(String path,List<SimpleShape> listeShapes) {
        JSonVisitor jsonVisitor = new JSonVisitor();
        FileWriter fileWriter = FileUtils.fileWriter(path,"json");
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

    public static List<SimpleShape> importJson(String path) throws IOException, ParseException {
        JSONParser jsonP = new JSONParser();
        JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(path));
        JSONArray shapes = (JSONArray) jsonO.get("shapes");
        List<SimpleShape> listeShapes = new ArrayList<>();

        for (int i = 0;i < shapes.size();i++){

            JSONObject shape = (JSONObject) shapes.get(i);
            String type = shape.get("type").toString();
            int x = Integer.parseInt(shape.get("x").toString());
            int y = Integer.parseInt(shape.get("y").toString());

            //System.out.println("New obj: " + type + "," + x + "," + y);
            SimpleShape figure;
            switch (type.toUpperCase()){
                case "CIRCLE":
                    figure = new Circle(x, y);
                    break;
                case "TRIANGLE":
                    figure = new Triangle(x, y);
                    break;
                case "SQUARE":
                    figure = new Square(x, y);
                    break;
                case "BINOME":
                    figure = new Binome(x, y);
                    break;
                default:
                    //System.out.println("No shape named " + m_selected);
                    figure = null;
            }

            //System.out.println("New obj: " + figure);
            listeShapes.add(figure);
        }

        return listeShapes;
    }

    public static void importJSon(List<SimpleShape> listeShapes, Graphics2D g2) {
        JSonVisitor jsonVisitor = new JSonVisitor();
        String line;
        try {
            //Récupère le texte du fichier Json
            BufferedReader lines = FileUtils.readFile("FileImport","json");
            //Vérifier que l'entête du fichier est correcte.
            if (jsonVisitor.estHead(lines)){
                System.out.println("Fichier json valide");
                line = FileUtils.readLine(lines);
                while(!line.equals("]")){
                    //Convertion des figures (format xml en obj SimpleShape)
                    System.out.println("figure: " + line);
                    //Récupération des figures (format xml)
                    String[] shapeData = line.split(",");
                    String type = shapeData[0].split("\"")[3];
                    int cordX = Integer.parseInt(shapeData[1].split(": ")[1]);
                    int cordY = Integer.parseInt(shapeData[2].split(": ")[1].split("}")[0]);
                    System.out.println(String.format("SimpleShape: Type: %s X: %s Y: %s",type,cordX,cordY));
                    createSimpleShape(type,cordX,cordY,listeShapes,g2);
                    //Passe à la figure suivante
                    line = FileUtils.readLine(lines);
                }
                //Vérifie que le fichier xml se termine correctement
                if (jsonVisitor.estFoot(lines)){
                    System.out.println("Importation réalisée avec succès");
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
