package edu.uga.miage.m1.polygons.gui;
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.swing.*;

import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;


/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JDrawingFrame extends JFrame
        implements MouseListener, MouseMotionListener {
    private enum Shapes {SQUARE, TRIANGLE, CIRCLE}
    private SimpleShape shapeSelected;

    ;
    private List<SimpleShape> listeShapes;
    private static final long serialVersionUID = 1L;
    private JToolBar m_toolbar;
    private Shapes m_selected;
    private JPanel m_panel;
    private JLabel m_label;
    private ActionListener m_reusableActionListener = new ShapeActionListener();

    /**
     * Tracks buttons to manage the background.
     */
    private Map<Shapes, JButton> m_buttons = new HashMap<>();

    /**
     * Default constructor that populates the main window.
     *
     * @param frameName
     **/
    public JDrawingFrame(String frameName) {
        super(frameName);
        // Instantiates components
        m_toolbar = new JToolBar("Toolbar");
        m_panel = new JPanel();
        m_panel.setBackground(Color.WHITE);
        m_panel.setLayout(null);
        m_panel.setMinimumSize(new Dimension(400, 400));
        m_panel.addMouseListener(this);
        m_panel.addMouseMotionListener(this);
        m_label = new JLabel(" ", JLabel.LEFT);

        // Fills the panel
        setLayout(new BorderLayout());
        add(m_toolbar, BorderLayout.NORTH);
        add(m_panel, BorderLayout.CENTER);
        add(m_label, BorderLayout.SOUTH);

        // Add shapes in the menu
        addShape(Shapes.SQUARE, new ImageIcon(getClass().getResource("images/square.png")));
        addShape(Shapes.TRIANGLE, new ImageIcon(getClass().getResource("images/triangle.png")));
        addShape(Shapes.CIRCLE, new ImageIcon(getClass().getResource("images/circle.png")));
        exportXLMButton(new ImageIcon(getClass().getResource("images/xml.png")));
        exportJSONButton(new ImageIcon(getClass().getResource("images/json.png")));
        importXLMButton(new ImageIcon(getClass().getResource("images/xml.png")));
        importJSonButton(new ImageIcon(getClass().getResource("images/json.png")));

        setPreferredSize(new Dimension(400, 400));
        this.listeShapes = new ArrayList<>();
    }


    /**
     * Injects an available <tt>SimpleShape</tt> into the drawing frame.
     *
     * @param name The name of the injected <tt>SimpleShape</tt>.
     * @param icon The icon associated with the injected <tt>SimpleShape</tt>.
     **/
    private void addShape(Shapes shape, ImageIcon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        m_buttons.put(shape, button);
        button.setActionCommand(shape.toString());
        button.addActionListener(m_reusableActionListener);

        if (m_selected == null) {
            button.doClick();
        }

        m_toolbar.add(button);
        m_toolbar.validate();
        repaint();
    }

    /*
     *Exporte le dessin au format XML
     */
    private void exportXLMButton(Icon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setActionCommand("exportXML");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportXML();
            }
        });
        m_toolbar.add(button);
        m_toolbar.validate();
        repaint();
    }

    /*
     *Génére le fichier XML du dessin dans le dossier xml
     */
    public void exportXML() {
        XMLVisitor xmlVisitor = new XMLVisitor();
        FileWriter fileWriter = FileUtils.fileWriter("xml");
        //Ajout du xml head
        FileUtils.xmlFileHead(fileWriter);

        // Ajout des figures (boucle for)
        for (SimpleShape s : listeShapes) {
            s.accept(xmlVisitor);
            try {
                fileWriter.write(xmlVisitor.getRepresentation() + '\n');
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        //Ajout des figures (stream)
        /*
        this.listeShapes.stream().map(
                (SimpleShape s) -> {
                    s.accept(xmlVisitor);
                    FileUtils.write(fileWriter, xmlVisitor.getRepresentation());
                    System.out.println(xmlVisitor.getRepresentation());
                    return null;
                }
        );*/
        //Ajout du xml foot
        FileUtils.xmlFileFoot(fileWriter);
        //Fermeture du fichier
        FileUtils.closer(fileWriter);
    }

    private void importXLMButton(Icon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setActionCommand("importXML");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importXML();
            }
        });
        m_toolbar.add(button);
        m_toolbar.validate();
        repaint();
    }

    public void importXML() {
        String line;
        try {
            //Récupère le texte du fichier xml
            BufferedReader lines = FileUtils.readFile("xml");
            //Vérifier que l'entête du fichier est correcte.
           if (FileUtils.estXMLHead(lines)){
               System.out.println("Fichier xml valide");
               line = FileUtils.readLine(lines);
               while(!line.equals("</shapes>")){
                   //Convertion des figures (format xml en obj SimpleShape)
                   System.out.println("figure: " + line);
                   //Récupération des figures (format xml)
                   String[] shapeData = String.join(">",line.split("<")).split(">");
                   System.out.println(String.format("SimpleShape: Type: %s X: %s Y: %s",shapeData[4],shapeData[8],shapeData[12]));
                   createSimpleShape(shapeData[4],Integer.parseInt(shapeData[8]),Integer.parseInt(shapeData[12]));
                   //Passe à la figure suivante
                   line = FileUtils.readLine(lines);

               }
               //Vérifie que le fichier xml se termine correctement
               if (FileUtils.estXMLFoot(lines)){
                   System.out.println("Importation réalisée avec succès");
               }
               else {
                   System.out.println("Fichier xml Invalide");
                   this.listeShapes.clear();
               }
           }
           else {
               System.out.println("Attention le fichier n'est pas un xml valide.");
               this.listeShapes.clear();
           }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void exportJSONButton(Icon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setActionCommand("exportJSON");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportJSON();
            }
        });
        m_toolbar.add(button);
        m_toolbar.validate();
        repaint();
    }

    /*
     *Génére le fichier JSON du dessin dans le dossier JSON
     */
    public String jsonVirgule(SimpleShape s){
        return (s.equals(this.listeShapes.get(listeShapes.size() - 1))) ? "" : ",";
    }

    public void exportJSON() {
        JSonVisitor jsonVisitor = new JSonVisitor();
        FileWriter fileWriter = FileUtils.fileWriter("json");
        //Ajout du json head
        FileUtils.jsonFileHead(fileWriter);

        // Ajout des figures (boucle for)
        for (SimpleShape s : listeShapes) {
            s.accept(jsonVisitor);
            try {
                fileWriter.write(jsonVisitor.getRepresentation() + jsonVirgule(s) + '\n');
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        //Ajout du json foot
        FileUtils.jsonFileFoot(fileWriter);
        //Fermeture du fichier
        FileUtils.closer(fileWriter);
    }

    private void importJSonButton(Icon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setActionCommand("importJSon");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importJSon();
            }
        });
        m_toolbar.add(button);
        m_toolbar.validate();
        repaint();
    }

    public void importJSon() {
        String line;
        try {
            //Récupère le texte du fichier Json
            BufferedReader lines = FileUtils.readFile("json");
            //Vérifier que l'entête du fichier est correcte.
            if (FileUtils.estJSonHead(lines)){
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
                    createSimpleShape(type,cordX,cordY);
                    //Passe à la figure suivante
                    line = FileUtils.readLine(lines);
                }
                //Vérifie que le fichier xml se termine correctement
                if (FileUtils.estJSonFoot(lines)){
                    System.out.println("Importation réalisée avec succès");
                }
                else {
                    System.out.println("Fichier json Invalide");
                    this.listeShapes.clear();
                }
            }
            else {
                System.out.println("Attention le fichier n'est pas un json valide.");
                this.listeShapes.clear();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crée et dessine la figure
     */
    public void createSimpleShape(String type,int x,int y){
        Graphics2D g2 = (Graphics2D) m_panel.getGraphics();
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
                System.out.println("No shape named " + m_selected);
                figure = null;

        }
        this.listeShapes.add(figure);
    }

    public SimpleShape shapeSelecte(MouseEvent evt){
        //
        if (this.listeShapes.isEmpty()) return null;
        int i = 0;
        while ((i < listeShapes.size()) && (!listeShapes.get(i).isSelect(evt.getX(),evt.getY()))){
            i++;
        }

        return (i == listeShapes.size())? null : listeShapes.get(i);
    }

    /**
     * TODO Use the factory to abstract shape creation
     * Implements method for the <tt>MouseListener</tt> interface to
     * draw the selected shape into the drawing canvas.
     *
     * @param evt The associated mouse event.
     **/
    public void mouseClicked(MouseEvent evt) {
        this.shapeSelected = shapeSelecte(evt);
        if ((this.shapeSelected == null) && (m_panel.contains(evt.getX(), evt.getY()))) {
            Graphics2D g2 = (Graphics2D) m_panel.getGraphics();
            SimpleShape figure;
            switch (m_selected) {
                case CIRCLE:
                    figure = new Circle(evt.getX(), evt.getY());
                    figure.draw(g2);
                    break;
                case TRIANGLE:
                    figure = new Triangle(evt.getX(), evt.getY());
                    figure.draw(g2);
                    break;
                case SQUARE:
                    figure = new Square(evt.getX(), evt.getY());
                    figure.draw(g2);
                    break;
                default:
                    System.out.println("No shape named " + m_selected);
                    figure = null;

            }
            this.listeShapes.add(figure);
        }
    }

    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     *
     * @param evt The associated mouse event.
     **/
    public void mouseEntered(MouseEvent evt) {

    }

    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     *
     * @param evt The associated mouse event.
     **/
    public void mouseExited(MouseEvent evt) {
        m_label.setText(" ");
        m_label.repaint();
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to initiate
     * shape dragging.
     *
     * @param evt The associated mouse event.
     **/
    public void mousePressed(MouseEvent evt) {
        this.shapeSelected = shapeSelecte(evt);
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to complete
     * shape dragging.
     *
     * @param evt The associated mouse event.
     **/
    public void mouseReleased(MouseEvent evt) {
        this.shapeSelected = null;

    }

    /**
     * Implements method for the <tt>MouseMotionListener</tt> interface to
     * move a dragged shape.
     *
     * @param evt The associated mouse event.
     **/
    public void mouseDragged(MouseEvent evt) {
        Graphics2D g2 = (Graphics2D) m_panel.getGraphics();
        if (this.shapeSelected != null){
            this.shapeSelected.setX(evt.getX());
            this.shapeSelected.setY(evt.getY());
            update(g2);
            for (SimpleShape s: this.listeShapes){
                s.draw(g2);
            }
            //System.out.println(this.shapeSelected.toString());
        }
    }

    /**
     * Implements an empty method for the <tt>MouseMotionListener</tt>
     * interface.
     *
     * @param evt The associated mouse event.
     **/
    public void mouseMoved(MouseEvent evt) {
        modifyLabel(evt);
    }

    private void modifyLabel(MouseEvent evt) {
        m_label.setText("(" + evt.getX() + "," + evt.getY() + ")");
    }

    /**
     * Simple action listener for shape tool bar buttons that sets
     * the drawing frame's currently selected shape when receiving
     * an action event.
     **/
    private class ShapeActionListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            int cmp = 0;
            // Itère sur tous les boutons
            Iterator<Shapes> keys = m_buttons.keySet().iterator();
            while (keys.hasNext()) {
                Shapes shape = keys.next();
                JButton btn = m_buttons.get(shape);
                cmp++;
                if (evt.getActionCommand().equals(shape.toString())) {
                    btn.setBorderPainted(true);
                    m_selected = shape;
                }
                btn.repaint();
            }
        }
    }
}