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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import edu.uga.miage.m1.polygons.gui.persistence.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

/**
 * This inner class implements the triangle <tt>SimpleShape</tt> service.
 * It simply provides a <tt>draw()</tt> that paints a triangle.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class Triangle implements SimpleShape, Visitable {

    int m_x;

    int m_y;

    public Triangle(int x, int y) {
        m_x = x - 25;
        m_y = y - 25;
    }

    /**
     * Implements the <tt>SimpleShape.draw()</tt> method for painting
     * the shape.
     * @param g2 The graphics object used for painting.
     */
    public void draw(Graphics2D g2){
        this.draw(g2,false);
    }
    public void draw(Graphics2D g2,boolean estDansGroupe) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint(m_x, m_y, Color.GREEN, m_x + 50, m_y, Color.WHITE);
        g2.setPaint(gradient);
        int[] xcoords = { m_x + 25, m_x, m_x + 50 };
        int[] ycoords = { m_y, m_y + 50, m_y + 50 };
        GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, xcoords.length);
        polygon.moveTo(m_x + 25, m_y);
        for (int i = 0; i < xcoords.length; i++) {
            polygon.lineTo(xcoords[i], ycoords[i]);
        }
        polygon.closePath();
        g2.fill(polygon);
        BasicStroke wideStroke = new BasicStroke(4.0f);
        Color bordure = (estDansGroupe) ? Color.red : Color.black;
        g2.setColor(bordure);
        g2.setStroke(wideStroke);
        g2.draw(polygon);
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
    @Override
    public void move(int deltaX,int deltaY){
        this.setX(this.m_x + deltaX);
        this.setY(this.m_y + deltaY);
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
