package edu.uga.miage.m1.polygons.gui.file;

import java.io.*;

public class FileUtils {

    /*
     *Ouvre/Crée un fichier
     */
    private static File createFile(String type) {
        return new File("fileExport." + type);
    }

    /*
    * Récupère le contenu d'un fichier dans un buffer
     */
    public static BufferedReader readFile(String type) throws IOException {
        File file = new File("fileExport." + type);
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
    public static FileWriter fileWriter(String type) {
        File file = createFile(type);
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
}
