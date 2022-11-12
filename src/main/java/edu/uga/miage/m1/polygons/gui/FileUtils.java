package edu.uga.miage.m1.polygons.gui;

import java.io.*;

public class FileUtils {
    public static boolean estJSonHead(BufferedReader lines){
        try {
            String head = lines.readLine() + lines.readLine();
            System.out.println(head);
            return head.equals("{\"shapes\":[");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean estJSonFoot(BufferedReader lines) {
        try {
            String foot = lines.readLine() + lines.readLine();
            System.out.println(foot);
            return foot.equals("}null");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean estXMLHead(BufferedReader lines){
        try {
            String head = lines.readLine() + lines.readLine() + lines.readLine();
            System.out.println(head);
            return head.equals("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                    "<root>" +
                    "<shapes>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean estXMLFoot(BufferedReader lines) {
        try {
            String foot = lines.readLine() + lines.readLine();
            System.out.println(foot);
            return foot.equals("</root>null");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Void jsonFileHead(FileWriter fileWriter) {
        write(fileWriter,"{\n\"shapes\":[\n");
        return null;
    }

    public static Void jsonFileFoot(FileWriter fileWriter) {
        write(fileWriter,"]\n}");
        return null;
    }

    public static Void xmlFileHead(FileWriter fileWriter) {
        write(fileWriter,
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                        "<root>\n" +
                        "<shapes>\n"
        );
        return null;
    }

    public static Void xmlFileFoot(FileWriter fileWriter) {
        write(fileWriter,
                "</shapes>\n" +
                        "</root>");
        return null;
    }

    public static void write(FileWriter fileWriter, String texte) {
        try {
            fileWriter.write(texte);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static FileWriter fileWriter(String type) {
        File file = createFile(type);
        try {
            return new FileWriter(file.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedReader readFile(String type) throws IOException {
        File file = new File("fileExport." + type);
        FileReader fileReader = new FileReader(file);
        BufferedReader lines = new BufferedReader(fileReader);
        return lines;
    }

    public static String readLine(BufferedReader lines){
        try {
            return lines.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File createFile(String type) {
        File fileExport = new File("fileExport." + type);
        try {
            fileExport.createNewFile();
            return fileExport;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closer(FileWriter fileWriter) {
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
