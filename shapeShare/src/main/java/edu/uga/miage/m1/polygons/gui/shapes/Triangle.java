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
package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.uga.miage.m1.polygons.gui.visitor.Visitor;

import javax.swing.*;

/**
 * This inner class implements the triangle <tt>SimpleShape</tt> service.
 * It simply provides a <tt>draw()</tt> that paints a triangle.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class Triangle implements SimpleShape {

    int m_x;
    int m_y;
    JLabel label;
    ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/icones/triangle.png")));

    public Triangle(int x, int y) {
        m_x = x - 25;
        m_y = y - 25;
    }

    /**
     * Implements the <tt>SimpleShape.draw()</tt> method for painting
     * the shape.
     * @param g2 The graphics object used for painting.
     */
    public void draw(JPanel m_panel){
        this.draw(m_panel,false);
    }
    public void draw(JPanel m_panel,boolean estDansGroupe) {
        this.label = new JLabel(icon);
        m_panel.add(this.label);
        Dimension size = label.getPreferredSize();
        if (estDansGroupe){
            this.label.setBorder( BorderFactory.createLineBorder(Color.RED) );
        }
        label.setBounds(getX()-25, getY()-25, size.width, size.height);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getX() {
        //System.out.println("->X "+ (m_x+25));
        return m_x + 25;
    }
    @Override
    public void setX(int x){
        this.m_x = x;
    }
    @Override
    public int getY() {
        //System.out.println("->Y "+ (m_y+25));
        return m_y + 25;
    }
    @Override
    public void setY(int Y){
        this.m_y = Y;
    }

    //Retourne le sommet haut
    public List<Integer> getA (){
        List<Integer> coord = new ArrayList<Integer>();
        coord.add(this.getX());
        coord.add(this.getY() - 25);
        return coord;
    }
    public int getAx(){
        return this.getA().get(0);
    }
    public int getAy(){
        return this.getA().get(1);
    }

    //Retourne le sommet gauche
    public List<Integer> getB (){
        List<Integer> coord = new ArrayList<Integer>();
        coord.add(this.getX() - 25);
        coord.add(this.getY() + 25);
        return coord;
    }
    public int getBx(){
        return this.getB().get(0);
    }
    public int getBy(){
        return this.getB().get(1);
    }

    //Retourne le sommet droite
    public List<Integer> getC (){
        List<Integer> coord = new ArrayList<Integer>();
        coord.add(this.getX() + 25);
        coord.add(this.getY() + 25);
        return coord;
    }
    public int getCx(){
        return this.getC().get(0);
    }
    public int getCy(){
        return this.getC().get(1);
    }

    public void select(Color couleur){
        this.label.setBorder( BorderFactory.createLineBorder(couleur,4));
    }

    @Override
    public void move(int x,int y) {
        this.label.setLocation(x,y);
        this.setX(x);
        this.setY(y);
    }

    @Override
    public void deplace(int deltaX, int deltaY){
        int x = this.m_x + deltaX;
        int y = this.m_y + deltaY;
        this.label.setLocation(x,y);
        this.setX(x);
        this.setY(y);
    }

    public JLabel getLabel(){
        return this.label;
    }

    @Override
    public boolean isSelect(int coordX, int coordY){
        /*
        System.out.println("---");
        System.out.println("->(1)"+ ( ( (getAx()) - (coordX) ) * ( (getBy()) - (coordY)) - ( (getAy()) - (coordY)) * ( (getBx()) - (coordX))));
        System.out.println("->(2)"+ ( ( (getBx()) - (coordX) ) * ( (getCy()) - (coordY)) - ( (getBy()) - (coordY)) * ( (getCx()) - (coordX))));
        System.out.println("->(3)"+ ( ( (getCx()) - (coordX) ) * ( (getAy()) - (coordY)) - ( (getCy()) - (coordY)) * ( (getAx()) - (coordX))));
        System.out.println("---");
        */

        return (
               ( ( (getAx()) - (coordX) ) * ( (getBy()) - (coordY)) - ( (getAy()) - (coordY)) * ( (getBx()) - (coordX))) < 0 &&
               ( ( (getBx()) - (coordX) ) * ( (getCy()) - (coordY)) - ( (getBy()) - (coordY)) * ( (getCx()) - (coordX))) < 0 &&
               ( ( (getCx()) - (coordX) ) * ( (getAy()) - (coordY)) - ( (getCy()) - (coordY)) * ( (getAx()) - (coordX))) < 0
        );
    }

    @Override
    public SimpleShape shapeSelect(int x, int y){
        return this;
    }
}
