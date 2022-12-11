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
import java.util.Objects;

import edu.uga.miage.m1.polygons.gui.visitor.Visitable;
import edu.uga.miage.m1.polygons.gui.visitor.Visitor;

import javax.swing.*;

/**
 * This class implements the square <tt>SimpleShape</tt> extension.
 * It simply provides a <tt>draw()</tt> that paints a square.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class Square implements SimpleShape, Visitable {
    int m_x;
    int m_y;
    JLabel label;
    ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("../images/icones/square.png")));

    public Square(int x, int y) {
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
        label.setBounds(getX(), getY(), size.width, size.height);
        this.label.setLocation(getX(),getY());
    }

    @Override
    public void accept(Visitor visitor) {
    	visitor.visit(this);
    }

    @Override
    public int getX() {
        return m_x;
    }
    @Override
    public void setX(int x){
        this.m_x = x;
    }
    @Override
    public int getY() {
        return m_y;
    }
    @Override
    public void setY(int Y){
        this.m_y = Y;
    }

    @Override
    public void move(int x,int y){
        this.label.setLocation(x,y);
        setX(x);
        setY(y);
    }

    @Override
    public boolean isSelect(int coordX, int coordY) {
        return (
                (coordX >= this.getX()) &&
                (coordX <= this.getX() + 50) &&
                (coordY >= this.getY()) &&
                (coordY <= this.getY() + 50)
                );
    }

    @Override
    public SimpleShape shapeSelect(int x, int y){
        return this;
    }
}