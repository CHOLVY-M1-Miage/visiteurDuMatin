package edu.uga.miage.m1.polygons.gui.graphique;
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
import java.util.*;
import java.util.List;

import javax.swing.*;

import edu.uga.miage.m1.polygons.gui.commands.*;
import edu.uga.miage.m1.polygons.gui.shapes.*;

import static edu.uga.miage.m1.polygons.gui.factory.Factory.createShape;
import static edu.uga.miage.m1.polygons.gui.file.Export.*;
import static edu.uga.miage.m1.polygons.gui.file.Import.importFile;
import static edu.uga.miage.m1.polygons.gui.graphique.shapeInteraction.whoWasClicked;


/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JDrawingFrame extends JFrame
        implements MouseListener, MouseMotionListener {
    public enum Shapes {SQUARE, TRIANGLE, CIRCLE, BINOME}

    private SimpleShape shapeDragged;
    private SimpleShape shapeDraggedInGroupe;
    private int shapeDraggedXOrigine;
    private int shapeDraggedYOrigine;
    private boolean shapeWasMove = false;
    private SimpleShape shapeClicked;
    private GroupeShape selectGroupe;
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

        // Icône du menu
        shapeButton(Shapes.SQUARE, new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/icones/square.png"))));
        shapeButton(Shapes.TRIANGLE, new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/icones/triangle.png"))));
        shapeButton(Shapes.CIRCLE, new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/icones/circle.png"))));
        shapeButton(Shapes.BINOME, new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/icones/binome.png"))));
        undoButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/icones/undo.png"))));
        forwardButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/icones/forward.png"))));
        clearButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/icones/clearAll.png"))));
        exportXLMButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/icones/exportXML.png"))));
        exportJSONButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/icones/exportJSON.png"))));
        importButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/icones/import.png"))));


        setPreferredSize(new Dimension(8000, 600));

        this.listeShapes = new ArrayList<>();
        this.selectGroupe = new GroupeShape();
        remote = new RemoteControl(this.listeShapes,this.selectGroupe, this.m_panel);

    }

//###[FONCTION MENU]##########################################################
    /**
     * Injects an available <tt>SimpleShape</tt> into the drawing frame.
     *
     * @param "The" name of the injected <tt>SimpleShape</tt>.
     * @param icon  The icon associated with the injected <tt>SimpleShape</tt>.
     **/
    private void shapeButton(Shapes shape, ImageIcon icon) {
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

    private void importButton(Icon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setActionCommand("importXML");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane j=new JOptionPane();
                int retour = JOptionPane.showConfirmDialog(j,
                        "Attention cette action va supprimer le dessins",
                        "Voullez vous continuer?",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (retour == JOptionPane.OK_OPTION) {
                    remote = importFile(listeShapes,selectGroupe,m_panel);
                    m_panel.removeAll();
                    remote.play();
                    repaint();
                }
            }
        });
        m_toolbar.add(button);
        m_toolbar.validate();
        repaint();
    }

    private void exportXLMButton(Icon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setActionCommand("exportXML");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportXml(listeShapes);
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
                exportJson(listeShapes);
            }
        });
        m_toolbar.add(button);
        m_toolbar.validate();
        repaint();
    }

    private void undoButton(Icon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setActionCommand("importJSon");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_panel.removeAll();
                remote.undo();
                repaint();
            }
        });
        m_toolbar.add(button);
        m_toolbar.validate();
        repaint();
    }

    private void forwardButton(Icon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setActionCommand("importJSon");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_panel.removeAll();
                remote.forward();
                repaint();
            }
        });
        m_toolbar.add(button);
        m_toolbar.validate();

    }

    private void clearButton(Icon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setActionCommand("importJSon");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane j=new JOptionPane();
                int retour = JOptionPane.showConfirmDialog(j,
                                "Voulez-vous vraiment réinitialiser le dessin?\n" +
                                        "Cette action est iréversible",
                        "Tous supprimer?",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (retour == JOptionPane.OK_OPTION) {
                    remote.clear();
                    m_panel.removeAll();
                    repaint();
                }

            }
        });
        m_toolbar.add(button);
        m_toolbar.validate();
    }

    private void newGroupe(Icon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        button.setActionCommand("importJSon");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectGroupe = new GroupeShape();
                m_panel.removeAll();
                remote.play();
            }
        });
        m_toolbar.add(button);
        m_toolbar.validate();
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
        System.out.println("-----------------------------------------");
        System.out.printf("EVENT [Click (%d,%d)]\n",evt.getX(),evt.getY());
        this.shapeClicked = whoWasClicked(this.listeShapes, evt.getX(), evt.getY());
        System.out.printf("\tShapeClicked: %s\n",this.shapeClicked);
        if (shapeClicked == null) {
            //Zone vide --> Nouvelle figure
            SimpleShape newShape = createShape(m_selected, evt.getX(), evt.getY());
            System.out.printf("\t NEW FIGURE %s\n",newShape);
            remote.addCommand(new Draw(newShape,remote));
        } else {
            //Figure/Groupe présent --> Selectionne la figure / le groupe
            System.out.printf("\t label: %s\n",shapeClicked.getLabel());
            if (this.selectGroupe.equals(shapeClicked)){
                System.out.printf("\t REMOVE\n");
                remote.addCommand(new Remove(shapeClicked.shapeSelect(evt.getX(),evt.getY()),this.selectGroupe,remote));
            }
            else {
                System.out.printf("\t ADD\n");
                remote.addCommand(new Add(shapeClicked,this.selectGroupe,remote));
            }

        }

        m_panel.removeAll();
        remote.play();

        System.out.println("----Shapes :"+ this.listeShapes.size()+"----");
        System.out.println("Remove L: " + this.listeShapes);
        System.out.println("Remove G: " + this.selectGroupe);
        System.out.println("\t" + this.selectGroupe.getShapes());
        System.out.println("-----------------------------------------");
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

    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to initiate
     * shape dragging.
     *
     * @param evt The associated mouse event.
     **/
    public void mousePressed(MouseEvent evt) {
        System.out.println("-----------------------------------------");
        System.out.printf("EVENT [Press (%d,%d)]\n",evt.getX(),evt.getY());
        if (true){
            this.shapeDragged = whoWasClicked(this.listeShapes, evt.getX(), evt.getY());
            this.shapeDraggedInGroupe = this.shapeDragged.shapeSelect(evt.getX(),evt.getY());
            if (this.shapeDragged != null) {
                this.shapeDraggedXOrigine = this.shapeDragged.getX();
                this.shapeDraggedYOrigine = this.shapeDragged.getY();
                System.out.println("\tOriginal " + this.shapeDraggedXOrigine + " " + this.shapeDraggedYOrigine);
            }
        }
        System.out.println("-----------------------------------------");
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to complete
     * shape dragging.
     *
     * @param evt The associated mouse event.
     **/
    public void mouseReleased(MouseEvent evt) {
        System.out.println("-----------------------------------------");
        System.out.printf("EVENT [Release (%d,%d)]\n",evt.getX(),evt.getY());
        if (this.shapeDragged != null && this.shapeWasMove) {
            remote.addCommand(new Move(this.shapeDragged, evt.getX() - 25, evt.getY() - 25));
            this.shapeDragged = null;
            this.m_panel.removeAll();
            remote.play();
            repaint();
            this.shapeWasMove = false;
        }
        System.out.println("-----------------------------------------");
    }

    /**
     * Implements method for the <tt>MouseMotionListener</tt> interface to
     * move a dragged shape.
     *
     * @param evt The associated mouse event.
     **/
    public void mouseDragged(MouseEvent evt) {
        System.out.println("-----------------------------------------");
        System.out.printf("EVENT [DRAG (%d,%d)]\n",evt.getX(),evt.getY());
        if (this.shapeDragged != null){
            this.shapeWasMove = true;
            int deltaX = evt.getX() - this.shapeDraggedInGroupe.getX();
            int deltaY = evt.getY() - this.shapeDraggedInGroupe.getY();
            System.out.println("\tSouris X: " + evt.getX() + " Y: " + evt.getY());
            System.out.println("\tFig X: " + this.shapeDragged.getX() + " Y: " + this.shapeDragged.getY());
            System.out.println("\tDelta X: " + deltaX + " Y: " + deltaY);
            this.shapeDragged.deplace(deltaX,deltaY);
        }
        System.out.println("-----------------------------------------");
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