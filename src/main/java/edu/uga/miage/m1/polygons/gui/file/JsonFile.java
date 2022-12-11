package edu.uga.miage.m1.polygons.gui.file;

import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static edu.uga.miage.m1.polygons.gui.factory.Factory.createShape;

public class JsonFile {

    public static JsonObject jsonFromString(String jsonObjectStr) {

        JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectStr));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();

        return object;
    }

    private static SimpleShape jsonToShape(JSONObject shape){
        String type = shape.get("type").toString();
        int x = Integer.parseInt(shape.get("x").toString());
        int y = Integer.parseInt(shape.get("y").toString());

        return createShape(type,x,y);
    }

    public static List<SimpleShape> importJson(String path) throws IOException, ParseException {
        JSONParser jsonP = new JSONParser();
        JSONObject jsonO = (JSONObject) jsonP.parse(new FileReader(path));
        JSONArray shapes = (JSONArray) jsonO.get("shapes");
        List<SimpleShape> listeShapes = new ArrayList<>();

        for (int i = 0;i < shapes.size();i++){
            JSONObject shape = (JSONObject) shapes.get(i);
            Object groupeShapes = shape.get("groupe");

            //Groupe
            if (groupeShapes != null){
                GroupeShape groupe = new GroupeShape();
                listeShapes.add(groupe);

                //System.out.println(groupeShapes);
                JSONArray groupeShapesArray = (JSONArray) groupeShapes;
                for (int j = 0;j < groupeShapesArray.size();j++) {
                    JSONObject gShape = (JSONObject) groupeShapesArray.get(j);
                    //System.out.println("->" + gShape);
                    groupe.add(jsonToShape(gShape));
                }
            }
            else {
                listeShapes.add(jsonToShape(shape));
            }
        }

        return listeShapes;
    }
}
