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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

import javax.swing.*;

import edu.uga.miage.m1.polygons.gui.commands.Draw;
import edu.uga.miage.m1.polygons.gui.persistence.Json;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.Xml;
import edu.uga.miage.m1.polygons.gui.shapes.*;
import static edu.uga.miage.m1.polygons.gui.shapeInteraction.whoWasClicked;


/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JDrawingFrame extends JFrame
        implements MouseListener, MouseMotionListener {
    private enum Shapes {SQUARE, TRIANGLE, CIRCLE, BINOME}
    private SimpleShape shapeDragged;
    private SimpleShape shapeClicked;
    private GroupeShape groupeShape;
    private List<SimpleShape> listeShapes;

    private static RemoteControl remote;

    private static final long serialVersionUID = 1L;
    private JToolBar m_toolbar;
    private Shapes m_selected;
    private JPanel m_panel;
    private JLabel m_label;
    private Graphics2D g2;
    private ActionListener m_reusableActionListener = new ShapeActionListener();

    private JLabel moveShape;
    /**
     * Tracks buttons to manage the background.
     */
    private Map<Shapes, JButton> m_buttons = new HashMap<>();


    /*Rafréchi l'affichage du pano*/
    public void graphiqueUpdate(){
        /*
        Graphics2D g2 = (Graphics2D) m_panel.getGraphics();
        m_panel.update(g2);
        for (SimpleShape s: this.listeShapes){
            System.out.println("Dessin de la figure: " + s);
            s.draw(g2);
        }*/
    }

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
        ImageIcon ico = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/icones/square.png")));

        // Fills the panel
        setLayout(new BorderLayout());
        add(m_toolbar, BorderLayout.NORTH);
        add(m_panel, BorderLayout.CENTER);
        add(m_label, BorderLayout.SOUTH);
        //add(m_shapeLabel,BorderLayout.CENTER);

        // Icône du menu
        addShape(Shapes.SQUARE, new ImageIcon(Objects.requireNonNull(getClass().getResource("images/icones/square.png"))));
        addShape(Shapes.TRIANGLE, new ImageIcon(Objects.requireNonNull(getClass().getResource("images/icones/triangle.png"))));
        addShape(Shapes.CIRCLE, new ImageIcon(Objects.requireNonNull(getClass().getResource("images/icones/circle.png"))));
        addShape(Shapes.BINOME, new ImageIcon(Objects.requireNonNull(getClass().getResource("images/icones/luffyHat.png"))));
        exportXLMButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/icones/xmlExport.png"))));
        exportJSONButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/icones/jsonExport.png"))));
        importXLMButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/icones/import.png"))));
        undoButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/icones/undo.png"))));
        forwardButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/icones/forward.png"))));

        setPreferredSize(new Dimension(8000, 600));

        this.listeShapes = new ArrayList<>();
        this.remote = new RemoteControl(this.listeShapes,this.m_panel);

    }

    private void exportXLMButton(Icon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setActionCommand("exportXML");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Xml.exportXML(new XMLVisitor(),"fileExport",listeShapes);
            }
        });
        m_toolbar.add(button);
        m_toolbar.validate();
        repaint();
    }

    private void importXLMButton(Icon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setActionCommand("importXML");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                listeShapes.clear();
                graphiqueUpdate();
                Graphics2D g2 = (Graphics2D) m_panel.getGraphics();
                Xml.importXML(listeShapes,g2);
                //graphiqieUpdate();*/
            }
        });
        m_toolbar.add(button);
        m_toolbar.validate();
        repaint();
    }

    private void exportJSONButton(Icon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setActionCommand("exportJSON");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Json.exportJSON("Json/fileAuto.json",listeShapes);
            }
        });
        m_toolbar.add(button);
        m_toolbar.validate();
        repaint();
    }

    private void importJSonButton(Icon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setActionCommand("importJSon");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });
        m_toolbar.add(button);
        m_toolbar.validate();
        repaint();
    }

    private void undo(){
        m_panel.removeAll();
        remote.undo();
        repaint();
    }

    private void undoButton(Icon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setActionCommand("importJSon");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               undo();
            }
        });
        m_toolbar.add(button);
        m_toolbar.validate();
        repaint();
    }

    private void forward(){
        m_panel.removeAll();
        remote.forward();
        repaint();
    }

    private void forwardButton(Icon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setActionCommand("importJSon");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forward();
            }
        });
        m_toolbar.add(button);
        m_toolbar.validate();
        repaint();
    }

    /*###########################"*/

    /*Retourne la figure cliqué*/
    public SimpleShape shapeSelecte(MouseEvent evt,boolean shapeUniquement){
        //
        if (this.listeShapes.isEmpty()) return null;
        int i = 0;
        while ((i < listeShapes.size()) && (!listeShapes.get(i).isSelect(evt.getX(),evt.getY()))){
            i++;
        }
        return (i == listeShapes.size())? null : (shapeUniquement)? listeShapes.get(i).shapeSelect(evt.getX(),evt.getY()) : listeShapes.get(i);
    }

    /*Ajoute une figure*/
    /**
     * Injects an available <tt>SimpleShape</tt> into the drawing frame.
     *
     * @param "The" name of the injected <tt>SimpleShape</tt>.
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

    /*Gestion du groupage*/
    private void groupShape(SimpleShape shapeClicked) {
        if (shapeClicked != null) {
            System.out.println("Fig: " + shapeClicked);

            // Création du groupe de figures
            if (this.groupeShape == null) {
                this.groupeShape = new GroupeShape();
                this.listeShapes.add(this.groupeShape);
            }

            // (De)Selecection la figure
            if (this.groupeShape.getShapes().contains(shapeClicked)) {
                // Sort la figure du groupe
                this.listeShapes.add(shapeClicked);
                this.groupeShape.remove(shapeClicked);
                //Supprime groupeShape si vide
                if (this.groupeShape.getShapes().isEmpty()){
                    this.listeShapes.remove(this.groupeShape);
                    this.groupeShape = null;
                }
            } else {
                // Entre la figure du groupe
                this.listeShapes.remove(shapeClicked);
                this.groupeShape.add(shapeClicked);
            }
            System.out.println("Shapes: " + this.listeShapes.toString());
            if (this.groupeShape != null) System.out.println("Groupe: " + this.groupeShape.getShapes().toString());

            // Met à jour le dessin
            graphiqueUpdate();
        }
    }

    public static SimpleShape drawShape(Shapes type,int x,int y){
        SimpleShape figure;
        switch (type) {
            case CIRCLE:
                figure = new Circle(x, y);
                break;
            case TRIANGLE:
                figure = new Triangle(x, y);
                break;
            case SQUARE:
                figure = new Square(x, y);
                break;
            case BINOME:
                figure = new Binome(x, y);
                break;
            default:
                System.out.println("No shape named " + type);
                figure = null;

        }
        System.out.println("New figure: "+ type+"("+x+","+y+")");
        return figure;
    }

    /*##################MOUSE LISTENER#######################*/
    /**
     * TODO Use the factory to abstract shape creation
     * Implements method for the <tt>MouseListener</tt> interface to
     * draw the selected shape into the drawing canvas.
     *
     * @param evt The associated mouse event.
     **/
    public void mouseClicked(MouseEvent evt) {
        System.out.println("Click "+evt.getX()+" "+evt.getY());
        this.shapeClicked = whoWasClicked(this.listeShapes,evt.getX(),evt.getY());
        System.out.println("ShapeClicked "+this.shapeClicked);
        if (shapeClicked == null){
            //Zone vide --> Nouvelle figure
            System.out.println("New figure");
            SimpleShape newShape = drawShape(m_selected,evt.getX(),evt.getY());
            this.remote.addCommand(new Draw(this.m_panel,newShape,listeShapes));
        }
        else {
            //Figure/Groupe présent --> Selectionne la figure / le groupe

        }
        m_panel.removeAll();
        this.remote.play();
        System.out.println("Shapes " + this.listeShapes.size());
        /*
        this.shapeClicked = shapeSelecte(evt,true);
        groupShape(this.shapeClicked);
        if ((this.shapeClicked == null) && (m_panel.contains(evt.getX(), evt.getY()))) {
            Graphics2D g2 = (Graphics2D) m_panel.getGraphics();
            SimpleShape figure;
            switch (m_selected) {
                case CIRCLE:
                    figure = new Circle(evt.getX(), evt.getY());
                    //figure.draw(g2);
                    break;
                case TRIANGLE:
                    figure = new Triangle(evt.getX(), evt.getY());
                    //figure.draw(g2);
                    break;
                case SQUARE:
                    figure = new Square(evt.getX(), evt.getY());
                    //figure.draw(g2);
                    break;
                case BINOME:
                    figure = new Binome(evt.getX(), evt.getY());
                    //figure.draw(g2);
                    break;
                default:
                    System.out.println("No shape named " + m_selected);
                    figure = null;

            }
            System.out.println("ICI");
            remote.addCommand(new Draw(g2,figure,this.listeShapes));
            remote.play();
            //this.listeShapes.add(figure);
        }*/
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
        //m_label.setText("");
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to initiate
     * shape dragging.
     *
     * @param evt The associated mouse event.
     **/
    public void mousePressed(MouseEvent evt) {
        this.shapeDragged = whoWasClicked(this.listeShapes,evt.getX(),evt.getY());
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to complete
     * shape dragging.
     *
     * @param evt The associated mouse event.
     **/
    public void mouseReleased(MouseEvent evt) {
        this.shapeDragged.setX(evt.getX()-25);
        this.shapeDragged.setY(evt.getY()-25);
        this.shapeDragged = null;
        this.m_panel.removeAll();
        this.remote.play();
    }

    /**
     * Implements method for the <tt>MouseMotionListener</tt> interface to
     * move a dragged shape.
     *
     * @param evt The associated mouse event.
     **/
    public void mouseDragged(MouseEvent evt) {
        this.shapeDragged.move(evt.getX()-25,evt.getY()-25);

        //moveShape.setLocation(evt.getX(),evt.getY());
        /*
        if ((this.shapeDragged != null) && (this.shapeDragged.shapeSelect(evt.getX(),evt.getY()) != null)){
            int deltaX = evt.getX() - this.shapeDragged.shapeSelect(evt.getX(),evt.getY()).getX();
            int deltaY = evt.getY() - this.shapeDragged.shapeSelect(evt.getX(),evt.getY()).getY();
            System.out.println("Souris X: " + evt.getX() + " Y: " + evt.getY());
            System.out.println("Fig X: " + this.shapeDragged.getX() + " Y: " + this.shapeDragged.getY());
            System.out.println("Delta X: " + deltaX + " Y: " + deltaY);
            this.shapeDragged.move(deltaX,deltaY);
            graphiqueUpdate();
            //System.out.println(this.shapeSelected.toString());
        }*/
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