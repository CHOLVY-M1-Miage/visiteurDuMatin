package edu.uga.miage.m1.polygons.gui.file;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Arrays;

public class FileUtils {
    private static final String REPERTOIRE = System.getProperty("user.dir");
    /*
     *Ouvre/Crée un fichier
     */
    private static File createFile(String path,String type) {
        String[] pathTab = path.split("\\.");
        return new File(pathTab[0] + "." + type);
    }

    /*
    * Récupère le contenu d'un fichier dans un buffer
     */
    public static BufferedReader readFile(String path,String type) throws IOException {
        File file = new File(path + type);
        FileReader fileReader = new FileReader(file);
        return new BufferedReader(fileReader);
    }

    /*
     * Retourne la ligne suivante du fichier
     */
    public static String readLine(BufferedReader lines){
        try {
            return lines.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /*
    * Retourne le fileWriter d'un fichier pour pouvoir écrire dedans.
     */
    public static FileWriter fileWriter(String path,String type) {
        File file = createFile(path,type);
        try {
            return new FileWriter(file.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    * Ecrire un texte dans le fichier
     */
    public static void write(FileWriter fileWriter, String texte) {
        try {
            fileWriter.write(texte);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    *Ferme le fichier
     */
    public static void closer(FileWriter fileWriter) {
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static FileNameExtensionFilter setFiltre(boolean bXml,boolean bJson){
        FileNameExtensionFilter filter = null;
        //Aucun
        if (!bXml && !bJson){
            filter = null;
        }
        //xml
        if (bXml && !bJson){
            filter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
        }
        //json
        if (!bXml && bJson){
            filter = new FileNameExtensionFilter("json files (*.json)", "json");
        }
        //xml & json
        if (bXml && bJson){
            filter = new FileNameExtensionFilter("xml OU json files (*.xml;*.json)", "xml","json");
        }
        return filter;
    }

    private static String chooseOpenFile(FileNameExtensionFilter filter){
        File inputFile = null;
        JFileChooser j=new JFileChooser(REPERTOIRE);
        j.setFileFilter(filter);
        //j.setDialogTitle("Open schedule file");
        int returnVal=j.showOpenDialog(j);
        if (returnVal==JFileChooser.APPROVE_OPTION)
        {
            inputFile=j.getSelectedFile();
        }
        return (inputFile != null)? inputFile.getPath() : "";
    }

    public static String getFileExtention(String path){
        String[] pathTab = path.split("\\.");
        return (pathTab.length > 2)? pathTab[1] : "";
    }

    private static String chooseSaveFile(FileNameExtensionFilter filter){
        File outputFile = null;
        JFileChooser j=new JFileChooser(REPERTOIRE);
        j.setFileFilter(filter);
        int returnVal=j.showSaveDialog(j);
        if (returnVal== JFileChooser.APPROVE_OPTION) {
            outputFile=j.getSelectedFile();
            if (outputFile.exists()) {
                int retour = JOptionPane.showConfirmDialog(j,
                        "Un fichier \""+outputFile+"\" existe déjà.\n" +
                                "Voulez-vous vraiment l'écraser?\n",
                        "Confirmation d'enregistrement",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (retour == JOptionPane.OK_OPTION) {
                    System.out.println("File: "+outputFile.getPath());
                }
            }
            else {
                System.out.println("File: "+outputFile.getPath());
            }
        }
        return (outputFile != null)? outputFile.getPath() : "";
    }

    private static String addExtension(String path,boolean bXml){
        String type = (bXml)? "xml" : "json";
        String[] pathTab = path.split("\\.");
        return (pathTab.length == 2 && pathTab[1].equals(type))? path : path+"."+type;
    }

    /*
    Selectionner un fichier json et/ou xml
     */
    public static String chooseFile(boolean modeSave,boolean bXml,boolean bJson){
        FileNameExtensionFilter filter = setFiltre(bXml,bJson);
        if (modeSave){
           return addExtension(chooseSaveFile(filter),bXml);
        }
        else {
            return chooseOpenFile(filter);
        }
    }
}
